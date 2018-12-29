package com.mika.toramhelper.enchantment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ResourceUtils;
import com.mika.toramhelper.R;
import com.mika.toramhelper.base.BaseFragment;
import com.zzhoujay.okhttpimagedownloader.OkHttpImageDownloader;
import com.zzhoujay.richtext.CacheType;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichType;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EDetailsFragment extends BaseFragment {

    @BindView(R.id.details_tv)
    TextView detailsTV;

    public static final String detailsPath = "details/EnchantmentDetails.md";

    String details = null;

    public EDetailsFragment() {
        log("EDetailsFragment");
        details = ResourceUtils.readAssets2String(detailsPath);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_enchantment_details, container, false);

        bind(inflate);

        OkHttpImageDownloader imageDownloader = new OkHttpImageDownloader();

        if (detailsTV != null) {
            RichText.from(details)
                    .noImage(false)
                    .cache(CacheType.all)
                    .imageDownloader(imageDownloader)
                    .type(RichType.markdown)
                    .into(detailsTV);
        }

        return inflate;
    }

}
