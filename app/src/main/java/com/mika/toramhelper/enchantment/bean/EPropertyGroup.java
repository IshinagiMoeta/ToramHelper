package com.mika.toramhelper.enchantment.bean;

import java.util.List;

/**
 * Created by moeta on 2018/12/27.
 * e-mail:367195887@qq.com
 * info:
 */

public class EPropertyGroup {
    private String name;
    private List<EProperty> propertyList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EProperty> getPropertyList() {
        return propertyList;
    }

    void setPropertyList(List<EProperty> propertyList) {
        this.propertyList = propertyList;
    }
}
