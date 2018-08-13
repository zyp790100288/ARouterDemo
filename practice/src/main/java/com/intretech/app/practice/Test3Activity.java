package com.intretech.app.practice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.intretech.app.base.ARouterPath;
import com.intretech.app.base.BaseActivity;

/**
 * Created by yq06171 on 2018/7/13.
 */
@Route(path = ARouterPath.TEST3_ACTIVITY)
public class Test3Activity extends BaseActivity {

    Button btnTest5;
    Button btnTest6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        findViewById();setListener();
    }

    @Override
    protected void findViewById() {

        btnTest5 = findViewById(R.id.btn_test5);
        btnTest6 = findViewById(R.id.btn_test6);
    }

    @Override
    protected void setListener() {
        btnTest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Test4Activity.class);
            }
        });
        btnTest6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void reLayout() {

    }

    @Override
    protected void init() {

    }
}