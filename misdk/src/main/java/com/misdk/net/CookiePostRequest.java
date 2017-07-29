package com.misdk.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 涉及到用Cookie发送请求的情形，基本上只在登录处用
 * <p>
 * https://my.oschina.net/liusicong/blog/361853
 * <p>
 * Created by javakam on 2017/5/19 0019.
 */
public class CookiePostRequest extends Request<JSONObject> {
    private static final String LOG_TAG = "123";

    private Map<String, String> mMap;
    //    private Response.Listener<JSONObject> mListener;
    private ResponseListener<JSONObject> mListener;
    /**
     * 超时时间
     */
    private final int mTimeOut = 10000;

    private String cookieFromResponse;

    private Map<String, String> sendHeader = new HashMap<String, String>();

    public CookiePostRequest(String url, ResponseListener<JSONObject> listener, Map map) {
        super(Request.Method.POST, url, listener);
        setRetryPolicy(new DefaultRetryPolicy(mTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setShouldCache(false);
        mListener = listener;
        mMap = map;
    }

    //当http请求是post时，则需要该使用该函数设置往里面添加的键值对
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.w(LOG_TAG, "get headers in parseNetworkResponse " + response.headers.toString());
            Log.i(LOG_TAG, "jsonString " + jsonString);

            //使用正则表达式从reponse的头中提取cookie内容的子串
            Pattern pattern = Pattern.compile("Set-Cookie=.*?;");
            Matcher m = pattern.matcher(response.headers.toString());
            if (m.find()) {
                cookieFromResponse = m.group();
//                Log.e(LOG_TAG, "cookie from server " + cookieFromResponse);
            }
            //去掉cookie末尾的分号
            cookieFromResponse = cookieFromResponse.substring(22, cookieFromResponse.length() - 1);
//            Log.e(LOG_TAG, "cookie substring " + cookieFromResponse);
            //将cookie字符串添加到jsonObject中，该jsonObject会被deliverResponse递交，调用请求时则能在onResponse中得到
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("Cookie", cookieFromResponse);
            Log.i(LOG_TAG, "jsonObject " + jsonObject.toString());
            return Response.success(jsonObject,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    public void addSessionCookie(String key, String cookie) {
        sendHeader.put(key, cookie);
    }
}