package com.wefind.javabean;

import java.io.Serializable;

public class ClassChooseBean implements Serializable {
    private String className = "";
    private String typeCode = "0";
    private boolean isSelected;

    public ClassChooseBean() {
    }

    public ClassChooseBean(String className, String typeCode) {
        this.className = className;
        this.typeCode = typeCode;
        isSelected = false;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
