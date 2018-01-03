package com.ljt.androidjs;

import android.webkit.JavascriptInterface;

/**
 * Created by ${JT.L} on 2018/1/3.
 */

public class AndroidToJs extends Object {

    //定义JS需要调用的方法
    //被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg){
        System.out.println("JS调用了Android的hello方法");
    }
}
