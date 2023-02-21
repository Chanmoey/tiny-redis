package com.moon.tinyredis.resp.reply.error;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public abstract class AbstractErrorReplay extends RuntimeException implements ErrorReply{
    protected AbstractErrorReplay(String errorMsg) {
        super(errorMsg);
    }
}
