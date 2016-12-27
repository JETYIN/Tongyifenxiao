package com.oki.tydrm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.ReceivablesDao;
import com.oki.tydrm.dao.item.SelectPaymentItem;
import com.oki.tydrm.dao.item.UserWithdrawItem;
import com.oki.tydrm.service.ServiceProvider;
import com.oki.tydrm.ui.TXmxActivity;

import java.util.List;
import java.util.TreeMap;

import cn.qmz.tools.utils.DrawableUtils;
import cn.qmz.tools.utils.ListUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Monica on 2015/8/4.
 */
public class SelectPayAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<SelectPaymentItem> list;
    ViewHolder _holder;
    Context context;
    String date;
    String sign;

    //====================== 自定义 Start ======================
    public SelectPayAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private boolean isEmpty(List<SelectPaymentItem> list) {
        return ListUtils.isEmpty(list);
    }

    public List<SelectPaymentItem> getList() {
        return list;
    }

    public void setList(List<SelectPaymentItem> lists) {
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
            convertView = inflater.inflate(R.layout.select_pay_item, null);
            _holder = new ViewHolder();
            //此处用此方法,_Holder里面的控件就不会为空指针
            ViewUtils.inject(_holder, convertView);

            convertView.setTag(_holder);
        } else {
            _holder = (ViewHolder) convertView.getTag();
        }

        //赋值
        SelectPaymentItem item = list.get(position);
        if (item != null) {
            if(!StringUtils.isEmpty(item.icon_src)){
                DrawableUtils.displayFromUrl(item.icon_src,_holder.confirm_order_pay_icon);
            }
            _holder.confirm_order_pay_name.setText(item.app_display_name);

            _holder.confirm_order_pay_checkbox.setChecked(item.check);

        }

        return convertView;
    }

//    class OkButtonListener implements View.OnClickListener {
//
//        private int position;
//
//        OkButtonListener(int position) {
//            this.position = position;
//        }
//
//        @Override
//        public void onClick(View v) {
//            int vid = v.getId();
//            if (vid == _holder.confirm_order_pay_checkbox.getId()) {
//
//            }
//        }
//    }

    static class ViewHolder {
        @ViewInject(R.id.confirm_order_pay_icon)
        ImageView confirm_order_pay_icon;

        @ViewInject(R.id.confirm_order_pay_name)
        TextView confirm_order_pay_name;

        @ViewInject(R.id.confirm_order_pay_checkbox)
        CheckBox confirm_order_pay_checkbox;

    }
}
