package C452;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by 1254109699@qq.com on 2018/7/1.
 * 信息增益相关类
 */
public class InfoGain {
    private ArrayList<String[]> trainData;
    private int decatt;

    public InfoGain(ArrayList<String[]> trainData, int decatt) {
        this.trainData = trainData;
        this.decatt = decatt;
    }


    /**
     * 计算信息熵
     */
    public double getEntropy(Map<String, Integer> attributeNum) {
        double entropy = 0.0;
        int sum = 0;
        for (int num : attributeNum.values()) {
            sum += num;
            entropy += (-1) * num * Math.log(num + Double.MIN_VALUE) / Math.log(2); //避免log1
        }
        entropy += sum * Math.log(sum + Double.MIN_VALUE) / Math.log(2);
        entropy /= sum;
        return entropy;
    }

    public double getEntropy(ArrayList<Integer> subset, int attributeIndex) {
        Map<String, Integer> attributeNum = get_AttributeNum(subset, attributeIndex);
        double entropy = getEntropy(attributeNum);
        return entropy;
    }


    //信息熵增益率相关
    public int getGainRatioMax(ArrayList<Integer> subset, LinkedList<Integer> selatt) {
        //计算原信息熵

        Map<String, Integer> old_TargetNum = get_AttributeNum(subset, decatt);
        double oldEntropy = getEntropy(old_TargetNum);
        double maxGainRatio = 0;
        int maxIndex = decatt;

        for (int attributeIndex : selatt) {
            Map<String, ArrayList<Integer>> attributeSubset = get_AttributeSubset(subset, attributeIndex);

            int sum = 0;
            double newEntropy = 0;
            for (ArrayList<Integer> tempSubset : attributeSubset.values()) {
                int num = tempSubset.size();
                sum += num;
                double tempEntropy = getEntropy(tempSubset, decatt);
                newEntropy += num * tempEntropy;
            }
            newEntropy /= sum;
            double tempGainRatio = (oldEntropy - newEntropy) / getEntropy(subset, attributeIndex);  //计算信息增益率

            //如果信息增益率为负，应该停止分支，此处避免麻烦没有做进一步讨论。
            if (tempGainRatio > maxGainRatio) {
                maxGainRatio = tempGainRatio;
                maxIndex = attributeIndex;
            }
        }
        return maxIndex;
    }

    /**
     * 判断分类是否唯一
     *
     * @param targetNum 各类数目的map
     * @return 分类是否唯一标识
     */
    public boolean isPure(Map<String, Integer> targetNum) {
        if (targetNum.size() > 1) {
            return false;
        }
        return true;
    }

    /**
     * 获得对应数据子集的对应特征的值-频率字典
     *
     * @param subset         子集行数
     * @param attributeIndex 特征列
     * @return
     */
    public Map<String, Integer> get_AttributeNum(ArrayList<Integer> subset, int attributeIndex) {
        Map<String, Integer> attributeNum = new HashMap<String, Integer>();
        for (int subsetIndex : subset) {
            String value = trainData.get(subsetIndex)[attributeIndex];
            Integer count = attributeNum.get(value);//int无法使用count!=null
            attributeNum.put(value, count != null ? ++count : 1);
        }
        return attributeNum;
    }

    /**
     * 获得数据在某一特征维度下的子集划分
     *
     * @param subset         原子集
     * @param attributeIndex 特征序号
     * @return 子集划分map
     */
    public Map<String, ArrayList<Integer>> get_AttributeSubset(ArrayList<Integer> subset, int attributeIndex) {
        Map<String, ArrayList<Integer>> attributeSubset = new HashMap<String, ArrayList<Integer>>();
        for (int subsetIndex : subset) {
            String value = trainData.get(subsetIndex)[attributeIndex];
            ArrayList<Integer> tempSubset = attributeSubset.get(value);
            if (tempSubset != null) {
                tempSubset.add(subsetIndex);
            } else {
                tempSubset = new ArrayList<Integer>();
                tempSubset.add(subsetIndex);
            }
            attributeSubset.put(value, tempSubset);
        }
        return attributeSubset;
    }

    /**
     * 根据类-数目，判读分类结果
     *
     * @param targetNum
     * @return
     */
    public String get_targetValue(Map<String, Integer> targetNum) {

        int maxNum = 0;
        String targetValue = "";
        for (String key : targetNum.keySet()) {
            int tempNum = targetNum.get(key);
            if (tempNum > maxNum) {
                maxNum = tempNum;
                targetValue = key;
            }
        }
        return targetValue;
    }
}