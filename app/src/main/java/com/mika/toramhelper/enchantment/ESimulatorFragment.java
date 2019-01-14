package com.mika.toramhelper.enchantment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mika.toramhelper.R;
import com.mika.toramhelper.base.BaseFragment;
import com.mika.toramhelper.common.GsonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ESimulatorFragment extends BaseFragment {


    public ESimulatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_enchantment_simulator, container, false);
        bind(inflate);
        log(GsonUtils.getInstance().getJson());
        return inflate;
    }
}
