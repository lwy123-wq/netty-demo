package com.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;

@Slf4j
public class InPipeline {
    static class SimpleInHandlerA extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("入站处理器A被调回");
            //super.channelRead(ctx, msg);
        }
    }
    static class SimpleInHandlerB extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("入站处理器B被调回");
            //super.channelRead(ctx, msg);
        }
    }
    static class SimpleInHandlerC extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("入站处理器C被调回");
            //super.channelRead(ctx, msg);
        }
    }

    @Test
    public void testPipelineInbound(){
        ChannelInitializer initializer=new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(new SimpleInHandlerA());
                channel.pipeline().addLast(new SimpleInHandlerB());
                channel.pipeline().addLast(new SimpleInHandlerC());

            }
        };
        EmbeddedChannel channel=new EmbeddedChannel(initializer);
        ByteBuf byteBuf= Unpooled.buffer();
        byteBuf.writeInt(1);
        channel.writeInbound(byteBuf);
    }
}
