package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;
import com.oki.tydrm.base.BaseDao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Monica on 2015/8/3.
 */
public class CommissionItem implements Serializable {
    @SerializedName("order_id")
    public String order_id;

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("com_money")
    public String com_money;

    @SerializedName("total_profit")
    public String total_profit;

    @SerializedName("proportion")
    public String proportion;

    @SerializedName("settlement_time")
    public String settlement_time;

    @SerializedName("current_balance")
    public String current_balance;
}