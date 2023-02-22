package com.moon.tinyredis.resp.command.keys;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.datastructure.value.BulkValue;
import com.moon.tinyredis.resp.datastructure.value.Value;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.StatusReply;
import com.moon.tinyredis.resp.reply.error.UnknownErrorReply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class Type extends Command {

    public Type(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        String key = new String(args[0], StandardCharsets.UTF_8);
        Value value = db.getDict().get(key);
        if (value == null) {
            return StatusReply.makeStatusReply("none");
        }
        if (value instanceof BulkValue) {
            return StatusReply.makeStatusReply("string");
        }
        return UnknownErrorReply.makeUnknownErrorReply();
    }
}
