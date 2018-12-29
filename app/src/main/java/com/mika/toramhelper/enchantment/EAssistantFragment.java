package com.mika.toramhelper.enchantment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mika.toramhelper.R;
import com.mika.toramhelper.base.BaseFragment;
import com.mika.toramhelper.enchantment.adapter.EOptionAdatper;
import com.mika.toramhelper.enchantment.adapter.EOptionItem;
import com.mika.toramhelper.enchantment.adapter.EOptionItemClickListener;
import com.mika.toramhelper.enchantment.bean.EProperty;
import com.mika.toramhelper.enchantment.bean.EPropertyConstant;
import com.mika.toramhelper.enchantment.bean.EPropertyGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EAssistantFragment extends BaseFragment {


    private int lvValue = 15;

    private List<EPropertyGroup> groupList;
    private ArrayList<String> groupNameItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> propertyNameItems = new ArrayList<>();
    private OptionsPickerView pvOptions;
    private int hisop1, hisop2, selectOptions = -1;

    @BindView(R.id.eoption_recycler)
    RecyclerView eOptionRecyclerView;
    private EOptionAdatper optionAdatper;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<EOptionItem> optionItems = new ArrayList<>();

    public EAssistantFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enchantment_assistant, container, false);
        bind(view);
        initOptionPicker();
        initOptionList();

        return view;
    }

    private void initOptionList() {
        for (int i = 0; i < 6; i++) {
            optionItems.add(new EOptionItem(null, 0));
        }
        optionAdatper = new EOptionAdatper(getContext(), optionItems, lvValue, optionItemClickListener);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        eOptionRecyclerView.setLayoutManager(mLayoutManager);
        eOptionRecyclerView.setAdapter(optionAdatper);

    }

    private EOptionItemClickListener optionItemClickListener = new EOptionItemClickListener() {
        @Override
        public void onClickItem(int position) {
            selectOptions = position;
            pvOptions.show();
        }

        @Override
        public void onClickItemSub(int position) {

        }

        @Override
        public void onClickItemAdd(int position) {

        }
    };

    private void initOptionPicker() {//条件选择器初始化
        pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                selectOption(selectOptions, options1, options2);
            }
        })
                .setTitleText("附魔选择")
                .setSelectOptions(0, 0)//默认选中项
                .isDialog(true)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        hisop1 = options1;
                        hisop2 = options2;
                    }
                })
                .build();
        setPickerValue();
    }

    private void selectOption(int selectOptions, int options1, int options2) {
        if (selectOptions != -1) {
            EOptionItem item = optionItems.get(selectOptions);
            item.setGroup(options1);
            item.setValue(options2);
            item.setProperty(groupList.get(options1).getPropertyList().get(options2));
            optionAdatper.updateData(optionItems);
        }
    }

    private void setPickerValue() {
        groupList = EPropertyConstant.getInstance().getGroupList();
        for (EPropertyGroup group : groupList) {
            //读取一级列表
            groupNameItems.add(group.getName());
            ArrayList<String> propertyList = new ArrayList<>();
            for (EProperty property : group.getPropertyList()) {
                //读取二级列表
                propertyList.add(property.getName() + "(" + (int) property.getPotential() + ")");
            }
            propertyNameItems.add(propertyList);
        }
        pvOptions.setPicker(groupNameItems, propertyNameItems);
    }
}
