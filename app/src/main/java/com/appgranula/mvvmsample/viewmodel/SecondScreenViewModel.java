package com.appgranula.mvvmsample.viewmodel;

import com.appgranula.mvvmsample.api.retrofit.RetrofitFactory;
import com.appgranula.mvvmsample.binding.ObservableString;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 25.12.2015
 */
public class SecondScreenViewModel extends BaseFragmentViewModel {
    public ObservableString text = new ObservableString();

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        text.set("Hello World!");
    }

    public Observable<String> calculateDistance() {
        String original = "55.1921269,61.3305967";
        String destination = "55.171783,61.0920729";
        Observable<String> result = executeCached(RetrofitFactory.getGoogleDirectionsApiService().getGoogleDirection(original, destination).flatMap(responseBody -> {
            try {
                return Observable.just(responseBody.string());
            } catch (IOException e) {
                return Observable.just(e.getMessage());
            }
        }).delaySubscription(5, TimeUnit.SECONDS));
        result.subscribe(text::set);
        return result;
    }
}
