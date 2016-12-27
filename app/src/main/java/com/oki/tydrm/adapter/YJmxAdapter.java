package com.oki.tydrm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.dao.item.CommissionItem;
import com.oki.tydrm.dao.item.UserWithdrawItem;

import java.util.List;

import cn.qmz.tools.utils.ListUtils;
import cn.qmz.tools.utils.StringUtils;

/**
 * Created by Monica on 2015/8/4.
 */
public class YJmxAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CommissionItem> list;
    ViewHolder _holder;
    Context context;

    //====================== 自定义 Start ======================
    public YJmxAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private boolean isEmpty(List<CommissionItem> list) {
        return ListUtils.isEmpty(list);
    }

    public List<CommissionItem> getList() {
        return list;
    }

    public void setList(List<CommissionItem> lists) {
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
            convertView = inflater.inflate(R.layout.ty_yj_mx_item, null);
            _holder = new ViewHolder();
            //此处用此方法,_Holder里面的控件就不会为空指针
            ViewUtils.inject(_holder, convertView);

            convertView.setTag(_holder);
        } else {
            _holder = (ViewHolder) convertView.getTag();
        }

        //赋值
        CommissionItem item = list.get(position);
        if (item != null) {
            _holder.pid.setText(item.order_id);
            _holder.date.setText(StringUtils.TimeStampDate(item.settlement_time, "yyyy-MM-dd"));
            _holder.statue.setText(item.proportion);
            _holder.opare.setText(item.com_money);
            _holder.yue.setText(item.current_balance);

        }

        return convertView;
    }

//    private String eToC(String en){
//        String chineseName = "审核中";
//        if("approval".equals(en)){
//            chineseName = "审核中";
//        }else if ("refuse".equals(en)){
//            chineseName = "审核拒绝";
//        }else if ("waittransfers".equals(en)){
//            chineseName = "待入账";
//        }else if ("transfers".equals(en)){
//            chineseName = "已入账";
//        }else if ("carryout".equals(en)){
//            chineseName = "已收款";
//        }
//        return chineseName;
//    }

//    class OkButtonListener implements View.OnClickListener {
//
//        private int position;
//
//        OkButtonListener(int pos) {
//            position = pos;
//        }
//
//        @Override
//        public void onClick(View v) {
//            int vid = v.getId();
//            if (vid == _holder.order_ok.getId()) {
//
//                Intent it_order_ok = new Intent(context, OrderEnsureActivity.class);
//                it_order_ok.putExtra("appointmentOrderId", list.get(position).appointmentOrderId);
//                context.startActivity(it_order_ok);
//
//            }
//        }
//    }



    static class ViewHolder {
        @ViewInject(R.id.pid)
        TextView pid;

        @ViewInject(R.id.date)
        TextView date;

        @ViewInject(R.id.statue)
        TextView statue;

        @ViewInject(R.id.opare)
        TextView opare;

        @ViewInject(R.id.yue)
        TextView yue;

    }
}
