package com.oki.tydrm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.ReceivablesDao;
import com.oki.tydrm.dao.UserWithdrawDao;
import com.oki.tydrm.dao.WithdrawDao;
import com.oki.tydrm.dao.item.UserWithdrawItem;
import com.oki.tydrm.service.ServiceProvider;
import com.oki.tydrm.ui.TXmxActivity;
import com.oki.tydrm.ui.UserMainActivity;

import java.util.List;
import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.ListUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Monica on 2015/8/4.
 */
public class TXmxAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<UserWithdrawItem> list;
    ViewHolder _holder;
    Context context;
    String date;
    String sign;

    //====================== 自定义 Start ======================
    public TXmxAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private boolean isEmpty(List<UserWithdrawItem> list) {
        return ListUtils.isEmpty(list);
    }

    public List<UserWithdrawItem> getList() {
        return list;
    }

    public void setList(List<UserWithdrawItem> lists) {
        this.list = lists;
    }
    //====================== 自定义 End ======================

    @Override
    public int getCount() {
        return ListUtils.getSize(list);
    }

    @Override
    public Object getItem(int position) {
        return isEmpty(list) ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ty_tx_mx_item, null);
            _holder = new ViewHolder();
            //此处用此方法,_Holder里面的控件就不会为空指针
            ViewUtils.inject(_holder, convertView);

            convertView.setTag(_holder);
        } else {
            _holder = (ViewHolder) convertView.getTag();
        }

        //赋值
        UserWithdrawItem item = list.get(position);
        if (item != null) {
            _holder.time.setText(StringUtils.TimeStampDate(item.com_time,"yyyy-MM-dd"));
            _holder.statue.setText(eToC(item.type));
            _holder.opare.setText(item.com_money);
            if("transfers".equals(item.type)){
                _holder.btn_opt.setVisibility(View.VISIBLE);
            }else {
                _holder.btn_opt.setVisibility(View.GONE);
            }
            _holder.btn_opt.setOnClickListener(new OkButtonListener(item.withdraw_id));
        }

        return convertView;
    }

    private String eToC(String en){
        String chineseName = "审核中";
        if("approval".equals(en)){
            chineseName = "审核中";
        }else if ("refuse".equals(en)){
            chineseName = "审核拒绝";
        }else if ("waittransfers".equals(en)){
            chineseName = "待入账";
        }else if ("transfers".equals(en)){
            chineseName = "已入账";
        }else if ("carryout".equals(en)){
            chineseName = "已收款";
        }
        return chineseName;
    }

    class OkButtonListener implements View.OnClickListener {

        private String withdraw_id;

        OkButtonListener(String withdraw_id) {
            this.withdraw_id = withdraw_id;
        }

        @Override
        public void onClick(View v) {
            int vid = v.getId();
            if (vid == _holder.btn_opt.getId()) {

                date = AppConfig.getInstance().getDate();

                TreeMap<String ,Object> map = new TreeMap<String ,Object>();
                map.put("method",AppConfig.methodHead+"receivables");
                map.put("withdraw_id",withdraw_id);
                map.put("date",date);
                map.put("direct","true");
                sign = AppConfig.getInstance().getSign(map);

                ServiceProvider.getInstance().receivables(
                        AppConfig.methodHead + "receivables",
                        withdraw_id,
                        date,
                        sign,
                        true,
                        mReceivables);

            }
        }
    }

    Callback<ReceivablesDao> mReceivables = new Callback<ReceivablesDao>() {
        @Override
        public void success(ReceivablesDao dao, Response response) {
            if(dao.IsOK()) {
                ToastUtils.show(context, dao.data, Toast.LENGTH_SHORT);
                ((TXmxActivity)context).flashView();
            }else{
                ToastUtils.show(context, dao.res, Toast.LENGTH_SHORT);
            }

        }

        @Override
        public void failure(RetrofitError error) {
        }
    };

    static class ViewHolder {
        @ViewInject(R.id.time)
        TextView time;

        @ViewInject(R.id.statue)
        TextView statue;

        @ViewInject(R.id.opare)
        TextView opare;

        @ViewInject(R.id.btn_opt)
        Button btn_opt;

    }
}
