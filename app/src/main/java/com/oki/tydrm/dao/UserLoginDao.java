package com.oki.tydrm.dao;

import com.google.gson.annotations.SerializedName;
import com.oki.tydrm.base.BaseDao;
import com.oki.tydrm.dao.item.LowerItem;
import com.oki.tydrm.dao.item.UserInfoItem;

/**
 * Created by Monica on 2015/8/3.
 */
public class UserLoginDao extends BaseDao {
    @SerializedName("data")
    public UserInfoItem data;
}