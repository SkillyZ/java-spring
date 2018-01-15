package com.skilly.neety.book.chapter01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by 1254109699@qq.com on 2018/1/15.
 * document
 * 1.@Sharable标记这个类的实例可以在 channel 里共享

     2.当被通知该 channel 是活动的时候就发送信息

     3.记录接收到的消息

     4.记录日志错误并关闭 channel
 */
@ChannelHandler.Sharable                                //1
public class EchoClientHandler extends
        SimpleChannelInboundHandler<ByteBuf> {

    /**
     * channelActive() - 服务器的连接被建立后调用
     * channelRead0() - 数据后从服务器接收到调用
     * exceptionCaught() - 捕获一个异常时调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", //2
                CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             ByteBuf in) {
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));    //3
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {                    //4
        cause.printStackTrace();
        ctx.close();
    }
}