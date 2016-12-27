package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/8/3.
 */
public class LowerUserItem implements Serializable {

    @SerializedName("level_name")
    public String level_name;

    @SerializedName("uname")
    public String uname;

    @SerializedName("mobile")
    public String mobile;

}