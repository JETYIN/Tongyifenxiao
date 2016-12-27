package com.oki.tydrm.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/7/30.
 */
public class BaseDao implements Serializable {
    @SerializedName("rsp")
    public String rsp;
    @SerializedName("res")
    public String res;

    /**
     * 判断是否正确
     * @return
     */
    public boolean IsOK() {
        if("succ".equals(rsp)) {
            return true;
        }
        return false;
    }
}
