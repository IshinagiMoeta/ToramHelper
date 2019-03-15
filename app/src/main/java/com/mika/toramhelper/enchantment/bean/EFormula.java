package com.mika.toramhelper.enchantment.bean;

import android.support.annotation.IntDef;

import java.util.List;

/**
 * Created by moeta on 2019/1/10.
 * e-mail:367195887@qq.com
 * info:
 */

public class EFormula {
    //武器
    public static final int WEAPON = 0;
    //防具
    public static final int ARMOR  = 1;

    private int                 code;
    private List<TypeListsBean> typeLists;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<TypeListsBean> getTypeLists() {
        return typeLists;
    }

    public void setTypeLists(List<TypeListsBean> typeLists) {
        this.typeLists = typeLists;
    }


    public static class TypeListsBean {
        private int                  type;
        private List<GroupListsBean> groupLists;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<GroupListsBean> getGroupLists() {
            return groupLists;
        }

        public void setGroupLists(List<GroupListsBean> groupLists) {
            this.groupLists = groupLists;
        }

        public static class GroupListsBean {

            private String              group;
            private List<StepListsBean> stepLists;

            public String getGroup() {
                return group;
            }

            public void setGroup(String group) {
                this.group = group;
            }

            public List<StepListsBean> getStepLists() {
                return stepLists;
            }

            public void setStepLists(List<StepListsBean> stepLists) {
                this.stepLists = stepLists;
            }

            public static class StepListsBean {
                /**
                 * needPotential : 16潜力
                 * step : 1)1%爆伤
                 * 16-10=6
                 * 2) 1爆伤
                 * 6-3=3
                 * 3)退6%魔攻
                 * 3+36*1.2=46
                 * 4)附到16爆伤(一次附一爆伤
                 * 46-3*15=1
                 * 5)附1暴击、退7命中、7%魔攻
                 * 1+(42+6-1)*1.45=69
                 * 6)附到16(+1)暴击(一次附1~2
                 * 69-15=54
                 * 7)附满，爆伤
                 * %(+1%)，-10%仇恨
                 * (60-18)*1.45=60-54=6
                 * 成功率：130+230/54*-6=104%
                 * title : 7%cd 16cd 16c -10%仇恨
                 */

                private String needPotential;
                private String step;
                private String title;

                public String getNeedPotential() {
                    return needPotential;
                }

                public void setNeedPotential(String needPotential) {
                    this.needPotential = needPotential;
                }

                public String getStep() {
                    return step;
                }

                public void setStep(String step) {
                    this.step = step;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}
