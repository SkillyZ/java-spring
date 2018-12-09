package PageRank;

import java.util.LinkedList;

public class Internet {

    private LinkedList<WebPage> linkList = new LinkedList<WebPage>();

    /**
     * 创建时自动init()，创建A-E五个webpage节点。
     */
    public Internet() {
        init();
    }

    /**
     * 创建webpage节点和节点间链接。
     */
    public void init() {
        WebPage wpA = new WebPage("A");
        WebPage wpB = new WebPage("B");
        WebPage wpC = new WebPage("C");
        WebPage wpD = new WebPage("D");
        WebPage wpE = new WebPage("E");

        wpA.addLink(wpB);
        wpB.addLink(wpE);
        wpC.addLink(wpA);
        wpD.addLink(wpA);
        wpE.addLink(wpA);

        linkList.add(wpA);
        linkList.add(wpB);
        linkList.add(wpC);
        linkList.add(wpD);
        linkList.add(wpE);

    }

    public int linkListSize() {
        return linkList.size();
    }

    public LinkedList<WebPage> getLinkList() {
        return linkList;
    }

    public WebPage getWebPage(int index) {
        return linkList.get(index);
    }


}
