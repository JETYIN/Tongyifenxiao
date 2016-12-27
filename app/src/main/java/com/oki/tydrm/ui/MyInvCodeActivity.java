package com.oki.tydrm.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;
import com.oki.tydrm.common.AppConfig;
import com.oki.tydrm.dao.CommwithdrawDao;
import com.oki.tydrm.dao.InviteDao;
import com.oki.tydrm.service.ServiceProvider;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import java.util.TreeMap;

import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.utils.StringUtils;
import cn.qmz.tools.utils.ToastUtils;
import cn.qmz.tools.view.popup.SharePopupWindow;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyInvCodeActivity extends ActivityBase implements View.OnClickListener{

    @ViewInject(R.id.yq_code_22)
    TextView yq_code_22;

    @ViewInject(R.id.yq_url_2)
    TextView yq_url_2;

    @ViewInject(R.id.yq_code_33)
    TextView yq_code_33;

    @ViewInject(R.id.yq_url_3)
    TextView yq_url_3;

    @ViewInject(R.id.share2)
    RelativeLayout share2;

    @ViewInject(R.id.share3)
    RelativeLayout share3;

    @ViewInject(R.id.yq2)
    RelativeLayout yq2;

    @ViewInject(R.id.yq3)
    RelativeLayout yq3;

    int level = 2;

    String shareUrl = "";

    String url2 = "";

    String url3 = "";

    String shareContent = "加入同蚁分销!月入百万!";

    String shareTitle = "快快加入吧";

    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("我的邀请");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_inv_activity);

    }

    @Override
    public void initListener() {
        share2.setOnClickListener(this);
        share3.setOnClickListener(this);
        yq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StringUtils.isEmpty(url2)){
                    ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(url2.trim());
                    ToastUtils.show(getThis(), "二级邀请链接已复制到剪贴板~", Toast.LENGTH_SHORT);
                }
            }
        });
        yq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StringUtils.isEmpty(url3)){
                    ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(url3.trim());
                    ToastUtils.show(getThis(), "三级邀请链接已复制到剪贴板~", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public void initDisplay() {

        date = AppConfig.getInstance().getDate();

        TreeMap<String ,Object> map = new TreeMap<String ,Object>();
        map.put("method",AppConfig.methodHead+"invite");
        map.put("member_id",AppConfig.getInstance().dao.data.member_id);
        map.put("date",date);
        map.put("direct","true");
        sign = AppConfig.getInstance().getSign(map);

        ServiceProvider.getInstance().invite(
                AppConfig.methodHead + "invite",
                AppConfig.getInstance().dao.data.member_id,
                date,
                sign,
                true,
                mInvite);

        initSocialSDK();

    }

    Callback<InviteDao> mInvite = new Callback<InviteDao>() {
        @Override
        public void success(InviteDao dao, Response response) {
            if (dao.IsOK()) {

                if("succ".equals(dao.rsp)){
                    yq_code_22.setText(dao.data.invitation_code2);
                    yq_url_2.setText(dao.data.invitation_url2);
                    url2 = dao.data.invitation_url2;
                    yq_code_33.setText(dao.data.invitation_code3);
                    yq_url_3.setText(dao.data.invitation_url3);
                    url3 = dao.data.invitation_url3;
                }

            } else {
                hideAllDialog();
                showError();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            hideAllDialog();
            showError();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share2:
                level = 2;
                showSharePopupWindow();
                shareUrl = url2;
                break;

            case R.id.share3:
                level = 3;
                showSharePopupWindow();
                shareUrl = url3;
                break;

        }
    }

    private SharePopupWindow sharePopup;

    /**
     * 弹出分享框
     */
    public void showSharePopupWindow() {
        if (sharePopup == null) {
            sharePopup = new SharePopupWindow(this, shareItemClick);
        }
        sharePopup.showOnView(R.id.main_layout);
    }

    View.OnClickListener shareItemClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.invite_qq: {

                    sharePopup.dismiss();

                    QQShareContent qqShareContent = new QQShareContent();
                    //设置分享文字
                    qqShareContent.setShareContent(shareContent);
                    //设置分享title
                    qqShareContent.setTitle(shareTitle);
                    //设置分享图片
                    qqShareContent.setShareImage(new UMImage(getThis(), R.mipmap.ic_launcher));
                    //设置点击分享内容的跳转链接
                    qqShareContent.setTargetUrl(shareUrl);

                    mController.setShareMedia(qqShareContent);

                    mController.postShare(getThis(), SHARE_MEDIA.QQ,
                            new SocializeListeners.SnsPostListener() {
                                @Override
                                public void onStart() {
                                    //Toast.makeText(getThis(), "开始分享.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                    if (eCode == 200) {
                                        Toast.makeText(getThis(), "分享成功", Toast.LENGTH_SHORT).show();

                                    } else {
                                        String eMsg = "";
                                        if (eCode == -101) {
                                            eMsg = "没有授权";
                                        }
//                                        Toast.makeText(getThis(), "分享失败[" + eCode + "] " +
//                                                eMsg,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
                case R.id.invite_qzone: {
                    sharePopup.dismiss();

                    QZoneShareContent qzone = new QZoneShareContent();
                    //设置分享文字
                    qzone.setShareContent(shareContent);
                    //设置点击消息的跳转URL
                    qzone.setTargetUrl(shareUrl);
                    //设置分享内容的标题
                    qzone.setTitle(shareTitle);
                    //设置分享图片
                    qzone.setShareImage(new UMImage(getThis(), R.mipmap.ic_launcher));
                    mController.setShareMedia(qzone);

                    mController.postShare(getThis(),SHARE_MEDIA.QZONE,
                            new SocializeListeners.SnsPostListener() {

                                @Override
                                public void onStart() {
                                    //Toast.makeText(getThis(), "开始分享.", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
                                    if (eCode == 200) {
                                        Toast.makeText(getThis(), "分享成功", Toast.LENGTH_SHORT).show();

                                    } else {
                                        String eMsg = "";
                                        if (eCode == -101){
                                            eMsg = "没有授权";
                                        }
//                            Toast.makeText(getThis(), "分享失败[" + eCode + "] " +
//                                    eMsg,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;

                case R.id.invite_weixin: {

                    sharePopup.dismiss();

                    WeiXinShareContent wxShareContent = new WeiXinShareContent();
                    //设置分享文字
                    wxShareContent.setShareContent(shareContent);
                    //设置分享title
                    wxShareContent.setTitle(shareTitle);
                    //设置分享图片
                    wxShareContent.setShareImage(new UMImage(getThis(), R.mipmap.ic_launcher));
                    //设置点击分享内容的跳转链接
                    wxShareContent.setTargetUrl(shareUrl);

                    mController.setShareMedia(wxShareContent);

                    mController.postShare(getThis(), SHARE_MEDIA.WEIXIN,
                            new SocializeListeners.SnsPostListener() {
                                @Override
                                public void onStart() {
                                    //Toast.makeText(getThis(), "开始分享.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                    if (eCode == 200) {
                                        Toast.makeText(getThis(), "分享成功", Toast.LENGTH_SHORT).show();

                                    } else {
                                        String eMsg = "";
                                        if (eCode == -101) {
                                            eMsg = "没有授权";
                                        }
//                                        Toast.makeText(getThis(), "分享失败[" + eCode + "] " +
//                                                eMsg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;

                case R.id.invite_weixin_clrcle: {

                    sharePopup.dismiss();

                    CircleShareContent circleShareContent = new CircleShareContent();
                    //设置分享文字
                    circleShareContent.setShareContent(shareContent);
                    //设置分享title
                    circleShareContent.setTitle(shareTitle);
                    //设置分享图片
                    circleShareContent.setShareImage(new UMImage(getThis(), R.mipmap.ic_launcher));
                    //设置点击分享内容的跳转链接
                    circleShareContent.setTargetUrl(shareUrl);

                    mController.setShareMedia(circleShareContent);

                    mController.postShare(getThis(), SHARE_MEDIA.WEIXIN_CIRCLE,
                            new SocializeListeners.SnsPostListener() {
                                @Override
                                public void onStart() {
                                    //Toast.makeText(getThis(), "开始分享.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                    if (eCode == 200) {
                                        Toast.makeText(getThis(), "分享成功", Toast.LENGTH_SHORT).show();

                                    } else {
                                        String eMsg = "";
                                        if (eCode == -101) {
                                            eMsg = "没有授权";
                                        }
//                                        Toast.makeText(getThis(), "分享失败[" + eCode + "] " +
//                                                eMsg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;

                case R.id.invite_weibo: {

                    sharePopup.dismiss();

                    SinaShareContent wbShareContent = new SinaShareContent();
                    //设置分享文字
                    wbShareContent.setShareContent(shareContent);
                    //设置分享title
                    wbShareContent.setTitle(shareTitle);
                    //设置分享图片
                    wbShareContent.setShareImage(new UMImage(getThis(), R.mipmap.ic_launcher));
                    //设置点击分享内容的跳转链接
                    wbShareContent.setTargetUrl(shareUrl);

                    mController.setShareMedia(wbShareContent);

                    mController.postShare(getThis(), SHARE_MEDIA.SINA,
                            new SocializeListeners.SnsPostListener() {
                                @Override
                                public void onStart() {
                                    //Toast.makeText(getThis(), "开始分享.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                    if (eCode == 200) {
                                        Toast.makeText(getThis(), "分享成功", Toast.LENGTH_SHORT).show();

                                    } else {
                                        String eMsg = "";
                                        if (eCode == -101) {
                                            eMsg = "没有授权";
                                        }
//                                        Toast.makeText(getThis(), "分享失败[" + eCode + "] " +
//                                                eMsg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
                case R.id.cancle: {
                    sharePopup.dismiss();
                }
                break;
            }
        }
    };

    private void initSocialSDK(){

        //分享给QQ空间
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1105097240",
                "IzUQqlJv74baAbk1");
        qZoneSsoHandler.addToSocialSDK();

        //分享给QQ好友
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1105097240",
                "IzUQqlJv74baAbk1");
        qqSsoHandler.addToSocialSDK();

        //支持微信
        UMWXHandler wxHandler = new UMWXHandler(this,"wx886bde5275db6ce5","f6f6ce13c0150964c39ab5177fef2282");
        wxHandler.addToSocialSDK();

        //支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this,"wx886bde5275db6ce5","f6f6ce13c0150964c39ab5177fef2282");
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
