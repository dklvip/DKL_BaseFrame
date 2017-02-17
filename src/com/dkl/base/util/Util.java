package com.dkl.base.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class Util {

	/**
	 * �ռ��豸������Ϣ
	 * 
	 * @param context
	 */
	public static String collectDeviceInfo(Context context) {
		Map<String, String> info = new HashMap<String, String>();// �����洢�豸��Ϣ���쳣��Ϣ
		try {
			PackageManager pm = context.getPackageManager();// ��ð�������
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);// �õ���Ӧ�õ���Ϣ������Activity
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				info.put("versionName", versionName);
				info.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		Field[] fields = Build.class.getDeclaredFields();// �������
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				info.put(field.getName(), field.get("").toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : info.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\r\n");
		}
		sb.append("\r\n");
		sb.append("*********user="
				+ DataCache.getInstance().get(PubConstant.LOGIN_NAME_KEY));
		sb.append("*********\r\n");
		return sb.toString();
	}
}
