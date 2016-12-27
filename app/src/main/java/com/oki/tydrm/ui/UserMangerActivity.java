package com.oki.tydrm.ui;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.adapter.LowerAdapter;
import com.oki.tydrm.adapter.LowerUserAdapter;
import com.oki.tydrm.adapter.TXmxAdapter;
import com.oki.tydrm.adapter.YJmxAdapter;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.LowerDao;
import com.oki.tydrm.dao.LowerUserDao;
import com.oki.tydrm.dao.UserWithdrawDao;
import com.oki.tydrm.dao.item.CommissionItem;
import com.oki.tydrm.dao.item.LowerItem;
import com.oki.tydrm.dao.item.LowerUserItem;
import com.oki.tydrm.dao.item.UserWithdrawItem;
import com.oki.tydrm.service.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.view.listview.PullRefreshListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserMangerActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.fxs_list)
    PullRefreshListView fxs_list;

    private List<LowerItem> mList = null;

    private LowerAdapter mAdapter = null;

    @ViewInject(R.id.yh_list)
    PullRefreshListView yh_list;

    private List<LowerUserItem> uList = null;

    private LowerUserAdapter uAdapter = null;

    @ViewInject(R.id.my_fx)
    TextView my_fx;

    @ViewInject(R.id.my_yh)
    TextView my_yh;

    @ViewInject(R.id.left_b)
    View left_b;

    @ViewInject(R.id.right_b)
    View right_b;

    @ViewInject(R.id.fx_people)
    RelativeLayout fx_people;

    @ViewInject(R.id.my_people)
    RelativeLayout my_people;

    int i = 1;

    boolean first = true;

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("用户管理");
    }

    @Override
    public void refresh() {
        date = AppConfig.getInstance().getDate();

        if(1==i) {
            startRefresh();
            TreeMap<String, Object> map = new TreeMap<String, Object>();
            map.put("method", AppConfig.methodHead + "lower");
            map.put("member_id", AppConfig.getInstance().dao.data.member_id);
            map.put("nPage", nPage);
            map.put("date", date);
            map.put("direct", "true");
            sign = AppConfig.getInstance().getSign(map);

            ServiceProvider.getInstance().lower(
                    AppConfig.methodHead + "lower",
                    AppConfig.getInstance().dao.data.member_id,
                    nPage,
                    date,
                    sign,
                    true,
                    mGetLower);
        }else {
            startRefresh2();
            TreeMap<String, Object> map = new TreeMap<String, Object>();
            map.put("method", AppConfig.methodHead + "loweruser");
            map.put("member_id", AppConfig.getInstance().dao.data.member_id);
            map.put("nPage", nPage2);
            map.put("date", date);
            map.put("direct", "true");
            sign = AppConfig.getInstance().getSign(map);

            ServiceProvider.getInstance().loweruser(
                    AppConfig.methodHead + "loweruser",
                    AppConfig.getInstance().dao.data.member_id,
                    nPage2,
                    date,
                    sign,
                    true,
                    mGetLowerUser);
        }
    }

    Callback<LowerDao> mGetLower = new Callback<LowerDao>() {
        @Override
        public void success(LowerDao dao, Response response) {
            if (dao.IsOK()) {
                List<LowerItem> data = dao.data;
                if (data != null && data.size() > 0) {

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

    Callback<LowerUserDao> mGetLowerUser = new Callback<LowerUserDao>() {
        @Override
        public void success(LowerUserDao dao, Response response) {
            if (dao.IsOK()) {
                List<LowerUserItem> data = dao.data;
                if (data != null && data.size() > 0) {

                    if (isRefresh2)
                        uList.clear();

                    uList.addAll(data);
                    uAdapter.setList(uList);
                    uAdapter.notifyDataSetChanged();

                }
            } else {
                hideAllDialog();
                showError();
            }
            stopRefresh2();
        }

        @Override
        public void failure(RetrofitError error) {
            hideAllDialog();
            showError();
        }
    };

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_user_manger_mx);

    }

    @Override
    public void initListener() {
        fxs_list.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //首次进行刷新数据
                isRefresh = true;
                nPage = 1;
                refresh();
            }
        });

        fxs_list.setOnLoadMoreListener(new PullRefreshListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                nPage++;
                refresh();
            }
        });

        yh_list.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //首次进行刷新数据
                isRefresh2 = true;
                nPage2 = 1;
                refresh();
            }
        });

        yh_list.setOnLoadMoreListener(new PullRefreshListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh2 = false;
                nPage2++;
                refresh();
            }
        });

        my_fx.setOnClickListener(this);
        my_yh.setOnClickListener(this);
    }

    @Override
    public void initDisplay() {
        mList = new ArrayList<LowerItem>();
        mAdapter = new LowerAdapter(getThis());
        fxs_list.setAdapter(mAdapter);
        if (!StringUtils.isEmpty(AppConfig.getInstance().dao.data.member_id)) {
            isRefresh = true;
            refresh();
        }
        uList = new ArrayList<LowerUserItem>();
        uAdapter = new LowerUserAdapter(getThis());
        yh_list.setAdapter(uAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.my_fx:
                i = 1;
                my_fx.setTextColor(Color.RED);
                my_yh.setTextColor(Color.BLACK);
                left_b.setVisibility(View.VISIBLE);
                right_b.setVisibility(View.INVISIBLE);
                fx_people.setVisibility(View.VISIBLE);
                my_people.setVisibility(View.GONE);
                break;

            case R.id.my_yh:
                i = 2;
                my_fx.setTextColor(Color.BLACK);
                my_yh.setTextColor(Color.RED);
                left_b.setVisibility(View.INVISIBLE);
                right_b.setVisibility(View.VISIBLE);
                fx_people.setVisibility(View.GONE);
                my_people.setVisibility(View.VISIBLE);

                if(first){
                    if (!StringUtils.isEmpty(AppConfig.getInstance().dao.data.member_id)) {
                        isRefresh2 = true;
                        refresh();
                    }
                    first = false;
                }

                break;

        }
    }

    /**
     * 开启刷新
     */
    protected void startRefresh() {
        //开启刷新Loading
        if (isRefresh) {
            fxs_list.onRefreshStart();
        } else {
            fxs_list.onLoadMoreStart();
        }
    }

    /**
     * 停止刷新
     */
    protected void stopRefresh() {
        //停止刷新Loading
        if (isRefresh) {
            fxs_list.onRefreshComplete();
        } else {
            fxs_list.onLoadMoreComplete();
        }
    }

    /**
     * 开启刷新
     */
    protected void startRefresh2() {
        //开启刷新Loading
        if (isRefresh2) {
            yh_list.onRefreshStart();
        } else {
            yh_list.onLoadMoreStart();
        }
    }

    /**
     * 停止刷新
     */
    protected void stopRefresh2() {
        //停止刷新Loading
        if (isRefresh2) {
            yh_list.onRefreshComplete();
        } else {
            yh_list.onLoadMoreComplete();
        }
    }

}
