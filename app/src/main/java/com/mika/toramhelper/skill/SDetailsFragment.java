package com.mika.toramhelper.skill;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mika.toramhelper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SDetailsFragment extends Fragment {


    public SDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skill_details, container, false);
    }

}
