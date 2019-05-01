package com.wefind.javabean;

public class ClassChooseBean {
    private String className = "";
    private String typeCode = "0";

    public ClassChooseBean() {
    }

    public ClassChooseBean(String className, String typeCode) {
        this.className = className;
        this.typeCode = typeCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
