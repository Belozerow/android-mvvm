package com.appgranula.mvvmsample.activity;

import android.os.Bundle;

import com.appgranula.mvvmsample.ExampleFragment;
import com.appgranula.mvvmsample.R;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 25.12.2015
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            replaceFragment(ExampleFragment.newInstance(), false);
        }
    }
}
