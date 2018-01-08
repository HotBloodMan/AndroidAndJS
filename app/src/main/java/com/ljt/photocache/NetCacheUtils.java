package com.ljt.photocache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ${JT.L} on 2018/1/3.
 */

public class NetCacheUtils {
    private LocalCacheUtils mLocalUtils;
    private MemoryCacheUtils memoryCacheUtils;
    private HttpURLConnection conn;

    public NetCacheUtils(LocalCacheUtils mLocalUtils, MemoryCacheUtils memoryCacheUtils) {
        this.mLocalUtils = mLocalUtils;
        this.memoryCacheUtils = memoryCacheUtils;
    }
    //从网络下载图片
    public void getBitmapFromNet(ImageView ivpic,String url){
        new BitmapTask().execute(ivpic,url);
    }

    class BitmapTask extends AsyncTask<Object,Void,Bitmap>{

        private ImageView ivPic;
        private String url;

        @Override
        protected Bitmap doInBackground(Object... params) {
           ivPic= (ImageView) params[0];
           url= (String) params[1];
            return downLoadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap!=null){
                ivPic.setImageBitmap(bitmap);
                mLocalUtils.setBitmapToLocal(url,bitmap);
                memoryCacheUtils.setBitmapToMemory(url,bitmap);
            }
        }
    }
    private Bitmap downLoadBitmap(String url){
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int responseCode = conn.getResponseCode();
            if(responseCode==200){
                InputStream inputStream = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=2;
                options.inPreferredConfig= Bitmap.Config.ARGB_4444;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                return bitmap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return null;
    }




}
