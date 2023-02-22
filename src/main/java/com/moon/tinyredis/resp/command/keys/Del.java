package com.moon.tinyredis.resp.command.keys;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.reply.IntegerReply;
import com.moon.tinyredis.resp.reply.Reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class Del extends Command {

    public Del(int arity) {
        super(arity);
    }

    /**
     * del k1 k2 ... kn
     * @param db 数据库
     * @param args k1 k2 ... kn
     * @return 结果
     */
    @Override
    public Reply exec(DB db, byte[][] args) {
        int del = 0;
        for (byte[] arg : args) {
            String key = new String(arg, StandardCharsets.UTF_8);
            del += db.getDict().remove(key);
        }
        return IntegerReply.makeStatusReply(del);
    }
}
