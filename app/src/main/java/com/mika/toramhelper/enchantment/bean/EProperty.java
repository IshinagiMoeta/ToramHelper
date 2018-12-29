package com.mika.toramhelper.enchantment.bean;

/**
 * Created by moeta on 2018/12/27.
 * e-mail:367195887@qq.com
 * info:
 */

public class EProperty {
    private String name;
    private int potential;
    private int value;
    private int max;

    EProperty(String name, int potential) {
        this(name, potential, 1);
    }

    EProperty(String name, int potential, int value) {
        this(name, potential, value, 100);
    }

    EProperty(String name, int potential, int value, int max) {
        this.name = name;
        this.potential = potential;
        this.value = value;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
