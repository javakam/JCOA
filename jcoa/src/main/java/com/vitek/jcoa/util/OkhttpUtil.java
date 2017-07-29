package com.vitek.jcoa.util;

import android.content.Context;

import com.misdk.util.ToastUtil;
import com.vitek.jcoa.net.JCOAApi;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class OkhttpUtil {
    private Context context;

    public OkhttpUtil(Context context) {
        this.context = context;
    }

    public void upImage() {
// 测试  c_id   99
        //file_type		enclosure          this_d:正件,enclosure：附件,ecpy_d:复印件
        // file    文件
//        File file=new File()
        String url = JCOAApi.ADD_CFILES;
        String c_id = "96";
        String file_type = "enclosure";
        File mFile =
                new File("/storage/sdcard0/zapya/photo/Screenshot_2017-03-29-10-37-23-495_com.eg.android.AlipayGphone.png");
//        String sha1_ = MD5Util.toMd5(mFile);
//        String size = String.valueOf(mFile.length());

        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.addFormDataPart("sha1",sha1_);
        builder.addFormDataPart("c_id", c_id);
        builder.addFormDataPart("file_type", file_type);
        builder.addFormDataPart("type", "1");
        builder.addFormDataPart("temp" + System.currentTimeMillis() + ".png", mFile.getAbsolutePath()
                , RequestBody.create(MediaType.parse("application/octet-stream"), mFile));

        MultipartBody multipartBody = builder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .post(multipartBody)
                .url(url)
                .build();

        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtil.longToast(context, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                    ToastUtil.longToast(context,"0000");
                VLogUtil.e("000000000000000");
            }
        });
    }


}
