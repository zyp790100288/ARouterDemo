package com.intretech.app.base.mvp;

/**
 * Created by yq06171 on 2017/11/16.
 */

public interface BaseView {
    void showProgress();

    void hideProgress();

    void networkError(Throwable throwable);
}
