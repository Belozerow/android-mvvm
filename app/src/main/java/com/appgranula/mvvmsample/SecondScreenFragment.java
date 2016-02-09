package com.appgranula.mvvmsample;

import android.os.Bundle;

import com.appgranula.mvvmsample.databinding.FragmentSecondScreenBinding;
import com.appgranula.mvvmsample.viewmodel.SecondScreenViewModel;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 25.12.2015
 */
public class SecondScreenFragment extends BaseBindingFragment<FragmentSecondScreenBinding, SecondScreenViewModel> {
    public static SecondScreenFragment newInstance() {

        Bundle args = new Bundle();

        SecondScreenFragment fragment = new SecondScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
