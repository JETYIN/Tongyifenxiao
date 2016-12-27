package com.oki.tydrm.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import cn.shopex.scstore.R;
import com.oki.tydrm.base.ActivityBase;

public class AboutActivity extends ActivityBase{

    @ViewInject(R.id.about_version_info)
    TextView about_version_info;

    @Override
    public void setOnHeaderClick() {
        mHeader.initTitleBar("关于我们");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void initLayout() {
        setContentView(R.layout.ty_about_us_activity);

    }

    @Override
    public void initListener() {
    }

    @Override
    public void initDisplay() {
        // 应用名与版本号
        PackageManager mPm = getPackageManager();
        try {
            PackageInfo pi = mPm.getPackageInfo(getPackageName(), 0);
            about_version_info
                    .setText(getString(R.string.about_version, pi.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
