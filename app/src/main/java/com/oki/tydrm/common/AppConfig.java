package com.oki.tydrm.common;

import com.oki.tydrm.dao.UserLoginDao;
import com.oki.tydrm.ui.UserMainActivity;
import com.oki.tydrm.utils.MD5andKL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by Monica on 2015/7/28.
 */
public class AppConfig {

    // 唯一配置实例
    public static AppConfig instance;

    private Date now;

    public static String methodHead = "mobileapi.member.";

    private SimpleDateFormat myFmt;

    public String totalMoney = "0";

    public UserLoginDao dao;

    public UserMainActivity main;

    public static boolean needFlash = false;

    /** 单例模式  */
    public static AppConfig getInstance() {
        if(instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getDate(){
        now=new Date();
        myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFmt.format(now);
    }

    public String getSign(TreeMap<String ,Object> map)
    {
//        map.put("method","mobileapi.member.commission");
//        map.put("nPage","1");
//        map.put("member_id","48");
//        map.put("method","mobileapi.member.commwithdraw");
//        map.put("member_id","48");
//        map.put("nPage","1");
//        map.put("withdraw_id","1");
//        map.put("withdraw_money","1");
//        map.put("date","2015-11-18 11:11:35");
//        map.put("direct","true");
        String sign = "";
        for(String key:map.keySet()){
            sign=sign+key+map.get(key);
        }
        sign = MD5andKL.MD5(sign);
        sign=sign.toUpperCase()+ "a1df65b565a7bb32bce4ef5518cf501e1b3c9f0bd97e1225ee23b7f40f1238cf";
        sign = MD5andKL.MD5(sign);
        sign=sign.toUpperCase();
        return sign;
    }

}
