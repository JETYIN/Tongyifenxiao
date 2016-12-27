package com.oki.tydrm.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.application.BaseApplication;
import com.oki.tydrm.base.impl.IActivityBase;

import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.qmz.tools.server.INetEventHandle;
import cn.qmz.tools.server.impl.NetReceiver.NetState;
import cn.qmz.tools.view.dialog.EmptyDialog;
import cn.qmz.tools.view.dialog.ErrorDialog;
import cn.qmz.tools.view.dialog.LoadingDialog;
import cn.qmz.tools.view.dialog.NetDialog;
import cn.qmz.tools.view.dialog.impl.OnErrorListener;
import cn.qmz.tools.view.header.Header;

/**
 * Created by Monica on 2015/7/28.
 */
public abstract class ActivityBase extends Activity implements IActivityBase, INetEventHandle, OnErrorListener {
    protected Header mHeader;
    //初始化Applicaton
    protected BaseApplication mApp = null;

    protected String date;

    protected String sign;

    //主界面
    @ViewInject(R.id.main_layout)
    protected LinearLayout mainLayout;

    protected boolean isRefresh = true;

    protected boolean isRefresh2 = true;

    protected int nPage = 1;

    protected int nPage2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化Applicaton
        mApp = BaseApplication.getInstance();

        //1 - 初始化布局
        initLayout();
        //2 - 初始化自定义标题栏
        mHeader = new Header(this, this);
        //3 - 设置标题栏事件
        setOnHeaderClick();
        //4 - 调用ViewUtils方法 - 初始化控件
        ViewUtils.inject(this);
        //5 - 设置显示数据
        initDisplay();
        //6 - 设置点击事件
        initListener();
    }

    @Override
    public Activity getThis() {
        return this;
    }

    //====================================== 进度条 Start =======================================

    //================== 所有进度条全部隐藏 =====================
    protected void hideAllDialog() {
        hideEmpty();
        hideLoading();
        hideError();
        hideNet();
    }

    //========== 等待动画 ==========
    private LoadingDialog mLoading;

    /*
     * 显示进度条
	 */
    protected void showLoading() {
        if (mLoading == null) {
            mLoading = new LoadingDialog(getThis());
        }
        mLoading.show();
    }

    /*
	 * 隐藏进度条
	 */
    public void hideLoading() {
        if (mLoading == null) {
            return;
        }
        mLoading.hide();//显示自定义的等待提示框
    }

    //========== 网络数据连接失败界面 ==========
    private ErrorDialog mErrorLoading;

    /*
	 * 显示进度条
	 */
    protected void showError() {
//        if (mErrorLoading == null) {
//            mErrorLoading = new ErrorDialog(getThis());
//        }
//        mErrorLoading.show();
    }

    /*
	 * 隐藏进度条
	 */
    public void hideError() {
        if (mErrorLoading == null) {
            return;
        }
        mErrorLoading.hide();//显示自定义的等待提示框
    }

    @Override
    public void onError() {
        refresh();
    }

    //========== 没有开启网络界面 ==========
    private NetDialog mNetLoading;

    /*
	 * 显示进度条
	 */
    protected void showNet() {
        if (mNetLoading == null) {
            mNetLoading = new NetDialog(getThis());
        }
        mNetLoading.show();
    }

    /*
	 * 隐藏进度条
	 */
    public void hideNet() {
        if (mNetLoading == null) {
            return;
        }
        mNetLoading.hide();//显示自定义的等待提示框
    }

    //========== 无数据界面 ==========
    private EmptyDialog mEmptyLoading;

    /*
	 * 显示进度条
	 */
    protected void showEmpty() {
        if (mEmptyLoading == null) {
            mEmptyLoading = new EmptyDialog(getThis());
        }
        mEmptyLoading.show();
    }

    /*
	 * 光标移动到最右边
	 */
    protected void editTextToRight(EditText editText){
        Editable ea = editText.getText();
        editText.setSelection(ea.length());
    }

    /*
	 * 隐藏进度条
	 */
    public void hideEmpty() {
        if (mEmptyLoading == null) {
            return;
        }
        mEmptyLoading.hide();//显示自定义的等待提示框
    }
    //====================================== 进度条 End =======================================

    //====================================== 网络状态 Start =======================================

    /**
     * 判断网络状态
     */
    @Override
    public void netState(NetState netCode) {
        //无网络状态
        if (netCode == NetState.NET_NO) {
            mainLayout.setVisibility(View.GONE);
            hideAllDialog();
            showNet();
        } else {
            refresh();
        }
    }
    //====================================== 网络状态 End =======================================

    //====================================== 界面封装 Start =======================================

    private LayoutInflater mInflater;

    public View inflate(int resId) {
        if (null == mInflater) {
            mInflater = LayoutInflater.from(getThis());
        }
        return mInflater.inflate(resId, null);
    }
    //====================================== 界面封装 End =======================================


    //====================================== 退出动画 Start ===========================================
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        out2Left();
    }

    public void out2Left() {
        overridePendingTransition(R.anim.new_dync_no,
                R.anim.new_dync_out_to_left);
    }

    public void int4Right() {
        overridePendingTransition(R.anim.new_dync_in_from_right,
                R.anim.new_dync_no);
    }
    //====================================== 退出动画 End ===========================================

    //====================================== 封装自定位toast Start ==========================================

    /**
     * 对 和 错 图标
     *
     * @param flag
     * @param msg
     */
    public void showToast(boolean flag, String msg) {
        Toast toast = Toast.makeText(this, "自定义", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View view = View.inflate(this, R.layout.customer_toast, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        if (flag) {
            iv_icon.setImageResource(R.drawable.icon_toast_game_ok);
        } else {
            iv_icon.setImageResource(R.drawable.icon_toast_game_error);
        }
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des.setText(msg);
        toast.setView(view);
        toast.show();
    }

    //====================================== 封装toast End ============================================

    public static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 3;// 结果

    public Bitmap bitmap;

    public File tempFile;

    public boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    protected static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

}