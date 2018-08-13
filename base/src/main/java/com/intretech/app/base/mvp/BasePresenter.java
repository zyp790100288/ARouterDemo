package com.intretech.app.base.mvp;

/**
 * Created by yq06171 on 2017/11/16.
 */

public interface BasePresenter <T extends BaseView> extends BaseLifecycleObserver {
    void attachView(T view);

    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();

    /**
     * RXjava注册
     */
    void subscribe();

    /**
     * RXjava取消注册，以避免内存泄露
     */
    void unSubscribe();


    @Override
    void onDestroy();

}
