package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/8/3.
 */
public class InviteItem implements Serializable {

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("invitation_code2")
    public String invitation_code2;

    @SerializedName("invitation_code3")
    public String invitation_code3;

    @SerializedName("invitation_url2")
    public String invitation_url2;

    @SerializedName("invitation_url3")
    public String invitation_url3;
}