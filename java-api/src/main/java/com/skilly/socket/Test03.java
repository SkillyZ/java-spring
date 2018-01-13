package com.skilly.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * 使用URL读取网页内容
 */
public class Test03 {
	public static void main(String[] args) {
		try {
			//创建一个URL实例
			URL url = new URL("http://www.baidu.com");
			//通过URL的openStream方法获取URL对象所表示的资源的字节输入流
			InputStream is = url.openStream();
			//将字节输入流转换为字符输入流
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			//为字符输入流添加缓冲
			BufferedReader br = new BufferedReader(isr);
			String data = br.readLine();//读取数据
			while (data != null) {//循环读取数据
				System.out.println(data);//输出数据
				data = br.readLine();
			}
			br.close();
			isr.close();
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
