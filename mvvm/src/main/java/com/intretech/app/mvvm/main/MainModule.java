package com.intretech.app.mvvm.main;

import com.intretech.app.mvvm.main.di.ActivityScoped;
import com.intretech.app.mvvm.main.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

/**
 * Created by yq06171 on 2018/8/3.
 */

@Module
public abstract class MainModule {

    @Provides
    @ActivityScoped
    public static User getUser(){
        return new User("xtttxx",13,"111111111");
    }

/*    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public static  MainViewModel mMainViewModel(MainViewModel mainViewModel){
        return mainViewModel;
    };*/

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract   MainViewModel mMainViewModel(MainViewModel mainViewModel);





}
