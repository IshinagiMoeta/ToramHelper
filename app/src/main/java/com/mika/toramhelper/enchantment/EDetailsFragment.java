package com.mika.toramhelper.enchantment;


import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.ResourceUtils;
import com.mika.toramhelper.R;
import com.mika.toramhelper.base.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EDetailsFragment extends BaseFragment {

    @BindView(R.id.details_web)
    WebView webView;

    public static final String detailsPath = "details/EnchantmentDetails.html";

    String details = null;

    public EDetailsFragment() {
        log("EDetailsFragment");
        details = ResourceUtils.readAssets2String(detailsPath);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_enchantment_details, container, false);

        bind(inflate);
        webView.getSettings().setJavaScriptEnabled(true);//启用js
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受所有网站的证书
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.loadUrl("file:///android_asset/details/EnchantmentDetails.html");
        return inflate;
    }

}
