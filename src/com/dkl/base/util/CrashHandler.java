package com.dkl.base.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dkl.base.R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * UncaughtException������,��������Uncaught�쳣��ʱ��,�ɸ������ӹܳ���,����¼���ʹ��󱨸�.
 */
public class CrashHandler implements UncaughtExceptionHandler {

	private static CrashHandler INSTANCE = new CrashHandler();// CrashHandlerʵ��
	private Context mContext;// �����Context����

	/** ��ֻ֤��һ��CrashHandlerʵ�� */
	private CrashHandler() {

	}

	/** ��ȡCrashHandlerʵ�� ,����ģʽ */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * ��ʼ��
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		Thread.setDefaultUncaughtExceptionHandler(this);// ���ø�CrashHandlerΪ�����Ĭ�ϴ�����
	}

	/**
	 * ��UncaughtException����ʱ��ת�����д�ķ���������
	 */
	public void uncaughtException(Thread thread, final Throwable ex) {
		if(ex!=null){
			ex.printStackTrace();
		}
		// 1���Ӻ�����Ӧ��
		Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(i);
//
//		Intent intent = new Intent(mContext, WelcomeActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1500, restartIntent);

		if (!PreferenceUtils.getInstance().isContains(PubConstant.IS_SAVE_CRASH_LOG)) {
			new Thread() {
				public void run() {
					// ������־�ļ�
					if(ex!=null){
					saveCrashInfo2File(ex);
					Looper.prepare();
					Toast.makeText(mContext, mContext.getString(R.string.txt_sorry_procedure_error_exit), Toast.LENGTH_LONG).show();
					Looper.loop();
					}
				}
			}.start();
			PreferenceUtils.getInstance().putString(PubConstant.IS_SAVE_CRASH_LOG, "a");
		}
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		android.os.Process.killProcess(android.os.Process.myPid());
		ex.printStackTrace();
	}

	private String saveCrashInfo2File(Throwable ex) {
		Log.e("error", ex.toString());
		StringBuffer sb = new StringBuffer();

		String errPath = FilePathUtils.getDefaultLogFileName(mContext);
		File f = new File(errPath);
		try {
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();

				sb.append(Util.collectDeviceInfo(mContext));
			}
			sb.append("\r\n\r\n\r\n\r\ncrash==================================================\r\n");

			Writer result = new StringWriter();
			PrintWriter printWriter = new PrintWriter(result);
			ex.printStackTrace(printWriter);
			sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":\r\n\r\n");
			sb.append(result.toString().replace("\n", "\r\n"));
			sb.append("\r\n\r\n");

			// ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
			FileWriter writer = new FileWriter(errPath, true);
			writer.write(sb.toString());
			printWriter.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
