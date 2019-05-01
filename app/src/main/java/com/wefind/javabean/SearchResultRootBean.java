package com.wefind.javabean;

import java.io.Serializable;
import java.util.List;

public class SearchResultRootBean implements Serializable {
    private int result_num;

    private List<SearchResultBean> result;

    private String log_id;

    public void setResult_num(int result_num){
        this.result_num = result_num;
    }
    public int getResult_num(){
        return this.result_num;
    }
    public void setResult(List<SearchResultBean> result){
        this.result = result;
    }
    public List<SearchResultBean> getResult(){
        return this.result;
    }
    public void setLog_id(String log_id){
        this.log_id = log_id;
    }
    public String getLog_id(){
        return this.log_id;
    }
}
