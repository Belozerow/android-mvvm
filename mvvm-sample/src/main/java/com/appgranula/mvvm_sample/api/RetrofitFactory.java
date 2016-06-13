package com.appgranula.mvvm_sample.api;

import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.support.annotation.NonNull;

import com.appgranula.bindingutils.binding.typeadapters.ObservableBooleanTypeAdapter;
import com.appgranula.bindingutils.binding.typeadapters.ObservableIntTypeAdapter;
import com.appgranula.bindingutils.binding.typeadapters.ObservableLongTypeAdapter;
import com.appgranula.bindingutils.binding.typeadapters.ObservableStringTypeAdapter;
import com.appgranula.bindingutils.binding.ObservableBoolean;
import com.appgranula.bindingutils.binding.ObservableString;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 09.02.2016
 */
public class RetrofitFactory {
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 15;
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static Retrofit RETROFIT_INSTANCE = null;
    private static Gson GSON_INSTANCE = null;
    private static GoogleDirectionsApiService GOOGLE_DIRECTIONS_API_SERVICE = null;

    static {
        CLIENT.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

    private RetrofitFactory() {
    }

    public static synchronized Gson getGson() {
        if (GSON_INSTANCE != null) {
            return GSON_INSTANCE;
        }
        GsonBuilder builder = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(ObservableString.class, new ObservableStringTypeAdapter())
                .registerTypeAdapter(ObservableBoolean.class, new ObservableBooleanTypeAdapter())
                .registerTypeAdapter(ObservableLong.class, new ObservableLongTypeAdapter())
                .registerTypeAdapter(ObservableInt.class, new ObservableIntTypeAdapter());
        GSON_INSTANCE = builder.create();
        return GSON_INSTANCE;
    }

    @NonNull
    public static synchronized GoogleDirectionsApiService getGoogleDirectionsApiService() {
        if (GOOGLE_DIRECTIONS_API_SERVICE == null) {
            GOOGLE_DIRECTIONS_API_SERVICE = getRetrofit().create(GoogleDirectionsApiService.class);
        }
        return GOOGLE_DIRECTIONS_API_SERVICE;
    }

    @NonNull
    private static synchronized Retrofit getRetrofit() {
        if (RETROFIT_INSTANCE != null) {
            return RETROFIT_INSTANCE;
        } else {
            RETROFIT_INSTANCE = new Retrofit.Builder()
                    .baseUrl("http://google.com")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(CLIENT)
                    .build();
            if (RETROFIT_INSTANCE.client().interceptors().size() == 0) {
                RETROFIT_INSTANCE.client().interceptors().add(new DebugInterceptor());
            }
            return RETROFIT_INSTANCE;
        }
    }

    private static class DebugInterceptor implements Interceptor {
        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    }

}
