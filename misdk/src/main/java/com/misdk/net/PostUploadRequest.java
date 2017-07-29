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
import com.misdk.form.FormImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by javakam on 16/7/18.
 */
public class PostUploadRequest<T> extends Request<T> {

    /**
     * 正确数据的时候回掉用
     */
    private ResponseListener<T> mListener;
    /*请求 数据通过参数的形式传入*/
    private List<FormImage> mListItem;
    /*请求 数据通过参数的形式传入*/
    private Map<String, String> mParams;
    /*用来解析 json 用的*/
    private Gson mGson;
    /*在用 gson 解析 json 数据的时候，需要用到这个参数*/
    private Type mClazz;

    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识(数据分隔线) 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    private String MULTIPART_FORM_DATA = "multipart/form-data";

    public PostUploadRequest(String url, Map<String, String> params, Class cla, List<FormImage> listItem, ResponseListener listener) {
        super(Method.POST, url, listener);
        this.mListener = listener;
        setShouldCache(false);
        this.mParams = params;
        mGson=new Gson();
        mClazz = cla;
        mListItem = listItem;
        //设置请求的响应事件，因为文件上传需要较长的时间，所以在这里加大了，设为5秒
        setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
            String mString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.v("123", "====mString===" + mString);
            result = mGson.fromJson(mString, mClazz);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
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
    public byte[] getBody() throws AuthFailureError {
        if (mListItem == null || mListItem.size() == 0) {
            return super.getBody();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int N = mListItem.size();
        FormImage formImage;

        /**
         * 当文件不为空，把文件包装并且上传
         */
//        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        StringBuffer sb = null;
        String params = "";

        /***
         * 以下是用于上传参数
         */
        if (mParams != null && mParams.size() > 0) {
            Iterator<String> it = mParams.keySet().iterator();
            while (it.hasNext()) {
                sb = null;
                sb = new StringBuffer();
//                String key = "cid";
                String key = it.next();
                String value = mParams.get(key);
                sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"").append(key)
                        .append("\"").append(LINE_END).append(LINE_END);
                sb.append(value);
//                sb.append(value).append(LINE_END);
                params = sb.toString();
//                Log.i("123", key + "=" + params + "##");

                try {
//                    bos.write(sb.toString().getBytes("utf-8"));
                /*第五行*/
                    bos.write(params.getBytes("utf-8"));
                    bos.write("\r\n".getBytes("utf-8"));

//                    Log.w("123", "参数-----" + sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        sb = null;
        params = null;
        sb = new StringBuffer();
        /**
         * 以下是上传图片
         */
        for (int i = 0; i < N; i++) {
            formImage = mListItem.get(i);
            /*第一行*/
            sb.append("--" + BOUNDARY);
            sb.append("\r\n");
            /*第二行*/
            //Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" + "\r\n"
            sb.append("Content-Disposition: form-data;");

            sb.append(" name=\"");
            sb.append(formImage.getName());
            sb.append("\"");

            sb.append("; filename=\"");
            sb.append(formImage.getFileName());
            sb.append("\"");
            sb.append("\r\n");
            /*第三行*/
            sb.append("Content-Type: ");
            sb.append(formImage.getMime());
            sb.append("\r\n");
            /*第四行*/
            sb.append("\r\n");
            try {
                bos.write(sb.toString().getBytes("utf-8"));
                /*第五行*/
                bos.write(formImage.getValue());
                bos.write("\r\n".getBytes("utf-8"));

//                Log.w("123", "图片草书-----" + StringUtils.utf8Encode(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        /*结尾行*/
        String endLine = "--" + BOUNDARY + "--" + "\r\n";
        try {
            bos.write(endLine.toString().getBytes("utf-8"));
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("123", "=====formImage====\n" + bos.toString());//总的打印
        return bos.toByteArray();
    }

    //Content-Type: multipart/form-data; boundary=----------8888888888888
    @Override
    public String getBodyContentType() {
        return MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
    }
}
