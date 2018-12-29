package com.mika.toramhelper.strategy;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mika.toramhelper.R;
import com.mika.toramhelper.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StrategyFragment extends BaseFragment {


    public StrategyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_strategy, container, false);
    }

}
