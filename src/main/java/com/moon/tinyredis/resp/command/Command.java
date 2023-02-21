package com.moon.tinyredis.resp.command;

import com.moon.tinyredis.resp.reply.Reply;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public abstract class Command {

    /**
     * 参数数量set k1 v1的参数数量为3
     */
    protected int arity;

    abstract Reply exec(byte[][] args);
}
