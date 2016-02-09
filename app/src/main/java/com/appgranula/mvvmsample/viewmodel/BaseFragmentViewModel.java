package com.appgranula.mvvmsample.viewmodel;

import android.content.Context;

import com.appgranula.mvvmsample.Application;

import java.io.Serializable;


/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 23.12.2015
 */
public class BaseFragmentViewModel implements Serializable {

    @SuppressWarnings("unused")
    public Context getContext() {
        return Application.getContext();
    }

    public void onViewCreated() {

    }

    public void onDestroyView() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onModelAttached() {

    }
}
