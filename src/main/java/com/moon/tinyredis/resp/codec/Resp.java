package com.moon.tinyredis.resp.codec;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class Resp<T> {

    protected T value;

    public T getValue() {
        return value;
    }

    public Resp<T> setValue(T value) {
        this.value = value;
        return this;
    }
}
