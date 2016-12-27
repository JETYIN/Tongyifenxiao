package com.oki.tydrm.dao;

import com.google.gson.annotations.SerializedName;
import com.oki.tydrm.base.BaseDao;
import com.oki.tydrm.dao.item.LowerItem;
import com.oki.tydrm.dao.item.UpgradeItem;

/**
 * Created by Monica on 2015/8/3.
 */
public class UpgradeDao extends BaseDao {
    @SerializedName("data")
    public UpgradeItem data;
}