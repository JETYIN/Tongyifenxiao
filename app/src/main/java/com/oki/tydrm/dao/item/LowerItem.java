package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/8/3.
 */
public class LowerItem implements Serializable {

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("level_id")
    public String level_id;

    @SerializedName("uname")
    public String uname;

    @SerializedName("mobile")
    public String mobile;

}