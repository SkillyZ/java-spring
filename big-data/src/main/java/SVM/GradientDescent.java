package SVM;


import java.text.DecimalFormat;

/**
 * 功能：梯度下降算法，求解 f(x)=x^4-3x^3+2 最小值
 * 导数为: f'(x)=4x^3-9x^2
 */
public class GradientDescent {
    static double y_cur = 0;//用于每次迭代后的值记录，循环终止时就是最小值
    static double x = 6; // 从 x=6开始迭代
    double step = 0.01; // 每次迭代的步长
    double precision = 0.00001;//误差
    static int iter = 0;//迭代次数

    //目标函数的导数
    private double  derivative(double x) {//导数
        return 4 * Math.pow(x, 3) - 9 *Math.pow(x, 2);
    }
    //目标函数，要求解最下值
    private double function(double x){//函数
        return Math.pow(x, 4)-3 * Math.pow(x, 3)+2;
    }

    private void getmin() {
        y_cur=function(x);
        double y_div=function(x);//初始y值
        while (y_div > precision){//下降梯度的幅度变化大于误差，继续迭代
            //System.out.println("当前y="+y_cur+",x="+x);
            x=x-step*derivative(x);//沿梯度负方向移动
            y_div=y_cur-function(x);//移动后计算y的变化幅度值
            y_cur=function(x);	//y值跟着x移动变化，计算下一轮迭代
            iter++;
        }
    }

    public static void main(String[] args) {
        GradientDescent gd = new GradientDescent();
        gd.getmin();
        DecimalFormat df=new DecimalFormat("#,##0.00");//格式化设置
        System.out.println("迭代"+iter+"次，函数最小值："+df.format(y_cur)+"，对应的x值："+df.format(x));
    }

}
