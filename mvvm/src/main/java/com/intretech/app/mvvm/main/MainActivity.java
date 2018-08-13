package com.intretech.app.mvvm.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.intretech.app.mvvm.R;
import com.intretech.app.mvvm.databinding.MvvmActivityMainBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;


/**
 * Created by yq06171 on 2018/7/31.
 */

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
     GithubViewModelFactory mFactory;
    MvvmActivityMainBinding mMainBinding;
    MainViewModel mModel;
    @Inject
    User mUser;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.mvvm_activity_main);
//        mModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
        mModel = new ViewModelProvider(this, mFactory).get(MainViewModel.class);
        mModel.setUser(mUser);
        mModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                mMainBinding.setUser(user);
            }
        });


    }






    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
