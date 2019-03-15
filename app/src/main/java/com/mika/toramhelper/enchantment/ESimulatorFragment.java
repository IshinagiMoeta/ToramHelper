package com.mika.toramhelper.enchantment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mika.toramhelper.R;
import com.mika.toramhelper.base.BaseFragment;
import com.mika.toramhelper.client.EAClient;
import com.mika.toramhelper.common.Common;
import com.mika.toramhelper.common.RetrofitUtils;
import com.mika.toramhelper.enchantment.adapter.ESimulatorAdapter;
import com.mika.toramhelper.enchantment.bean.EFormula;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ESimulatorFragment extends BaseFragment {

    private EFormula formula;

    @BindView(R.id.e_simulator_recycler)
    private RecyclerView mRecyclerView;

    private ESimulatorAdapter simulatorAdapter;

    public ESimulatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_enchantment_simulator, container, false);
        bind(inflate);
        initData();
        initView();
        return inflate;
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        mRecyclerView.setAdapter(simulatorAdapter);
        //设置分隔线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        //设置增加或删除条目的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        Retrofit retrofit = RetrofitUtils.getInstance(getContext()).getRetrofit();
        if (retrofit != null) {
            EAClient client = retrofit.create(EAClient.class);
            Call<EFormula> model = client.repo();
            model.enqueue(new Callback<EFormula>() {
                @Override
                public void onResponse(Call<EFormula> call, Response<EFormula> response) {
                    formula = response.body();
                    LogUtils.d(formula);
                }

                @Override
                public void onFailure(Call<EFormula> call, Throwable t) {
                    LogUtils.d(t.getCause());
                    ToastUtils.showShort(R.string.mika_connectionless);
                }
            });
        } else {
            ToastUtils.showShort(R.string.mika_connectionless);
        }
    }
}
