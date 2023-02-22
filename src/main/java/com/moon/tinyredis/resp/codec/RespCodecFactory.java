package com.moon.tinyredis.resp.codec;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class RespCodecFactory {

    private static final Map<RespType, RespCodec<?>> CODEC_MAP = new HashMap<>();

    {
        CODEC_MAP.put(RespType.SIMPLE_STRING, new SimpleStringDecode());
    }
}
