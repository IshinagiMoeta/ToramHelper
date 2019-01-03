package com.mika.toramhelper.enchantment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.sevenheaven.segmentcontrol.SegmentControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EAssistantFragment extends BaseFragment implements View.OnClickListener {


    private static final float CONSUME_COEFFICIENT = (float) 0.3;
    private int lvValue = 15;

    private List<EPropertyGroup> groupList;
    private ArrayList<String> groupNameItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> propertyNameItems = new ArrayList<>();
    private OptionsPickerView pvOptions;
    private int hisop1, hisop2, selectOptions = -1;
    private int potential, defalutPotential, realPotential, hisPotential;

    @BindView(R.id.eoption_recycler)
    RecyclerView eOptionRecyclerView;
    @BindView(R.id.e_assistant_clear_btn)
    Button clearBtn;
    @BindView(R.id.e_assistant_start_btn)
    Button startBtn;
    @BindView(R.id.e_assistant_lv_tv)
    TextView lvTv;
    @BindView(R.id.e_assistant_potential_tv)
    TextView potentialTv;
    @BindView(R.id.e_assistant_defalut_potential_tv)
    TextView defalutPotentialTv;
    @BindView(R.id.e_assistant_success_rate_tv)
    TextView rateTv;
    @BindView(R.id.e_assistant_residue_tv)
    TextView residueTv;
    @BindView(R.id.e_assistant_steps_tv)
    TextView stepsTv;
    @BindView(R.id.e_assistant_consume_tv)
    TextView consumeTv;
    @BindView(R.id.e_assistant_equip_selector)
    SegmentControl equipSelector;


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
        initEvent();
        return view;
    }

    private void initEvent() {
        clearBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        lvTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(lvTv.getText())) {
                    Integer lvNum = Integer.parseInt(String.valueOf(lvTv.getText()));
                    lvValue = lvNum / 10;
                }
            }
        });
        potentialTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(potentialTv.getText())) {
                    potential = Integer.parseInt(String.valueOf(potentialTv.getText()));
                    realPotential = Integer.parseInt(String.valueOf(potentialTv.getText()));
                    hisPotential = Integer.parseInt(String.valueOf(potentialTv.getText()));
                    clearList();
                    updatePage();
                }
            }
        });
    }

    /**
     * 清理页面
     */
    private void clearList() {
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
            updatePage();
        }

        @Override
        public void onClickItemSub(int position) {
            updatePage();
            log("onClickItemSub");
        }

        @Override
        public void onClickItemAdd(int position) {
            updatePage();
            log("onClickItemAdd");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.e_assistant_start_btn:
                break;
            case R.id.e_assistant_clear_btn:
                break;
            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void updatePage() {
        calc();
        String residue = getContext().getResources().getString(R.string.e_assistant_residue);
        residueTv.setText(residue + realPotential + "/" + hisPotential);
    }


    /**
     * 计算
     */
    private void calc() {
        if (potential == 0) {
            return;
        }
        boolean max = true;
        int consumePotential = 0;
        Map<String, Integer> groupMap = new HashMap<>();
        for (EOptionItem item : optionItems) {
            if (item.getProperty() == null) {
                max = false;
                continue;
            }
            if (item.getValue() < 0) {
                consumePotential += item.getValue() * item.getProperty().getPotential() * CONSUME_COEFFICIENT;
            } else {
                consumePotential += item.getValue() * item.getProperty().getPotential();
            }
            //计算同类加成
            Integer num = groupMap.get(String.valueOf(item.getGroup()));
            groupMap.put(String.valueOf(item.getGroup()), num == null ? 1 : num + 1);
        }

        if (max) {
            return;
        }

        for (Object key : groupMap.keySet()) {
            if (groupMap.get(key) > 1) {
                consumePotential = (int) (consumePotential * ((100.0 + groupMap.get(key) * groupMap.get(key) * 5) / 100));
            }
        }
        realPotential = hisPotential - consumePotential;
    }

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
            item.setPosition(options2);
            item.setProperty(groupList.get(options1).getPropertyList().get(options2));
            optionAdatper.updateData(optionItems);
            updatePage();
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
