package com.misdk.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.misdk.form.FormText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by javakam on 16/7/18.
 */
public class PostFormRequest<T> extends Request<T> {

    /**
     * 正确数据的时候回掉用
     */
    private DataResponseListener<T> mListener ;
    /*用来解析 json 用的*/
    private Gson mGson ;
    /*在用 gson 解析 json 数据的时候，需要用到这个参数*/
    private Type mClazz ;
    /*请求 数据通过参数的形式传入*/
    private List<FormText> mListItem ;

    private String BOUNDARY = "---------8888888888888"; //数据分隔线
    private String MULTIPART_FORM_DATA = "multipart/form-data";

    public PostFormRequest(String url, List<FormText> listItem, Type type, DataResponseListener<T> listener) {
        super(Method.POST, url, listener);
        this.mListener = listener ;
//        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() ;
        mGson= new Gson();
        mClazz = type ;
        setShouldCache(false);
        mListItem = listItem ;
    }

    /**
     * 这里开始解析数据
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result ;
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.v("zgy", "====SearchResult===" + jsonString);
            result = mGson.fromJson(jsonString,mClazz) ;
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 回调正确的数据
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mListItem == null||mListItem.size() == 0){
            return super.getBody() ;
        }
        final ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        int N = mListItem.size() ;
        FormText formText ;
        for (int i = 0; i < N ;i++){
            formText = mListItem.get(i) ;
            StringBuffer sb= new StringBuffer() ;
            /*第一行*/
            sb.append("--"+BOUNDARY);
            sb.append("\r\n") ;
            /*第二行*/
            sb.append("Content-Disposition: form-data;");
            sb.append(" name=\"");
            sb.append(formText.getName()) ;
            sb.append("\"") ;
            sb.append("\r\n") ;
            /*第三行*/
            sb.append("\r\n") ;
            /*第四行*/
            sb.append(formText.getValue()) ;
            sb.append("\r\n") ;
            try {
                bos.write(sb.toString().getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*结尾行*/
        String endLine = "--" + BOUNDARY + "--"+ "\r\n" ;
        try {
            bos.write(endLine.toString().getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.postData(bos.toString());
            }
        }) ;
        Log.v("zgy","=====formText====\n"+bos.toString()) ;
        return bos.toByteArray();
    }

    @Override
    public String getBodyContentType() {
        return MULTIPART_FORM_DATA+"; boundary="+BOUNDARY;
    }
}
