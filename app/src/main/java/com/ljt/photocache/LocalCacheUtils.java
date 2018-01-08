package com.ljt.photocache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by ${JT.L} on 2018/1/3.
 */

public class LocalCacheUtils {

     private static final String CACHE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/webnews";

    //从本地读取
  public Bitmap getBitmapFromLocal(String url){
      String fileName;
      try {
          fileName=MD5Encoder.encode(url);
          File file = new File(CACHE_PATH, fileName);
          Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
          return bitmap;
      }catch (Exception e){
          e.printStackTrace();
      }

      return null;
  }

    //从网络获取图片后，保存至本地缓存
    public void setBitmapToLocal(String url,Bitmap bitmap){
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH, fileName);
            if(!file.exists()){
                file.mkdirs();
            }
         bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
