package com.oki.tydrm.ui;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;

import cn.qmz.tools.utils.ActivityUtils;

public class UserReg2 extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.next)
    Button next;

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("注册");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_reg_2);

    }

    @Override
    public void initListener() {
        next.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.next:
                ActivityUtils.to(getThis(), UserReg3.class);
                break;

        }
    }

    //=================================== 登陆 START ==========================================


    //=================================== 登陆 END ==========================================



}
