package com.appgranula.mvvm;

import android.app.Application;
import android.content.Context;

/**
 * Created: Belozerov Sergei
 * E-mail: belozerow@gmail.com
 * Company: APPGRANULA LLC
 * Date: 13/06/16
 */
public class MVVMInitializer {
    private static Application context;

    public static void init(Application application) {
        context = application;
    }

    static Context getContext() {
        return context;
    }
}
