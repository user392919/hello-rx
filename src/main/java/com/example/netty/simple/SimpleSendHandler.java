package com.example.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class SimpleSendHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.flush();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {


        if (msg instanceof DefaultLastHttpContent) {
            ByteBuf respContent = ((DefaultLastHttpContent) msg).content();

            try {
                while (respContent.isReadable()) { // (1)
                    System.out.print((char) respContent.readByte());
                    System.out.flush();
                }
            } catch (Exception e) {
                System.out.println("---------------- Reading Exception ----------------");
                e.printStackTrace();
            }

        }


        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}