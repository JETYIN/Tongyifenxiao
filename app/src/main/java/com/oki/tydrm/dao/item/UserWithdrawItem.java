package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/8/3.
 */
public class UserWithdrawItem implements Serializable {

    @SerializedName("withdraw_id")
    public String withdraw_id;

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("com_money")
    public String com_money;

    @SerializedName("type")
    public String type;

    @SerializedName("com_time")
    public String com_time;

}