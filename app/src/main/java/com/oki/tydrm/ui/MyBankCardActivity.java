package com.oki.tydrm.ui;

import android.view.View;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;

import cn.qmz.tools.utils.ActivityUtils;

public class MyBankCardActivity extends ActivityBase implements View.OnClickListener{

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBarForRight("我的银行卡","更换",null);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_my_bankcard);

    }

    @Override
    public void initListener() {
    }

    @Override
    public void initDisplay() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.view6:
                ActivityUtils.to(getThis(), MyInvCodeActivity.class);
                break;

        }
    }




}
