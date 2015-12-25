package com.appgranula.mvvmsample.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 23.12.2015
 */
public class ExampleViewModel extends BaseFragmentViewModel {
    public ObservableField<String> imageUrl = new ObservableField<>();

    public interface OnAction {
        void goToNextScreen();
    }

    private OnAction onAction;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        imageUrl.set("http://lorempixel.com/g/400/200/");
    }

    public void onImageClick(View v) {
        onAction.goToNextScreen();
    }
}
