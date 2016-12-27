package com.oki.tydrm.ui;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
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

public class UserLoginActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.login_btn)
    RelativeLayout login_btn;

    @ViewInject(R.id.userInput)
    EditText userInput;

    @ViewInject(R.id.passInput)
    EditText passInput;

    @ViewInject(R.id.text2)
    TextView text2;

    String date;

    String sign;

    private String LOGIN_FILE_NAME = "user_info";

    String name;

    String pass;

    @Override
    public void setOnHeaderClick() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_login);

    }

    @Override
    public void initListener() {
        login_btn.setOnClickListener(this);
        text2.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {
        SharedPreferences infoPreferences = getSharedPreferences(LOGIN_FILE_NAME, MODE_PRIVATE);

        String uname = infoPreferences.getString("uname", "");

        String pass = infoPreferences.getString("pass", "");

        userInput.setText(uname);

        passInput.setText(pass);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.login_btn:

                name = userInput.getText().toString();

                pass = passInput.getText().toString();

                if(StringUtils.isEmpty(name)){
                    ToastUtils.show(getThis(),"请输入用户名!",Toast.LENGTH_SHORT);
                    return;
                }

                if(StringUtils.isEmpty(pass)){
                    ToastUtils.show(getThis(),"请输入密码!",Toast.LENGTH_SHORT);
                    return;
                }

                date = AppConfig.getInstance().getDate();

                TreeMap<String ,Object> map = new TreeMap<String ,Object>();
                map.put("method","mobileapi.passport.post_login");
                map.put("uname",name);
                map.put("password",pass);
                map.put("date",date);
                map.put("direct","true");
                sign = AppConfig.getInstance().getSign(map);

                ServiceProvider.getInstance().post_login(
                        "mobileapi.passport.post_login",
                        name,
                        pass,
                        date,
                        sign,
                        true,
                        mLogin
                );
                break;

            case R.id.text2:
                ActivityUtils.to(getThis(), UserReg1.class);
                break;

        }

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

                SharedPreferences preferences = getSharedPreferences(LOGIN_FILE_NAME, MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("uname", name);
                editor.putString("pass", pass);
                editor.putString("login", "true");

                editor.commit();

                finish();


                ToastUtils.show(getThis(), "登录成功!", Toast.LENGTH_SHORT);
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
