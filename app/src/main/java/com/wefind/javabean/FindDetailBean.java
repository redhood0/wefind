package com.wefind.javabean;

/**
 * @author cky
 * date 2019-10-23
 */
public class FindDetailBean {
    private int imgId;
    private String title;
    private String content;
    private int money;

    public FindDetailBean(int imgId, String title, String content, int money) {
        this.imgId = imgId;
        this.title = title;
        this.content = content;
        this.money = money;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
