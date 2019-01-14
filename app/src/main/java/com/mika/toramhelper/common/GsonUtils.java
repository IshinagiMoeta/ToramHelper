package com.mika.toramhelper.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mika.toramhelper.enchantment.bean.Eformula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moeta on 2019/1/14.
 * e-mail:367195887@qq.com
 * info:
 */

public class GsonUtils {
    private Gson gson;

    private static GsonUtils instance;

    private GsonUtils() {
        gson = new GsonBuilder().create();
    }

    public static GsonUtils getInstance() {
        if (instance == null) {
            instance = new GsonUtils();
        }
        return instance;
    }

    public Gson getGson() {
        return gson;
    }

    public String getJson() {
        String json = null;
        Eformula.StepList stepList = new Eformula.StepList("7%atk 3%cd 16cd 16ci 之类的", "步骤", 15);
        List<Eformula.StepList> stepLists = new ArrayList<>();
        stepLists.add(stepList);
        stepLists.add(stepList);
        Eformula.GroupList physicsList = new Eformula.GroupList("物理职通用", stepLists);
        stepLists.clear();
        Eformula.GroupList phyPropertyList = new Eformula.GroupList("物理职属性", stepLists);
        Eformula.GroupList magicList = new Eformula.GroupList("魔法职通用", stepLists);
        Eformula.GroupList magPropertyList = new Eformula.GroupList("魔法职属性", stepLists);
        Eformula.GroupList tankList = new Eformula.GroupList("坦衣", stepLists);
        Eformula.GroupList artisanList = new Eformula.GroupList("匠装", stepLists);

        List<Eformula.GroupList> groupLists = new ArrayList<>();
        groupLists.add(physicsList);
        groupLists.add(phyPropertyList);
        groupLists.add(magicList);
        groupLists.add(magPropertyList);
        groupLists.add(tankList);
        groupLists.add(artisanList);

        Eformula.TypeList armorList = new Eformula.TypeList(Eformula.ARMOR, groupLists);
        Eformula.TypeList weaponList = new Eformula.TypeList(Eformula.WEAPON, groupLists);

        List<Eformula.TypeList> typeLists = new ArrayList<>();
        typeLists.add(armorList);
        typeLists.add(weaponList);

        Eformula formula = new Eformula(0, typeLists);
        json = gson.toJson(formula);
        return json;
    }
}
