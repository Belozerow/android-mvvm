package com.appgranula.mvvm_sample;

import com.appgranula.mvvm.MVVMInitializer;

/**
 * Created: Belozerov Sergei
 * E-mail: belozerow@gmail.com
 * Company: APPGRANULA LLC
 * Date: 13/06/16
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MVVMInitializer.init(this);
    }
}
