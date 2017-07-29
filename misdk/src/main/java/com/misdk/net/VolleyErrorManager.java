package com.misdk.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Volley的异常列表：<p>
 * AuthFailureError：   如果在做一个HTTP的身份验证，可能会发生这个错误。<p>
 * NetworkError：       Socket关闭，服务器宕机，DNS错误都会产生这个错误。<p>
 * NoConnectionError：  和NetworkError类似，这个是客户端没有网络连接。<p>
 * ParseError：         在使用JsonObjectRequest或JsonArrayRequest时，如果接收到的JSON是畸形，会产生异常。<p>
 * ServerError：        服务器的响应的一个错误，最有可能的4xx或5xx HTTP状态代码。<p>
 * TimeoutError：       Socket超时，服务器太忙或网络延迟会产生这个异常。默认情况下，
 * Volley的超时时间为2.5秒。如果得到这个错误可以使用RetryPolicy。<p>
 * Created by javakam on 2016/7/5.
 */
public class VolleyErrorManager {
    public static String getErrorMsg(VolleyError error) {
        if (error == null) {
            return "VolleyError is null";
        }
        if (error instanceof AuthFailureError) {
//            Log.e("VolleyError", "getErrorMsg: "+error );
            return "HTTP身份验证错误";
        } else if (error instanceof NetworkError) {
            return "客户端没有网络连接";
        } else if (error instanceof NoConnectionError) {//没有在同一个网段，会报此异常
            return "网络连接异常";
        } else if (error instanceof ParseError) {
            return "数据解析异常";
        } else if (error instanceof ServerError) {
            return "服务器响应失败";
        } else if (error instanceof TimeoutError) {
            return "网络请求超时";
        } else if (error.toString().contains("com.google.gson.JsonSyntaxException")) {
            Log.e("123", "getErrorMsg: " + error);
            return "数据异常";//JSON数据异常
        } else {
            Log.e("123", "getErrorMsg: " + error);
            return "未知异常";//服务器异常  1.JSON数据异常 2....
        }
    }
}