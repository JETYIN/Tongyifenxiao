package com.oki.tydrm.dao.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Monica on 2015/8/3.
 */
public class DistributionInfoItem implements Serializable {

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("parent_member_id")
    public String parent_member_id;

    @SerializedName("payment_id")
    public String payment_id;

    @SerializedName("member_name")
    public String member_name;

    @SerializedName("invite_member_id")
    public String invite_member_id;

    @SerializedName("area")
    public String area;

    @SerializedName("bank_name")
    public String bank_name;

    @SerializedName("bank_no")
    public String bank_no;

    @SerializedName("branch_bank_name")
    public String branch_bank_name;

    @SerializedName("card_holder")
    public String card_holder;

    @SerializedName("alipay_account")
    public String alipay_account;

    @SerializedName("alipay_name")
    public String alipay_name;

    @SerializedName("license_encoding")
    public String license_encoding;

    @SerializedName("license_image_url")
    public String license_image_url;

    @SerializedName("corporation_name")
    public String corporation_name;

    @SerializedName("corporation_no")
    public String corporation_no;

    @SerializedName("corporation_image_url")
    public String corporation_image_url;

    @SerializedName("corporation_image_back_url")
    public String corporation_image_back_url;

    @SerializedName("invitation_code2")
    public String invitation_code2;

    @SerializedName("invitation_code3")
    public String invitation_code3;

    @SerializedName("invitation_url2")
    public String invitation_url2;

    @SerializedName("invitation_url3")
    public String invitation_url3;

    @SerializedName("level_id")
    public String level_id;

    @SerializedName("state")
    public String state;

    @SerializedName("real_name")
    public String real_name;

}