package com.moon.tinyredis.resp.command.string;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.datastructure.value.BulkValue;
import com.moon.tinyredis.resp.datastructure.value.Value;
import com.moon.tinyredis.resp.reply.BulkReply;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.constant.NullBulkReply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class Get extends Command{

    public Get(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        String key = new String(args[0], SystemConfig.SYSTEM_CHARSET);
        Value value = db.getDict().get(key);
        if (value == null) {
            return NullBulkReply.makeNullBulkReply();
        }
        // 判断value的类型
        if (value instanceof BulkValue bulkValue) {
            return BulkReply.makeBulkReply(bulkValue.getBulk());
        }

        // TODO: 对其他数据类型的支持
        return CommonErrorReply.makeCommonErrorReply("Unknown Error");
    }
}
