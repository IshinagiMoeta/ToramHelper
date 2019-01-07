package com.mika.toramhelper.enchantment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.mika.toramhelper.R;
import com.mika.toramhelper.enchantment.bean.EPropertyConstant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moeta on 2018/12/28.
 * e-mail:367195887@qq.com
 * info:
 */

public class EOptionAdatper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private ArrayList<EOptionItem> optionItemList;
    private EOptionItemClickListener clickListener;
    private int lvLimit = 15;
    private static final int TYPE_NULL = 0;
    private static final int TYPE_DEFAULT = 1;
    private boolean allowOperation = true;


    public EOptionAdatper(Context context, ArrayList<EOptionItem> data, int lvLimit, EOptionItemClickListener listener) {
        this.ctx = context;
        this.optionItemList = data;
        this.lvLimit = lvLimit;
        this.clickListener = listener;
    }

    public void updateData(ArrayList<EOptionItem> data) {
        this.optionItemList = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolderByViewType(parent, viewType);
    }

    private RecyclerView.ViewHolder getViewHolderByViewType(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_eoption_item, parent, false);
        View nullView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_eoption_item_null, parent, false);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_NULL:
                holder = new NullViewHolder(nullView);
                break;
            case TYPE_DEFAULT:
                holder = new DefalutViewHolder(view);
                break;
        }
        return holder;
    }

    public void setAllowOperation(boolean allowOperation) {
        this.allowOperation = allowOperation;
    }

    public void setLvLimit(int lvLimit) {
        this.lvLimit = lvLimit;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (optionItemList.get(position).getProperty() != null) {
            final DefalutViewHolder viewHolder = (DefalutViewHolder) holder;
            final EOptionItem item = optionItemList.get(position);
            viewHolder.name.setText(item.getProperty().getName());
            viewHolder.value.setText(String.valueOf(item.getValue()));

            ((DefalutViewHolder) holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClickItem(position);
                }
            });
            viewHolder.addBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if (!allowOperation) {
                        ToastUtils.showShort(R.string.e_assistant_not_allowOperation_hint);
                        return;
                    }
                    if (item.isDeploy() && item.getValueNum() < 0 && item.getValueNum() == item.getDeployValue()) {
                        return;
                    }
                    if (item.getValueNum() < EPropertyConstant.getInstance().getPropertyLimit(lvLimit, item.getProperty())) {
                        item.setValue(item.getValueNum() + 1);
                        viewHolder.value.setText(item.getValue());
                        viewHolder.value.setText(String.valueOf(item.getValue()));
                        clickListener.onClickItemAdd(position);
                    } else if (EPropertyConstant.getInstance().getPropertyLimit(lvLimit, item.getProperty()) == 0 && item.getValueNum() == 0) {
                        item.setValue(item.getValueNum() + 1);
                        viewHolder.value.setText(item.getValue());
                        viewHolder.value.setText(String.valueOf(item.getValue()));
                        clickListener.onClickItemAdd(position);
                    }
                }
            });
            viewHolder.maxBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!allowOperation) {
                        ToastUtils.showShort(R.string.e_assistant_not_allowOperation_hint);
                        return;
                    }
                    if (item.isDeploy() && item.getValueNum() < 0 && item.getValueNum() == item.getDeployValue()) {
                        return;
                    }
                    if (item.getValueNum() < EPropertyConstant.getInstance().getPropertyLimit(lvLimit, item.getProperty())) {
                        item.setValue(EPropertyConstant.getInstance().getPropertyLimit(lvLimit, item.getProperty()));
                        viewHolder.value.setText(item.getValue());
                        viewHolder.value.setText(String.valueOf(item.getValue()));
                        clickListener.onClickItemMax(position);
                    } else if (EPropertyConstant.getInstance().getPropertyLimit(lvLimit, item.getProperty()) == 0 && item.getValueNum() == 0) {
                        item.setValue(item.getValueNum() + 1);
                        viewHolder.value.setText(item.getValue());
                        viewHolder.value.setText(String.valueOf(item.getValue()));
                        clickListener.onClickItemMax(position);
                    }
                }
            });
            viewHolder.subBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if (!allowOperation) {
                        ToastUtils.showShort(R.string.e_assistant_not_allowOperation_hint);
                        return;
                    }
                    if (item.isDeploy() && item.getValueNum() > 0 && item.getValueNum() == item.getDeployValue()) {
                        return;
                    }
                    if (item.getValueNum() > -EPropertyConstant.getInstance().getPropertyLimit(lvLimit, item.getProperty())) {
                        item.setValue(item.getValueNum() - 1);
                        viewHolder.value.setText(item.getValue());
                        viewHolder.value.setText(String.valueOf(item.getValue()));
                        clickListener.onClickItemSub(position);
                    }
                }
            });

            viewHolder.minBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if (!allowOperation) {
                        ToastUtils.showShort(R.string.e_assistant_not_allowOperation_hint);
                        return;
                    }
                    if (item.isDeploy() && item.getValueNum() > 0 && item.getValueNum() == item.getDeployValue()) {
                        return;
                    }
                    if (item.getValueNum() > -EPropertyConstant.getInstance().getPropertyLimit(lvLimit, item.getProperty())) {
                        item.setValue(-EPropertyConstant.getInstance().getPropertyLimit(lvLimit, item.getProperty()));
                        viewHolder.value.setText(item.getValue());
                        viewHolder.value.setText(String.valueOf(item.getValue()));
                        clickListener.onClickItemMin(position);
                    }
                }
            });

        } else {
            ((NullViewHolder) holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClickItem(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (optionItemList.get(position).getProperty() == null) {
            return TYPE_NULL;
        } else {
            return TYPE_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        return optionItemList.size();
    }

    static class DefalutViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.eoption_item_name)
        TextView name;
        @BindView(R.id.eoption_item_sub)
        View subBtn;
        @BindView(R.id.eoption_item_add)
        View addBtn;
        @BindView(R.id.eoption_item_min)
        View minBtn;
        @BindView(R.id.eoption_item_max)
        View maxBtn;
        @BindView(R.id.eoption_item_value)
        TextView value;
        View view;

        DefalutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }
    }

    public static class NullViewHolder extends RecyclerView.ViewHolder {
        View view;

        NullViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }
    }
}
