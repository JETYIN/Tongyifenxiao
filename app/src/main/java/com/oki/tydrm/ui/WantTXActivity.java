package com.oki.tydrm.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.CommissionDao;
import com.oki.tydrm.dao.CommwithdrawDao;
import com.oki.tydrm.dao.item.CommissionItem;
import com.oki.tydrm.service.ServiceProvider;

import java.util.List;
import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import cn.qmz.tools.view.header.view.HeaderLayout;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WantTXActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.sqstx_btn)
    RelativeLayout sqstx_btn;

    @ViewInject(R.id.money)
    TextView money;

    @ViewInject(R.id.withdraw_money)
    EditText withdraw_money;


    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBarForRight("我要提现", R.mipmap.history, new HeaderLayout.onRightClickListener() {
            @Override
            public void onClick() {
                ActivityUtils.to(getThis(), TXmxActivity.class);
            }
        });
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_tiqu_money_activity);

    }

    @Override
    public void initListener() {
        sqstx_btn.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {
        money.setText(AppConfig.getInstance().totalMoney);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.sqstx_btn:

                String moneyTxt = withdraw_money.getText().toString();

                if(StringUtils.isEmpty(moneyTxt)){
                    ToastUtils.show(getThis(),"请输入提现金额!", Toast.LENGTH_SHORT);
                    return;
                }

                date = AppConfig.getInstance().getDate();

                TreeMap<String ,Object> map = new TreeMap<String ,Object>();
                map.put("method",AppConfig.methodHead+"commwithdraw");
                map.put("member_id",AppConfig.getInstance().dao.data.member_id);
                map.put("withdraw_money",Integer.parseInt(moneyTxt));
                map.put("date",date);
                map.put("direct","true");
                sign = AppConfig.getInstance().getSign(map);

                ServiceProvider.getInstance().commwithdraw(
                        AppConfig.methodHead + "commwithdraw",
                        AppConfig.getInstance().dao.data.member_id,
                        Integer.parseInt(moneyTxt),
                        date,
                        sign,
                        true,
                        mCommWithdraw);

                break;

        }
    }

    Callback<CommwithdrawDao> mCommWithdraw = new Callback<CommwithdrawDao>() {
        @Override
        public void success(CommwithdrawDao dao, Response response) {
            if (dao.IsOK()) {

                ToastUtils.show(getThis(),dao.data,Toast.LENGTH_SHORT);

                if("succ".equals(dao.rsp)){
                    AppConfig.needFlash = true;
                    finish();
                }

            } else {
                hideAllDialog();
                showError();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            hideAllDialog();
            showError();
        }
    };


}
