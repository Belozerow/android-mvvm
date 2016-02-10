package com.appgranula.mvvmsample.fragment;

import android.os.Bundle;
import android.widget.Toast;

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

    @Override
    protected void onViewModelCreated(SecondScreenViewModel model) {
        super.onViewModelCreated(model);
        subscribe(model.calculateDistance(), s -> {
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        });
    }
}
