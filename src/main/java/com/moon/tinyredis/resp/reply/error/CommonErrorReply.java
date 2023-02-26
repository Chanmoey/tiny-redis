package com.moon.tinyredis.resp.reply.error;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class CommonErrorReply extends AbstractErrorReplay {

    private final String errMsg;

    CommonErrorReply(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    @Override
    public byte[] toBytes() {
        return ("-" + errMsg + "\r\n").getBytes(SystemConfig.SYSTEM_CHARSET);
    }

    public static CommonErrorReply makeCommonErrorReply(String errMsg) {
        return new CommonErrorReply(errMsg);
    }
}
