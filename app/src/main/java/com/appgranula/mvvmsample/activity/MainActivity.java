package com.appgranula.mvvmsample.activity;

import android.os.Bundle;

import com.appgranula.mvvmsample.fragment.ExampleFragment;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 25.12.2015
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            replaceFragment(ExampleFragment.newInstance(), false);
        }
    }
}
