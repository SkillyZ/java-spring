package PageRank;

public class Person {

    private String name;
    private WebPage browserPage;

    /**
     * 需初始化Person的起始浏览节点browserPage。
     *
     * @param name
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * 初始化Person的起始浏览节点
     *
     * @param wp
     */
    public void init(WebPage wp) {
        setBrowserPage(wp);
    }

    public void visit(WebPage wp) {
        wp.addVisitCount();
        setBrowserPage(wp);
    }

    public WebPage getBrowserPage() {
        return browserPage;
    }

    public void setBrowserPage(WebPage wp) {
        this.browserPage = wp;
    }

    public String getName() {
        return this.name;
    }

}
