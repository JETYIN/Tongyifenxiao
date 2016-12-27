package com.oki.tydrm.base.impl;

import android.app.Activity;

/**
 * Created by Monica on 2015/7/28.
 */
public interface IActivityBase {
    /**
     * 初始化布局
     */
    void initLayout();

    /**
     * 初始化事件
     */
    void initListener();

    /**
     * 初始化显示信息
     */
    void initDisplay();

    /**
     * 获取本身的Activity
     * @return
     */
    Activity getThis();

    /**
     * 设置标题栏事件
     */
    void setOnHeaderClick();

    /**
     * 刷新数据
     */
    void refresh();
}
