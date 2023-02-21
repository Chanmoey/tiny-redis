package com.moon.tinyredis.resp.datastructure.value;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class AbstractValue {

    protected long expireTime = -1;

    protected boolean isExpire() {
        if (expireTime == -1L) {
            return false;
        }
        return expireTime < System.currentTimeMillis();
    }
}
