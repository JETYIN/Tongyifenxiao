package com.oki.tydrm.dao;

import com.google.gson.annotations.SerializedName;
import com.oki.tydrm.base.BaseDao;
import com.oki.tydrm.dao.item.LowerUserItem;
import com.oki.tydrm.dao.item.UserWithdrawItem;

import java.util.List;

/**
 * Created by Monica on 2015/8/3.
 */
public class UserWithdrawDao extends BaseDao {
    @SerializedName("data")
    public List<UserWithdrawItem> data;
}