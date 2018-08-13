package com.intretech.app.practice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.intretech.app.base.ARouterPath;
import com.intretech.app.base.BaseActivity;

/**
 * Created by yq06171 on 2018/7/13.
 */
@Route(path = ARouterPath.TEST2_ACTIVITY)
public class Test2Activity extends BaseActivity {
    @Autowired
    String name;
    TextView mTextView;
    Button btnTest5;
    Button btnTest6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ARouter.getInstance().inject(this);
        findViewById();
        setListener();
    }

    @Override
    protected void findViewById() {
        mTextView = findViewById(R.id.tv_test);
        mTextView.setText(name);
        btnTest5 = findViewById(R.id.btn_test5);
        btnTest6 = findViewById(R.id.btn_test6);
    }

    @Override
    protected void setListener() {
        btnTest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Test3Activity.class);
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
