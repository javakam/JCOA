package com.misdk.net;

/**
 * Created by javakam on 16/7/18.
 */
public interface DataResponseListener<T> extends ResponseListener<T> {
     void postData(String data) ;
}
