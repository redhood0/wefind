package com.wefind.javabean;

/**
 * @author cky
 * date 2019-07-09
 * 聊天
 */
public class ChattingMessage {
    private String content;
    private TYPE type;

    public enum TYPE{
        RECEIVED,SEND
    }

    public ChattingMessage(String content, TYPE type){
        this.content = content;
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
