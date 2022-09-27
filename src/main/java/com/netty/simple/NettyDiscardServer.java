package com.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyDiscardServer {
    private final int serverPort;

    ServerBootstrap bootstrap=new ServerBootstrap();

    public NettyDiscardServer(int serverPort) {
        this.serverPort = serverPort;
    }
    public void runServer() {
        //创建反应器轮询组
        EventLoopGroup boss=new NioEventLoopGroup(1);
        EventLoopGroup work=new NioEventLoopGroup(1);
        //1. 设置反应器轮询组
        bootstrap.group(boss,work)
                //2. 设置nio类型的通道
                .channel(NioServerSocketChannel.class)
                //3. 设置监听端口
                .localAddress(serverPort)
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
                        socketChannel.pipeline().addLast();
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

    public static void main(String[] args) {
        new NettyDiscardServer(9999).runServer();
    }
}
