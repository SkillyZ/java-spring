package PageRank;

import java.util.Random;

/**
 * 随机访问者模型实现
 *
 */
public class VisitMain {

    private static int REPEAT_TRAIN_COUNT = 1000000;        //测试次数
    private static double RANDOM_RANGE = 0.15 * REPEAT_TRAIN_COUNT;        //随机访问范围区间

    public static void main(String[] argv) {
        System.out.println("Begin test pagerank...");

        Internet internet = new Internet();
        Person person = new Person("Skilly");
        Random random = new Random();

        //设定初始person的browserpage
        int index;
        index = random.nextInt(internet.linkListSize());
        person.init(internet.getWebPage(index));

        WebPage wp;
        for (int i = 0; i < REPEAT_TRAIN_COUNT; i++) {
            if (random.nextInt(REPEAT_TRAIN_COUNT) < RANDOM_RANGE) {        //取15%的几率随机访问其他网页
                wp = internet.getWebPage(random.nextInt(internet.linkListSize()));
//				System.out.println("Random jump into page:"+wp.getName());
            } else {
                index = random.nextInt(person.getBrowserPage().linkListSize());
                wp = person.getBrowserPage().getLink(index);
            }
            person.visit(wp);
        }

        for (WebPage page : internet.getLinkList()) {
            System.out.println("Visit WebPage " + page.getName() + " count is:" + page.getVisitCount() + "\t" + (double) 100 * page.getVisitCount() / REPEAT_TRAIN_COUNT + "%.");
        }

    }
}
