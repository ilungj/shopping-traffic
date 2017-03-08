package com.ilungj.android.shoppingtraffic;

import android.content.Context;

/**
 * Created by Il Ung on 1/8/2017.
 */

public class Application extends android.app.Application {

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
