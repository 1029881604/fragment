package team.antelope.fg.common;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * @Author：hwc
 * @Date：2018/1/3 13:34
 * @Desc: Glide框架的基本设置
 */
@GlideModule
public final class MyGlideModule extends AppGlideModule {
    /**
     * MemorySizeCalculator类通过考虑设备给定的可用内存和屏幕大小想出合理的默认大小.
     * 通过LruResourceCache进行缓存。
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
//        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb  自定义内存缓存大小
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));

//        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB Disk Cache.自定义内置磁盘缓存大小并指定路径.
//        builder.setDiskCache(
//                new InternalCacheDiskCacheFactory(context, "fgimg",
//                        diskCacheSizeBytes));

        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB Disk Cache.自定义外置磁盘缓存大小并指定路径.
        builder.setDiskCache(
                new ExternalCacheDiskCacheFactory(context,
                        "fgimg", diskCacheSizeBytes));
//        new ExternalPreferredCacheDiskCacheFactory();
    }
//    ExternalCacheDiskCacheFactory代表/sdcard/Android/data/<application package>/cache
//    InternalCacheDiskCacheFactory代表/data/data/<application package>/cache

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }

}
