package com.oki.tydrm.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.CommwithdrawDao;
import com.oki.tydrm.service.ServiceProvider;

import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import cn.qmz.tools.view.header.view.HeaderLayout;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WantTSActivity extends ActivityBase implements View.OnClickListener{

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("我要投诉");

    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_want_ts);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDisplay() {

    }

    @Override
    public void onClick(View v) {


    }

}
