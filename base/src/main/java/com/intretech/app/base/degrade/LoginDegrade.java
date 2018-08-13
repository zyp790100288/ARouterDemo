package com.intretech.app.base.degrade;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.launcher.ARouter;
import com.intretech.app.base.ARouterPath;

/**
 * Created by yq06171 on 2018/7/24.
 */
@Route(path = ARouterPath.TEST_DEGRADE_TEST)
public class LoginDegrade implements DegradeService {
    Context mContext;
    @Override
    public void onLost(Context context, Postcard postcard) {
        ARouter.getInstance().build(ARouterPath.TEST1_ACTIVITY).withString("name","全局降级处理").navigation();
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
