package C452;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.lang.Math.*;


/**
 * Created by 1254109699@qq.com on 2018/7/1.
 */
public class DecisionTree {

    private ArrayList<String> train_AttributeName = new ArrayList<String>(); // 存储训练集属性的名称
    private ArrayList<ArrayList<String>> train_attributeValue = new ArrayList<ArrayList<String>>(); // 存储训练集每个属性的取值
    private ArrayList<String[]> trainData = new ArrayList<String[]>(); // 训练集数据 ，即arff文件中的data字符串

    public static final String patternString = "@attribute(.*)[{](.*?)[}]";
    //正则表达，其中*? 表示重复任意次，但尽可能少重复，防止匹配到更后面的"}"符号

    private int decatt; // 决策变量在属性集中的索引(即类标所在列)
    private InfoGain infoGain;
    private TreeNode root;


    public void train(String data_path, String targetAttr) {
        //模型初始化操作
        read_trainARFF(new File(data_path));
        //printData();
        setDec(targetAttr);
        infoGain = new InfoGain(trainData, decatt);

        //拼装行与列
        LinkedList<Integer> ll = new LinkedList<Integer>(); //LinkList用于增删比ArrayList有优势
        for (int i = 0; i < train_AttributeName.size(); i++) {
            if (i != decatt) ll.add(i);  //防止类别变量不在最后一列发生错误
        }
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i = 0; i < trainData.size(); i++) {
            al.add(i);
        }

        //构建决策树
        root = buildDT("root", "null", al, ll);
        //剪枝
        cutBranch(root);
    }

    /**
     * 构建决策树
     *
     * @param fatherName  节点名称
     * @param fatherValue 节点值
     * @param subset      数据行子集
     * @param subset      数据列子集
     * @return 返回根节点
     */
    public TreeNode buildDT(String fatherName, String fatherValue, ArrayList<Integer> subset, LinkedList<Integer> selatt) {
        TreeNode node = new TreeNode();
        Map<String, Integer> targetNum = infoGain.get_AttributeNum(subset, decatt);//计算类-频率
        String targetValue = infoGain.get_targetValue(targetNum);//判定分类
        node.setTargetNum(targetNum);
        node.setAttributeName(fatherName);
        node.setAttributeValue(fatherValue);
        node.setTargetValue(targetValue);

        //终止条件为类标单一/树深度达到特征长度（还有可能是信息增益率不存在）
        if (infoGain.isPure(targetNum) | selatt.isEmpty()) {
            node.setNodeType("leafNode");
            return node;
        }
        int maxIndex = infoGain.getGainRatioMax(subset, selatt);
        selatt.remove(new Integer(maxIndex));  //这样可以remove object
        String childName = train_AttributeName.get(maxIndex);

        Map<String, ArrayList<Integer>> childSubset = infoGain.get_AttributeSubset(subset, maxIndex);
        ArrayList<TreeNode> childNode = new ArrayList<TreeNode>();
        for (String childValue : childSubset.keySet()) {
            TreeNode child = buildDT(childName, childValue, childSubset.get(childValue), selatt);
            child.setFatherTreeNode(node);  //顺序很重要：回溯
            childNode.add(child);
        }
        node.setChildTreeNode(childNode);
        return node;
    }

    /**
     * 剪枝函数
     *
     * @param node 判断结点
     * @return 剪枝之后的叶子结点集
     */
    public ArrayList<int[]> cutBranch(TreeNode node) {
        ArrayList<int[]> resultNum = new ArrayList<int[]>();
        if (node.getNodeType() == "leafNode") {
            int[] tempNum = get_leafNum(node);
            resultNum.add(tempNum);
            return resultNum;
        } else {
            int sumNum = 0;
            double oldRatio = 0;
            for (TreeNode child : node.getChildTreeNode()) {
                for (int[] leafNum : cutBranch(child)) {
                    resultNum.add(leafNum);
                    oldRatio += 0.5 + leafNum[0];
                    sumNum += leafNum[1];
                }
            }
            double oldNum = oldRatio;
            oldRatio /= sumNum;
            double sd = Math.sqrt(sumNum * oldRatio * (1 - oldRatio));
            int temLeaf[] = get_leafNum(node);
            double newNum = temLeaf[0] + 0.5;
            if (newNum < oldNum + sd) {//符合剪枝条件，剪枝并返回本身
                node.setChildTreeNode(null);
                node.setNodeType("leafNode");
                resultNum.clear();
                resultNum.add(temLeaf);
            }//不符合剪枝条件，返回叶子结点
            return resultNum;
        }
    }

    //获得叶子结点的数目
    public int[] get_leafNum(TreeNode node) {
        int[] resultNum = new int[2];
        Map<String, Integer> targetNum = node.getTargetNum();
        int minNum = Integer.MAX_VALUE;
        int sumNum = 0;
        for (int num : targetNum.values()) {
            minNum = Integer.min(minNum, num);
            sumNum += num;
        }
        if (targetNum.size() == 1) minNum = 0;
        resultNum[0] = minNum;
        resultNum[1] = sumNum;
        return resultNum;
    }

    /**
     * 读取arff文件，给attribute、attributevalue、data赋值
     *
     * @param file 传入的文件
     */
    public void read_trainARFF(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Pattern pattern = Pattern.compile(patternString);
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    train_AttributeName.add(matcher.group(1).trim()); //获取第一个括号里的内容
                    //涉及取值，尽量加.trim()，后面也可以看到，即使是换行符也可能会造成字符串不相等
                    String[] values = matcher.group(2).split(",");
                    ArrayList<String> al = new ArrayList<String>(values.length);
                    for (String value : values) {
                        al.add(value.trim());
                    }
                    train_attributeValue.add(al);
                } else if (line.startsWith("@data")) {
                    while ((line = br.readLine()) != null) {
                        if (line == "")
                            continue;
                        String[] row = line.split(",");
                        trainData.add(row);
                    }
                } else {
                    continue;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印Data
     */
    public void printData() {
        System.out.println("当前的ATTR为");
        for (String attr : train_AttributeName) {
            System.out.print(attr + " ");
        }
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("当前的DATA为");
        for (String[] row : trainData) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------");
    }

    //将决策树存储到xml文件中
    public void write_DecisionTree(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fs = new FileOutputStream(filename);
            BufferedOutputStream bos = new BufferedOutputStream(fs);
            write_Node(bos, root, "");
            bos.flush();
            bos.close();
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write_Node(BufferedOutputStream bos, TreeNode node, String block) {
        String outputWords1 = block + "<" + node.getAttributeName() + " value=\"" + node.getAttributeValue() + "\"";
        String outputWords2;
        Map<String, Integer> targetNum = node.getTargetNum();
        for (String value : targetNum.keySet()) {
            outputWords1 += " " + value + ":" + targetNum.get(value);
        }
        outputWords1 += ">";
        if (node.getNodeType() == "leafNode") {
            outputWords1 += node.getTargetValue();
            outputWords2 = "</" + node.getAttributeName() + ">" + "\n";
        } else {
            outputWords1 += "\n";
            outputWords2 = block + "</" + node.getAttributeName() + ">" + "\n";
        }

        try {
            bos.write(outputWords1.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<TreeNode> childNode = node.getChildTreeNode();
        if (childNode != null) {
            for (TreeNode child : childNode) {
                write_Node(bos, child, block + "  ");
            }
        }

        try {
            bos.write(outputWords2.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //设置决策变量
    public void setDec(int n) {
        if (n < 0 || n >= train_AttributeName.size()) {
            System.err.println("决策变量指定错误。");
            System.exit(2);
        }
        decatt = n;
    }

    public void setDec(String targetAttr) {
        int n = train_AttributeName.indexOf(targetAttr);
        setDec(n);
    }


    public static void main(String[] args) {
        DecisionTree dt = new DecisionTree();
        dt.train("files/train.arff", "play");
        dt.write_DecisionTree("files/Tree.xml");
    }

}