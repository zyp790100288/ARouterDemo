package com.intretech.app.base.utils;

import android.content.Context;
import android.util.Log;

import com.intretech.app.base.BuildConfig;


/**
 * 日志打印工具类
 * BuildConfig.DEBUG true -- debug false --release
 * 
 */
public class LogUtils {
	
	public static int VERBOSE = Log.VERBOSE;
	public static int DEBUG = Log.DEBUG;
	public static int INFO = Log.INFO;
	public static int WARN = Log.WARN;
	public static int ERROR = Log.ERROR;
	public static int ASSERT = Log.ASSERT;
	
	
	public static void printLog(String content) {
		if (content != null&& BuildConfig.DEBUG) {
			Log.e(android.os.Build.MODEL, content);
		}

	}

	/**
	 * <b>LogType:</b>
	 * </br> (1) LogUtils.VERBOSE
	 * </br> (2) LogUtils.DEBUG
	 * </br> (3) LogUtils.INFO
	 * </br> (4) LogUtils.WARN
	 * </br> (5) LogUtils.ERROR
	 * @param content
	 * @param logType
	 */
	public static void printLog(String content, int logType) {
		if (content != null&& BuildConfig.DEBUG) {
			switch (logType) {
			case Log.VERBOSE:
				Log.v(android.os.Build.MODEL, content);
				break;
			case Log.DEBUG:
				Log.d(android.os.Build.MODEL, content);
				break;
			case Log.INFO:
				Log.i(android.os.Build.MODEL, content);
				break;
			case Log.WARN:
				Log.w(android.os.Build.MODEL, content);
				break;
			case Log.ERROR:
				Log.e(android.os.Build.MODEL, content);
				break;

			default:
				break;
			}
		}
	}

	public static void printInfoLog(String type, String content) {
		if (content != null&& BuildConfig.DEBUG) {
			Log.i(type, content);
		}
	}
	
	public static void printWarningLog(String type, String content) {
		if (content != null&& BuildConfig.DEBUG) {
			Log.w(type, content);
		}
	}

	public static void printLog(Context context, String methodName, String content) {
		if (content != null&& BuildConfig.DEBUG) {
			Log.e(android.os.Build.MODEL + ", " + context.getClass().getSimpleName() + " --> ", methodName + ":  " + content);
		}

	}
}
