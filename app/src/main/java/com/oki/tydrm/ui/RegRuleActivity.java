package com.oki.tydrm.ui;

import android.view.View;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;

public class RegRuleActivity extends ActivityBase implements View.OnClickListener{

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("同蚁");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_reg_rule);

    }

    @Override
    public void initListener() {
//        sqstx_btn.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.sqstx_btn:
                break;

        }
    }


}
