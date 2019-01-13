package com.bawei.week_0112;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author : FangShiKang
 * @date : 2019/01/12.
 * email : fangshikang@outlook.com
 * desc :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
