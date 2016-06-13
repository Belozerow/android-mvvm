package com.appgranula.mvvm_sample.api;

import com.squareup.okhttp.ResponseBody;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 09.02.2016
 */
public interface GoogleDirectionsApiService {
    @GET("https://maps.googleapis.com/maps/api/directions/json?language=ru")
    Observable<ResponseBody> getGoogleDirection(@Query("origin") String origin,
                                                @Query("destination") String destination);
}
