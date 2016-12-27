package com.oki.tydrm.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.UserLoginDao;
import com.oki.tydrm.dao.WithdrawDao;
import com.oki.tydrm.service.ServiceProvider;

import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class WelcomeActivity extends ActivityBase {

    private String GUIDE_FILE_NAME = "user_guide";

    private String LOGIN_FILE_NAME = "user_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_activity);

        if (isOpenNetwork()) {
            skipActivity(2);
        } else {
            ToastUtils.show(this, "网络不给力！", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void initLayout() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 对网络连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    protected boolean isOpenNetwork()
    {
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null)
        {
            return connManager.getActiveNetworkInfo().isAvailable() || connManager.getActiveNetworkInfo().isConnected();
        }
        return false;
    }

    @Override
    public void initDisplay() {

    }

    @Override
    public void setOnHeaderClick() {

    }

    @Override
    public void refresh() {

    }

    /**
     * 延迟多少秒进入主界面
     *
     * @param min 秒
     */
    private void skipActivity(int min) {
        new android.os.Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences(GUIDE_FILE_NAME, MODE_PRIVATE);

                String guide = preferences.getString("guide", "false");

                if ("false".equals(guide)) {
                    Intent intent = new Intent(WelcomeActivity.this,
                            GuideActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                } else {

                    SharedPreferences infoPreferences = getSharedPreferences(LOGIN_FILE_NAME, MODE_PRIVATE);

                    String uname = infoPreferences.getString("uname", "");

                    String pass = infoPreferences.getString("pass", "");

                    String login = infoPreferences.getString("login", "false");

                    if (StringUtils.isEmpty(uname) || StringUtils.isEmpty(pass)||"false".equals(login)) {
                        Intent intent = new Intent(WelcomeActivity.this,
                                UserLoginActivity.class);
                        startActivity(intent);
                        WelcomeActivity.this.finish();
                    } else {
                        date = AppConfig.getInstance().getDate();

                        TreeMap<String ,Object> map = new TreeMap<String ,Object>();
                        map.put("method","mobileapi.passport.post_login");
                        map.put("uname",uname);
                        map.put("password",pass);
                        map.put("date",date);
                        map.put("direct","true");
                        sign = AppConfig.getInstance().getSign(map);

                        ServiceProvider.getInstance().post_login(
                                "mobileapi.passport.post_login",
                                uname,
                                pass,
                                date,
                                sign,
                                true,
                                mLogin);
                    }

                }


            }
        }, 1000 * min);
    }

    Callback<UserLoginDao> mLogin = new Callback<UserLoginDao>() {
        @Override
        public void success(UserLoginDao dao, Response response) {
            if(dao.IsOK()){
                AppConfig.getInstance().dao = dao;

                if(!StringUtils.isEmpty(dao.data.member_id)){
                    date = AppConfig.getInstance().getDate();
                    TreeMap<String ,Object> map = new TreeMap<String ,Object>();
                    map.put("method","mobileapi.member.withdraw");
                    map.put("member_id",dao.data.member_id);
                    map.put("date",date);
                    map.put("direct","true");
                    sign = AppConfig.getInstance().getSign(map);

                    ServiceProvider.getInstance().withdraw(
                            "mobileapi.member.withdraw",
                            dao.data.member_id,
                            date,
                            sign,
                            true,
                            mWithdraw
                    );
                }
            }else {
                ToastUtils.show(getThis(),dao.res, Toast.LENGTH_SHORT);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            hideAllDialog();
            showError();
        }
    };

    Callback<WithdrawDao> mWithdraw = new Callback<WithdrawDao>() {
        @Override
        public void success(WithdrawDao dao, Response response) {
            if(dao.IsOK()) {
                AppConfig.getInstance().totalMoney = dao.data.total_money;
                ActivityUtils.to(getThis(), UserMainActivity.class);
                finish();
                ToastUtils.show(getThis(), "欢迎回来!", Toast.LENGTH_SHORT);
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

}
