package com.moon.tinyredis.resp.reply.error;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class SyntaxErrorReply extends AbstractErrorReplay{

    private static final SyntaxErrorReply INSTANCE = new SyntaxErrorReply();

    SyntaxErrorReply() {
        super("Syntax Err");
    }

    private static final byte[] UNKNOWN_ERROR_BYTES = "-Err syntax error\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);

    @Override
    public byte[] toBytes() {
        return UNKNOWN_ERROR_BYTES;
    }

    public static SyntaxErrorReply makeSyntaxErrorReply() {
        return INSTANCE;
    }
}
