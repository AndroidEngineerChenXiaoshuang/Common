package com.jixingmao.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.provider.MediaStore;
import android.view.View;

public class BitmapImageUtils {
    /**
     * 生成View的bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap createViewBitmap(View v) {
        //测量使得view指定大小
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        return bmp;
    }

    /**
     * 保存图片到图库
     *
     * @param bmp
     */
    public static void saveBitmapToGallery(Context context, Bitmap bmp) {
        //插入到系统图库
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, "mine", "description");
    }
}
