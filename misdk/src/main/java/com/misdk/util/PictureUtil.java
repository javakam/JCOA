package com.misdk.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.view.View;

import java.io.ByteArrayOutputStream;

/**
 * 图片工具类<p>
 * Created by javakam on 2016/7/8.
 *
 * @version 1.0
 */
public class PictureUtil {

    /**
     * 截取应用程序界面（去除状态栏）
     *
     * @param activity 界面Activity
     * @return Bitmap对象
     */
    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, statusBarHeight, PhoneUtil.getScreenWidth(activity),
                PhoneUtil.getScreenHeight(activity) - statusBarHeight);
        view.destroyDrawingCache();
        return bitmap2;
    }

    /**
     * 截取应用程序界面
     *
     * @param activity 界面Activity
     * @return Bitmap对象
     */
    public static Bitmap takeFullScreenShot(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);

        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();

        View view = activity.getWindow().getDecorView();

        Bitmap bmp2 = Bitmap.createBitmap(480, 800, Config.ARGB_8888);

        // view.draw(new Canvas(bmp2));

        // bmp就是截取的图片了，可通过bmp.compress(CompressFormat.PNG, 100, new
        // FileOutputStream(file));把图片保存为文件。

        // 1、得到状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        System.out.println("状态栏高度：" + statusBarHeight);

        // 2、得到标题栏高度
        int wintop = activity.getWindow().findViewById(android.R.id.content).getTop();
        int titleBarHeight = wintop - statusBarHeight;
        System.out.println("标题栏高度:" + titleBarHeight);

        // //把两个bitmap合到一起
        // Bitmap bmpall=Biatmap.createBitmap(width,height,Config.ARGB_8888);
        // Canvas canvas=new Canvas(bmpall);
        // canvas.drawBitmap(bmp1,x,y,paint);
        // canvas.drawBitmap(bmp2,x,y,paint);

        return bmp;
    }

//    /**
//     * 需要zxing包
//     * <p>
//     * 根据指定内容生成自定义宽高的二维码图片
//     *
//     * @param content 需要生成二维码的内容
//     * @param width   二维码宽度
//     * @param height  二维码高度
//     * @throws WriterException 生成二维码异常
//     */
//    public static Bitmap makeQRImage(String content, int width, int height) throws WriterException {
//        // 判断URL合法性
//        if (content == null || content == "")
//            return null;
//
//        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        // 图像数据转换，使用了矩阵转换
//        BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//        int[] pixels = new int[width * height];
//        // 按照二维码的算法，逐个生成二维码的图片，两个for循环是图片横列扫描的结果
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                if (bitMatrix.get(x, y))
//                    pixels[y * width + x] = 0xff000000;
//                else
//                    pixels[y * width + x] = 0xffffffff;
//            }
//        }
//        // 生成二维码图片的格式，使用ARGB_8888
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }

    /**
     * Drawable转成Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 从资源文件中获取图片
     *
     * @param context    上下文
     * @param drawableId 资源文件id
     * @return
     */
    public static Bitmap gainBitmap(Context context, int drawableId) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
        return bmp;
    }

    /**
     * 将bitmap转成 byte数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] toBtyeArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将byte数组转成bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap bytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 将Bitmap转换成指定大小
     *
     * @param bitmap 需要改变大小的图片
     * @param width  宽
     * @param height 高
     * @return
     */
    public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * 在图片右下角添加水印
     *
     * @param srcBMP  原图
     * @param markBMP 水印图片
     * @return 合成水印后的图片
     */
    public static Bitmap composeWatermark(Bitmap srcBMP, Bitmap markBMP) {
        if (srcBMP == null) {
            return null;
        }
        // 创建一个新的和SRC长度宽度一样的位图
        Bitmap newb = Bitmap.createBitmap(srcBMP.getWidth(), srcBMP.getHeight(), Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        // 在 0，0坐标开始画入原图
        cv.drawBitmap(srcBMP, 0, 0, null);
        // 在原图的右下角画入水印
        cv.drawBitmap(markBMP, srcBMP.getWidth() - markBMP.getWidth() + 5, srcBMP.getHeight() - markBMP.getHeight() + 5,
                null);
        // 保存
        cv.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        cv.restore();

        return newb;
    }

    /**
     * 缩放图片
     *
     * @param bmp  需要缩放的图片源
     * @param newW 需要缩放成的图片宽度
     * @param newH 需要缩放成的图片高度
     * @return 缩放后的图片
     */
    public static Bitmap zoom(Bitmap bmp, int newW, int newH) {

        // 获得图片的宽高
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        // 计算缩放比例
        float scaleWidth = ((float) newW) / width;
        float scaleHeight = ((float) newH) / height;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
