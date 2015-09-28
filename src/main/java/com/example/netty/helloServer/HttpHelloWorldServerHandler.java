/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.example.netty.helloServer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.util.Iterator;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter {
    private static final byte[] CONTENT = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd'};

    private HttpRequest request;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            request = req;
            if (req.getMethod() != HttpMethod.POST) {
                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, METHOD_NOT_ALLOWED);
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {


                boolean valid = false;
                for (Map.Entry<String, String> header : req.headers()) {
                    if (header.getKey().equals("my-special-header")) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    FullHttpResponse resp = new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST);
                    ctx.write(resp).addListener(ChannelFutureListener.CLOSE);;
                } else {
                    if (HttpHeaders.is100ContinueExpected(request)) {
                        ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
                    }
                }

            }


        } else if (msg instanceof LastHttpContent && msg != LastHttpContent.EMPTY_LAST_CONTENT) {
            DefaultLastHttpContent content = (DefaultLastHttpContent) msg;

            System.out.println("content read");
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, content.content());
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

            boolean keepAlive = false;//HttpHeaders.isKeepAlive(request);
            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
