package com.mika.toramhelper.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.blankj.utilcode.util.ToastUtils;
import com.mika.toramhelper.R;
import com.mika.toramhelper.common.Common;

import butterknife.BindView;

public class GratitudeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gratitude_web)
    WebView webView;
    @BindView(R.id.gratitude_reload_btn)
    BootstrapButton reloadBtn;
    @BindView(R.id.gratitude_progress_bar)
    BootstrapProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gratitude);
        bind();
        WebSettings webSettings = webView.getSettings();
        /* 设置支持Js,必须设置的,不然网页基本上不能看 */
        webSettings.setJavaScriptEnabled(true);
        /* 设置WebView是否可以由JavaScript自动打开窗口，默认为false，通常与JavaScript的window.open()配合使用。*/
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /* 设置缓存模式,我这里使用的默认,不做多讲解 */
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        /* 设置为true表示支持使用js打开新的窗口 */
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */
        webSettings.setDomStorageEnabled(true);
        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        webSettings.setGeolocationEnabled(true);
        /* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        webSettings.setBuiltInZoomControls(false);
        /* 提高网页渲染的优先级 */
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        /* 设置显示水平滚动条,就是网页右边的滚动条.我这里设置的不显示 */
        webView.setHorizontalScrollBarEnabled(false);
        /* 指定垂直滚动条是否有叠加样式 */
        webView.setVerticalScrollbarOverlay(true);
        /* 设置滚动条的样式 */
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        /* 这个不用说了,重写WebChromeClient监听网页加载的进度,从而实现进度条 */
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
        /* 同上,重写WebViewClient可以监听网页的跳转和资源加载等等... */
        webView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                ToastUtils.showShort(R.string.mika_load_faild);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        reloadBtn.setOnClickListener(this);

        getUrl();
    }

    private void getUrl() {
        String url = Common.getGratitudeUrl(this);
        if (TextUtils.isEmpty(url)) {
            reloadBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);
        } else {
            webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            reloadBtn.setVisibility(View.GONE);
            webView.loadUrl(url);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.e_details_reload_btn:
                getUrl();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
