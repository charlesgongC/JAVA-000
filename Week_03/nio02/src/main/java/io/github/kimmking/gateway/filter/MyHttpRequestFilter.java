package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class MyHttpRequestFilter implements HttpRequestFilter {
    private FullHttpRequest fullHttpRequest;

    public FullHttpRequest getFullHttpRequest() {
        return fullHttpRequest;
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        // 添加头
        fullRequest.headers().add("nio","Cheng Gong");
    }
}
