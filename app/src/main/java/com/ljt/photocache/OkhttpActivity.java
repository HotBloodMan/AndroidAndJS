package com.ljt.photocache;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ljt.androidjs.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpActivity extends Activity {

    private static OkHttpClient client;
    private static final long cacheSize=1024*1024*20;
    private static String cacheDirectory= Environment.getExternalStorageDirectory()+"/okhttpcaches";
    private static Cache cache=new Cache(new File(cacheDirectory),cacheSize);
    private boolean flag=true;
    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS);
        builder.writeTimeout(8,TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.cache(cache);
        client=builder.build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        OkHttpClient okHttpClient = new OkHttpClient();
        //如果服务端没有做缓存处理的话，返回的响应头中Cache-Control是no-cache，
        // 这时还是无法做缓存。那么如果还想要实现缓存，可以通过拦截器修改响应头中Cache-Control
        //参考：https://www.jianshu.com/p/1f7ad1757198
        //方式1
        okHttpClient.newBuilder().addInterceptor(new HttpCacheInterceptor())
                .cache(cache).connectTimeout(10, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url("").build();
        //方式2 参考：https://www.cnblogs.com/lenve/p/6063851.html
        OkHttpClient client2 = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(new File(this.getExternalCacheDir(), "okhttpcache"), 10 * 1024 * 1024))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            private StringBuffer stringBuffer;

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    stringBuffer = new StringBuffer();
                    try{
                        JSONObject jo = new JSONObject(response.body().string());
                        JSONArray tngou = jo.optJSONArray("tngou");
                        for(int i=0;i<tngou.length();i++){
                            stringBuffer.append(tngou.optJSONObject(i).optString("name")).append("\n");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

    }
    //参考：https://www.jianshu.com/p/1f7ad1757198
    public class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
           if(flag){
               int maxAge=0;
               response.newBuilder().header("Cache-Control","public,max-age="+maxAge)
                       .removeHeader("Pragma")
                       .build();
           }else{
               int maxStale=60*60*24;
               response.newBuilder().header("Cache-Control","public,only-if-cached,max-stale"+maxStale)
                       .removeHeader("Pragma").build();
           }
            return response;
        }
    }

    public void Cache2(){
        CacheControl cControl = new CacheControl.Builder()
//                .minFresh(10,TimeUnit.SECONDS).maxAge(10,TimeUnit.SECONDS)
                .maxStale(5,TimeUnit.SECONDS).build(); //手机可以接收超出5s的响应
        Request request = new Request.Builder().cacheControl(cControl).url("").build();

    }


}
