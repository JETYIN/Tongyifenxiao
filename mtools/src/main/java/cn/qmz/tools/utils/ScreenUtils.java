package cn.qmz.tools.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * <li>{@link ScreenUtils#dpToPx(Context, float)}</li>
 * <li>{@link ScreenUtils#pxToDp(Context, float)}</li>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-14
 */
public class ScreenUtils {

	private ScreenUtils() {
		throw new AssertionError();
	}

	/**
	 * 获取屏幕区域
	 * @return
	 */
	public static Rect getScreenRect(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		Rect rect = new Rect(0, 0, dm.widthPixels, dm.heightPixels);
		return rect;
	}

	/**
	 * 获取屏幕高度
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		return getScreenRect(context).height();
	}

	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		return getScreenRect(context).width();
	}

	/**
	 * 获取系统显示信息
	 * @return
	 */
	public DisplayMetrics getDisplayInfo(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm;
	}

	/**
	 * 获取屏幕分辨率
	 * @param pxValue
	 * @return
	 */
	public int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static float dpToPx(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}

	public static float pxToDp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return px / context.getResources().getDisplayMetrics().density;
	}

	public static float dpToPxInt(Context context, float dp) {
		return (int)(dpToPx(context, dp) + 0.5f);
	}

	public static float pxToDpCeilInt(Context context, float px) {
		return (int)(pxToDp(context, px) + 0.5f);
	}
}

