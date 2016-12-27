package com.oki.tydrm.ui;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;

public class MyInfoActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.view4)
    RelativeLayout view4;

    @ViewInject(R.id.view5)
    RelativeLayout view5;

    @ViewInject(R.id.view6)
    RelativeLayout view6;

    @ViewInject(R.id.nickname)
    TextView nickname;

    @ViewInject(R.id.tel)
    TextView tel;

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("我的资料");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_my_info_activity);

    }

    @Override
    public void initListener() {
        view4.setOnClickListener(this);
        view5.setOnClickListener(this);
        view6.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {
        if(!StringUtils.isEmpty(AppConfig.getInstance().dao.data.name)){
            nickname.setText(AppConfig.getInstance().dao.data.name);
        }
        if(!StringUtils.isEmpty(AppConfig.getInstance().dao.data.mobile)){
            tel.setText(AppConfig.getInstance().dao.data.mobile);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.view4:
                ActivityUtils.to(getThis(), MyBankCardActivity.class);
                break;

            case R.id.view5:
                ActivityUtils.to(getThis(), MyBankCardActivity.class);
                break;

            case R.id.view6:
                ActivityUtils.to(getThis(), MyInvCodeActivity.class);
                break;

        }
    }




}
