package com.dkl.base;

import com.dkl.base.util.AppManager;
import com.dkl.base.util.PreferenceUtils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity {
	protected Activity instance;
	/**
	 * 共享存储变量
	 */
	protected PreferenceUtils preferenceUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		instance = this;
		preferenceUtils = PreferenceUtils.getInstance();
		hideTitle(false);
		setFullScreen(false);
		setLandscape(false);
		setTranslucentStatusBar(false);
		AppManager.getAppManager().addActivity(instance);
		initView(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AppManager.getAppManager().finishActivity(instance);
	}

	public void eLog(String info) {
		Log.e(instance.getLocalClassName(), info);
	}

	/**
	 * 当作onCreate使用
	 * 
	 * @param savedInstanceState
	 */
	public abstract void initView(Bundle savedInstanceState);

	/**
	 * 是否有标题栏
	 * 
	 * @param flag
	 */
	private void hideTitle(boolean flag) {
		if (flag)
			requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
	}

	/**
	 * 是否全屏
	 * 
	 * @param flag
	 */
	private void setFullScreen(boolean flag) {
		if (flag)
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
	}

	/**
	 * 是否为横屏，不是横屏就是竖屏
	 * 
	 * @param flag
	 */
	private void setLandscape(boolean flag) {
		if (flag) {
			if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		} else {
			if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		}
	}

	/**
	 * 设置沉浸式的状态栏
	 * 
	 * @param flag
	 */
	private void setTranslucentStatusBar(boolean flag) {
		if (android.os.Build.VERSION.SDK_INT <= 18) {
			return;
		}
		if (!flag) {
			return;
		}
		Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		// 设置根布局的内边距
		// RelativeLayout relativeLayout = (RelativeLayout)
		// findViewById(R.id.layout);
		// relativeLayout.setPadding(0,
		// getActionBarHeight()+getStatusBarHeight(), 0, 0);
	}
}
