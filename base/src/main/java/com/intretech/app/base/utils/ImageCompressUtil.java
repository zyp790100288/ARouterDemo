package com.intretech.app.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by yq06171 on 2017/2/24.
 * 图片压缩和角度转换
 */

public class ImageCompressUtil {
    final public static int DEFAULT_IMAGE_WIDTH = 1560;
    final public static int MIN_IMAGE_WIDTH = 768;

    public static Bitmap compressBitmapWithDefaultWidth(Bitmap bitmapIn, int width) {
        if (bitmapIn.getWidth() > width) {
            int targetWidth = width; // your arbitrary fixed limit
            int targetHeight = (int) (bitmapIn.getHeight() * targetWidth / (double) bitmapIn.getWidth());
            Bitmap result = Bitmap.createScaledBitmap(bitmapIn, targetWidth, targetHeight, true);
			return result;
        }
        return bitmapIn;
    }

    /**
     * 读取图片的旋转的角度
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {

            ExifInterface exifInterface = new ExifInterface(path);

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 鏍规嵁鏃嬭浆瑙掑害锛岀敓鎴愭棆杞煩闃�
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 灏嗗師濮嬪浘鐗囨寜鐓ф棆杞煩闃佃繘琛屾棆杞紝骞跺緱鍒版柊鐨勫浘鐗�
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }
 
    
    /** 
     * 质量压缩方法 
     * 
     * @param image 
     * @return 
     */  
    public static Bitmap compressImage(Bitmap image) {
      
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;  
      
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩  
            baos.reset(); // 重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;  
    }

    /**
     * bitmap图片转成字节
     *
     * @param bmp
     * @return
     */
    public static byte[] BmpToJPGByteArray(Bitmap bmp) {
        // Default size is 32 bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

}
