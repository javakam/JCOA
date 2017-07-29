package com.misdk.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by javakam on 16/7/18.
 */
public class PostObjectRequest<T> extends Request<T> {
    private static final String LOG_TAG = "123";

    /**
     * 正确数据的时候回掉用
     */
    private ResponseListener<T> mListener;
    /*用来解析 json 用的*/
    private Gson mGson;
    /*在用 gson 解析 json 数据的时候，需要用到这个参数*/
    private Type mClazz;
    /**
     * 超时时间
     */
    private final int mTimeOut = 5000;
    /*请求 数据通过参数的形式传入*/
    private Map<String, String> mParams;

    private String jsonString = "";
    private Map<String, String> sendHeader = new HashMap<String, String>();
    private String sss;

    public PostObjectRequest(String url, Map<String, String> params, Type type, ResponseListener<T> listener) {
        super(Method.POST, url, listener);
        this.mListener = listener;
//        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        mGson = new Gson();
        mClazz = type;
        setRetryPolicy(new DefaultRetryPolicy(mTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setShouldCache(false);
        mParams = params;
    }

    public PostObjectRequest(String url, Map<String, String> params, Class cla, ResponseListener<T> listener) {
        super(Method.POST, url, listener);
        this.mListener = listener;
//        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        mGson = new Gson();
        mClazz = cla;
        setRetryPolicy(new DefaultRetryPolicy(mTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setShouldCache(false);
        this.mParams = params;
    }

    /**
     * 这里开始解析数据
     *
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result;
            jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.w(LOG_TAG, "====get headers in parseNetworkResponse====" + response.headers.toString());
            Log.v(LOG_TAG, "====POST-NetworkResponse===" + jsonString);
            result = mGson.fromJson(jsonString, mClazz);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.i(LOG_TAG, "====POST-NetworkResponse Exception===" + Response.error(new ParseError(e)));
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    /**
     * 回调正确的数据
     *
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    //    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        Log.v(LOG_TAG, "getHeaders  Cookie  ---  " + sss);
//        //获取存储的session值
//        if (!sss.equals("")) {
//            HashMap<String, String> headers = new HashMap<String, String>();
//            headers.put("jsessionid", sss);//设置session    ";jsessionid="+
//            Log.v(LOG_TAG, " headers ======" + headers);
//            return headers;
//        } else {
//            return super.getHeaders();
//        }
//    }

    public void addSessionCookie(String cookie) {
        sss = cookie;
    }

    public String getJsonString() {
        return jsonString;
    }
}
