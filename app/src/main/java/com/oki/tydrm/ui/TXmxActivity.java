package com.oki.tydrm.ui;

import android.view.View;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.adapter.TXmxAdapter;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.UserWithdrawDao;
import com.oki.tydrm.dao.item.UserWithdrawItem;
import com.oki.tydrm.service.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import cn.qmz.tools.view.listview.PullRefreshListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TXmxActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.tx_list)
    PullRefreshListView tx_list;

    private List<UserWithdrawItem> mList = null;

    private TXmxAdapter mAdapter = null;

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("提现明细");
    }

    @Override
    public void refresh() {
        startRefresh();

        date = AppConfig.getInstance().getDate();

        TreeMap<String ,Object> map = new TreeMap<String ,Object>();
        map.put("method",AppConfig.methodHead+"userwithdraw");
        map.put("member_id",AppConfig.getInstance().dao.data.member_id);
        map.put("nPage",nPage);
        map.put("date",date);
        map.put("direct","true");
        sign = AppConfig.getInstance().getSign(map);

//        ToastUtils.show(getThis(),nPage+"", Toast.LENGTH_SHORT);

        ServiceProvider.getInstance().userwithdraw(
                AppConfig.methodHead + "userwithdraw",
                AppConfig.getInstance().dao.data.member_id,
                nPage,
                date,
                sign,
                true,
                mGetAllList);
    }

    public void flashView(){
        isRefresh = true;
        nPage = 1;
        refresh();
    }

    Callback<UserWithdrawDao> mGetAllList = new Callback<UserWithdrawDao>() {
        @Override
        public void success(UserWithdrawDao dao, Response response) {
            if (dao.IsOK()) {
                List<UserWithdrawItem> data = dao.data;
                if (data != null&&data.size()>0) {

                            if (isRefresh)
                                mList.clear();

                            mList.addAll(data);
                            mAdapter.setList(mList);
                            mAdapter.notifyDataSetChanged();

                }
            } else {
                hideAllDialog();
                showError();
            }
            stopRefresh();
        }

        @Override
        public void failure(RetrofitError error) {
            hideAllDialog();
            showError();
        }
    };

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_tx_mx);

    }

    @Override
    public void initListener() {
        tx_list.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //首次进行刷新数据
                isRefresh = true;
                nPage = 1;
                refresh();
            }
        });

        tx_list.setOnLoadMoreListener(new PullRefreshListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                nPage++;
                refresh();
            }
        });
    }

    @Override
    public void initDisplay() {
        mList = new ArrayList<UserWithdrawItem>();
        mAdapter = new TXmxAdapter(getThis());
        tx_list.setAdapter(mAdapter);
        if (!StringUtils.isEmpty(AppConfig.getInstance().dao.data.member_id)) {
            isRefresh = true;
            refresh();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.view6:
                ActivityUtils.to(getThis(), MyInvCodeActivity.class);
                break;

        }
    }

    /**
     * 开启刷新
     */
    protected void startRefresh() {
        //开启刷新Loading
        if (isRefresh) {
            tx_list.onRefreshStart();
        } else {
            tx_list.onLoadMoreStart();
        }
    }

    /**
     * 停止刷新
     */
    protected void stopRefresh() {
        //停止刷新Loading
        if (isRefresh) {
            tx_list.onRefreshComplete();
        } else {
            tx_list.onLoadMoreComplete();
        }
    }


}
