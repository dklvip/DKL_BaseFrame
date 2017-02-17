package com.dkl.base;

import com.dkl.base.util.CrashHandler;
import android.app.Application;

public class BaseApplication extends Application{
	private static Application mInstance;

	public static Application getInstance() {
		return mInstance;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		// ·â×°³ÌÐòÒì³£
		CrashHandler.getInstance().init(mInstance);
	}
}
