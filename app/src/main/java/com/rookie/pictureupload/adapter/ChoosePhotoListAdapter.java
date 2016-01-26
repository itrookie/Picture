package com.rookie.pictureupload.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rookie.pictureupload.R;
import com.rookie.pictureupload.utils.DeviceUtils;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/1/26.
 */
public class ChoosePhotoListAdapter extends BaseAdapter {

    private List<PhotoInfo> mList;
    private LayoutInflater mInflater;
    private int mScreenWidth;
    private Activity mActivity;

    public ChoosePhotoListAdapter(Activity activity,List<PhotoInfo> mList) {
        this.mList = mList;
        this.mActivity=activity;
        this.mInflater = LayoutInflater.from(activity);
        this.mScreenWidth = DeviceUtils.getScreenPix(activity).widthPixels;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView =  mInflater.inflate(R.layout.adapter_photo_list_item,null);
        ImageView ivPhoto = (ImageView)convertView.findViewById(R.id.iv_photo);
        setHeight(ivPhoto);
        PhotoInfo photoInfo = mList.get(position);
        Glide.with(mActivity).load("file://" + photoInfo.getPhotoPath()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(ivPhoto);
        convertView.findViewById(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                Toast.makeText(mActivity,"xx"+position,Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

    private void setHeight(final View convertView) {
        int height = mScreenWidth / 3 ;
        convertView.setLayoutParams(new FrameLayout.LayoutParams((int)(mScreenWidth/2.6), height));
    }
}
