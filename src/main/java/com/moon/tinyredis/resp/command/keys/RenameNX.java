package com.moon.tinyredis.resp.command.keys;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.datastructure.value.Value;
import com.moon.tinyredis.resp.reply.IntegerReply;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.constant.OkReply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class RenameNX extends Command {

    public RenameNX(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        String oldKey = new String(args[0], StandardCharsets.UTF_8);

        // 看看key2存不存在
        String newKey = new String(args[1], StandardCharsets.UTF_8);
        Value v2 = db.getDict().get(newKey);
        if (v2 != null) {
            return IntegerReply.makeStatusReply(0);
        }

        Value v1 = db.getDict().get(oldKey);
        if (v1 == null) {
            return CommonErrorReply.makeCommonErrorReply("no such key");
        }
        db.getDict().put(newKey, v1);
        db.getDict().remove(oldKey);
        return IntegerReply.makeStatusReply(1);
    }
}
