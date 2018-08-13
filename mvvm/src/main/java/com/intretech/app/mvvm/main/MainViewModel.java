package com.intretech.app.mvvm.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

/**
 * Created by yq06171 on 2018/7/31.
 */

public class MainViewModel extends ViewModel {
    private MutableLiveData<User> user;

    @Inject
    public MainViewModel(UserRepository userRepository){

        user = new MutableLiveData<>();
    }

    public MutableLiveData<User> getUser(){
        if(user == null){
            return new MutableLiveData<>();
        }
        return user;
    }

    public void setUser(User user){
        this.user.setValue(user);
    }
}
