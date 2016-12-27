package com.oki.tydrm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.oki.tydrm.dao.item.LowerUserItem;

import java.util.List;

import cn.qmz.tools.utils.ListUtils;
import cn.shopex.scstore.R;

/**
 * Created by Monica on 2015/8/4.
 */
public class LowerUserAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<LowerUserItem> list;
    ViewHolder _holder;
    Context context;

    //====================== 自定义 Start ======================
    public LowerUserAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private boolean isEmpty(List<LowerUserItem> list) {
        return ListUtils.isEmpty(list);
    }

    public List<LowerUserItem> getList() {
        return list;
    }

    public void setList(List<LowerUserItem> lists) {
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
            convertView = inflater.inflate(R.layout.ty_user_xx_item, null);
            _holder = new ViewHolder();
            //此处用此方法,_Holder里面的控件就不会为空指针
            ViewUtils.inject(_holder, convertView);

            convertView.setTag(_holder);
        } else {
            _holder = (ViewHolder) convertView.getTag();
        }

        //赋值
        LowerUserItem item = list.get(position);
        if (item != null) {
            _holder.name.setText(item.uname);
            _holder.level.setText(item.level_name);
        }

        return convertView;
    }

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
        @ViewInject(R.id.name)
        TextView name;

        @ViewInject(R.id.level)
        TextView level;

    }
}
