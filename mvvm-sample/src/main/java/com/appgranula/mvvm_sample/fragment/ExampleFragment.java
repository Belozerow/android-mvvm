package com.appgranula.mvvm_sample.fragment;

import android.os.Bundle;

import com.appgranula.mvvm.fragment.BaseBindingFragment;
import com.appgranula.mvvm_sample.databinding.FragmentExampleBinding;
import com.appgranula.mvvm_sample.viewmodel.ExampleViewModel;


/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 23.12.2015
 */
public class ExampleFragment extends BaseBindingFragment<FragmentExampleBinding, ExampleViewModel> {
    public static ExampleFragment newInstance() {
        Bundle args = new Bundle();
        ExampleFragment fragment = new ExampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onViewModelCreated(ExampleViewModel model) {
        super.onViewModelCreated(model);
        getBinding().image.setOnClickListener(view -> replaceFragmentWithBackStack(SecondScreenFragment.newInstance()));
    }
}
