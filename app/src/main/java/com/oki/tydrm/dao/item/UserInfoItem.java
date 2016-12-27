package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/8/3.
 */
public class UserInfoItem implements Serializable {

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("parent_member_id")
    public String parent_member_id;

    @SerializedName("member_lv_id")
    public String member_lv_id;

    @SerializedName("crm_member_id")
    public String crm_member_id;

    @SerializedName("name")
    public String name;

    @SerializedName("point")
    public String point;

    @SerializedName("mobile")
    public String mobile;

}