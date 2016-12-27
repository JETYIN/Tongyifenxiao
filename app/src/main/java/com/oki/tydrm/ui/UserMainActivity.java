package com.oki.tydrm.ui;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.WithdrawDao;
import com.oki.tydrm.service.ServiceProvider;

import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserMainActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.setting)
    ImageView setting;

    @ViewInject(R.id.my_info)
    TextView my_info;

    @ViewInject(R.id.money)
    TextView money;

    @ViewInject(R.id.wytx)
    Button wytx;

    @ViewInject(R.id.txmx_layout)
    RelativeLayout txmx_layout;

    @ViewInject(R.id.yjmx_layout)
    RelativeLayout yjmx_layout;

    @ViewInject(R.id.yhgl_layout)
    RelativeLayout yhgl_layout;

    @ViewInject(R.id.wysj_layout)
    RelativeLayout wysj_layout;

    @Override
    public void setOnHeaderClick() {

    }

    @Override
    public void refresh() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppConfig.needFlash){
            if(!StringUtils.isEmpty(AppConfig.getInstance().dao.data.member_id)){
                date = AppConfig.getInstance().getDate();
                TreeMap<String ,Object> map = new TreeMap<String ,Object>();
                map.put("method","mobileapi.member.withdraw");
                map.put("member_id",AppConfig.getInstance().dao.data.member_id);
                map.put("date",date);
                map.put("direct","true");
                sign = AppConfig.getInstance().getSign(map);

                ServiceProvider.getInstance().withdraw(
                        "mobileapi.member.withdraw",
                        AppConfig.getInstance().dao.data.member_id,
                        date,
                        sign,
                        true,
                        mWithdraw
                );
            }
        }
    }

    Callback<WithdrawDao> mWithdraw = new Callback<WithdrawDao>() {
        @Override
        public void success(WithdrawDao dao, Response response) {
            if(dao.IsOK()) {
                AppConfig.getInstance().totalMoney = dao.data.total_money;
                money.setText(AppConfig.getInstance().totalMoney);
                AppConfig.needFlash = false;
            }else{
                ToastUtils.show(getThis(),dao.res, Toast.LENGTH_SHORT);
            }

        }

        @Override
        public void failure(RetrofitError error) {
            hideAllDialog();
            showError();
        }
    };

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_main_activity);
    }

    @Override
    public void initListener() {
        setting.setOnClickListener(this);
        my_info.setOnClickListener(this);
        wytx.setOnClickListener(this);
        txmx_layout.setOnClickListener(this);
        yjmx_layout.setOnClickListener(this);
        yhgl_layout.setOnClickListener(this);
        wysj_layout.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {
        if(AppConfig.getInstance().dao!=null){
              if(!StringUtils.isEmpty(AppConfig.getInstance().dao.data.name)){
                  my_info.setText(AppConfig.getInstance().dao.data.name);
              }
            if(!StringUtils.isEmpty(AppConfig.getInstance().totalMoney)){
                money.setText(AppConfig.getInstance().totalMoney);
            }
        }
        AppConfig.getInstance().main = (UserMainActivity)getThis();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.setting:
                ActivityUtils.to(getThis(), SetActivity.class);
                break;

            case R.id.my_info:
                ActivityUtils.to(getThis(), MyInfoActivity.class);
                break;

            case R.id.wytx:
                ActivityUtils.to(getThis(), WantTXActivity.class);
                break;

            case R.id.txmx_layout:
                ActivityUtils.to(getThis(), TXmxActivity.class);
                break;

            case R.id.yhgl_layout:
                ActivityUtils.to(getThis(), UserMangerActivity.class);
                break;

            case R.id.yjmx_layout:
                ActivityUtils.to(getThis(), YJmxActivity.class);
                break;

            case R.id.wysj_layout:
                ActivityUtils.to(getThis(), WantUpdateActivity.class);
                break;

        }

    }
    // ==========================================退出=====================================
    private static long firstTime;

    /**
     * 连续按两次返回键就退出
     */
    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            super.onBackPressed();
        } else {
            ToastUtils.show(this, "再按一次退出程序");
        }
        firstTime = System.currentTimeMillis();
    }

    // ==========================================退出=====================================

}
