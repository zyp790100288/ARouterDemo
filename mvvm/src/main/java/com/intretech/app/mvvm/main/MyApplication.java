package com.intretech.app.mvvm.main;


import com.intretech.app.mvvm.main.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by yq06171 on 2018/8/3.
 */

public class MyApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
