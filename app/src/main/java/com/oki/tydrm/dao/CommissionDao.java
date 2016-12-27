package com.oki.tydrm.dao;

import com.google.gson.annotations.SerializedName;
import com.oki.tydrm.base.BaseDao;
import com.oki.tydrm.dao.item.CommissionItem;

import java.util.List;

/**
 * Created by Monica on 2015/8/3.
 */
public class CommissionDao extends BaseDao {
    @SerializedName("data")
    public List<CommissionItem> data;
}