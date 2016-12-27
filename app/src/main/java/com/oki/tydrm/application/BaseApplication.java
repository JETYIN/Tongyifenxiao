package com.oki.tydrm.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Rect;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;

import cn.qmz.tools.utils.Logger;
import cn.qmz.tools.utils.NetworkUtils;
import cn.qmz.tools.utils.ScreenUtils;

/**
 * Created by Monica on 2015/7/30.
 */
public class BaseApplication extends Application {
    // 唯一配置实例
    public static BaseApplication mInstance;
    //判断网络是否连接
    public static boolean isConnect = false;

    /** 单例模式  */
    public static BaseApplication getInstance() {
        if(mInstance == null) {
            mInstance = new BaseApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //判断网络是否连接 - 配合定位
        isConnect = NetworkUtils.GetInternetstate(this);
        //初始化图片属性
        //初始化图片数据
        initImageLoader();
        
        Context appContext =  this.getApplicationContext();

        String appId = "900012639";   //上Bugly(bugly.qq.com)注册产品获取的AppId

        boolean isDebug = true ;  //true代表App处于调试阶段，false代表App发布阶段

        CrashReport. initCrashReport(appContext, appId, isDebug);  //初始化SDK
        
    }

    /**
     * //初始化图片数据
     */
    private void initImageLoader() {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        //全局初始化此配置 
        Rect screenrect = ScreenUtils.getScreenRect(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(screenrect.width(), screenrect.height())
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .memoryCacheSize(5 * 1024 * 1024)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(1000)
                .threadPoolSize(4)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default

                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        Logger.d("LYApplication:initImageLoader() 宽高 : " + screenrect.width() + " " + screenrect.height());
    }

}
