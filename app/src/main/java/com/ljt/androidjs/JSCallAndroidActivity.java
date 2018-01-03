package com.ljt.androidjs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class JSCallAndroidActivity extends AppCompatActivity {
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jscall_android);
        mWebView = (WebView) findViewById(R.id.wb_jscall);
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        //通过addJavaScriptInterface()将Java对象映射到JS对象
        //参数1：JavaScript对象名
        //参数2 ：Java对象名
        //AndroidToJS类对象映射到js的test对象
        mWebView.addJavascriptInterface(new AndroidToJs(),"test");
        // 加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        mWebView.loadUrl("file:///android_asset/javascript.html");
    }
}
