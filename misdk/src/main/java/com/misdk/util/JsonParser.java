package com.misdk.util;

import com.google.gson.Gson;

/**
 * Created by javakam on 2016/4/17 0017.
 */
public class JsonParser {
    public static <T> T toObject(String result, Class<T> cla) {
        Gson gson = new Gson();
        return gson.fromJson(result, cla);
    }
}
