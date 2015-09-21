package com.example.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

public class SimpleSend {

    static final String HOST = System.getProperty("host", "www.mocky.io");
    static final int PORT = Integer.parseInt(System.getProperty("port", "80"));


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
//                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new HttpRequestEncoder());
                            p.addLast(new HttpResponseDecoder());
                            p.addLast(new SimpleSendHandler());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();


            HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/v2/5185415ba171ea3a00704eed");
            request.headers().set(HttpHeaders.Names.HOST, HOST);


           f.channel().writeAndFlush(request);

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }



    }
}
