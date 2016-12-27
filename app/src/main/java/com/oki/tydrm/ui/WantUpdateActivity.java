package com.oki.tydrm.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.adapter.SelectPayAdapter;
import com.oki.tydrm.adapter.TXmxAdapter;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.DistributionInfoDao;
import com.oki.tydrm.dao.SelectPaymentDao;
import com.oki.tydrm.dao.item.SelectPaymentItem;
import com.oki.tydrm.dao.item.UserWithdrawItem;
import com.oki.tydrm.service.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.qmz.tools.utils.ToastUtils;
import cn.qmz.tools.view.bugfix.MyListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WantUpdateActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.select_pay)
    MyListView select_pay;
    private List<SelectPaymentItem> mList = null;
    private SelectPayAdapter mAdapter = null;

    @ViewInject(R.id.login_btn)
    RelativeLayout login_btn;

    @ViewInject(R.id.my_level_txt)
    TextView my_level_txt;

    @ViewInject(R.id.next_level_txt)
    TextView next_level_txt;

    @ViewInject(R.id.money)
    TextView money;

    private int position = 0;

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("我要升级");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_want_update_activity);

    }

    @Override
    public void initListener() {
        login_btn.setOnClickListener(this);
        select_pay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i =0;i<mList.size();i++){
                    mList.get(i).check = false;
                }
                WantUpdateActivity.this.position = position;
                mList.get(position).check = true;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initDisplay() {

        date = AppConfig.getInstance().getDate();

        TreeMap<String ,Object> map = new TreeMap<String ,Object>();
        map.put("method",AppConfig.methodHead+"upgrade");
        map.put("member_id",AppConfig.getInstance().dao.data.member_id);
        map.put("date",date);
        map.put("direct","true");
        sign = AppConfig.getInstance().getSign(map);

        ServiceProvider.getInstance().upgrade(
                AppConfig.methodHead + "upgrade",
                AppConfig.getInstance().dao.data.member_id,
                date,
                sign,
                true,
                mUpgrade);

        date = AppConfig.getInstance().getDate();

        TreeMap<String ,Object> map2 = new TreeMap<String ,Object>();
        map2.put("method","mobileapi.order.select_payment");
        map2.put("date", date);
        map2.put("direct","true");
        sign = AppConfig.getInstance().getSign(map2);

        ServiceProvider.getInstance().select_payment(
                "mobileapi.order.select_payment",
                date,
                sign,
                true,
                mSelect_payment);

        mList = new ArrayList<SelectPaymentItem>();
        mAdapter = new SelectPayAdapter(getThis());
        select_pay.setAdapter(mAdapter);

    }

    Callback<SelectPaymentDao> mSelect_payment = new Callback<SelectPaymentDao>() {
        @Override
        public void success(SelectPaymentDao dao, Response response) {
            if(dao.IsOK()) {
                if(dao.data!=null){
                    mList = dao.data;
                    if(mList.size()>0){

                        for(int i = 0;i<mList.size();i++){
                            if(mList.get(i)!=null&&"deposit".equals(mList.get(i).app_rpc_id)){
                                mList.remove(i);
                            }
                        }

                        mList.get(0).check = true;
                    }
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                }
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

    Callback<JsonObject> mUpgrade = new Callback<JsonObject>() {
        @Override
        public void success(JsonObject dao, Response response) {
            String rsp = dao.get("rsp").getAsString();
            if("succ".equals(rsp)){
                String level_id = dao.getAsJsonObject("data").get("level_id").getAsString();
                String upgrade_level_id = dao.getAsJsonObject("data").get("upgrade_level_id").getAsString();
                String datamoney = dao.getAsJsonObject("data").get("datamoney").getAsString();
                my_level_txt.setText(level_id+"级分销商");
                next_level_txt.setText(upgrade_level_id+"级分销商");
                money.setText(datamoney);
            }else {
                ToastUtils.show(getThis(), dao.get("data").getAsString(), Toast.LENGTH_SHORT);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            hideAllDialog();
            showError();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:










                break;
        }
    }
}
