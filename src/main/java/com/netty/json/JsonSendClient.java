package com.netty.json;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class JsonSendClient {
    private int serverPort;
    private String serverIp;
    Bootstrap b=new Bootstrap();
    static String context="";
    public JsonSendClient(String ip,int port){
        this.serverPort=port;
        this.serverIp=ip;
    }
    public void runClient(){
        EventLoopGroup work=new NioEventLoopGroup();
        b.group(work);
        b.channel(NioSocketChannel.class);
        b.remoteAddress(serverIp,serverPort);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

            }
        });
        ChannelFuture f=b.connect();
        f.addListener((ChannelFuture futureListener)->{
            if(futureListener.isSuccess()){
                System.out.println("客户端连接成功!");
            }else {
                System.out.println("客户端连接失败!");
            }
        });
        try {
            f.sync();
            Channel channel= f.channel();
            Scanner scanner=new Scanner(System.in);
            System.out.println("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
