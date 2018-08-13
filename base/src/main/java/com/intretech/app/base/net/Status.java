package com.intretech.app.base.net;

/**
 * Created by yq06171 on 2018/8/3.
 */

/**
 * 网络状态
 * Status of a resource that is provided to the UI.
 * <p>
 * These are usually created by the Repository classes where they return
 * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
 */
public enum Status {
    SUCCESS,
    ERROR,
    LOADING,
    INIT
}
