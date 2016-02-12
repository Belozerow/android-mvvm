package com.appgranula.mvvmsample.viewmodel;

import android.content.Context;

import com.appgranula.mvvmsample.Application;
import com.appgranula.mvvmsample.api.Api;

import java.io.Serializable;

import rx.Observable;


/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 23.12.2015
 */
public class BaseFragmentViewModel implements Serializable {
    private Api api;

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

    public String getModelTag() {
        return getClass().getSimpleName();
    }

    public void setApi(Api api) {
        this.api = api;
    }

    @SuppressWarnings("unused")
    public <T> Observable<T> execute(Observable<T> observable) {
        return api.execute(observable);
    }

    @SuppressWarnings("unused")
    public <T> Observable<T> executeCached(Observable<T> observable) {
        return api.executeCached(getModelTag(), getModelTag(), observable);
    }

    @SuppressWarnings("unused")
    public <T> Observable<T> reExecuteCached(Observable<T> observable) {
        return reExecuteCached(getModelTag(), observable);
    }

    public <T> Observable<T> reExecuteCached(String tag, Observable<T> observable) {
        clearCache(tag);
        return executeCached(tag, observable);
    }

    @SuppressWarnings("unused")
    public <T> Observable<T> executeCached(String tag, Observable<T> observable) {
        return api.executeCached(getModelTag(), tag, observable);
    }

    @SuppressWarnings("unused")
    public void clearCache() {
        api.clearCache(getModelTag());
    }

    @SuppressWarnings("unused")
    public void clearCache(String tag) {
        api.clearCache(getModelTag(), tag);
    }
}
