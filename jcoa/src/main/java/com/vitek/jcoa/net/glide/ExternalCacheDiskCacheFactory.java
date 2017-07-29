package com.vitek.jcoa.net.glide;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;

import java.io.File;

/**
 * SD卡缓存配置
 * Created by javakam on 16/8/8.
 */
public class ExternalCacheDiskCacheFactory extends DiskLruCacheFactory {
    public ExternalCacheDiskCacheFactory(Context context) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
    }

    public ExternalCacheDiskCacheFactory(Context context, int diskCacheSize) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public ExternalCacheDiskCacheFactory(final Context context, final String diskCacheName, int diskCacheSize) {
        super(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                File cacheDirectory;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 如果SD卡可以用的话把图片缓存到SD卡上
                    cacheDirectory = context.getExternalCacheDir();
                } else {
                    // 把图片缓存到应用data/data/包/...下
                    cacheDirectory = context.getCacheDir();
                }
                if (cacheDirectory == null) {
                    return null;
                }
                if (diskCacheName != null) {
                    return new File(cacheDirectory, diskCacheName);
                }
                return cacheDirectory;
            }
        }, diskCacheSize);
    }
}
