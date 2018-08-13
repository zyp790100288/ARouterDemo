package com.intretech.app.arouterdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.intretech.app.base.ARouterPath;
import com.intretech.app.base.BaseActivity;
import com.intretech.app.base.GlobalConfig;
import com.intretech.app.base.data.BusEvent;
import com.intretech.app.base.service.HelloServiceImpl;
import com.intretech.app.base.utils.RxBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    Button btnZxing;
    Button btnTest1;
    Button btnTest2;
    Button btnTest3;
    Button btnTest4;
    Button btnTest5;
    Button btnTest6;
    Button btnTest7;
    Button btnTest8;
    Button btnTest9;
    Button btnTest10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setListener();
        init();


    }

    @Override
    protected void findViewById() {
        btnZxing = findViewById(R.id.btn_zxing);
        btnTest1 = findViewById(R.id.btn_test1);
        btnTest2 = findViewById(R.id.btn_test2);
        btnTest3 = findViewById(R.id.btn_test3);
        btnTest4 = findViewById(R.id.btn_test4);
        btnTest5 = findViewById(R.id.btn_test5);
        btnTest6 = findViewById(R.id.btn_test6);
        btnTest7 = findViewById(R.id.btn_test7);
        btnTest8 = findViewById(R.id.btn_test8);
        btnTest9 = findViewById(R.id.btn_test9);
        btnTest10 = findViewById(R.id.btn_test10);

    }

    @Override
    protected void setListener() {
        btnZxing.setOnClickListener(this);
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
        btnTest3.setOnClickListener(this);
        btnTest4.setOnClickListener(this);
        btnTest5.setOnClickListener(this);
        btnTest6.setOnClickListener(this);
        btnTest7.setOnClickListener(this);
        btnTest8.setOnClickListener(this);
        btnTest9.setOnClickListener(this);
        btnTest10.setOnClickListener(this);

    }

    @Override
    protected void reLayout() {

    }

    @Override
    protected void init() {
        // RxBus跨模块通信
        Observable.interval(3000,5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                RxBus.getInstance().send(new BusEvent<String>("xx","rxbus 跨模块通信"));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_zxing:
                // 普通跨模块
                ARouter.getInstance().build(ARouterPath.CAPTURE_ACTIVITY).navigation();
                break;
            case R.id.btn_test1:
                // 普通获取参数
                ARouter.getInstance().build(ARouterPath.TEST1_ACTIVITY).withString("name","普通获取参数").navigation();
                break;
            case R.id.btn_test2:
                //注解获取参数
                ARouter.getInstance().build(ARouterPath.TEST2_ACTIVITY).withString("name","注解获取参数").navigation();
                break;
            case R.id.btn_test3:
                if (Build.VERSION.SDK_INT >= 16) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.
                            makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);

                    ARouter.getInstance()
                            .build(ARouterPath.TEST2_ACTIVITY)
                            .withOptionsCompat(compat).withString("name","新版动画")
                            .navigation();
                } else {
                    Toast.makeText(this, "API < 16,不支持新版本动画", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_test4:
                // 必须传入context
                ARouter.getInstance().build(ARouterPath.TEST2_ACTIVITY).withTransition(R.anim.push_right_in,R.anim.push_right_out)
                        .withString("name","普通转场动画").navigation(MainActivity.this);
                break;
            case R.id.btn_test5:
                // 登录拦截测试
                ARouter.getInstance().build(ARouterPath.TEST1_ACTIVITY).withString("name","111")
                        .withString(GlobalConfig.BundleKey.INTERCEPTOR_LOGIN,
                                GlobalConfig.BundleKey.INTERCEPTOR_LOGIN).navigation();

                break;
            case R.id.btn_test6:
                // 绿色通道不拦截
                ARouter.getInstance().build(ARouterPath.TEST1_ACTIVITY).greenChannel().withString("name","绿色通道不拦截")
                        .withString(GlobalConfig.BundleKey.INTERCEPTOR_LOGIN,
                                GlobalConfig.BundleKey.INTERCEPTOR_LOGIN).navigation();

                break;
            case R.id.btn_test7:


                break;
            case R.id.btn_test8:
                ((HelloServiceImpl)ARouter.getInstance()
                        .build(ARouterPath.HELLO_SERVICE).navigation()).sayHello("hello world");
                break;
            case R.id.btn_test9:

                //单独降级 单独降级在全局降级之前执行，且只能执行单独降级
                ARouter.getInstance().build(ARouterPath.TEST5_ACTIVITY).withString("name","注解获取参数")
                        .withString(GlobalConfig.BundleKey.INTERCEPTOR_LOGIN,GlobalConfig.BundleKey.INTERCEPTOR_LOGIN).navigation(this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        super.onLost(postcard);
                        Toast.makeText(MainActivity.this,"单独降级处理",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_test10:
                //全局降级
                ARouter.getInstance().build(ARouterPath.TEST5_ACTIVITY).withString("name","注解获取参数")
                        .withString(GlobalConfig.BundleKey.INTERCEPTOR_LOGIN,GlobalConfig.BundleKey.INTERCEPTOR_LOGIN).navigation();

                break;

            default:
                break;
        }
    }
}
