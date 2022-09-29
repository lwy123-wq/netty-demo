package com.netty.json;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

public class JsonServer {
    public void runServer(){
        EventLoopGroup boss=new NioEventLoopGroup(1);
        EventLoopGroup work=new NioEventLoopGroup(1);
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(boss,work)
                //2. 设置nio类型的通道
                .channel(NioServerSocketChannel.class)
                //3. 设置监听端口
                .localAddress(9999)
                //4. 设置通道的参数
                .option(ChannelOption.SO_KEEPALIVE,true)
                //5. 装配子通道流水线
                //有连接到达时会创建一个通道
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //流水线的职责：负责管理通道中的处理器
                    //向“子通道”（传输通道）流水线添加一个处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //TODO
                        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
                        socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                        socketChannel.pipeline().addLast(new JsonMsgDecoder());

                    }
                });
        try {
            //6. 开始绑定服务器
            ChannelFuture channelFuture = bootstrap.bind().sync();
            //channelFuture.channel().localAddress();
            //7. 等待通道关闭的异步任务结束
            ChannelFuture closeFuture=channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
    static class JsonMsgDecoder extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String json= (String) msg;
            JsonMsg jsonMsg=JsonMsg.parseFromJson(json);
            System.out.println("收到一个 Json 数据包 =>>" + jsonMsg);
        }
    }

    public static void main(String[] args) {
        new JsonServer().runServer();
    }
}
