package com.misdk.net;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by gyzhong on 15/3/1.
 */
public class GetObjectRequest<T> extends Request<T> {
    private static final String LOG_TAG = "123";
    /**
     * 正确数据的时候回掉用
     */
    private ResponseListener<T> mListener;
    /**
     * 用来解析 json 用的
     */
    private Gson mGson;
    /**
     * 在用 gson 解析 json 数据的时候，需要用到这个参数
     */
    private Type mClazz;
    /**
     * 超时时间
     */
    private int mTimeOut = 10000;
    private String cookie;

    public GetObjectRequest(String url, Type type, ResponseListener<T> listener) {
        super(Method.GET, url, listener);
        this.mListener = listener;
        /**
         * @Expose注解的作用：区分实体中不想被序列化的属性，
         * 其自身包含两个属性deserialize(反序列化)和serialize（序列化），默认都为true。
         * 使用 new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
         * 创建Gson对象，没有@Expose注释的属性将不会被序列化.。
         *
         * kam 如果采用下面注释掉的方法，需要在试题中的属性前面加上@Expose注解
         */
//        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        mGson = new Gson();
        mClazz = type;
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(mTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * 不带缓存的get请求
     *
     * @param url
     * @param cla
     * @param listener
     */
    public GetObjectRequest(String url, Class cla, ResponseListener<T> listener) {
        super(Method.GET, url, listener);
        this.mListener = listener;
        mGson = new Gson();
        mClazz = cla;
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(mTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * 带缓存的get请求
     *
     * @param url
     * @param cla
     * @param cache
     * @param listener
     */
    public GetObjectRequest(String url, Class cla, boolean cache, ResponseListener<T> listener) {
        super(Method.GET, url, listener);
        this.mListener = listener;
        mGson = new Gson();
        mClazz = cla;
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(mTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.v(LOG_TAG, "====GET-NetworkResponse Header===" + response.headers.toString());
            //联网返回最原生的JSON格式的数据
            Log.v(LOG_TAG, "====GET-NetworkResponse===" + jsonString);
            result = mGson.fromJson(jsonString, mClazz);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.v(LOG_TAG, "====GET-NetworkResponse Exception===" + Response.error(new ParseError(e)));
            return Response.error(new ParseError(e));
        }
    }

//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        Map<String, String> map = new HashMap<>();
//        map.put("Set-Cookie", cookie);
//        return map;
//    }
//
//    public void setHeaders(String cookie) {
//        this.cookie = cookie;
//    }

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
    protected String getParamsEncoding() {
        return super.getParamsEncoding();
    }
}

