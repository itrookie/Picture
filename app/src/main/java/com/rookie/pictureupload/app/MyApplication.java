package com.rookie.pictureupload.app;

import android.app.Application;
import android.graphics.Color;

import com.rookie.pictureupload.BuildConfig;
import com.rookie.pictureupload.listener.GlidePauseOnScrollListener;
import com.rookie.pictureupload.loader.GlideImageLoader;

import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/1/25.
 */
public class MyApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        //initConfig();
    }

    private ThemeConfig customTheme(){
       ThemeConfig theme = new ThemeConfig.Builder()
               .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
               .setTitleBarTextColor(Color.BLACK)
               .setTitleBarIconColor(Color.BLACK)
               .setFabNornalColor(Color.RED)
               .setCheckNornalColor(Color.WHITE)
               .setCheckSelectedColor(Color.BLACK)
               .build();
    return theme;
    }

    private void initConfig(List<PhotoInfo> list) {
        //设置主题
        ThemeConfig theme = new ThemeConfig.Builder().build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true) //相机功能
                .setEnableCamera(true)
                .setEnableCrop(false) //裁剪功能
                .setEnableRotate(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(5) //多选数量
                .setSelected(list)
                .build();

        ImageLoader imageLoader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this,imageLoader,customTheme())
                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false,true))
                .build();
        GalleryFinal.init(coreConfig);
    }
}
