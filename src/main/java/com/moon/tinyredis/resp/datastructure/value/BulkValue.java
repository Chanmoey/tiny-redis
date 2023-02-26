package com.moon.tinyredis.resp.datastructure.value;

import com.moon.tinyredis.resp.config.SystemConfig;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class BulkValue extends AbstractValue{

    private byte[] bulk;

    @Override
    public int hashCode() {
        return new String(bulk, SystemConfig.SYSTEM_CHARSET).hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj instanceof BulkValue o) {

            if (bulk == null || o.bulk == null) {
                return false;
            }

            if (bulk.length != o.bulk.length) {
                return false;
            }

            for (int i = 0; i < bulk.length; i++) {
                if (bulk[i] != o.bulk[i]) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }
}
