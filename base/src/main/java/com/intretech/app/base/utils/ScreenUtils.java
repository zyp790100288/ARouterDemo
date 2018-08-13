package com.intretech.app.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 屏幕相关工具类
 * @author Alfred_Huang
 *
 */
public class ScreenUtils {
	
	private static ScreenUtils mInstance = null;
	
	public synchronized static ScreenUtils getInstance(){
		if(mInstance == null){
			mInstance = new ScreenUtils();
		}
		return mInstance;
	}
	
	/**
     * 获取屏幕的宽度和高度
     * 
     * @param context
     * @return screenDimen[0] = width, screenDimen[1] = height
     */
    public static int[] getScreenDimenArray(Context context){
    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	int width = wm.getDefaultDisplay().getWidth();
    	int height = wm.getDefaultDisplay().getHeight();
    	int[] screenDimen = new int[]{width, height};
		return screenDimen;
    }
	
	/**
	 * 获取手机顶部状态栏的高度
	 * 
	 * @param activity
	 * @return int statusBarHeight
	 */
	public static int getStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
	/**
	 * 在声明周期获取手机顶部状态栏的高度
	 * 
	 * @param activity
	 * @return int statusBarHeight
	 */
	public static int getStatusBarHeightInLifecycle(Context mContext) {

		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = mContext.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			Log.e("EXCEPTION", "get status bar height fail");
			e1.printStackTrace();
		}
		System.out.println("x = " + x + "，sbar = " + sbar);
		return sbar;

	}
	
	/**
	 * 获取手机虚拟导航栏的高度
	 * 
	 * @param context
	 * @return int navigationBarHeight
	 */
	public int getNavigationBarHeight(Context context){
		Resources resources = context.getResources();
		int navigationBarHeight = 0;
		// 通过资源名称获得资源id ("资源名称", "资源属性", "包名"), 未找到该资源则return 0
		int rid = resources.getIdentifier("config_showNavigationBar", "bool", "android");
		if (rid > 0) {
			//获取导航栏是否显示true or false
			boolean isNavigationBarShowing = resources.getBoolean(rid);
		}
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		if (resourceId > 0) {
			//获取高度
			navigationBarHeight = resources.getDimensionPixelSize(resourceId);
		}
		return navigationBarHeight;
	}
	
	/**
	 * 获得某组件的高度
	 * 
	 * @param view
	 * @return int widgetHeight
	 */
	public static int getWidgetHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredHeight();
	}

	/**
	 * 获取某组件的坐标数组
	 * 
	 * @param widget
	 * @return location[0] = X-Coordinate, location[1] = Y-Coordinate
	 */
	public static int[] getWidgetPosArray(View widget) {
		int[] location = new int[2];
		widget.getLocationOnScreen(location);
		return location;
	}
	
	/**
	 * 根据手机的分辨率从sp单位转成为px
	 * 
	 * @param context
	 * @param spValue
	 * @return int pxValue
	 */
	public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }
	
	/**
	 * 根据手机的分辨率从dp单位转成为px
	 * 
	 * @param context
	 * @param dpValue
	 * @return int pxValue
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从px单位转成为dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return int dipValue
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
