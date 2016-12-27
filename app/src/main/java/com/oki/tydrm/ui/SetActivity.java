package com.oki.tydrm.ui;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.ToastUtils;

public class SetActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.view7)
    RelativeLayout view7;

    @ViewInject(R.id.view8)
    RelativeLayout view8;

    @ViewInject(R.id.login_out)
    RelativeLayout login_out;

    private String LOGIN_FILE_NAME = "user_info";

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("设置");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_set_activity);

    }

    @Override
    public void initListener() {
        view7.setOnClickListener(this);
        view8.setOnClickListener(this);
        login_out.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view7:
                ActivityUtils.to(getThis(),WantTSActivity.class);
                break;

            case R.id.view8:
                ActivityUtils.to(getThis(),AboutActivity.class);
                break;

            case R.id.login_out:
                AppConfig.getInstance().dao = null;
                if(AppConfig.getInstance().main!=null){
                    AppConfig.getInstance().main.finish();
                }

                SharedPreferences preferences = getSharedPreferences(LOGIN_FILE_NAME, MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("login", "false");

                finish();
                ActivityUtils.to(getThis(), UserLoginActivity.class);
                ToastUtils.show(getThis(),"退出成功,请重新登录!", Toast.LENGTH_SHORT);
                break;
        }
    }
}
