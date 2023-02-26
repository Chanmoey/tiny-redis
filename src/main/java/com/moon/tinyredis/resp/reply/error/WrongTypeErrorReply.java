package com.moon.tinyredis.resp.reply.error;

import com.moon.tinyredis.resp.config.SystemConfig;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class WrongTypeErrorReply extends AbstractErrorReplay{

    private static final WrongTypeErrorReply INSTANCE = new WrongTypeErrorReply();

    WrongTypeErrorReply() {
        super("Wrong Type Err");
    }

    private static final byte[] UNKNOWN_ERROR_BYTES = "-WRONGTYPE Operation against a key holding the wrong kind of value\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);

    @Override
    public byte[] toBytes() {
        return UNKNOWN_ERROR_BYTES;
    }

    public static WrongTypeErrorReply makeWrongTypeErrorReply() {
        return INSTANCE;
    }
}
