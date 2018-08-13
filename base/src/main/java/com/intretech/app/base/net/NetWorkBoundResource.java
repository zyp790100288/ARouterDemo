package com.intretech.app.base.net;

/**
 * Created by yq06171 on 2018/8/2.
 */

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * LiveData + rxjava2.0 + retrofit2.0
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetWorkBoundResource<ResultType, RequestType> {

  /*  private Resource<ResultType> result = new Resource<>(Status.INIT,null,"");*/
//    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private final MutableLiveData<Resource<ResultType>> result = new MutableLiveData<>();


    public NetWorkBoundResource(){
        ResultType resultType = loadFromDb();
        if(shouldFetch(resultType)){
            fetchFromNetwork();
        }else{
            result.setValue(new Resource<ResultType>(Status.SUCCESS,resultType,""));

        }

    }

    /**
     * 从网络获取数据
     */
    private void fetchFromNetwork() {
        Observable<RequestType> observable = createCall();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RequestType>() {
                    @Override
                    public void accept(RequestType requestType) throws Exception {
                        ResultType mRequestType = convertToApiForm(requestType);
                        saveCallResult(mRequestType);
                        result.setValue(new Resource<ResultType>(Status.SUCCESS, mRequestType, ""));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onFetchFailed();
                        result.setValue(new Resource<ResultType>(Status.ERROR, null, throwable.toString()));
                    }
                });
    }




    /**
     * 保存网络数据
     * @param item
     */
    @WorkerThread
    protected abstract void saveCallResult(@NonNull ResultType item);

    // Called with the data in the database to decide whether it should be
    // fetched from the network.

    /**
     * 是否需要网络获取数据
     * @param data
     * @return
     */
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    /**
     * 数据库获取数据
     * @return
     */
    // Called to get the cached data from the database
    @NonNull @MainThread
    protected abstract ResultType loadFromDb();

    /**
     * 网络请求
     * @return
     */
    // Called to create the API call.
    @NonNull @MainThread
    protected abstract Observable<RequestType> createCall();

    /**
     * 请求失败
     */
    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected abstract void onFetchFailed();

    /**
     * 协议如果加密数据转换
     * @param result
     * @return
     */
    protected abstract ResultType  convertToApiForm(RequestType result) ;

    // returns a LiveData that represents the resource
    public final MutableLiveData<Resource<ResultType>> getAsResult() {
        return  result;
    }

}
