package C45Hadoop.datatype;

/**
 * 与决策树中某个节点、属性相关联的一个决策树路径的统计记录
 */
public class StatisticRecord {
    public Integer nid;// 与之关联的决策树中节点的ID
    public Integer aid;// 属性ID
    public String avalue;// 属性取值
    public String label;// 类标号
    public int count;// 条件与该路径相符合的训练集元组个数

    public StatisticRecord(Integer nid, Integer aid, String avalue, String label,
                           int count) {
        this.nid = nid;
        this.aid = aid;
        this.avalue = avalue;
        this.label = label;
        this.count = count;
    }

    public String toString() {
        return this.nid + "," + this.aid + "," + this.avalue + "," + this.label
                + "\\t" + count;
    }
}
