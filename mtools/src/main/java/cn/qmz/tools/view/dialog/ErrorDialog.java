package cn.qmz.tools.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.qmz.tools.R;
import cn.qmz.tools.view.dialog.impl.OnErrorListener;

/**
 * 网络错误
 * Created by Monica on 2015/8/4.
 */
public class ErrorDialog {
    Dialog loadingDialog;
    /**
     * 使用new方法默认创建一个文字在下方的等待提示框
     * @param context
     */
    public ErrorDialog(Context context) {
//        int fivedp_px = (int) PixelUtils.dp2px(5, context);//计算1dp的像素数
        boolean isCancel = false;//默认不可以取消
        boolean isRight = false;

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        View view = inflater.inflate(R.layout.loading_page_error, null);
        //动画旋转
        Button btn = (Button) view.findViewById(R.id.page_bt);
        btn.setText("网络数据连接失败，点击重连");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mErrorListener) {
                    // tab项被选中的回调事件
                    mErrorListener.onError();
                }
            }
        });
        
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

    // 回调接口，用于获取tab的选中状态
    private OnErrorListener mErrorListener;

    public void setOnErrorDialogListener(OnErrorListener listener) {
        this.mErrorListener = listener;
    }
}

