package com.mika.toramhelper.enchantment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
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


    private static final int WEAPON_INDEX = 0;
    private static final int ARMOR_INDEX = 1;
    private static final int ADDITIONAL_FACTOR = 2;
    private static double CONSUME_COEFFICIENT = (float) 0.305;
    private List<EPropertyGroup> groupList;

    private ArrayList<String> groupNameItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> propertyNameItems = new ArrayList<>();
    private OptionsPickerView pvOptions;
    private boolean allowOperation = true;
    private boolean weapon = true;
    private boolean potentialLock = false;
    private int lvValue = 16;
    private int stepNum = 0;
    private int selectOptions = -1;
    private int potential, defalutPotential, realPotential, hisPotential;
    private int successRate = 100;
    private StringBuilder stepBuilder, materialBuilder;

    @BindView(R.id.eoption_recycler)
    RecyclerView eOptionRecyclerView;
    @BindView(R.id.e_assistant_clear_btn)
    BootstrapButton clearBtn;
    @BindView(R.id.e_assistant_start_btn)
    BootstrapButton startBtn;
    @BindView(R.id.e_assistant_lv_tv)
    BootstrapEditText lvTv;
    @BindView(R.id.e_assistant_point_tv)
    BootstrapEditText pointTv;
    @BindView(R.id.e_assistant_potential_tv)
    BootstrapEditText potentialTv;
    @BindView(R.id.e_assistant_defalut_potential_tv)
    BootstrapEditText defalutPotentialTv;
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
    @BindView(R.id.e_assistant_potential_lock)
    ImageView potentialImg;
    @BindView(R.id.e_assistant_defalut_potential_hint_img)
    ImageView potentialHintImg;


    private EOptionAdatper optionAdatper;
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
        clearPage();
        return view;
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        clearBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        potentialTv.setOnClickListener(this);
        potentialImg.setOnClickListener(this);
        potentialHintImg.setOnClickListener(this);

        equipSelector.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                if (WEAPON_INDEX == index) {
                    weapon = true;
                } else if (ARMOR_INDEX == index) {
                    weapon = false;
                }
                clearPage();
            }
        });

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
                    optionAdatper.setLvLimit(lvValue);
                    clearPage();
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
                }
                updatePage();
            }
        });
        defalutPotentialTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(defalutPotentialTv.getText())) {
                    defalutPotential = Integer.parseInt(String.valueOf(defalutPotentialTv.getText()));
                    updatePage();
                }
            }
        });
        pointTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(pointTv.getText())) {
                    int point = Integer.parseInt(String.valueOf(pointTv.getText()));
                    CONSUME_COEFFICIENT = ((double) point * 0.001 + 0.05);
                    log(String.valueOf(CONSUME_COEFFICIENT));
                    clearPage();
                }
            }
        });
    }

    /**
     * 初始化能力列表
     */
    private void initOptionList() {
        for (int i = 0; i < 6; i++) {
            optionItems.add(new EOptionItem(null, 0));
        }
        optionAdatper = new EOptionAdatper(getContext(), optionItems, lvValue, optionItemClickListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        eOptionRecyclerView.setLayoutManager(mLayoutManager);
        eOptionRecyclerView.setAdapter(optionAdatper);
    }

    /**
     * 能力列表事件监听器
     */
    private EOptionItemClickListener optionItemClickListener = new EOptionItemClickListener() {
        @Override
        public void onClickItem(int position) {
            if (optionItems.get(position).isDeploy() || realPotential == 0 || !allowOperation) {
                if (potential == 0) {
                    ToastUtils.showShort(R.string.e_assistant_no_fill_potential_hint);
                }
                if (!allowOperation) {
                    ToastUtils.showShort(R.string.e_assistant_not_allowOperation_hint);
                }
                return;
            }
            selectOptions = position;
            pvOptions.show();
            updatePage();
        }

        @Override
        public void onClickItemSub(int position) {
            updatePage();
        }

        @Override
        public void onClickItemAdd(int position) {
            updatePage();
        }

        @Override
        public void onClickItemMax(int position) {
            updatePage();
        }

        @Override
        public void onClickItemMin(int position) {
            updatePage();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.e_assistant_start_btn:
                //点击开始强化按钮
                if (allowOperation) {
                    startEnchantment();
                } else {
                    ToastUtils.showShort(R.string.e_assistant_not_allowOperation_hint);
                }
                break;
            case R.id.e_assistant_clear_btn:
                clearPage();
                break;
            case R.id.e_assistant_potential_tv:
            case R.id.e_assistant_potential_lock:
                changePotentialHint();
                break;
            case R.id.e_assistant_defalut_potential_hint_img:
                popupPotentialHint();
                break;
            default:
                break;
        }
    }

    private void popupPotentialHint() {
        AlertDialog.Builder hintDialogBuild =
                new AlertDialog.Builder(getContext());
        hintDialogBuild.setTitle(StringUtils.getString(R.string.mika_hint));
        hintDialogBuild.setMessage(StringUtils.getString(R.string.e_assistant_default_potential_hint));
        hintDialogBuild.show();
    }

    /**
     * 解锁提示
     */
    private void changePotentialHint() {
        if (potentialLock) {
            AlertDialog.Builder hintDialogBuild =
                    new AlertDialog.Builder(getContext());
            hintDialogBuild.setTitle(StringUtils.getString(R.string.mika_hint));
            hintDialogBuild.setMessage(StringUtils.getString(R.string.e_assistant_change_potential_hint));
            hintDialogBuild.setPositiveButton(StringUtils.getString(R.string.mika_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            unLockPotential();
                            clearPage();
                        }
                    });
            hintDialogBuild.setNegativeButton(StringUtils.getString(R.string.mika_close),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            hintDialogBuild.show();
        } else {
            potentialTv.setText("");
        }
    }

    /**
     * 点击强化
     */
    private void startEnchantment() {
        lockPotential();
        for (EOptionItem item : optionItems) {
            if (item.getProperty() != null && item.getValueNum() != 0) {
                item.setDeploy(true);
                item.setDeployValue(item.getValueNum());
            }
            allowOperation = item.getProperty() == null;
            optionAdatper.setAllowOperation(allowOperation);
        }
        if (realPotential < 0) {
            realPotential = 0;
            allowOperation = false;
            optionAdatper.setAllowOperation(allowOperation);
        }
        outputStep();
        outputMaterial();
        hisPotential = realPotential;
        updatePage();
    }

    /**
     * 输出素材
     */
    private void outputMaterial() {
        int metal = 0, cloth = 0, beast = 0, wood = 0, drug = 0, magic = 0;
        for (EOptionItem item : optionItems) {
            if (item.getProperty() != null) {
                switch (item.getProperty().getMaterialType()) {
                    case EProperty.METAL:
                        metal = metal + getMaterial(item.getValueNum(), item.getProperty().getMaterialValue());
                        break;
                    case EProperty.CLOTH:
                        cloth = cloth + getMaterial(item.getValueNum(), item.getProperty().getMaterialValue());
                        break;
                    case EProperty.BEAST:
                        beast = beast + getMaterial(item.getValueNum(), item.getProperty().getMaterialValue());
                        break;
                    case EProperty.WOOD:
                        wood = wood + getMaterial(item.getValueNum(), item.getProperty().getMaterialValue());
                        break;
                    case EProperty.DRUG:
                        drug = drug + getMaterial(item.getValueNum(), item.getProperty().getMaterialValue());
                        break;
                    case EProperty.MAGIC:
                        magic = magic + getMaterial(item.getValueNum(), item.getProperty().getMaterialValue());
                        break;
                }
            }
        }
        materialBuilder = null;
        materialBuilder = new StringBuilder();
        materialBuilder.append("消耗素材：").append("\n");
        if (metal != 0) {
            materialBuilder.append("消耗金属：").append(metal).append("pt").append("\n");
        }
        if (cloth != 0) {
            materialBuilder.append("消耗布料：").append(cloth).append("pt").append("\n");
        }
        if (beast != 0) {
            materialBuilder.append("消耗兽品：").append(beast).append("pt").append("\n");
        }
        if (wood != 0) {
            materialBuilder.append("消耗木材：").append(wood).append("pt").append("\n");
        }
        if (drug != 0) {
            materialBuilder.append("消耗药品：").append(drug).append("pt").append("\n");
        }
        if (magic != 0) {
            materialBuilder.append("消耗魔素：").append(magic).append("pt").append("\n");
        }
        consumeTv.setText(materialBuilder);
    }

    private int getMaterial(int value, double defalutMaterial) {
        return (int) (defalutMaterial * (value * (value + 1) * (2 * value + 1)) / 6);
    }

    /**
     * 输出步骤
     */
    private void outputStep() {
        if (potential == 0) {
            return;
        }
        if (stepBuilder == null) {
            stepBuilder = new StringBuilder();
        }
        stepNum++;
        stepBuilder.append(stepNum).append("、附");
        for (EOptionItem item : optionItems) {
            if (item.getProperty() != null) {
                int value = item.getValueNum() * item.getProperty().getValue();
                String valueStr;
                if (value > 0) {
                    valueStr = "+" + value;
                } else {
                    valueStr = String.valueOf(value);
                }
                stepBuilder.append(item.getProperty().getName()).append(" ").append(valueStr).append(" | ");
            }
        }
        if (stepBuilder.length() != 0 && stepBuilder.lastIndexOf("|") >= 0) {
            stepBuilder.deleteCharAt(stepBuilder.lastIndexOf("|"));
        }
        stepBuilder.append("剩余潜力:").append(realPotential).append(" ");
        String rate = StringUtils.getString(R.string.e_assistant_success_rate);
        stepBuilder.append(rate).append(successRate).append("%");
        stepBuilder.append("\n");
        stepsTv.setText(stepBuilder);
    }

    /**
     * 清理页面
     */
    @SuppressLint("SetTextI18n")
    private void clearPage() {
        allowOperation = true;
        optionAdatper.setAllowOperation(true);
        if (!TextUtils.isEmpty(potentialTv.getText())) {
            potential = Integer.parseInt(String.valueOf(potentialTv.getText()));
            realPotential = Integer.parseInt(String.valueOf(potentialTv.getText()));
            hisPotential = Integer.parseInt(String.valueOf(potentialTv.getText()));
        }

        for (EOptionItem item : optionItems) {
            item.setProperty(null);
            item.setValue(0);
            item.setPosition(0);
            item.setGroup(0);
            item.setDeploy(false);
            item.setDeployValue(0);
        }
        optionAdatper.updateData(optionItems);
        String residue = StringUtils.getString(R.string.e_assistant_residue);
        residueTv.setText(residue + 0 + "/" + 0);
        String rate = StringUtils.getString(R.string.e_assistant_success_rate);
        rateTv.setText(rate + 100 + "%");
        unLockPotential();
        stepBuilder = null;
        materialBuilder = null;
        stepsTv.setText("");
        consumeTv.setText("");
        updatePage();
    }

    /**
     * 更新页面
     */
    @SuppressLint("SetTextI18n")
    private void updatePage() {
        calc();
        String residue = StringUtils.getString(R.string.e_assistant_residue);
        residueTv.setText(residue + realPotential + "/" + hisPotential);
        int denominator = hisPotential > defalutPotential ? hisPotential : defalutPotential;
        if (denominator != 0) {
            successRate = 130 + (int) (230 * (float) realPotential / denominator);
        }
        if (successRate > 100) {
            successRate = 100;
        } else if (successRate < 0) {
            successRate = 0;
        }
        successRate = successRate < 100 ? successRate : 100;
        String rate = StringUtils.getString(R.string.e_assistant_success_rate);
        rateTv.setText(rate + successRate + "%");
    }


    /**
     * 计算潜力值
     */
    private void calc() {
        if (potential == 0) {
            return;
        }
        if (!allowOperation) {
            return;
        }
        int consumePotential = 0;
        Map<String, Integer> groupMap = new HashMap<>();
        for (EOptionItem item : optionItems) {
            if (item.getProperty() == null) {
                continue;
            }

            int onesConsumePotential;
            if (item.isDeploy()) {
                onesConsumePotential = (item.getValueNum() - item.getDeployValue()) * item.getProperty().getPotential();
            } else {
                onesConsumePotential = item.getValueNum() * item.getProperty().getPotential();
            }

            //双倍退潜项
            if (item.getGroup() == 3 || item.getGroup() == 5) {
                //攻击力选项
                if (!weapon) {
                    onesConsumePotential = onesConsumePotential * ADDITIONAL_FACTOR;
                }
            }

            if (item.getGroup() == 2 || item.getGroup() == 6 || item.getGroup() == 3) {
                //防御力选项
                if (weapon) {
                    onesConsumePotential = onesConsumePotential * ADDITIONAL_FACTOR;
                }
            }

            if (item.getGroup() == 9) {
                if (item.getPosition() <= 5) {
                    if (!weapon) {
                        onesConsumePotential = onesConsumePotential * ADDITIONAL_FACTOR;
                    }
                } else {
                    if (weapon) {
                        onesConsumePotential = onesConsumePotential * ADDITIONAL_FACTOR;
                    }
                }
            }

            if (item.getValueNum() < 0) {
                onesConsumePotential = (int) (onesConsumePotential * CONSUME_COEFFICIENT);
                consumePotential += onesConsumePotential;
            } else {
                consumePotential += onesConsumePotential;
            }
            Integer num = groupMap.get(String.valueOf(item.getGroup()));
            groupMap.put(String.valueOf(item.getGroup()), num == null ? 1 : num + 1);
        }
        //计算同类加成
        for (String key : groupMap.keySet()) {
            if (groupMap.get(key) > 1) {
                consumePotential = (int) (consumePotential * ((100.0 + groupMap.get(key) * groupMap.get(key) * 5) / 100));
            }
        }
        realPotential = hisPotential - consumePotential;
    }

    /**
     * 条件选择器初始化
     */
    private void initOptionPicker() {
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

                    }
                })
                .build();
        setPickerValue();
    }

    /**
     * 条件选择器设值
     */
    private void setPickerValue() {
        groupList = EPropertyConstant.getInstance().getGroupList();
        for (EPropertyGroup group : groupList) {
            //读取一级列表
            groupNameItems.add(group.getName());
            ArrayList<String> propertyList = new ArrayList<>();
            for (EProperty property : group.getPropertyList()) {
                //读取二级列表
                propertyList.add(property.getName() + "(" + property.getPotential() + ")");
            }
            propertyNameItems.add(propertyList);
        }
        pvOptions.setPicker(groupNameItems, propertyNameItems);
    }

    /**
     * 条件选择器做出选择
     *
     * @param selectOptions 触发选择器的列表位置
     * @param options1      选择出的组
     * @param options2      选择出的列
     */
    private void selectOption(int selectOptions, int options1, int options2) {
        if (options1 == 0 && options2 == 0) {
            EOptionItem nullItem = new EOptionItem(null, 0);
            optionItems.set(selectOptions, nullItem);
            optionAdatper.updateData(optionItems);
            updatePage();
            return;
        }
        if (!weapon) {
            if (options1 == groupList.size() - 1 || options1 == groupList.size() - 2) {
                ToastUtils.showShort(R.string.e_assistant_no_choose_hint);
                return;
            }
        }
        for (EOptionItem item : optionItems) {
            if (item.getGroup() == options1 && item.getPosition() == options2) {
                if (optionItems.get(selectOptions) != null && optionItems.get(selectOptions).getProperty() != null) {
                    if (item.getProperty().getName().equals(optionItems.get(selectOptions).getProperty().getName())) {
                        return;
                    }
                }
                ToastUtils.showShort(R.string.e_assistant_repetition_hint);
                return;
            }
        }
        if (selectOptions != -1) {
            EOptionItem item = optionItems.get(selectOptions);
            item.setGroup(options1);
            item.setPosition(options2);
            item.setValue(0);
            item.setProperty(groupList.get(options1).getPropertyList().get(options2));
            optionAdatper.updateData(optionItems);
            updatePage();
        }
    }

    private void lockPotential() {
        if (!potentialLock) {
            potentialLock = true;
            potentialImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_enchantment_lock));
        }
    }

    private void unLockPotential() {
        if (potentialLock) {
            potentialLock = false;
            potentialImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_enchantment_unlock));
        }
    }
}
