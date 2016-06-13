package com.appgranula.mvvm;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created: Belozerov Sergei
 * E-mail: belozerow@gmail.com
 * Company: APPGRANULA LLC
 * Date: 13/06/16
 */
public class ContextHelper {
    public static Context getContext(){
        return MVVMInitializer.getContext();
    }

    public static Resources getResources(){
        return getContext().getResources();
    }
}
