package com.wefind.javabean;

public class SearchResultBean{
    private double score;

    private String brief;

    private String cont_sign;

    public void setScore(double score){
        this.score = score;
    }
    public double getScore(){
        return this.score;
    }
    public void setBrief(String brief){
        this.brief = brief;
    }
    public String getBrief(){
        return this.brief;
    }
    public void setCont_sign(String cont_sign){
        this.cont_sign = cont_sign;
    }
    public String getCont_sign(){
        return this.cont_sign;
    }
}
