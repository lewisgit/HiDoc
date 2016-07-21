package com.happydoc.lewis.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.avos.avoscloud.AVOSCloud;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.ThirdPartUserUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * Created by Lewis on 2016/7/11.
 */
public class App extends Application {
    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator + "CircleDemo" + File.separator + "Images"
            + File.separator;
    private static Context mContext;
    //-----------------------
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, getString(R.string.app_id), getString(R.string.app_key));
        ChatManager.setDebugEnabled(true);// tag leanchatlib
        AVOSCloud.setDebugLogEnabled(true);  // set false when release
        initImageLoader(this);
        ChatManager.getInstance().init(this);
        //ThirdPartUserUtils.setThirdPartUserProvider(new CustomUserProvider());

        //
        mContext = getApplicationContext();
        //LeakCanary.install(this);
        //QPManager.getInstance(getApplicationContext()).initRecord();

    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
                        //.memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }



    public static Context getContext(){
        return mContext;
    }
}
