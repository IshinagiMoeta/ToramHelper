package com.mika.toramhelper.enchantment.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by moeta on 2018/12/27.
 * e-mail:367195887@qq.com
 * info:
 */

public class EProperty {
    //金属
    public static final int METAL = 0;
    //布料
    public static final int CLOTH = 1;
    //兽品
    public static final int BEAST = 2;
    //木材
    public static final int WOOD = 3;
    //药品
    public static final int DRUG = 4;
    //魔素
    public static final int MAGIC = 5;

    @IntDef({METAL, CLOTH, BEAST, WOOD, DRUG, MAGIC})
    @Retention(RetentionPolicy.SOURCE)
    @interface MATERIALTYPE {

    }

    private String name;
    private int potential;
    @MATERIALTYPE
    private int materialType;
    private double materialValue;
    private int value;
    private int max;

    EProperty(String name, int potential, int materialType, double materialValue) {
        this(name, potential, materialType, materialValue, 1);
    }

    EProperty(String name, int potential, int materialType, double materialValue, int value) {
        this(name, potential, materialType, materialValue, value, 100);
    }

    EProperty(String name, int potential, int materialType, double materialValue, int value, int max) {
        this.name = name;
        this.potential = potential;
        this.materialType = materialType;
        this.materialValue = materialValue;
        this.value = value;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPotential() {
        return potential;
    }

    public int getValue() {
        return value;
    }

    public int getMax() {
        return max;
    }

    public int getMaterialType() {
        return materialType;
    }

    public double getMaterialValue() {
        return materialValue;
    }
}
