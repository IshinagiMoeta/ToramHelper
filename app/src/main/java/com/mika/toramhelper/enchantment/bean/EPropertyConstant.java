package com.mika.toramhelper.enchantment.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moeta on 2018/12/27.
 * e-mail:367195887@qq.com
 * info:
 */

public class EPropertyConstant {
    static private EPropertyConstant instance;
    private static final int PROPERTYMAX = 70;

    private List<EPropertyGroup> groupList = new ArrayList<>();

    static public EPropertyConstant getInstance() {
        if (instance == null) {
            instance = new EPropertyConstant();
        }
        return instance;
    }

    public List<EPropertyGroup> getGroupList() {
        return groupList;
    }

    private EPropertyConstant() {
        initNull();
        initNumerical();
        //HPMP
        initHPMP();
        //攻击
        initDamage();
        //防御
        initDefense();
        //命中
        initAccuracy();
        //回避
        initAvoid();
        //速度
        initSpeed();
        initCritical();
        //属性
        initNature();
        initSpecial();
        //属性觉醒
        initAwaken1();
        initAwaken2();
    }

    public int getPropertyLimit(int lvValue, EProperty property) {
        int limitProperty = EPropertyConstant.PROPERTYMAX / property.getPotential();
        int limit = (lvValue < property.getMax()) ? lvValue : property.getMax();
        limit = (limit < limitProperty) ? limit : limitProperty;
        return limit;
    }

    private void initNull() {
        EPropertyGroup nullGroup = new EPropertyGroup();
        nullGroup.setName("空");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("空", 0, -1, 0));
        nullGroup.setPropertyList(propertyList);
        groupList.add(nullGroup);
    }

    private void initNumerical() {
        EPropertyGroup numericalGroup = new EPropertyGroup();
        numericalGroup.setName("能力数值");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("力量", 5, EProperty.BEAST, 25));
        propertyList.add(new EProperty("力量%", 10, EProperty.BEAST, 50));
        propertyList.add(new EProperty("智力", 5, EProperty.WOOD, 25));
        propertyList.add(new EProperty("智力%", 10, EProperty.WOOD, 50));
        propertyList.add(new EProperty("耐力", 5, EProperty.METAL, 25));
        propertyList.add(new EProperty("耐力%", 10, EProperty.METAL, 50));
        propertyList.add(new EProperty("灵巧", 5, EProperty.CLOTH, 25));
        propertyList.add(new EProperty("灵巧%", 10, EProperty.CLOTH, 50));
        propertyList.add(new EProperty("敏捷", 5, EProperty.DRUG, 25));
        propertyList.add(new EProperty("敏捷%", 10, EProperty.DRUG, 50));
        numericalGroup.setPropertyList(propertyList);
        groupList.add(numericalGroup);
    }

    private void initHPMP() {
        EPropertyGroup HPMPGroup = new EPropertyGroup();
        HPMPGroup.setName("HP,MP数值");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("HP自然恢复", 5, EProperty.METAL, 25));
        propertyList.add(new EProperty("HP自然恢复%", 10, EProperty.METAL, 50));
        propertyList.add(new EProperty("MP自然恢复", 10, EProperty.WOOD, 50));
        propertyList.add(new EProperty("MP自然恢复%", 20, EProperty.WOOD, 100));
        propertyList.add(new EProperty("HP上限", 3, EProperty.METAL, 16.5, 10));
        propertyList.add(new EProperty("HP上限%", 10, EProperty.METAL, 50));
        propertyList.add(new EProperty("MP上限", 6, EProperty.WOOD, 33.5, 10));
        HPMPGroup.setPropertyList(propertyList);
        groupList.add(HPMPGroup);
    }

    private void initDamage() {
        EPropertyGroup damageGroup = new EPropertyGroup();
        damageGroup.setName("攻击力");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("物理攻击", 3, EProperty.BEAST, 16.5));
        propertyList.add(new EProperty("物理攻击%", 10, EProperty.BEAST, 50));
        propertyList.add(new EProperty("魔法攻击", 3, EProperty.WOOD, 16.5));
        propertyList.add(new EProperty("魔法攻击%", 10, EProperty.WOOD, 50));
        propertyList.add(new EProperty("稳定度%", 20, EProperty.DRUG, 100));
        propertyList.add(new EProperty("物理贯穿%", 20, EProperty.BEAST, 100));
        propertyList.add(new EProperty("魔法贯穿%", 20, EProperty.WOOD, 100));
        damageGroup.setPropertyList(propertyList);
        groupList.add(damageGroup);
    }

    private void initDefense() {
        EPropertyGroup defenseGroup = new EPropertyGroup();
        defenseGroup.setName("防御力");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("物理防御", 3, EProperty.METAL, 16.5));
        propertyList.add(new EProperty("物理防御%", 10, EProperty.METAL, 50));
        propertyList.add(new EProperty("魔法防御", 3, EProperty.METAL, 16.5));
        propertyList.add(new EProperty("魔法防御%", 10, EProperty.METAL, 50));
        propertyList.add(new EProperty("物理抗性%", 10, EProperty.METAL, 50));
        propertyList.add(new EProperty("魔法抗性%", 10, EProperty.WOOD, 50));
        defenseGroup.setPropertyList(propertyList);
        groupList.add(defenseGroup);
    }

    private void initAccuracy() {
        EPropertyGroup accuracyGroup = new EPropertyGroup();
        accuracyGroup.setName("命中");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("命中", 10, EProperty.DRUG, 50));
        propertyList.add(new EProperty("命中%", 20, EProperty.DRUG, 100));
        accuracyGroup.setPropertyList(propertyList);
        groupList.add(accuracyGroup);
    }

    private void initAvoid() {
        EPropertyGroup avoidGroup = new EPropertyGroup();
        avoidGroup.setName("回避");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("回避", 10, EProperty.CLOTH, 50));
        propertyList.add(new EProperty("回避%", 20, EProperty.CLOTH, 100));
        avoidGroup.setPropertyList(propertyList);
        groupList.add(avoidGroup);
    }

    private void initSpeed() {
        EPropertyGroup speedGroup = new EPropertyGroup();
        speedGroup.setName("速度");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("攻击速度", 1, EProperty.CLOTH, 1.5));
        propertyList.add(new EProperty("攻击速度%", 1, EProperty.CLOTH, 5));
        propertyList.add(new EProperty("施法速度", 1, EProperty.DRUG, 1.5));
        propertyList.add(new EProperty("施法速度%", 1, EProperty.DRUG, 5));
        speedGroup.setPropertyList(propertyList);
        groupList.add(speedGroup);
    }

    private void initCritical() {
        EPropertyGroup criticalGroup = new EPropertyGroup();
        criticalGroup.setName("暴击");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("暴击率", 1, EProperty.MAGIC, 5));
        propertyList.add(new EProperty("暴击率%", 1, EProperty.MAGIC, 5));
        propertyList.add(new EProperty("暴击伤害", 3, EProperty.MAGIC, 16.5));
        propertyList.add(new EProperty("暴击伤害%", 10, EProperty.MAGIC, 50));
        criticalGroup.setPropertyList(propertyList);
        groupList.add(criticalGroup);
    }

    private void initNature() {
        EPropertyGroup natureGroup = new EPropertyGroup();
        natureGroup.setName("属性强化");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("对[火]属性伤害增强", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[水]属性伤害增强", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[风]属性伤害增强", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[地]属性伤害增强", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[光]属性伤害增强", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[暗]属性伤害增强", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[火]抗性", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[水]抗性", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[风]抗性", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[地]抗性", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[光]抗性", 5, EProperty.MAGIC, 25));
        propertyList.add(new EProperty("对[暗]抗性", 5, EProperty.MAGIC, 25));
        natureGroup.setPropertyList(propertyList);
        groupList.add(natureGroup);
    }

    private void initSpecial() {
        EPropertyGroup specialGroup = new EPropertyGroup();
        specialGroup.setName("特殊强化");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("异常抗性%", 20, EProperty.MAGIC, 100));
        propertyList.add(new EProperty("格挡率%", 20, EProperty.MAGIC, 100));
        propertyList.add(new EProperty("格挡力%", 20, EProperty.MAGIC, 100));
        propertyList.add(new EProperty("闪躲率%", 20, EProperty.MAGIC, 100));
        propertyList.add(new EProperty("仇恨值%", 6, EProperty.MAGIC, 33.5, 1, 10));
        specialGroup.setPropertyList(propertyList);
        groupList.add(specialGroup);
    }

    private void initAwaken1() {
        EPropertyGroup awakenGroup = new EPropertyGroup();
        awakenGroup.setName("属性觉醒(原属)");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("火属性", 10, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("水属性", 10, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("风属性", 10, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("地属性", 10, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("光属性", 10, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("暗属性", 10, EProperty.MAGIC, 150, 1, 0));
        awakenGroup.setPropertyList(propertyList);
        groupList.add(awakenGroup);
    }

    private void initAwaken2() {
        EPropertyGroup awakenGroup = new EPropertyGroup();
        awakenGroup.setName("属性觉醒(非原属)");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("火属性", 100, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("水属性", 100, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("风属性", 100, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("地属性", 100, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("光属性", 100, EProperty.MAGIC, 150, 1, 0));
        propertyList.add(new EProperty("暗属性", 100, EProperty.MAGIC, 150, 1, 0));
        awakenGroup.setPropertyList(propertyList);
        groupList.add(awakenGroup);
    }
}
