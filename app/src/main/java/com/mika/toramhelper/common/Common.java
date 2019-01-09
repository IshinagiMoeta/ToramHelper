package com.mika.toramhelper.common;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mika.toramhelper.R;
import com.tencent.stat.StatConfig;

/**
 * Created by moeta on 2019/1/9.
 * e-mail:367195887@qq.com
 * info:
 */

public class Common {

    public static final String ENCHANTMENT_DETAILS_URL = "enchantment_details_url";

    public static String getEnchantmentDetailsUrl(Context ctx) {
        String url = StatConfig.getCustomProperty(ctx, ENCHANTMENT_DETAILS_URL);
        LogUtils.v(StatConfig.getCustomGlobalReportContent());
        LogUtils.v(url);
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showShort(R.string.mika_connectionless);
            url = null;
        }
        return url;
    }
}
