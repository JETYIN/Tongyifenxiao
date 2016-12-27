package com.oki.tydrm.ui;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;

import cn.qmz.tools.utils.ActivityUtils;

public class UserReg1 extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.next)
    Button next;

    @ViewInject(R.id.text2)
    TextView text2;

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("注册");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_reg_1);

    }

    @Override
    public void initListener() {
        next.setOnClickListener(this);
        text2.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.next:
                ActivityUtils.to(getThis(), UserReg2.class);
                break;

            case R.id.text2:
                ActivityUtils.to(getThis(), RegRuleActivity.class);
                break;

        }
    }

    //=================================== 登陆 START ==========================================


    //=================================== 登陆 END ==========================================



}
