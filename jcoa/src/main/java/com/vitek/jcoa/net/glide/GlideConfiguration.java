package com.vitek.jcoa.net.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.volley.VolleyGlideModule;


/**
 * Glide setting
 * http://blog.csdn.net/xx326664162/article/details/50971769
 * Created by javakam on 16/8/8.
 */
public class GlideConfiguration extends VolleyGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // set size & external vs. internal
        int cacheSize100MegaBytes = 104857600;
        // 配置图片将缓存到SD卡
        ExternalCacheDiskCacheFactory externalCacheDiskCacheFactory
                = new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes);

        builder.setDiskCache(externalCacheDiskCacheFactory);
        // 如果配置图片将缓存到SD卡后那么getPhotoCacheDir返回仍然没有变化
        Log.w("123", Glide.getPhotoCacheDir(context).getPath());
        //   /data/user/0/com.mirun.quiz/cache/image_manager_disk_cache


        // Glide 内部使用了 MemorySizeCalculator 类去决定内存缓存大小以及 bitmap 的缓存池
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
//        //120% 缓存作为 Glide 的默认值
////        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
////        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
//
//        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize));
//        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        super.registerComponents(context, glide);
    }
}
