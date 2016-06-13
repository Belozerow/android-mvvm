package com.appgranula.mvvm.api;

import rx.Observable;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 09.02.2016
 */
public interface BaseMVVMApi {
    <T> Observable<T> executeCached(String modelTag, String tag, Observable<T> observable);

    <T> Observable<T> execute(Observable<T> observable);

    void clearCache(String modelTag);

    void clearCacheExcept(String modelTag);

    void clearCache(String modelTag, String tag);
}
