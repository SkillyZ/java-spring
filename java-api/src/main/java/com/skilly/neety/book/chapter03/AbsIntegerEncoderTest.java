package com.skilly.neety.book.chapter03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ${1254109699@qq.com} on 2018/1/17.
 * 1 用 @Test 标记
     2 新建 ByteBuf 并写入负整数
     3 新建 EmbeddedChannel 并安装 AbsIntegerEncoder 来测试
     4 写 ByteBuf 并预测 readOutbound() 产生的数据
     5 标记 channel 已经完成
     6 读取产生到的消息，检查负值已经编码为绝对值
 *
 */
public class AbsIntegerEncoderTest {

    @Test   //1
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();  //2
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());  //3
        Assert.assertTrue(channel.writeOutbound(buf)); //4

        Assert.assertTrue(channel.finish()); //5
        for (int i = 1; i < 10; i++) {
            Assert.assertEquals(i, channel.readOutbound());  //6
        }
        Assert.assertNull(channel.readOutbound());
    }
}