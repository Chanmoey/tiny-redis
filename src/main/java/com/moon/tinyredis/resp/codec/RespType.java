package com.moon.tinyredis.resp.codec;

import java.util.Arrays;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public enum RespType {
    OK((byte) '+'),
    ERROR((byte) '-'),
    INTEGER((byte) ':'),
    BULK((byte) '$'),
    MULTI_BULK((byte) '*');

    private final byte flag;

    RespType(byte flag) {
        this.flag = flag;
    }

    public static RespType of(byte flag) {
        return Arrays.stream(RespType.values()).filter(r -> r.flag == flag).findAny().orElse(null);
    }
}
