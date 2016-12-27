package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/8/3.
 */
public class UpgradeItem implements Serializable {

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("datamoney")
    public int datamoney;

    @SerializedName("level_id")
    public String level_id;

    @SerializedName("upgrade_level_id")
    public int upgrade_level_id;

}