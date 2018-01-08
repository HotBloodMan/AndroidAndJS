package com.ljt.androidjs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import static android.R.id.message;

//参考：https://www.jianshu.com/p/a25907862523
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private WebView mWebView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webView);
        btn = (Button) findViewById(R.id.btn_main);
        btn.setOnClickListener(this);
        mWebView.loadUrl("file:///android_asset/test.html");
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl("file:///android_asset/test.html");

        /*
        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        * */
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });
    }
    //Android调用有返回值js方法
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onClick(View v) {
        Log.e(TAG, "----->>>>onClick =");
        //方式 1
//        mWebView.post(new Runnable() {
//            @Override
//            public void run() {
//                // 注意调用的JS方法名要对应上
//                // 调用javascript的callJS()方法
//            mWebView.loadUrl("javascript:callJS()");
//            }
//        });
        //方式 2   Android 4.4以后
        mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
            }
        });

    }

}
