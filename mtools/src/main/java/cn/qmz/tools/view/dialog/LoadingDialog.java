package cn.qmz.tools.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.qmz.tools.R;

/**
 * Created by Monica on 2015/7/31.
 */
public class LoadingDialog {

    Dialog loadingDialog;
    /**
     * 使用new方法默认创建一个文字在下方的等待提示框
     * @param context
     */
    public LoadingDialog(Context context) {
//        int fivedp_px = (int) PixelUtils.dp2px(5, context);//计算1dp的像素数
        boolean isCancel = false;//默认不可以取消
        boolean isRight = false;

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        View view = inflater.inflate(R.layout.loading_page_loading, null);
        //动画旋转
        ImageView imageView = (ImageView) view.findViewById(R.id.pb_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView
                .getBackground();
        animationDrawable.start();

        loadingDialog = new Dialog(context, R.style.Dialog_Fullscreen);// 创建自定义样式dialog
        
        loadingDialog.setContentView(view);// 设置布局
        //loadingDialog.show();
    }

    public void show() {
        try {
            loadingDialog.show();
        } catch (Exception e) {
        }
    }

    public void hide() {
        try {
            loadingDialog.hide();
        } catch (Exception e) {
        }
    }
}