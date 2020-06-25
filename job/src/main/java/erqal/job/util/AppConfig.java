package erqal.job.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */

public class AppConfig {

	private final static String APP_CONFIG = "config";
	public static final String TAG_PREFERENCE_COOKIE = "cookie";
	
	public static final String TAG_PREFERENCE_NAME_HAS_SHORTCUT = "HasShortcut";
	public static final String TAG_PREFERENCE_NAME_LANGUAGE = "language";
	
//	private final static String LOCAL_OBJECT_DATA_FILE_SUFFIX = "cer";

	private Context mContext;
	private static AppConfig appConfig;
//	private Controller controller;


	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			//appConfig = new AppConfig(context);
			appConfig.mContext = context;	
		}
		return appConfig;
	}
	
//	public AppConfig(Context context){
//		this.controller=Controller.getController(context);
//	}

	/**
	 * 获取Preference设置
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public String getCookie() {
		return get(TAG_PREFERENCE_COOKIE);
	}

	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}

	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			// 读取files目录下的config
			// fis = activity.openFileInput(APP_CONFIG);

			// 读取app_config目录下的config
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator + APP_CONFIG);

			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			// 把config建在files目录下
			// fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

			// 把config建在(自定义)app_config的目录下
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	public void set(Properties ps) {
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}

	public void set(String key, String value) {
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	public void remove(String... key) {
		Properties props = get();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}
	
//	public boolean saveLocalObjectData(String fileName, Object object, boolean checkLanguage) {
//		if (checkLanguage)
//			if (!controller.isUyghurLanguage())
//				fileName += Locale.CHINESE.getLanguage();
//
//		FileOutputStream fos = null;
//		ObjectOutputStream oos = null;
//
//		try {
//
//			String fileName_ = fileName + "." + LOCAL_OBJECT_DATA_FILE_SUFFIX;
//
//			File f = new File(controller.getCurrentAct().getFilesDir(), fileName_);
//
//			if (f.exists()) {
//				f.delete();
//			}
//
//			fos = controller.getCurrentAct().openFileOutput(fileName_, Context.MODE_PRIVATE);
//
//			oos = new ObjectOutputStream(fos);
//			oos.writeObject(object);
//			return true;
//		} catch (FileNotFoundException e) {
//
//		} catch (IOException e) {
//
//		} finally {
//			if (fos != null) {
//				try {
//					fos.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			}
//			if (oos != null) {
//				try {
//					oos.close();
//				} catch (IOException e2) {
//					e2.printStackTrace();
//				}
//			}
//		}
//		return false;
//	}
//
//	public Object loadLoacalObjectDataFile(String fileName, boolean checkLanguage) {
//		if (checkLanguage)
//			if (!controller.isUyghurLanguage())
//				fileName += Locale.CHINESE.getLanguage();
//		FileInputStream fis = null;
//		ObjectInputStream ois = null;
//		try {
//			fis = controller.getCurrentAct().openFileInput(fileName + "." + LOCAL_OBJECT_DATA_FILE_SUFFIX);
//
//			ois = new ObjectInputStream(fis);
//			Object obj = ois.readObject();
//			return obj;
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (fis != null) {
//				try {
//					fis.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			}
//			if (ois != null) {
//				try {
//					ois.close();
//				} catch (IOException e2) {
//					e2.printStackTrace();
//				}
//			}
//		}
//		return null;
//	}
}
