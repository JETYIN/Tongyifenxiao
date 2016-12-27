package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Monica on 2015/8/3.
 */
public class SelectPaymentItem implements Serializable {

    @SerializedName("app_name")
    public String app_name;

    @SerializedName("app_staus")
    public String app_staus;

    @SerializedName("app_version")
    public String app_version;

    @SerializedName("app_id")
    public String app_id;

    @SerializedName("app_rpc_id")
    public String app_rpc_id;

    @SerializedName("app_class")
    public String app_class;

    @SerializedName("app_des")
    public String app_des;

    @SerializedName("app_pay_type")
    public String app_pay_type;

    @SerializedName("app_display_name")
    public String app_display_name;

    @SerializedName("app_pay_brief")
    public String app_pay_brief;

    @SerializedName("app_order_by")
    public String app_order_by;

    @SerializedName("app_info")
    public String app_info;

    @SerializedName("support_cur")
    public String support_cur;

    @SerializedName("pay_fee")
    public String pay_fee;

//    @SerializedName("supportCurrency")
//    public SupportCurrencyItem supportCurrency;

    @SerializedName("is_cod")
    public String is_cod;

    @SerializedName("app_platform")
    public String app_platform;

    @SerializedName("icon_src")
    public String icon_src;

    @SerializedName("check")
    public boolean check = false;

}