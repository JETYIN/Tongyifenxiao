package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/8/3.
 */
public class WithdrawItem implements Serializable {

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("total_money")
    public String total_money;

}