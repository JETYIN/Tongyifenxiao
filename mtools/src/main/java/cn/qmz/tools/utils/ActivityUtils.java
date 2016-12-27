package cn.qmz.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ActivityUtils {
	//============================跳转页面==========================
	/**
	 * 跳转页面
	 * @param cls
	 */
	public static void to(Context context, Class<?> cls){
		to(context, cls, null);
	}

	/**
	 * 带数据的跳转页面
	 * @param context
	 * @param cls
	 * @param data
	 */
	public static void to(Context context, Class<?> cls, Bundle data){
		Intent intent = new Intent(context, cls);
		if(data != null){
			intent.putExtras(data);
		}
		context.startActivity(intent);
	}

	/**
	 *  显示软键盘
	 * @param view
	 */
	public static void showSoftInputView(Activity activity, View view) {
		if (activity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (activity.getCurrentFocus() != null)
				((InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE))
						.showSoftInput(view, 0);
		}
	}

	/** 隐藏软键盘
	 * hideSoftInputView
	 * @Title: hideSoftInputView
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public static void hideSoftInputView(Activity activity) {
		InputMethodManager manager = ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (activity.getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}

