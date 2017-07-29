package com.vitek.jcoa.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Base64;
import android.view.View;

import com.vitek.jcoa.Constant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 将bitmap保存在SD卡
 * <p/>
 * Created by javakam on 2016年7月30日17:21:19 .
 */
public class BitmapUtils {

    public static final int KB = 1024;
    private static final int QUALITY = 100;
    private static final int PRO_QUALITY = 10;

    /**
     * @param path
     * @param maxSize 最大允许的kb大小
     * @return
     */
    @Deprecated
    public static Bitmap compressBitmap(String path, int maxSize) {
        Bitmap bitmap = scaleBitmap(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 50, baos);
        if (maxSize != 0 && maxSize * KB < baos.toByteArray().length * 4) {
            int i = 1;
            do {
                int cur = QUALITY - PRO_QUALITY * i;
                baos.reset();
                bitmap.compress(CompressFormat.JPEG, cur, baos);
                System.out.println(
                        "cur  is -----" + cur + "------size is ---" + baos.toByteArray().length * 4 / 1024 + "kb");
                i++;
            } while (maxSize * KB < baos.toByteArray().length * 4 && i < 10);
        }
        System.out.println("bitmap size is -----" + baos.toByteArray().length * 4 / 1024 + "kb");
        bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
        return bitmap;
    }

    private static int maxHeight = 1280;

    /**
     * 图片存储的位置
     *
     * @throws FileNotFoundException
     */
    public static String saveBitmap(Bitmap b) throws Exception {
        String path = Constant.APP_FOLDER + "Pics";
        File mediaFile = new File(path);
        if (!mediaFile.exists()) {
            if (!mediaFile.mkdirs()) {
                return null;
            }
        }
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        File bitmapFile = new File(mediaFile.getPath() + File.separator + "IMG_" + name + ".jpg");
        FileOutputStream fos = new FileOutputStream(bitmapFile);
        b.compress(CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        b.recycle();
        return bitmapFile.getPath();
    }

    /**
     * 图片压缩处理
     *
     * @param path
     * @return
     */
    public static Bitmap scaleBitmap(String path) {

        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);

        System.out.println("原始--　width : " + opts.outWidth + " --- height : " + opts.outHeight);
        int temp = opts.outHeight > opts.outWidth ? opts.outHeight : opts.outWidth;
        if (temp > maxHeight) {

            int scale = temp / maxHeight;
            System.out.println("原始scale = " + scale);
            int i = 1;
            while (scale > Math.pow(2, i)) {
                i++;
            }
            scale = (int) Math.pow(2, i);
            System.out.println("计算后scale = " + scale);
            opts.inSampleSize = scale;
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, opts);
            System.out.println("压缩-- width : " + bitmap.getWidth() + " --- height : " + bitmap.getHeight());
            return bitmap;
        } else {
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, opts);
        }
        return bitmap;
    }

    public static Bitmap drawDate2Bitmap(Bitmap bitmap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String date = sdf.format(new Date());
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true); // 获取可改变的位图
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.RED);
        // text size in pixels
        paint.setTextSize(30);
        // text shadow
        // paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        Rect bounds = new Rect();
        paint.getTextBounds(date, 0, date.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width());
        canvas.drawText(date, x - 10, bitmap.getHeight() - 10, paint);
        canvas.save();
        return bitmap;
    }

    /**
     * 显示数值
     *
     * @param bitmap
     * @param number
     * @param radius
     * @param textsize
     * @return
     */
    public static Bitmap drawNumberToBitmap(Bitmap bitmap, String number, int radius, int textsize) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true); // 获取可改变的位图
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text size in pixels
        paint.setTextSize(textsize);
        Rect bounds = new Rect();
        paint.getTextBounds(number, 0, number.length(), bounds);
        paint.setColor(Color.RED);
        float cx = bitmap.getWidth() - radius;
        float cy = radius;
        canvas.drawCircle(cx, cy, radius, paint);
        canvas.save();
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(number, cx, cy + bounds.height() / 2, paint);
        canvas.save();
        return bitmap;
    }

    public static Bitmap convertViewToBitmap(View view) {
        Bitmap bitmap = null;
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width != 0 && height != 0) {
                bitmap = Bitmap.createBitmap(width, height,
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.layout(0, 0, width, height);
                view.setBackgroundColor(Color.WHITE);
                view.draw(canvas);
            }
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取图片的base64数据 用于上传图片
     *
     * @param path 图片路径
     * @return
     */
    public static String getPhotoString(String path) {
        Bitmap bitmap = BitmapUtils.compressBitmap(path, 300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, baos);
        String photoString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        bitmap.recycle();
        return photoString;
    }

}
