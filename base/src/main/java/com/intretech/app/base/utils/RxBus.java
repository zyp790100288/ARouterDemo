package com.intretech.app.base.utils;


import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * Created by yq06171 on 2017/3/9.
 * PublishRelay 该Subject不会改变事件的发送顺序。如果在已经发送了一部分事件之后注册的observer，是不会收到之前发送的事件的
 * BehaviorRelay 该类有创建时需要一个默认参数，该默认参数会在subject未发送过其他的事件时，向注册的observer发送
 * ReplayRelay 将事件发送到observer，无论什么时候注册observer，无论何时通过该observable发射的所有事件，均会发送给新的observer
 */
public class RxBus {
    private static volatile RxBus instance;
    private final Relay<Object> mBus;



    public RxBus() {
        this.mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = Holder.BUS;
                }
            }
        }
        return instance;
    }
    public void send(Object obj) {
        mBus.accept(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return  mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

}
