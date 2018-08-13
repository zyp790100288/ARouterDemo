package com.intretech.app.practice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.intretech.app.base.ARouterPath;
import com.intretech.app.base.BaseActivity;
import com.intretech.app.base.data.BusEvent;
import com.intretech.app.base.utils.RxBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by yq06171 on 2018/7/13.
 */
@Route(path = ARouterPath.TEST1_ACTIVITY)
public class Test1Activity extends BaseActivity {
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        findViewById();
        init();
    }

    @Override
    protected void findViewById() {
        mTextView = findViewById(R.id.tv_test);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
            mTextView.setText(name);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void reLayout() {

    }

    @Override
    protected void init() {
        RxBus.getInstance().toObservable().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        BusEvent<String> busEvent = (BusEvent<String>) o;
                        Toast.makeText(Test1Activity.this,busEvent.getData(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
