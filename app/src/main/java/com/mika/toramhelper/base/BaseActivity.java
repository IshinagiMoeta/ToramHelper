package com.mika.toramhelper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by moeta on 2018/12/11.
 * e-mail:367195887@qq.com
 * info:
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void bind(){
        ButterKnife.bind(this);
    }

    protected void log(String msg) {
        LogUtils.vTag(this.getLocalClassName(), msg);
    }
}
