package com.moon.tinyredis.resp.codec;

import com.moon.tinyredis.resp.reply.RespConstant;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class RespCodecTable {

    private static final Map<RespType, RespCodec<?>> CODEC_MAP = new HashMap<>();

    public static void register(RespType type, RespCodec<?> codec) {
        CODEC_MAP.put(type, codec);
    }

    public static RespCodec<?> getCodec(RespType type) {
        return CODEC_MAP.get(type);
    }


    private static RespType determineReplyType(ByteBuf buffer) {
        byte firstByte = buffer.readByte();
        RespType type = RespType.of(firstByte);
        if (type == null) {
            throw new IllegalArgumentException("Formatting Error");
        }
        return type;
    }
}
