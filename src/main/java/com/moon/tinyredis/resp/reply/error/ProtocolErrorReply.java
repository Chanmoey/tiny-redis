package com.moon.tinyredis.resp.reply.error;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class ProtocolErrorReply extends AbstractErrorReplay{

    private final String msg;

    public ProtocolErrorReply(String msg) {
        super("Protocol Error");
        this.msg = msg;
    }

    @Override
    public byte[] toBytes() {
        return ("-Err Protocol error: '" + msg + "'\r\n")
                .getBytes(StandardCharsets.UTF_8);
    }
}
