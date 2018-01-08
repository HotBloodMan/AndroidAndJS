package com.ljt.photocache;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

/**
 * Created by ${JT.L} on 2018/1/3.
 */

public class MemoryCacheUtils {

    private LruCache<String,Bitmap> mMemoryCache;

    public MemoryCacheUtils(){
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;
        mMemoryCache=new LruCache<String ,Bitmap>((int)maxMemory){
            //计算条目的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };
    }

    public Bitmap getBitmapFromMemory(String url){
        Bitmap bitmap = mMemoryCache.get(url);
        return bitmap;
    }
    public void setBitmapToMemory(String url,Bitmap bitmap){
        mMemoryCache.put(url,bitmap);
    }

}
