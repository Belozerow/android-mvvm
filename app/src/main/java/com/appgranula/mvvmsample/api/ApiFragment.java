package com.appgranula.mvvmsample.api;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 09.02.2016
 */
@SuppressWarnings("unused")
public class ApiFragment extends Fragment implements Api {
    private HashMap<String, HashMap<String, Observable>> observableHashMap = new HashMap<>();

    public static ApiFragment newInstance() {
        Bundle args = new Bundle();
        ApiFragment fragment = new ApiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public <T> Observable<T> executeCached(String modelTag, String methodTag, Observable<T> observable) {
        HashMap<String, Observable> fragmentMap = observableHashMap.get(modelTag);
        if (fragmentMap == null) {
            fragmentMap = new HashMap<>();
            observableHashMap.put(modelTag, fragmentMap);
        }
        if (fragmentMap.get(methodTag) != null) {
            //noinspection unchecked
            return fragmentMap.get(methodTag);
        }
        Observable<T> cached = execute(observable);
        fragmentMap.put(methodTag, cached);
        return cached;
    }

    @Override
    public <T> Observable<T> execute(Observable<T> observable) {
        return observable.cache().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void clearCache(String modelTag) {
        observableHashMap.remove(modelTag);
    }

    @SuppressWarnings("Convert2streamapi")
    @Override
    public void clearCacheExcept(String modelTag) {
        ArrayList<String> keyForRemove = new ArrayList<>();
        for (String key : observableHashMap.keySet()) {
            if (!key.equals(modelTag)) {
                keyForRemove.add(key);
            }
        }
        for (String key : keyForRemove) {
            observableHashMap.remove(key);
        }
    }

    @Override
    public void clearCache(String modelTag, String tag) {
        HashMap<String, Observable> fragmentMap = observableHashMap.get(modelTag);
        if (fragmentMap != null)
            fragmentMap.remove(tag);
    }
}
