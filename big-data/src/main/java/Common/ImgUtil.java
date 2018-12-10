package Common;

import KMeans.XYbean;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;


/**
 * 图片处理工具类
 *
 * @author Daven
 */
public class ImgUtil {


    public static void main(String[] args) {
        ImgUtil.composePic("D:/back.png", "http://t3.qlogo.cn/mbloghead/d387538f629cfb53c2b0/100", "D:/output.png", 53, 161);
    }

    /**
     * 合成图片(类似图片水印)
     *
     * @param backImage  背景图片对象
     * @param headImage  用户头像对象
     * @param outPutPath 输出路径（D：/img/out.png）
     * @param leftX      距离目标图片左侧的偏移量
     * @param leftY      距离目标图片左侧的偏移量
     * @throws InterruptedException
     * @throws IOException
     */
    public static void composePic(Image backImage, Image headImage,
                                  String outPutPath, int leftX, int leftY)
            throws InterruptedException, IOException {

        // 图片的高/宽度
        int bwidth = backImage.getWidth(null);
        int bheight = backImage.getHeight(null);
        int hwidth = headImage.getWidth(null);
        int hheight = headImage.getHeight(null);

        int alphaType = BufferedImage.TYPE_INT_RGB;
			/*if (hasAlpha(backImage)) {
				alphaType = BufferedImage.TYPE_INT_ARGB;
			}*/

        // 画图
        BufferedImage backgroundImage = new BufferedImage(bwidth, bheight, alphaType);
        Graphics2D graphics2D = backgroundImage.createGraphics();
        graphics2D.drawImage(backImage, 0, 0, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
        graphics2D.drawImage(headImage, leftX, leftY, hwidth, hheight, null);

        // 输出
        ImageIO.write(backgroundImage, "png", new File(outPutPath));

    }

    //        new ImgUtil().drawXYbeans(width, height, cresult, "d:/2.png", 0, 0);
    public static void drawXYbeans(int width, int height, List<List<XYbean>> data, String path) throws IOException {

        int alphaType = BufferedImage.TYPE_INT_RGB;
        BufferedImage backgroundImage = new BufferedImage(width, height, alphaType);
        Graphics2D graphics2D = backgroundImage.createGraphics();

        for (List<XYbean> beanList : data) {
            for (XYbean bean : beanList) {
                graphics2D.drawString("0", bean.getX(), bean.getY());
            }
        }

        // 输出
        ImageIO.write(backgroundImage, "png", new File(path));
    }

    /**
     * 合成图片
     *
     * @param backPicPath 背景图片本地路径
     * @param headPicUrl  用户头像网络url
     * @param outPutPath  输出路径（D：/img/out.png）
     * @param leftX       距离目标图片左侧的偏移量
     * @param leftY       距离目标图片左侧的偏移量
     */
    public static void composePic(String backPicPath, String headPicUrl,
                                  String outPutPath, int leftX, int leftY) {
        try {
            // 读取背景图片
            Image backImage = ImgUtil.loadImageLocal(backPicPath);

            // 加昵称
            int ln = ImgUtil.getLength("深水鱼的马甲") * 24;
            backImage = ImgUtil.pressText("深水鱼的马甲", backImage, "微软雅黑", Font.BOLD,
                    ImgUtil.String2Color("#FFFFFF"), 24, 175 + (266 - ln) / 2, 188, 1);
            // 得分
            backImage = ImgUtil.pressText("100分", backImage, "华康海报体W12(P)", Font.LAYOUT_NO_LIMIT_CONTEXT,
                    ImgUtil.String2Color("#FD6E10"), 22, 280, 233, 1);
            // 收益
            backImage = ImgUtil.pressText("85%", backImage, "迷你简凌波", Font.LAYOUT_NO_LIMIT_CONTEXT,
                    ImgUtil.String2Color("#E60000"), 24, 368, 264, 1);
            // 称号
            int lc = ImgUtil.getLength("有股神潜力，万众膜拜！") * 32;
            backImage = ImgUtil.pressText("有股神潜力，万众膜拜！", backImage, "华康海报体W12(P)", Font.LAYOUT_NO_LIMIT_CONTEXT,
                    ImgUtil.String2Color("#880000"), 32, (488 - lc) / 2, 310, 1);
            // 读取头像图片
            Image headImage = ImgUtil.loadImageUrl(headPicUrl);
            // 图像
            ImgUtil.composePic(backImage, headImage, outPutPath, leftX, leftY);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片上加文字（文字水印）
     *
     * @param pressText 水印文字
     * @param targetImg 目标图片
     * @param fontName  字体名称
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param color     字体颜色
     * @param fontSize  字体大小
     * @param x         水印文字距离目标图片左侧的偏移量
     * @param y         水印文字距离目标图片上侧的偏移量
     * @param alpha     透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     * @throws IOException
     */
    public static BufferedImage pressText(String pressText, Image targetImg, String fontName, int fontStyle,
                                          Color color, int fontSize, int x, int y, float alpha) throws IOException {

        // 图片宽高
        int width = targetImg.getWidth(null);
        int height = targetImg.getHeight(null);
        //样式
        Font font = new Font(fontName, fontStyle, fontSize);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(targetImg, 0, 0, width, height, null);
        g.setColor(color);// 颜色
        g.setFont(font);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g.drawString(pressText, x, y);
        g.dispose();

        return image;
    }

    /**
     * 是否开启alpha通道
     *
     * @param image
     * @return
     * @throws InterruptedException
     */
    public static boolean hasAlpha(Image image)
            throws InterruptedException {

        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }

        PixelGrabber pGrabber = new PixelGrabber(image, 0, 0, 1, 1, false);
        pGrabber.grabPixels();
        ColorModel colorModel = pGrabber.getColorModel();

        return colorModel.hasAlpha();
    }

    /**
     * 导入本地图片到缓冲区
     *
     * @param imgPath 本地的图片地址
     * @return
     * @throws IOException
     */
    public static BufferedImage loadImageLocal(String imgPath)
            throws IOException {
        return ImageIO.read(new File(imgPath));
    }

    /**
     * 导入网络图片到缓冲区
     *
     * @param imgUrl 网络图片url
     * @return
     * @throws IOException
     */
    public static BufferedImage loadImageUrl(String imgUrl)
            throws IOException {
        URL url = new URL(imgUrl);
        return ImageIO.read(url);
    }

    /**
     * 16进制转Color对象
     *
     * @param str
     * @return
     */
    public static Color String2Color(String str) {
        int i = Integer.parseInt(str.substring(1), 16);
        return new Color(i);
    }

    /**
     * Color对象转16进制
     *
     * @param color
     * @return
     */
    public static String Color2String(Color color) {
        String R = Integer.toHexString(color.getRed());
        R = R.length() < 2 ? ('0' + R) : R;
        String B = Integer.toHexString(color.getBlue());
        B = B.length() < 2 ? ('0' + B) : B;
        String G = Integer.toHexString(color.getGreen());
        G = G.length() < 2 ? ('0' + G) : G;
        return '#' + R + B + G;
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     *
     * @param text
     * @return 字符长度，如：text="中国",返回 2
     * text="test",返回 2
     * text="中国ABC",返回 4
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }


}
