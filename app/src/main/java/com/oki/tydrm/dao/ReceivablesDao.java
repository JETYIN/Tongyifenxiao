package com.oki.tydrm.dao;

import com.google.gson.annotations.SerializedName;
import com.oki.tydrm.base.BaseDao;

/**
 * Created by Monica on 2015/8/3.
 */
public class ReceivablesDao extends BaseDao {
    @SerializedName("data")
    public String data;

    @SerializedName("total_results")
    public int total_results;
}