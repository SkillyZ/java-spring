package com.skilly.neety;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

/**
 * Created by 1254109699@qq.com on 2018/1/14.
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> { // 1

    //Handler需要声明泛型为<FullHttpRequest>，声明之后，只有msg为FullHttpRequest的消息才能进来。
    //由于泛型的过滤比较简单，我们就不改代码来验证了，但是在这里我们可以利用泛型的特性另外做个小测试，将泛型去掉，并且将HttpServer中.addLast("aggregator", new HttpObjectAggregator(512 * 1024)) // 3这一行代码注释掉，然后观察注释前后的log。
    //注释前：

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("class:" + msg.getClass().getName());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer("test".getBytes())); // 2

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if(null != cause)
        {
            cause.printStackTrace();
        }
        if(null != ctx) {
            ctx.close();
        }
    }
}
