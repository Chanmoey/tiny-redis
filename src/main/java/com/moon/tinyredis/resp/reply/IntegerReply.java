package com.moon.tinyredis.resp.reply;

import com.moon.tinyredis.resp.config.SystemConfig;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class IntegerReply implements Reply {

    private final int number;

    public IntegerReply(int number) {
        this.number = number;
    }

    @Override
    public byte[] toBytes() {
        return (":" + number + RespConstant.CRLF).getBytes(SystemConfig.SYSTEM_CHARSET);
    }

    public static IntegerReply makeStatusReply(int number) {
        return new IntegerReply(number);
    }
}
