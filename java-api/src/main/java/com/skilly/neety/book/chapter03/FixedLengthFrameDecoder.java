package com.skilly.neety.book.chapter03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/17.
 * 1 继承 ByteToMessageDecoder 用来处理入站的字节并将他们解码为消息
 * 2 指定产出的帧的长度
 * 3 检查是否有足够的字节用于读到下个帧
 * 4 从 ByteBuf 读取新帧
 * 5 添加帧到解码好的消息 List
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder { //1

    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) { //2
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= frameLength) { //3
            ByteBuf buf = in.readBytes(frameLength);//4
            out.add(buf); //5
        }
    }
}