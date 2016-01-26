package com.rookie.pictureupload;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.rookie.pictureupload.adapter.ChoosePhotoListAdapter;
import com.rookie.pictureupload.listener.GlidePauseOnScrollListener;
import com.rookie.pictureupload.loader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.HorizontalListView;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    private HorizontalListView mLvPhoto;
    private ChoosePhotoListAdapter choosePhotoListAdapter;

    //图片集合
    private List<PhotoInfo> mPhotoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });

        init();
    }

    private void init() {

        mLvPhoto = (HorizontalListView) findViewById(R.id.lv_photo);
        mPhotoList = new ArrayList<>();
        choosePhotoListAdapter = new ChoosePhotoListAdapter(this,mPhotoList);
        mLvPhoto.setAdapter(choosePhotoListAdapter);
        choosePhotoListAdapter.notifyDataSetChanged();
        initConfig(mPhotoList);

    }

    private void open(){
        ActionSheet.createBuilder(this,getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("相册","拍照")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener(){

                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {}

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        switch (index){
                            case 0:
                                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY,2,mOnHanlderResultCallback);
                                break;
                            case 1:
                                GalleryFinal.openCamera(REQUEST_CODE_CAMERA,mOnHanlderResultCallback);
                                break;

                        }
                    }
                }).show();
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback(){

        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if(resultList!=null){
                mPhotoList.addAll(resultList);
                for (PhotoInfo info:mPhotoList){
                    Log.e("LMW",info.getPhotoPath());
                }
                choosePhotoListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
