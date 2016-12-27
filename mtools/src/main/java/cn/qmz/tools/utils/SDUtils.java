package cn.qmz.tools.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import cn.qmz.tools.bean.DeviceInfo;

public class SDUtils {
	private Context mContext;
	//设备信息
	public static DeviceInfo deviceInfo;
	//项目制定文件目录
	private static String projectName = "NetCar";

	//用于存放日志，缓存等到外置SD卡上
	public static String sdPath;
	//外置存储根目录
	public static String sdRootPath;
	// 用户存放数据等到私有目录下
	public static String dataPath;
	//图像目录
	public static String imagePath;
	//缓存目录
	public static String sdCachePath;
	//日志目录
	public static String sdLogPath;
	//数据库目录
	public static String databasePath;
	//数据库文件名称
	public static String dataFileName ="NetCar.db";

	//屏幕宽度高度
	public static int scrhei = 720;
	public static int scrwid = 1280;
	//	//5dp的像素数
	public static float fivedp = 8;

	public SDUtils(Context context) {
		this.mContext = context;
		init();
	}

	private void init() {
		deviceInfo = new DeviceInfo();
		//读取本地缓存的服务者数据
		//userInfo = new UserInfo();

		deviceInfo.initDeviceInfo(mContext);
		// 初始化文件目录
		String fileSeparator = System.getProperty("file.separator");
		//sd卡目录
		sdPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() ;
		//wyt根目录
		sdRootPath = sdPath + fileSeparator + projectName+fileSeparator;
		//普通数据
		dataPath = sdRootPath  + "data" + fileSeparator;
		//数据库
		databasePath = sdRootPath +"db" + fileSeparator;
		//图片
		imagePath = sdRootPath + "image" + fileSeparator;
		//缓存
		sdCachePath = sdRootPath + "cache" + fileSeparator;
		//日志
		sdLogPath = sdRootPath + "log" + fileSeparator;
		//初始化数据库，数据，图片文件夹,日志文件夹
		checkAndCreatePrivateDirectory();
		//初始化缓存文件夹
		checkAndCreateSdDirectory();
	}

	private static void checkAndCreatePrivateDirectory() {
		// 创建私有文件夹
		String[] path = {  sdRootPath,databasePath ,dataPath,imagePath,sdCachePath, sdLogPath };
		//这里创建目录要先创建sd/layou/  再创建sd/layou/image/  否则创建失败
		for (String x : path) {
			File fi = new File(x);
			if (!fi.exists()) {
				fi.mkdir();
			}
		}
	}

	/** 每次启动需要删除的旧文件夹 */
	private static void checkAndCreateSdDirectory() {

		if ((!TextUtils.isEmpty(sdPath) && new File(sdPath).canRead())) {

			String[] path = { sdCachePath,imagePath };
			for (String x : path) {
				File fi = new File(x);
				if (!fi.exists()) {
					fi.mkdir();
				} else {
					FileUtils.deleteFile(x);
					fi.mkdir();
				}
			}
			// 禁止系统Media搜索程序目录;
			String nomediapath = imagePath + ".nomedia";
			File nomedia = new File(nomediapath);
			try {
				if (!nomedia.exists())
					nomedia.createNewFile();
			} catch (IOException e) {
			}
		}
	}
}

