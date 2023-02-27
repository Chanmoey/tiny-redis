package com.moon.tinyredis.resp.command.string;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.datastructure.value.BulkValue;
import com.moon.tinyredis.resp.reply.IntegerReply;
import com.moon.tinyredis.resp.reply.Reply;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class SetNX extends Command {
    public SetNX(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        String key = new String(args[0], SystemConfig.SYSTEM_CHARSET);
        byte[] valBytes = args[1];
        BulkValue bulkReply = new BulkValue(valBytes);

        int result = db.getDict().putIfAbsent(key, bulkReply);
        return IntegerReply.makeStatusReply(result);
    }
}
