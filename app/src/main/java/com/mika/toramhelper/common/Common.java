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

    private static final String BASE_URL                = "base_url";
    private static final String ENCHANTMENT_DETAILS_URL = "enchantment_details_url";
    private static final String GRATITUDE_URL           = "gratitude_url";
    private static final String ENCHANTMENT_FORMULA_URL = "enchantment_formula_url";

    private static final String DEFAULT_BASE_URL      = "http://174.137.62.128";
    private static final String DEFAULT_DETAILS_URL   = "http://174.137.62.128/EnchantmentDetails.html";
    private static final String DEFAULT_GRATITUDE_URL = "http://174.137.62.128/Gratitude.html";
    private static final String DEFAULT_FORMULA_URL   = "http://174.137.62.128/EnchantmentFormula.json";

    public static String getBaseUrl(Context ctx) {
        String url = StatConfig.getCustomProperty(ctx, BASE_URL);
        LogUtils.v(StatConfig.getCustomGlobalReportContent());
        LogUtils.v(url);
        if (TextUtils.isEmpty(url)) {
            LogUtils.d(R.string.mika_connectionless);
            url = DEFAULT_BASE_URL;
        }
        return url;
    }

    public static String getEnchantmentDetailsUrl(Context ctx) {
        String url = StatConfig.getCustomProperty(ctx, ENCHANTMENT_DETAILS_URL);
        LogUtils.v(StatConfig.getCustomGlobalReportContent());
        LogUtils.v(url);
        if (TextUtils.isEmpty(url)) {
            LogUtils.d(R.string.mika_connectionless);
            url = DEFAULT_DETAILS_URL;
        }
        return url;
    }

    public static String getGratitudeUrl(Context ctx) {
        String url = StatConfig.getCustomProperty(ctx, GRATITUDE_URL);
        LogUtils.v(StatConfig.getCustomGlobalReportContent());
        LogUtils.v(url);
        if (TextUtils.isEmpty(url)) {
            LogUtils.d(R.string.mika_connectionless);
            url = DEFAULT_GRATITUDE_URL;
        }
        return url;
    }

    public static String getEnchantmentFormulaUrl(Context ctx) {
        String url = StatConfig.getCustomProperty(ctx, ENCHANTMENT_FORMULA_URL);
        LogUtils.v(StatConfig.getCustomGlobalReportContent());
        LogUtils.v(url);
        if (TextUtils.isEmpty(url)) {
            LogUtils.d(R.string.mika_connectionless);
            url = DEFAULT_FORMULA_URL;
        }
        return url;
    }
}
