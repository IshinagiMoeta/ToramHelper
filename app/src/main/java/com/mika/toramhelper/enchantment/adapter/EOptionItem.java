package com.mika.toramhelper.enchantment.adapter;

import com.mika.toramhelper.enchantment.bean.EProperty;

/**
 * Created by moeta on 2018/12/28.
 * e-mail:367195887@qq.com
 * info:
 */

public class EOptionItem {
    private EProperty property;
    private int value;
    private int group = -1;
    private int position = -1;
    private int deployValue = 0;
    private boolean deploy = false;

    public EProperty getProperty() {
        return property;
    }

    public void setProperty(EProperty property) {
        this.property = property;
    }

    public String getValue() {
        if (value > 0) {
            return "+" + value;
        }
        return String.valueOf(value);
    }

    public int getValueNum() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDeployValue() {
        return deployValue;
    }

    public void setDeployValue(int deployValue) {
        this.deployValue = deployValue;
    }

    public boolean isDeploy() {
        return deploy;
    }

    public void setDeploy(boolean deploy) {
        this.deploy = deploy;
    }

    public EOptionItem(EProperty property, int value) {
        this.property = property;
        this.value = value;
    }
}
