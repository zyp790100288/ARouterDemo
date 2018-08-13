package com.intretech.app.mvvm.main;

import com.intretech.app.base.utils.LogUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yq06171 on 2018/8/4.
 */
@Singleton
public class UserRepository {
    @Inject
    public UserRepository(){
        LogUtils.printLog("zyp inject userRepostory");
    }
}
