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
    public static final int PROPERTYMAX = 70;

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
        initNumerical();
        initHPMP();
        initDamage();
        initDefense();
        initAccuracy();
        initAvoid();
        initSpeed();
        initCritical();
        initNature();
        initSpecial();
        initAwaken();
    }

    public int getPropertyLimit(int lvValue, EProperty property) {
        int limitProperty = (int) (EPropertyConstant.PROPERTYMAX / property.getPotential());
        int limit = (lvValue < property.getMax()) ? lvValue : property.getMax();
        limit = (limit < limitProperty) ? limit : limitProperty;
        return limit;
    }

    private void initNumerical() {
        EPropertyGroup numericalGroup = new EPropertyGroup();
        numericalGroup.setName("能力数值");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("力量", 5));
        propertyList.add(new EProperty("力量%", 10));
        propertyList.add(new EProperty("智力", 5));
        propertyList.add(new EProperty("智力%", 10));
        propertyList.add(new EProperty("耐力", 5));
        propertyList.add(new EProperty("耐力%", 10));
        propertyList.add(new EProperty("灵巧", 5));
        propertyList.add(new EProperty("灵巧%", 10));
        propertyList.add(new EProperty("敏捷", 5));
        propertyList.add(new EProperty("敏捷%", 10));
        numericalGroup.setPropertyList(propertyList);
        groupList.add(numericalGroup);
    }

    private void initHPMP() {
        EPropertyGroup HPMPGroup = new EPropertyGroup();
        HPMPGroup.setName("HP,MP数值");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("HP自然恢复", 5));
        propertyList.add(new EProperty("HP自然恢复%", 10));
        propertyList.add(new EProperty("MP自然恢复", 10));
        propertyList.add(new EProperty("HP自然恢复%", 20));
        propertyList.add(new EProperty("HP上限", 3, 10));
        propertyList.add(new EProperty("HP上限%", 10));
        propertyList.add(new EProperty("MP上限", 6, 10));
        HPMPGroup.setPropertyList(propertyList);
        groupList.add(HPMPGroup);
    }

    private void initDamage() {
        EPropertyGroup damageGroup = new EPropertyGroup();
        damageGroup.setName("攻击力");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("物理攻击", 3));
        propertyList.add(new EProperty("物理攻击%", 10));
        propertyList.add(new EProperty("魔法攻击", 3));
        propertyList.add(new EProperty("魔法攻击%", 10));
        propertyList.add(new EProperty("稳定度%", 20));
        propertyList.add(new EProperty("物理贯穿%", 20));
        propertyList.add(new EProperty("魔法贯穿%", 20));
        damageGroup.setPropertyList(propertyList);
        groupList.add(damageGroup);
    }

    private void initDefense() {
        EPropertyGroup defenseGroup = new EPropertyGroup();
        defenseGroup.setName("防御力");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("物理防御", 3));
        propertyList.add(new EProperty("物理防御%", 10));
        propertyList.add(new EProperty("魔法防御", 3));
        propertyList.add(new EProperty("魔法防御%", 10));
        propertyList.add(new EProperty("物理抗性%", 10));
        propertyList.add(new EProperty("魔法抗性%", 10));
        defenseGroup.setPropertyList(propertyList);
        groupList.add(defenseGroup);
    }

    private void initAccuracy() {
        EPropertyGroup accuracyGroup = new EPropertyGroup();
        accuracyGroup.setName("命中");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("命中", 10));
        propertyList.add(new EProperty("命中%", 20));
        accuracyGroup.setPropertyList(propertyList);
        groupList.add(accuracyGroup);
    }

    private void initAvoid() {
        EPropertyGroup avoidGroup = new EPropertyGroup();
        avoidGroup.setName("回避");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("回避", 10));
        propertyList.add(new EProperty("回避%", 20));
        avoidGroup.setPropertyList(propertyList);
        groupList.add(avoidGroup);
    }

    private void initSpeed() {
        EPropertyGroup speedGroup = new EPropertyGroup();
        speedGroup.setName("速度");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("攻击速度", 1));
        propertyList.add(new EProperty("攻击速度%", 1));
        propertyList.add(new EProperty("施法速度", 1));
        propertyList.add(new EProperty("施法速度%", 1));
        speedGroup.setPropertyList(propertyList);
        groupList.add(speedGroup);
    }

    private void initCritical() {
        EPropertyGroup criticalGroup = new EPropertyGroup();
        criticalGroup.setName("暴击");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("暴击率", 1));
        propertyList.add(new EProperty("暴击率%", 1));
        propertyList.add(new EProperty("暴击伤害", 3));
        propertyList.add(new EProperty("暴击伤害%", 10));
        criticalGroup.setPropertyList(propertyList);
        groupList.add(criticalGroup);
    }

    private void initNature() {
        EPropertyGroup natureGroup = new EPropertyGroup();
        natureGroup.setName("属性强化");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("对[火]属性伤害增强", 5));
        propertyList.add(new EProperty("对[水]属性伤害增强", 5));
        propertyList.add(new EProperty("对[风]属性伤害增强", 5));
        propertyList.add(new EProperty("对[地]属性伤害增强", 5));
        propertyList.add(new EProperty("对[光]属性伤害增强", 5));
        propertyList.add(new EProperty("对[暗]属性伤害增强", 5));
        propertyList.add(new EProperty("对[火]抗性", 5));
        propertyList.add(new EProperty("对[水]抗性", 5));
        propertyList.add(new EProperty("对[风]抗性", 5));
        propertyList.add(new EProperty("对[地]抗性", 5));
        propertyList.add(new EProperty("对[光]抗性", 5));
        propertyList.add(new EProperty("对[暗]抗性", 5));
        natureGroup.setPropertyList(propertyList);
        groupList.add(natureGroup);
    }

    private void initSpecial() {
        EPropertyGroup specialGroup = new EPropertyGroup();
        specialGroup.setName("特殊强化");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("异常抗性%", 20));
        propertyList.add(new EProperty("格挡率%", 20));
        propertyList.add(new EProperty("格挡力%", 20));
        propertyList.add(new EProperty("闪躲率%", 20));
        propertyList.add(new EProperty("仇恨值%", 6, 1, 10));
        specialGroup.setPropertyList(propertyList);
        groupList.add(specialGroup);
    }

    private void initAwaken() {
        EPropertyGroup awakenGroup = new EPropertyGroup();
        awakenGroup.setName("属性觉醒");
        List<EProperty> propertyList = new ArrayList<>();
        propertyList.add(new EProperty("火属性", 100, 1, 1));
        propertyList.add(new EProperty("水属性", 100, 1, 1));
        propertyList.add(new EProperty("风属性", 100, 1, 1));
        propertyList.add(new EProperty("地属性", 100, 1, 1));
        propertyList.add(new EProperty("光属性", 100, 1, 1));
        propertyList.add(new EProperty("暗属性", 100, 1, 1));
        awakenGroup.setPropertyList(propertyList);
        groupList.add(awakenGroup);
    }
}
