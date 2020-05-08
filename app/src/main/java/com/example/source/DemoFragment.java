package com.example.source;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * @time 2020/4/1 20:50
 * @desc
 */
public class DemoFragment extends Fragment {

    public static DemoFragment newInstance() {

        Bundle args = new Bundle();

        DemoFragment fragment = new DemoFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
