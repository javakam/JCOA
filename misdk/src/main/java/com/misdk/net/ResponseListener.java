package com.misdk.net;

import com.android.volley.Response;

/**
 * Volley的联网回调<p>
 * Created by javakam on 16/7/18.
 */
public interface ResponseListener<T> extends Response.ErrorListener, Response.Listener<T> {
}
