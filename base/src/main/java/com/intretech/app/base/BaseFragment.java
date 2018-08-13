package com.intretech.app.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
    /**
     * Appliction基类对象
     **/
    public String TAG = "BaseFragment";
    /**
     * Acitivity对象
     **/
    protected Activity mActivity;
    /**
     * 上下
     **/
    protected Context mContext;
    /**
     * 当前显示的内
     **/
    protected View mView;

    abstract public String getFragmentName();

    //protected CurrFragmentHandler mCurrFragmentHandler;
    public BaseFragment(Activity activity,
                        Context context) {
        mActivity = activity;
        mContext = context;
        //	mCurrFragmentHandler=new CurrFragmentHandler();
    }

    public BaseFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = mActivity;
    }

    public void releaseImageView(ImageView imageView) {
        if (imageView != null) {
            Drawable d = imageView.getDrawable();
            if (d != null) {
                d.setCallback(null);
            }
            imageView.setImageDrawable(null);
            imageView.setBackgroundDrawable(null);
        }
    }

    protected abstract void refreshUI();


    /**
     * 绑定界面UI
     **/
    protected abstract void findViewById();

    /**
     * 界面UI事件监听
     **/
    protected abstract void setListener();
    /**接收通知，当有操作时，smartCore返回值会直接传递进来**/


    /**
     * 界面数据初始�?
     **/
    protected abstract void initData();

    /**
     * 通过ID绑定UI
     **/
    protected View findViewById(int id) {
        return mView.findViewById(id);
    }

    /**
     * 显示菜单列表
     **/
    protected void showSlidingMenu() {
        //((MainActivity) mActivity).showMenu();
    }

    /**
     * 短暂显示Toast提示(来自res)
     **/
    protected void showShortToast(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    protected void showShortToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    protected void showLongToast(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    protected void showLongToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    /**
     * Debug输出Log日志
     **/
    protected void showLogDebug(String tag, String msg) {
        Log.d(tag, msg);
    }

    /**
     * Error输出Log日志
     **/
    protected void showLogError(String tag, String msg) {
        Log.e(tag, msg);
    }

    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }


    /**
     * 通过ResultCode跳转界面
     **/
    protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        if (mContext != null) {
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.setClass(mContext, cls);
            mContext.startActivity(intent);
        }
        if (mActivity != null) {
            mActivity.overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_left_out);
        }
    }

    protected void startActivity(Activity mActivity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    /**
     * 通过Action跳转界面
     **/
    protected void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    /**
     * 含有标题和内容的对话�?
     **/
    protected AlertDialog showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle(title).setMessage(message).show();
        return alertDialog;
    }

    /**
     * 含有标题、内容�?图标、两个按钮的对话
     **/
    protected AlertDialog showAlertDialog(String title, String message,
                                          int icon, String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener,
                                          String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle(title).setMessage(message).setIcon(icon)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .show();
        return alertDialog;
    }

    /**
     * 含有标题、内容两个按钮的对话框
     **/
    protected AlertDialog showAlertDialog(String title, String message,
                                          String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener,
                                          String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .show();
        return alertDialog;
    }
}
