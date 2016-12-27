package com.oki.tydrm.service;

import com.google.gson.JsonObject;
import com.oki.tydrm.dao.CommissionDao;
import com.oki.tydrm.dao.CommwithdrawDao;
import com.oki.tydrm.dao.DistributionInfoDao;
import com.oki.tydrm.dao.InviteDao;
import com.oki.tydrm.dao.LowerDao;
import com.oki.tydrm.dao.LowerUserDao;
import com.oki.tydrm.dao.ReceivablesDao;
import com.oki.tydrm.dao.SelectPaymentDao;
import com.oki.tydrm.dao.UpgradeDao;
import com.oki.tydrm.dao.UserLoginDao;
import com.oki.tydrm.dao.UserWithdrawDao;
import com.oki.tydrm.dao.WithdrawDao;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Monica on 2015/7/28.
 */
public interface RetrofitService {
    //0、分销商管理--登录 ok
    @FormUrlEncoded
    @POST("/api?")
    void post_login(
            @Field("method") String method,
            @Field("uname") String uname,
            @Field("password") String password,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<UserLoginDao> result);

    //1、分销商管理--我的佣金 ok
    //mobileapi.member.commission
    @FormUrlEncoded
    @POST("/api?")
    void commission(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("nPage") int nPage,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<CommissionDao> result);

    //2、分销商管理--我的推荐 ok
    @FormUrlEncoded
    @POST("/api?")
    void invite(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<InviteDao> result);

    //3、分销商管理--我的下线（分销商） ok
    @FormUrlEncoded
    @POST("/api?")
    void lower(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("nPage") int nPage,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<LowerDao> result);

    //4、分销商管理--我的下线（会员） ok
    @FormUrlEncoded
    @POST("/api?")
    void loweruser(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("nPage") int nPage,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<LowerUserDao> result);

    //5、分销商管理--申请佣金提现（查询佣金总额）ok
    //mobileapi.member.withdraw
    @FormUrlEncoded
    @POST("/api?")
    void withdraw(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<WithdrawDao> result);


    //6、分销商管理--申请佣金提现（去提现） ok
    @FormUrlEncoded
    @POST("/api?")
    void commwithdraw(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("withdraw_money") int withdraw_money,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<CommwithdrawDao> result);

    //7、分销商管理--我的提现（分销商确认收款）
    @FormUrlEncoded
    @POST("/api?")
    void receivables(
            @Field("method") String method,
            @Field("withdraw_id") String withdraw_id,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<ReceivablesDao> result);

    //8、分销商管理--我的提现（查询提现记录） ok
    @FormUrlEncoded
    @POST("/api?")
    void userwithdraw(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("nPage") int nPage,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<UserWithdrawDao> result);

    //9、分销商管理--我要升级（校验升级）
    @FormUrlEncoded
    @POST("/api?")
    void upgrade(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<JsonObject> result);

    //10、分销商信息
    @FormUrlEncoded
    @POST("/api?")
    void distributionInfo(
            @Field("method") String method,
            @Field("member_id") String member_id,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<DistributionInfoDao> result);

    //11、支付方式
    @FormUrlEncoded
    @POST("/api?")
    void select_payment(
            @Field("method") String method,
            @Field("date") String date,
            @Field("sign") String sign,
            @Field("direct") boolean direct,
            Callback<SelectPaymentDao> result);

}
