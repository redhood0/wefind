package com.wefind.context;

import com.wefind.javabean.ClassChooseBean;

import java.util.ArrayList;
import java.util.Arrays;

//物品分类：
//人0:(孩童（男1女2）成年人（男5女6）老年人（男10女11）)
//动物:30（公1母2）
//物品（日用品，证件，贵重物品，电子产品，图书，服装鞋帽，纪念品）
public class ClassificationContext {
    public static final ArrayList<ClassChooseBean> classList = new ArrayList<>();

    public static ArrayList<ClassChooseBean> setAndGetClassList(){
        ClassChooseBean item1 = new ClassChooseBean("电子产品","50");
        ClassChooseBean item2 = new ClassChooseBean("日用品","51");
        ClassChooseBean item3 = new ClassChooseBean("证件","52");
        ClassChooseBean item4 = new ClassChooseBean("贵重物品","53");
        ClassChooseBean item5 = new ClassChooseBean("图书","54");
        ClassChooseBean item6 = new ClassChooseBean("服装鞋帽","55");
        ClassChooseBean item7 = new ClassChooseBean("美妆","56");
        ClassChooseBean item8 = new ClassChooseBean("其他","100");
        ClassChooseBean item9 = new ClassChooseBean("宠物","30");
        ClassChooseBean item10 = new ClassChooseBean("寻人","0");

        classList.addAll(Arrays.asList(item1,item2,item3,item4,item5,item6,item7,item8,item9,item10));
        return classList;
    }
}
