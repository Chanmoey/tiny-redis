package com.moon.tinyredis.resp.parser;

/**
 * @author Chanmoey
 * @date 2023年02月23日
 */
public class ReadState {

    // 时候多行模式，*数组和$多行字符串都属于多行模式
    private boolean readingMultiLine;

    // 期待接收的参数个数
    private int expectedArgsCount;

    // 消息类型：多行数组、多行字符串、其他
    private byte msgType;

    // 参数的个数，例如 set key value 的参数为3
    private byte[][] args;

    // 多行字符串的长度
    private int bulkLen;

    // 已经读取了多少个参数了
    private int readArgNum;

    public void resetState() {
        this.readingMultiLine = false;
        this.expectedArgsCount = 0;
        this.msgType = 0;
        this.args = null;
        this.bulkLen = 0;
        this.readArgNum = 0;
    }

    public void appendArg(byte[] arg) {
        args[readArgNum++] = arg;
    }

    public boolean finished() {
        return expectedArgsCount > 0 && readArgNum == expectedArgsCount;
    }

    public boolean isReadingMultiLine() {
        return readingMultiLine;
    }

    public void setReadingMultiLine(boolean readingMultiLine) {
        this.readingMultiLine = readingMultiLine;
    }

    public int getExpectedArgsCount() {
        return expectedArgsCount;
    }

    public void setExpectedArgsCount(int expectedArgsCount) {
        this.expectedArgsCount = expectedArgsCount;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public byte[][] getArgs() {
        return args;
    }

    public void setArgs(byte[][] args) {
        this.args = args;
    }

    public int getBulkLen() {
        return bulkLen;
    }

    public void setBulkLen(int bulkLen) {
        this.bulkLen = bulkLen;
    }

    public int getReadArgNum() {
        return readArgNum;
    }

    public void setReadArgNum(int readArgNum) {
        this.readArgNum = readArgNum;
    }
}
