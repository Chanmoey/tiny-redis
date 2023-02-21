package com.moon.tinyredis.resp.codec;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class CommandLine {

    private byte[] command;

    private byte[][] args;

    public CommandLine(byte[] command, byte[][] args) {
        this.command = command;
        this.args = args;
    }

    public byte[] getCommand() {
        return command;
    }

    public void setCommand(byte[] command) {
        this.command = command;
    }

    public byte[][] getArgs() {
        return args;
    }

    public void setArgs(byte[][] args) {
        this.args = args;
    }
}
