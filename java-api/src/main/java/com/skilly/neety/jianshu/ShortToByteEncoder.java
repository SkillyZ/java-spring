package com.skilly.neety.jianshu;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by 1254109699@qq.com on 2018/1/15.
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short> {  //1

    @Override
    public void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out)
            throws Exception {
        out.writeShort(msg);  //2
    }
}
