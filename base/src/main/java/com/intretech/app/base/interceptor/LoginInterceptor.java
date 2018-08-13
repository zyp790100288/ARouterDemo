package com.intretech.app.base.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.intretech.app.base.ARouterPath;
import com.intretech.app.base.GlobalConfig;

/**
 * Created by yq06171 on 2018/7/20.
 */
@Interceptor(priority = 6)
public class LoginInterceptor implements IInterceptor{
    Context mContext;
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if(postcard.getExtras().getString(GlobalConfig.BundleKey.INTERCEPTOR_LOGIN,"")
                .equals(GlobalConfig.BundleKey.INTERCEPTOR_LOGIN)){
                // 判断登录标志
            boolean login = false;
            if(login){
                callback.onContinue(postcard);
            }else{
                ARouter.getInstance().build(ARouterPath.TEST1_ACTIVITY).withString("name","登录拦截测试").navigation();
            }

        }else{
            callback.onContinue(postcard);
        }

    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
