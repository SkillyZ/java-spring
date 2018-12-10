package com.skilly.neety.book.chapter02;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by ${1254109699@qq.com} on 2018/1/15.
 * document 1.绑定服务器到指定的端口。
 * <p>
 * 2.接受一个连接。
 * <p>
 * 3.创建一个新的线程来处理连接。
 * <p>
 * 4.将消息发送到连接的客户端。
 * <p>
 * 5.一旦消息被写入和刷新时就 关闭连接。
 * <p>
 * 6.启动线程。
 */
public class PlainOioServer {

    public void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);     //1
        try {
            for (; ; ) {
                final Socket clientSocket = socket.accept();    //2
                System.out.println("Accepted connection from " + clientSocket);

                new Thread(new Runnable() {                        //3
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));                            //4
                            out.flush();
                            clientSocket.close();                //5

                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                // ignore on close
                            }
                        }
                    }
                }).start();                                        //6
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}