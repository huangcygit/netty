package com.hy.netty.pastePackage;

import com.hy.netty.first.NettyTimeServerhandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyTimeServer {
    public void  bind(int port){
        //创建两个NioEventLoopGroup，NioEventLoopGroup包含一组NIO线程，专门用于网络事件的处理
        //创建两个，一个是用于服务端接收客户端的连接，另一个用于进行SocketChannel的网络读写。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建ServerBootstrap，是netty用于启动NIO服务端的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChildChannelHandler()); //绑定I/O的处理类为ChildChannelHandler
            //调用bind方法绑定监听端口，调用同步阻塞方法sync等待绑定操作的完成
            ChannelFuture f = b.bind(port).sync();
            //进行阻塞，等待服务端链路关闭之后才退出main函数
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyPasteTimeServerhandler());
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        new NettyTimeServer().bind(port);
    }
}
