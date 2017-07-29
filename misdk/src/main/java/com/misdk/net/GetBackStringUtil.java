package com.misdk.net;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * 测试用<p>
 * Created by javakam on 2016/7/8.
 */
public class GetBackStringUtil {
    private String response = "";
    private VolleyError error = null;

    public GetBackStringUtil() {
    }

    public String getGetBack(String urlGet) {
        StringRequest request = new StringRequest(Request.Method.GET, urlGet, listener, errorListener);
        return response + "---" + error;
    }

    public String getPostBack(String urlPost) {
        Log.w("123", "getPostBack");
        StringRequest request = new StringRequest(Request.Method.POST, urlPost, listener, errorListener);
        return response + "---" + error;

    }

    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("123", "xxx" + response);
//            GetBackStringUtil.this.response = response;
        }
    };
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("123", "xxx" + error.toString());
//            GetBackStringUtil.this.error = error;
        }
    };
}
