package com.vitek.jcoa.net;

import android.graphics.Bitmap;

import com.android.volley.Request;
import com.misdk.form.FormImage;
import com.misdk.net.PostUploadRequest;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javakam on 2017/5/29 0029.
 */
public class UploadUtil {

    /**
     * 上传图片接口
     *
     * @param bitmapList 需要上传的图片
     * @param listener   请求回调
     */
    public static void uploadImgList(String c_id, String file_type, List<Bitmap> bitmapList, Class cla, ResponseListener listener) {
        String url = JCOAApi.ADD_CFILES + ";jsessionid=" + LoginUtil.getLocalCookie();
        for (Bitmap bitmap : bitmapList) {
            List<FormImage> imageList = new ArrayList<FormImage>();
            imageList.add(new FormImage(bitmap));
            // 测试  c_id   99
            //file_type		enclosure          this_d:正件,enclosure：附件,ecpy_d:复印件
            // file    文件
            Request request = new PostUploadRequest(url,
                    JCOAApi.getAddCFilesMap(c_id, file_type)
                    , cla, imageList, listener);
            VolleyUtil.getRequestQueue().add(request);
        }
    }

    /**
     * 上传图片接口
     *
     * @param bitmap   需要上传的图片
     * @param listener 请求回调
     */
    public static void uploadImg(Bitmap bitmap, Class cla, ResponseListener listener) {
        List<FormImage> imageList = new ArrayList<FormImage>();
        imageList.add(new FormImage(bitmap));
        // 测试  c_id   99
        //file_type		enclosure          this_d:正件,enclosure：附件,ecpy_d:复印件
        // file    文件
        Request request = new PostUploadRequest(
                JCOAApi.ADD_CFILES
                        + ";jsessionid=" + LoginUtil.getLocalCookie(),
                JCOAApi.getAddCFilesMap("99", "this_d")
                , cla, imageList, listener);
        VolleyUtil.getRequestQueue().add(request);
    }
}