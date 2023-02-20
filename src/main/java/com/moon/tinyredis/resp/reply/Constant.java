package com.moon.tinyredis.resp.reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class Constant {

    public static final byte[] NULL_BULK_REPLY_BYTES = "$-1".getBytes(StandardCharsets.UTF_8);

    public static final String CRLF = "\r\n";
}
