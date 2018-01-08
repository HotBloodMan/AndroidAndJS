package com.ljt.photocache;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ljt.androidjs.R;

/**
 * Created by ${JT.L} on 2018/1/3.
 */

public class ImageLoaders {
    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public ImageLoaders(){
        mMemoryCacheUtils=new MemoryCacheUtils();
        mLocalCacheUtils=new LocalCacheUtils();
        mNetCacheUtils=new NetCacheUtils(mLocalCacheUtils,mMemoryCacheUtils);
    }
    public void display(ImageView ivpic,String url){
        ivpic.setImageResource(R.mipmap.ic_launcher);
        Bitmap bitmap;
        //从内存中拿
        bitmap=mMemoryCacheUtils.getBitmapFromMemory(url);
        if(bitmap!=null){
            ivpic.setImageBitmap(bitmap);
            return;
        }
        //从本地拿
        bitmap=mLocalCacheUtils.getBitmapFromLocal(url);
        if(bitmap!=null){
            ivpic.setImageBitmap(bitmap);
            mMemoryCacheUtils.setBitmapToMemory(url,bitmap);
            return;
        }
        //网络缓存
        mNetCacheUtils.getBitmapFromNet(ivpic,url);
    }
}
