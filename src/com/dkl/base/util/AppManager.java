package com.dkl.base.util;

import java.util.Stack;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

/**
 * Ӧ�ó���Activity�����ࣺ����Activity�����Ӧ�ó���
 */

public class AppManager {

	private static Stack<Activity> activityStack;

	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * ��һʵ��
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * ���Activity����
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * ��ȡ��ǰActivity
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * ������ǰActivity
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * ����ָ����Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;

		}
	}

	/**
	 * ����ָ��������Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * ����Activity
	 */
	public void finishAllActivity(boolean isContainLogin) {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			Activity activity = activityStack.get(i);
			if (null != activity) {
				// if(activity instanceof LoginActivity && !isContainLogin) {
				// continue;
				// }
				activity.finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * activity ����
	 * 
	 * @param activity
	 * @param appIntent
	 */
	public void startActivity(Context activity, Intent appIntent) {
		appIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(appIntent);
		// activity.overridePendingTransition(android.R.anim.slide_in_left,
		// android.R.anim.slide_in_left);
	}

	/**
	 * * Ӧ�ó���
	 * ��ҪȨ��<uses-permission android:name="android.permission.RESTART_PACKAGES" />
	 */
	public void AppExit(Context context, boolean isContainLogin) {
		try {
			finishAllActivity(isContainLogin);
			if (isContainLogin) {
				ActivityManager activityMgr = (ActivityManager) context
						.getSystemService(Context.ACTIVITY_SERVICE);
				activityMgr.restartPackage(context.getPackageName());
				// �˳�����
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		} catch (Exception e) {
		}
	}

}