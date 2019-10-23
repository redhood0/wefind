package com.wefind.javabean;

/**
 * @author cky
 * date 2019-07-04
 * 消息
 */
public class MessageBean {
    private String name;
    private String content;
    private String date;
    private int headImgId;


    public MessageBean(String name, String content, String date, int headImgId) {
        this.name = name;
        this.content = content;
        this.date = date;
        this.headImgId = headImgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHeadImgId() {
        return headImgId;
    }

    public void setHeadImgId(int headImgId) {
        this.headImgId = headImgId;
    }


}
