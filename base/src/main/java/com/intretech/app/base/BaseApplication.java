package com.intretech.app.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.intretech.app.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BaseApplication extends android.app.Application {


    private static Context context;
    public List<Activity> activities = new ArrayList<Activity>();
    private int lightPagerCount;
    private int outletpagerCount;
    private boolean isViewPagerScroll = true;


    /**
     * 维护Activity 的list
     */
    private static List<Activity> mActivitys = Collections
            .synchronizedList(new LinkedList<Activity>());



    public static Context getContextObject() {
        return context;
    }

    public void getContext(Context context) {
        this.context = getApplicationContext();
    }

    public boolean getIsViewPagerScroll() {
        return isViewPagerScroll;
    }

    public void setIsViewPagerScroll(boolean isViewPagerScroll) {
        this.isViewPagerScroll = isViewPagerScroll;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        registerActivityListener();

        /**
         * 初始化
         */
        if(BuildConfig.DEBUG){ // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init( this ); // 尽可能早，推荐在Application中初始化


    }

    public int getLightPagerCount() {
        return lightPagerCount;
    }

    public void setLightPagerCount(int lightPagerCount) {
        this.lightPagerCount = lightPagerCount;
    }

    public int getOutletpagerCount() {
        return outletpagerCount;
    }

    public void setOutletpagerCount(int outletpagerCount) {
        this.outletpagerCount = outletpagerCount;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public void addActivity(Activity activity) {
        activities.add(activity);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i) != null) {
                activities.get(i).finish();
            }
        }

        System.exit(0);
    }

    public void onTerminateExceptLastOne() {
        super.onTerminate();
        /*if (SocketToServer.getInstance() != null) {
            SocketToServer.getInstance().stopWork();
		}*/

        //Activity activity : activities
        for (int i = 0; i < activities.size() - 1; i++) {
            if (activities.get(i) != null) {

                activities.get(i).finish();
            }
        }
        System.exit(0);
    }

    /**
     * 结束所有acitvity
     */
    public void finishAll() {
        finishAllExcept(null);
    }

    /**
     * 结束除了指定的activity之外的所有activity
     *
     * @param exceptActivity
     */
    public void finishAllExcept(Class<?> exceptActivity) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i) != null) {
                // 结束除了MainActivity的activity
                if (activities.get(i).getClass() == exceptActivity) {
                    continue;
                } else {
                    activities.get(i).finish();
                }

            }
        }

    }

    public boolean isActivityExist(Class<?> existActivity) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i) != null) {
                // 结束除了MainActivity的activity
                if (activities.get(i).getClass() == existActivity) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * 结束指定的activity
     *
     * @param exceptActivity
     */
    public void finishAppoint(Class<?> exceptActivity) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i) != null) {
                // 如果列表中存在被排除的activity, 就跳过并继续
                if (activities.get(i).getClass() == exceptActivity) {
                    activities.get(i).finish();

                } else {
                    continue;
                }

            }
        }

    }
    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public void pushActivity(Activity activity) {
        mActivitys.add(activity);
        LogUtils.printLog("activityList:size:"+mActivitys.size());
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void popActivity(Activity activity) {
        LogUtils.printLog("zyp pop:"+activity.getClass().getName());
        mActivitys.remove(activity);
        LogUtils.printLog("activityList:size:"+mActivitys.size());
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if (mActivitys == null||mActivitys.isEmpty()) {
            return null;
        }
        Activity activity = mActivitys.get(mActivitys.size()-1);
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        if (mActivitys == null||mActivitys.isEmpty()) {
            return;
        }
        Activity activity = mActivitys.get(mActivitys.size()-1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (mActivitys == null||mActivitys.isEmpty()) {
            return;
        }
        if (activity != null) {
            mActivitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (mActivitys == null||mActivitys.isEmpty()) {
            return;
        }
        for (Activity activity : mActivitys) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public static Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (mActivitys != null) {
            for (Activity activity : mActivitys) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (mActivitys) {
            final int size = mActivitys.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivitys.get(size);
        }
        return mBaseActivity;

    }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (mActivitys) {
            final int size = mActivitys.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivitys.get(size);
        }
        return mBaseActivity.getClass().getName();
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
            activity.finish();
        }
        mActivitys.clear();
    }

    /**
     * 退出应用程序
     */
    public  static void appExit() {
        try {
            LogUtils.printLog("app exit");
            finishAllActivity();
        } catch (Exception e) {
        }
    }



    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    pushActivity(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null==mActivitys||mActivitys.isEmpty()){
                        return;
                    }
                    if (mActivitys.contains(activity)){
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        popActivity(activity);
                    }
                }
            });
        }
    }




}
