package com.moon.tinyredis.resp;

import com.moon.tinyredis.resp.parser.Parser;
import com.moon.tinyredis.resp.parser.ReadState;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Chanmoey
 * @date 2023年02月26日
 */
public class TinyRedisServerStarter {

    private final EventLoopGroup mainGroup;
    private final EventLoopGroup subGroup;
    private final ServerBootstrap server;

    public TinyRedisServerStarter() {
        this.mainGroup = new NioEventLoopGroup(1);
        this.subGroup = new NioEventLoopGroup();
        this.server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                // 服务端可连接队列大小
                .option(ChannelOption.SO_BACKLOG, 10240)
                // 参数表示允许重复使用本地地址和端口
                .option(ChannelOption.SO_REUSEADDR, true)
                // 是否禁用Nagle算法 简单点说是否批量发送数据 true关闭 false开启
                // 开启的话可以减少一定的网络开销，但影响消息实时性
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 保活开关2h没有数据服务端会发送心跳包
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 解码器
                        ch.pipeline().addLast(new Parser(new ReadState()));
                    }
                });
    }

    public void start() {
        this.server.bind();
    }
}