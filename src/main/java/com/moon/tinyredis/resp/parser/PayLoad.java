package com.moon.tinyredis.resp.parser;

import com.moon.tinyredis.resp.reply.Reply;

/**
 * RESP中，客户端请求和服务端响应都是一样的，所以用Reply接收客户端的请求
 *
 * @author Chanmoey
 * @date 2023年02月23日
 */
public class PayLoad {

    private Reply data;

    private DecodeException exception;

    public Reply getData() {
        return data;
    }

    public void setData(Reply data) {
        this.data = data;
    }

    public DecodeException getException() {
        return exception;
    }

    public void setException(DecodeException exception) {
        this.exception = exception;
    }
}
