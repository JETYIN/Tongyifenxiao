package cn.qmz.tools.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.qmz.tools.R;

/**
 * Created by Monica on 2015/7/31.
 */
public class ImageUtils {
    //图片地址
    private static String IMG_URL = "http://119.254.101.239:9090/layouFiles";

    private static ImageUtils instance = null;

    public static synchronized ImageUtils getInstance() {
        if(instance == null) {
            instance = new ImageUtils();
        }
        return instance;
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public static void displayFromSDCard(String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName
     *            图片名称，带后缀的，例如：1.png
     * @param imageView
     */
    public static void dispalyFromAssets(String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        ImageLoader.getInstance().displayImage("assets://" + imageName, imageView);
    }

    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId
     * @param imageView
     */
    public static void displayFromDrawable(int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        ImageLoader.getInstance().displayImage("drawable://" + imageId, imageView);
    }

    /**
     * 从内容提提供者中抓取图片
     */
    public static void displayFromContent(String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        ImageLoader.getInstance().displayImage("content://" + uri, imageView);
    }

    /**
     * 用于显示网络图片,采用不是很真实的色彩杜绝OOM
     * 用法  getBitmapUtils(getThis).display(img, "img1.gtimg.com/news/pics/hv1/63/26/1451/94357968.jpg";
     * @param img (不可传本地图片"file://sdcard0/94357968.jpg")
     * @param url
     */
    public static void displayFromUrl(String url, ImageView imageView) {
        if (StringUtils.isBlank(url))
            return;
        String globurl = IMG_URL + url;

        ImageLoader.getInstance().displayImage(globurl, imageView, image_display_options, new AnimateFirstDisplayListener());
    }

    //=====================================图片属性设置==================================

    //节省内存的options 一个像素消耗内存2B
    private static DisplayImageOptions image_display_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.empty_photo)
            .showImageOnFail(R.drawable.empty_photo)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    //=====================================图片动画设置==================================

    /**
     * 产生一个fadeIn动画显示图片
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}
