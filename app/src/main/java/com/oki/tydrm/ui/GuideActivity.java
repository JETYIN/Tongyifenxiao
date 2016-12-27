package com.oki.tydrm.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oki.tydrm.MainActivity;
import cn.shopex.scstore.R;
import com.oki.tydrm.adapter.GuidePagerAdapter;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.UserLoginDao;
import com.oki.tydrm.dao.WithdrawDao;
import com.oki.tydrm.service.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import cn.qmz.tools.view.indicator.CirclePageIndicator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GuideActivity extends ActivityBase {

    private ViewPager pager_splash_guide;
    private GuidePagerAdapter adapter;
    private CirclePageIndicator indicator;
    private String GUIDE_FILE_NAME = "user_guide";
    private String LOGIN_FILE_NAME = "user_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        pager_splash_guide = (ViewPager) findViewById(R.id.pager_splash_guide);

        List<View> views = new ArrayList<View>();

        View view1 = LayoutInflater.from(this).inflate(
                R.layout.view_guide_item, null);

        ImageView iv_img1 = (ImageView) view1.findViewById(R.id.iv_img);
        iv_img1.setImageResource(R.mipmap.guide1);
        views.add(view1);

        View view2 = LayoutInflater.from(this).inflate(
                R.layout.view_guide_item, null);
        ImageView iv_img2 = (ImageView) view2.findViewById(R.id.iv_img);
        iv_img2.setImageResource(R.mipmap.guide2);
        views.add(view2);

        View view3 = LayoutInflater.from(this).inflate(
                R.layout.view_guide_item, null);
        LinearLayout iv_bt3 = (LinearLayout) view3.findViewById(R.id.iv_bt);
        iv_bt3.setVisibility(View.VISIBLE);

        LinearLayout new_user = (LinearLayout) view3.findViewById(R.id.iv_bt);

        new_user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences(GUIDE_FILE_NAME, MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("guide", "true");

                editor.commit();

                SharedPreferences infoPreferences = getSharedPreferences(LOGIN_FILE_NAME, MODE_PRIVATE);

                String uname = infoPreferences.getString("uname", "");

                String pass = infoPreferences.getString("pass", "");

                String login = infoPreferences.getString("login", "false");

                if (StringUtils.isEmpty(uname) || StringUtils.isEmpty(pass)||"false".equals(login)) {
                    Intent intent = new Intent(getThis(),
                            UserLoginActivity.class);
                    startActivity(intent);
                    getThis().finish();
                } else {
                    date = AppConfig.getInstance().getDate();

                    TreeMap<String ,Object> map = new TreeMap<String ,Object>();
                    map.put("method","mobileapi.passport.post_login");
                    map.put("uname",uname);
                    map.put("password",pass);
                    map.put("date",date);
                    map.put("direct", "true");
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
        });

        ImageView iv_img3 = (ImageView) view3.findViewById(R.id.iv_img);
        iv_img3.setImageResource(R.mipmap.guide3);
        views.add(view3);

        adapter = new GuidePagerAdapter(this, views);
        pager_splash_guide.setAdapter(adapter);
        indicator = (CirclePageIndicator) findViewById(R.id.viewflowindic);
        indicator.setViewPager(pager_splash_guide);
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

    @Override
    public void initLayout() {

    }

    @Override
    public void initListener() {

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
}
