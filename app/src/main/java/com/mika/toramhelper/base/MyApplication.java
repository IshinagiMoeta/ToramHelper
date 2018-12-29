package com.mika.toramhelper.base;

import android.app.Application;

import com.zzhoujay.richtext.RichText;

/**
 * Created by moeta on 2018/12/5.
 * e-mail:367195887@qq.com
 * info:
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化RichText
        RichText.initCacheDir(this);
    }
}
