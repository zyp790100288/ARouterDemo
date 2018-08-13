package com.intretech.app.mvvm.main.di;

import android.support.v4.app.Fragment;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Created by yq06171 on 2018/8/6.
 */

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface FragmentKey {
    Class<? extends Fragment> value();
}
