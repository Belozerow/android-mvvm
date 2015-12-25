package com.appgranula.mvvmsample.viewmodel;

import android.databinding.ObservableField;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 25.12.2015
 */
public class SecondScreenViewModel extends BaseFragmentViewModel {
    public ObservableField<String> text = new ObservableField<>();

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        text.set("Hello World!");
    }
}
