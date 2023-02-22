package com.moon.tinyredis.resp.reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class RespConstant {

    public static final byte CR = '\r';

    private RespConstant(){}

    public static final byte[] SIMPLE = "+".getBytes();

    public static final byte[] ERROR = "-".getBytes();

    public static final byte[] INTEGER = ":".getBytes();

    public static final byte[] BULK = "$".getBytes();

    public static final byte[] ARRAY = "*".getBytes();

    public static final byte[] NULL_BULK_REPLY_BYTES = "$-1".getBytes(StandardCharsets.UTF_8);

    public static final String CRLF = "\r\n";
}
