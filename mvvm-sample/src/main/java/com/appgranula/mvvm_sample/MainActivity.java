package com.appgranula.mvvm_sample;

import android.os.Bundle;

import com.appgranula.mvvm.activity.BaseMVVMActivity;
import com.appgranula.mvvm_sample.fragment.ExampleFragment;

public class MainActivity extends BaseMVVMActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            replaceFragment(ExampleFragment.newInstance(), false);
        }
    }
}
