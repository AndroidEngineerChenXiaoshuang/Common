package com.jixingmao.common.utils;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.jixingmao.common.R;

import java.io.File;

public class GlideUtils {

    private GlideUtils() {
    }

    private static GlideUtils glideUtils = new GlideUtils();

    public static GlideUtils getInstance() {
        return glideUtils;
    }

    //1.加载图片
    public void showPic(String url, ImageView iv) {
        Glide.with(ContextUtils.getContext())
                .load(url)
                .skipMemoryCache(false)  //内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)  //缓存到sd卡
                .placeholder(R.mipmap.glide_placeholder)
                .error(R.mipmap.glide_error)
                .into(iv);
    }

    public void showPic(Bitmap bitmap, ImageView iv) {
        Glide.with(ContextUtils.getContext())
                .load(bitmap)
                .skipMemoryCache(false)  //内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)  //缓存到sd卡
                .placeholder(R.mipmap.glide_placeholder)
                .error(R.mipmap.glide_error)
                .into(iv);
    }

    //加载文件图片
    public void showFilePic(String filePath, ImageView iv) {
        String loadPath;
        if (filePath.startsWith("content")) {
            loadPath = UriUtils.getPath(ContextUtils.getContext(), Uri.parse(filePath));
        } else {
            loadPath = filePath;
        }
        LogUtils.e("loadPath = " + loadPath);
        if (loadPath != null) {
            Glide.with(ContextUtils.getContext())
                    .load(new File(loadPath))
                    .skipMemoryCache(false)  //内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)  //缓存到sd卡
                    .placeholder(R.mipmap.glide_placeholder)
                    .error(R.mipmap.glide_error)
                    .into(iv);
        }
    }

    //加载资源图片
    public void showPic(int imgResId, ImageView iv) {
        Glide.with(ContextUtils.getContext())
                .load(imgResId)
                .skipMemoryCache(false)  //内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)  //缓存到sd卡
                .placeholder(R.mipmap.glide_placeholder)
                .error(R.mipmap.glide_error)
                .into(iv);
    }


    //2.加载圆形图片
    public void showCirclePic(int resource, ImageView iv) {
        Glide.with(ContextUtils.getContext())
                .load(resource)
                .skipMemoryCache(true)  //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //不缓存到sd卡
                .placeholder(R.mipmap.glide_placeholder_circle)
                .error(R.mipmap.glide_error_circle)
                .circleCrop()
                .into(iv);
    }

    //2.加载圆形图片
    public void showCirclePic(Uri url, ImageView iv) {
        Glide.with(ContextUtils.getContext())
                .load(url)
                .skipMemoryCache(true)  //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //不缓存到sd卡
                .placeholder(R.mipmap.glide_placeholder_circle)
                .error(R.mipmap.glide_error_circle)
                .circleCrop()
                .into(iv);
    }

    //2.加载圆形图片
    public void showCirclePic(String url, ImageView iv) {
        Glide.with(ContextUtils.getContext())
                .load(url)
                .skipMemoryCache(false)  //内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)  //缓存到sd卡
                .placeholder(R.mipmap.glide_placeholder_circle)
                .error(R.mipmap.glide_error_circle)
                .circleCrop()
                .into(iv);
    }

    //3.加载圆角图片
    public void showRoundedPic(String url, ImageView iv, int r) {
        Glide.with(ContextUtils.getContext())
                .load(url)
                .skipMemoryCache(true)    //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //不缓存到sd卡
                .placeholder(R.mipmap.glide_placeholder) //占位符
                .error(R.mipmap.glide_error)
                .transform(new CenterCrop(), new RoundedCorners(r))
                .into(iv);
    }
}