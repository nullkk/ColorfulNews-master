package com.kaku.colorfulnews.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 像素和dp,sp之间的转换
 */
public class DensityUtil 
{
	public static int dp2px(Context paramContext, float paramFloat) {
		float f = paramContext.getResources().getDisplayMetrics().density;
		return (int) (paramFloat * f + 0.5F);
	}

	public static int px2dp(Context paramContext, float paramFloat) {
		float f = paramContext.getResources().getDisplayMetrics().density;
		return (int) (paramFloat / f + 0.5F);
	}

	public static int sp2px(Context paramContext, float paramFloat) {
		float f = paramContext.getResources().getDisplayMetrics().scaledDensity;
		return (int) (paramFloat * f + 0.5F);
	}

	public static int px2sp(Context paramContext, float paramFloat) {
		float f = paramContext.getResources().getDisplayMetrics().scaledDensity;
		return (int) (paramFloat / f + 0.5F);
	}
	
	public static DisplayMetrics getDisplayMetrics(Context context)
	{
		DisplayMetrics outMetrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics;
	}
	/**
	 * 获取statusBar的高度(像素值)
	 */
	public static int statusBarHeight(Context context)
	{
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}
}