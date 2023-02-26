package com.moon.tinyredis.resp.parser;

/**
 * @author Chanmoey
 * @date 2023年02月24日
 */
public class DecodeException extends RuntimeException {

    public DecodeException(String msg) {
        super(msg);
    }

}
