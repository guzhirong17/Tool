package com.gzr.tool;

import android.app.Application;
import android.os.Handler;

public class MyApplication extends Application {
    /**
     * app实例
     */
    private static MyApplication sInstance;
    /**
     * 全局Handler
     */
    public static Handler handler = new Handler();

    public static synchronized MyApplication getInstance ()
    {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
