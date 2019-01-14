package com.mika.toramhelper.enchantment.bean;

import android.support.annotation.IntDef;

import java.util.List;

/**
 * Created by moeta on 2019/1/10.
 * e-mail:367195887@qq.com
 * info:
 */

public class Eformula {
    //防具
    public static final int ARMOR = 0;
    //武器
    public static final int WEAPON = 1;

    @IntDef({ARMOR, WEAPON})
    @interface ITEMTYPE {
    }

    // 返回code，默认0
    private int code;
    private List<TypeList> typeLists;

    public Eformula(int code, List<TypeList> typeLists) {
        this.code = code;
        this.typeLists = typeLists;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<TypeList> getTypeLists() {
        return typeLists;
    }

    public void setTypeLists(List<TypeList> typeLists) {
        this.typeLists = typeLists;
    }

    public static class TypeList {
        @ITEMTYPE
        private int type;
        private List<GroupList> groupLists;

        public TypeList(int type, List<GroupList> groupLists) {
            this.type = type;
            this.groupLists = groupLists;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<GroupList> getGroupLists() {
            return groupLists;
        }

        public void setGroupLists(List<GroupList> groupLists) {
            this.groupLists = groupLists;
        }
    }

    public static class GroupList {
        //子分类
        String group;
        List<StepList> stepLists;

        public GroupList(String group, List<StepList> stepLists) {
            this.group = group;
            this.stepLists = stepLists;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public List<StepList> getStepLists() {
            return stepLists;
        }

        public void setStepLists(List<StepList> stepLists) {
            this.stepLists = stepLists;
        }
    }

    public static class StepList {
        //具体附魔数值
        String title;
        //步骤
        String step;
        int needPotential;

        public StepList(String title, String step, int needPotential) {
            this.title = title;
            this.step = step;
            this.needPotential = needPotential;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public int getNeedPotential() {
            return needPotential;
        }

        public void setNeedPotential(int needPotential) {
            this.needPotential = needPotential;
        }
    }
}
