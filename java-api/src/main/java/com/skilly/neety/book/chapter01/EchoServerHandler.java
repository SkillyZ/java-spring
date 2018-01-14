package com.skilly.neety.book.chapter01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by 1254109699@qq.com on 2018/1/15.
 * document 1.@Sharable 标识这类的实例之间可以在 channel 里面共享

             2.日志消息输出到控制台

             3.将所接收的消息返回给发送者。注意，这还没有冲刷数据

             4.冲刷所有待审消息到远程节点。关闭通道后，操作完成

             5.打印异常堆栈跟踪

             6.关闭通道
 */
@ChannelHandler.Sharable //1
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * channelRead() - 每个信息入站都会调用
     * channelReadComplete() - 通知处理器最后的 channelread() 是当前批处理中的最后一条消息时调用
     * exceptionCaught()- 读操作时捕获到异常时调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("channelRead :  Server received: " + in.toString(CharsetUtil.UTF_8));        //2
        ctx.write(in);                            // 3
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//4
                .addListener(ChannelFutureListener.CLOSE);
        System.out.println("channelReadComplete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();                //5
        ctx.close();                            //6
        System.out.println("exceptionCaught");
    }
}