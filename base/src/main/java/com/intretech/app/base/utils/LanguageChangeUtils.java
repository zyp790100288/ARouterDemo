package com.intretech.app.base.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by yq06171 on 2018/6/1.
 * 语言切换
 */

public class LanguageChangeUtils {

    private static int DEFAULT_LANGUAGE = 3;

    public static final int LANGUAGE_SYS = 3;//系统
    public static final int LANGUAGE_SIMPLE_CHINESE = 1;//简体中文
    public static final int LANGUAGE_TRADITIONAL_CHINESE = 0;//繁体中文
    public static final int LANGUAGE_ENGLISH = 2;//英语



    /**
     * 设置语言
     *
     * @param context
     * @param languageType
     */
    public static void setLanguage(Context context, int languageType, ResultListener resultListener) {

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        switch (languageType) {
            case LANGUAGE_SYS:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(Locale.getDefault());
                } else {
                    config.locale = Locale.getDefault();
                }
                break;
            case LANGUAGE_SIMPLE_CHINESE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(Locale.SIMPLIFIED_CHINESE);
                } else {

                    config.locale = Locale.SIMPLIFIED_CHINESE;
                }
                break;
            case LANGUAGE_TRADITIONAL_CHINESE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(Locale.TRADITIONAL_CHINESE);
                } else {
                    config.locale = Locale.TRADITIONAL_CHINESE;
                }
                break;
            case LANGUAGE_ENGLISH:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(Locale.ENGLISH);
                } else {

                    config.locale = Locale.ENGLISH;
                }
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config);
        }
        resources.updateConfiguration(config, dm);
        if (resultListener != null) {
            resultListener.setLanguageSuccess(languageType);
        }
    }

    public interface ResultListener {
        void setLanguageSuccess(int type);
    }




    /**
     * 初始化语言设置
     *
     * @param context
     */
    public static void initLanguage(Context context,int type,ResultListener resultListener) {
        setLanguage(context, type, resultListener);
    }


    /**
     * 重启App
     *
     * @param activity
     */
    public static void restartApp(Application mApplication, Activity activity) {
        Intent i = activity.getPackageManager()
                .getLaunchIntentForPackage(activity.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
        mApplication.onTerminate();
        activity.finish();
        System.exit(0);

    }


    /**
     * 返回当前应用具体语言
     *
     * @param cx
     * @return
     */
    public static int currentLaguageType(Context cx,int type) {
        int languageType = type;
        if (languageType == LanguageChangeUtils.LANGUAGE_SYS) {
            Locale locale = cx.getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            if (language.endsWith("zh")) {
                String country = Locale.getDefault().getCountry().toLowerCase();
                if ("tw".equals(country)) {
                    return LANGUAGE_TRADITIONAL_CHINESE;
                } else {
                    return LANGUAGE_SIMPLE_CHINESE;
                }
            } else {
                return LANGUAGE_ENGLISH;
            }
        } else {
            return languageType;
        }
    }




}
