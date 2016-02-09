package com.appgranula.mvvmsample.viewmodel;

import android.databinding.ObservableField;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 23.12.2015
 */
public class ExampleViewModel extends BaseFragmentViewModel {
    public ObservableField<String> imageUrl = new ObservableField<>();

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        imageUrl.set("http://lorempixel.com/g/400/200/");
    }
}
