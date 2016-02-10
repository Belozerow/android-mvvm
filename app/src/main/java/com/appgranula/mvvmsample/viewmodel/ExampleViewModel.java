package com.appgranula.mvvmsample.viewmodel;

import com.appgranula.mvvmsample.binding.ObservableString;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 23.12.2015
 */
public class ExampleViewModel extends BaseFragmentViewModel {
    public ObservableString imageUrl = new ObservableString();

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        imageUrl.set("http://lorempixel.com/g/400/200/");
    }
}
