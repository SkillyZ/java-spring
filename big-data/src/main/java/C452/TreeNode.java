package C452;

import java.util.ArrayList;
import java.util.Map;

/**
 * 节点类
 * <p>
 * Created by 1254109699@qq.com on 2018/7/1.
 */
public class TreeNode {

    private String nodeType;
    private String attributeName;
    private String attributeValue;
    private ArrayList<TreeNode> childTreeNode;
    private TreeNode fatherTreeNode;
    private Map<String, Integer> targetNum;
    private String targetValue;
    //private List<String> pathName;


    public TreeNode() {
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public ArrayList<TreeNode> getChildTreeNode() {
        return childTreeNode;
    }

    public void setChildTreeNode(ArrayList<TreeNode> childTreeNode) {
        this.childTreeNode = childTreeNode;
    }

    public TreeNode getFatherTreeNode() {
        return fatherTreeNode;
    }

    public void setFatherTreeNode(TreeNode fatherTreeNode) {
        this.fatherTreeNode = fatherTreeNode;
    }

    public Map<String, Integer> getTargetNum() {
        return targetNum;
    }

    public void setTargetNum(Map<String, Integer> targetNum) {
        this.targetNum = targetNum;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }
}