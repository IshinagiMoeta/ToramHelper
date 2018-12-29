package com.mika.toramhelper.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by moeta on 2018/12/24.
 * e-mail:367195887@qq.com
 * info:
 */

public class BaseFragment extends Fragment {


    protected void bind(View root) {
        ButterKnife.bind(this, root);
    }

    protected void log(String msg) {
        LogUtils.vTag(this.getClass().getName(), msg);
    }
}
