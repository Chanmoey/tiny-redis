package com.moon.tinyredis.resp.client;

import com.moon.tinyredis.resp.parser.Parser;
import com.moon.tinyredis.resp.parser.ReadState;
import com.moon.tinyredis.resp.reply.MultiBulkReply;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class TinyRedisClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9736;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new Parser(new ReadState()));
                    ch.pipeline().addLast(new TinyClientHandler());
                }
            });

            // Start the client.
            Channel channel = b.connect(host, port).sync().channel(); // (5)

            while (true) {
                Scanner input = new Scanner(System.in);
                String str = input.nextLine();
                String[] strings = str.split(" ");
                byte[][] bytes = new byte[strings.length][];
                for (int i = 0; i < strings.length; i++) {
                    bytes[i] = strings[i].getBytes();
                }
                byte[] resp = MultiBulkReply.makeMultiBulkReply(bytes).toBytes();
                ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(resp.length + 4);
                byteBuf.writeInt(resp.length);
                byteBuf.writeBytes(resp);
                channel.writeAndFlush(byteBuf);
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
