package com.moon.tinyredis.resp.reply;

import com.moon.tinyredis.resp.config.SystemConfig;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class RespConstant {

    private RespConstant() {
    }

    public static final byte OK = '+';

    public static final byte ERROR = '-';

    public static final byte INTEGER = ':';

    public static final byte BULK = '$';

    public static final byte MULTI_BULK = '*';

    public static final byte[] NULL_BULK_REPLY_BYTES = "$-1".getBytes(SystemConfig.SYSTEM_CHARSET);

    public static final String EMPTY_STRING = "";
    public static final Long ZERO = 0L;
    public static final Long NEGATIVE_ONE = -1L;
    public static final Long ONE = 1L;
    public static final byte CR = '\r';
    public static final byte LF = (byte) '\n';
    public static final String CRLF = "\r\n";
    public static final byte[] CRLF_BYTE = "\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);
    
    public static final String PROTOCOL_ERROR = "protocol error: ";
}
