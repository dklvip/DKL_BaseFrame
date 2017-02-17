package com.dkl.base.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

/**
 * �ļ�·��������
 */
public class FilePathUtils {
	
	
	/**
	 * ���ݴ����uniqueName��ȡӲ�̻����·����ַ��
	 */
	@SuppressLint("NewApi")
	private static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}
	
	/**
	 * ��õ�ǰӦ��Ĭ�ϵĽ�ѹ·��
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultUnzipFile(Context mContext) {
		File cacheDir = getDiskCacheDir(mContext, "unZip");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir.getAbsolutePath();
	}

	/**
	 * ��ȡSD��Ŀ¼�����Ӧ���������µ����ݿ��·��
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultDataBasePath(Context mContext) {
		File cacheDir = getDiskCacheDir(mContext, "database");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir.getAbsolutePath();
	}

	public static File getDefaultDataBasePath_XMPP(Context mContext) {
		File cacheDir = getDiskCacheDir(mContext, "xmpp_database");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir;
	}

	/**
	 * ��ȡSD��Ŀ¼�����Ӧ���������µ��ļ������ͼƬ��·��
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultFilePath(Context mContext) {
		File cacheDir = getDiskCacheDir(mContext, "file");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir.getAbsolutePath();
	}
	
	public static String getRecvFilePath() {
		try {
			String path = Environment.getExternalStorageDirectory().getAbsolutePath();
			File file = new File(path + "/kingnode/recvFile");
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getAbsolutePath();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * ��ȡSD��Ŀ¼�����Ӧ���������µ����ձ����ͼƬ��·��
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultImageFilePath(Context mContext) {
		File cacheDir = getDiskCacheDir(mContext, "image");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir.getAbsolutePath();
	}
	
	/**
	 * ��ȡSD��Ŀ¼�����Ӧ���������µ�¼�������ͼƬ��·��
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultRecordPath(Context mContext) {
		File cacheDir = getDiskCacheDir(mContext, "record");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir.getAbsolutePath();
	}

	/**
	 * ��ȡSD��Ŀ¼�����Ӧ���������µ���Ƶ�����ͼƬ��·��
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultVideoPath(Context mContext) {
		File cacheDir = getDiskCacheDir(mContext, "video");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir.getAbsolutePath();
	}

	@SuppressLint("NewApi")
	public static void deleteAllCacel(Context context) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		try {
			deletefile(new File(cachePath + File.separator).getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ��ĳ���ļ����µ������ļ��к��ļ�
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean deletefile(String delpath) throws Exception {
		try {
			File file = new File(delpath);
			// ���ҽ����˳���·������ʾ���ļ������� ��һ��Ŀ¼ʱ������ true
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "/" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						System.out.println(delfile.getAbsolutePath() + "ɾ���ļ��ɹ�");
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "/" + filelist[i]);
					}
				}
				System.out.println(file.getAbsolutePath() + "ɾ���ɹ�");
				file.delete();
			}

		} catch (FileNotFoundException e) {
		}
		return true;
	}
	
	/**
	 * ��־����Ŀ¼
	 * @return
	 */
	public static String getDefaultLogPath(Context mContext) {
		File cacheDir = getDiskCacheDir(mContext, "log");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir.getAbsolutePath();
	}
	
	/**
	 * ��־����Ŀ¼
	 * 
	 * @return
	 */
	public static String getDefaultLogFileName(Context mContext) {
		return getDefaultLogPath(mContext) + "/" + mContext.getPackageName() + "-"
				+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-"
				+ "alanqiu" + ".txt";

	}

	public static String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection,null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }

        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

	public static void openFile(Context ct, File file) {
		try {
			if (null == file || !file.exists()) {
				Toast.makeText(ct, "�ļ������ڣ�", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// ����intent��Action����
			intent.setAction(Intent.ACTION_VIEW);
			// ��ȡ�ļ�file��MIME����
			String type = getMIMEType(file);
			// ����intent��data��Type���ԡ�
			intent.setDataAndType(/* uri */Uri.fromFile(file), type);
			// ��ת
			ct.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(ct, "�Ҳ������Դ򿪸��ļ����͵�Ӧ��\r\n�ļ������:\r\n" + file.getPath(),Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * �����ļ���׺����ö�Ӧ��MIME���͡�
	 * 
	 * @param file
	 */
	private static String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();
		// ��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* ��ȡ�ļ��ĺ�׺�� */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if ("".equals(end)) {
			return type;
		}
		// ��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡�
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}
	private final static String[][] MIME_MapTable = {
			// {��׺����MIME����}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };


//	public static int getFileIcon(String fileName) {
//		String ext = "";
//		if (fileName.contains(".")) {
//			ext = fileName.substring(fileName.lastIndexOf(".") + 1);
//		}
//		if ("doc".equals(ext) || "docx".equals(ext) || "rtf".equals(ext) || "wps".equalsIgnoreCase(ext)) {
//			return R.drawable.fdq;
//		} else if ("xls".equalsIgnoreCase(ext) || "xlsx".equalsIgnoreCase(ext)) {
//			return R.drawable.fcg;
//		} else if ("ppt".equalsIgnoreCase(ext) || "pptx".equalsIgnoreCase(ext)) {
//			return R.drawable.fcz;
//		} else if ("xml".equalsIgnoreCase(ext) || "html".equalsIgnoreCase(ext)) {
//			return R.drawable.fdb;
//		} else if ("pdf".equalsIgnoreCase(ext)) {
//			return R.drawable.fda;
//		} else if ("txt".equalsIgnoreCase(ext)) {
//			return R.drawable.fcv;
//		} else if ("png".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "gif".equalsIgnoreCase(ext) || "bmp".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext)) {
//			return R.drawable.fdd;
//		} else if ("swf".equalsIgnoreCase(ext) || "rmvb".equalsIgnoreCase(ext) || "avi".equalsIgnoreCase(ext) || "mp4".equalsIgnoreCase(ext) || "rm".equalsIgnoreCase(ext) || "mkv".equalsIgnoreCase(ext)) {
//			return R.drawable.fch;
//		} else if ("amr".equalsIgnoreCase(ext) || "wav".equalsIgnoreCase(ext) || "mp3".equalsIgnoreCase(ext)) {
//			return R.drawable.fdc;
//		} else if ("rar".equalsIgnoreCase(ext) || "zip".equalsIgnoreCase(ext) || "7z".equalsIgnoreCase(ext)) {
//			return R.drawable.fcf;
//		} else {
//			return R.drawable.fco;
//		}
//	}
}
