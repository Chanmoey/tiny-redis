package com.moon.tinyredis.resp.parser;

/**
 * @author Chanmoey
 * @date 2023年02月23日
 */
public class ReadState {

    private boolean readingMultiLine;

    private int expectedArgsCount;

    private byte msgType;

    private byte[][] args;

    private int bulkLen;

    private int readArgNum;

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
