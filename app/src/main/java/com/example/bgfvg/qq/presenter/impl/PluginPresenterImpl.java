package com.example.bgfvg.qq.presenter.impl;

import com.example.bgfvg.qq.listener.EmClientCallBackListener;
import com.example.bgfvg.qq.presenter.PluginPresenter;
import com.example.bgfvg.qq.view.PluginView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by BGFVG on 2017/3/14.
 */

public class PluginPresenterImpl implements PluginPresenter {
    private PluginView mPluginView;

    public PluginPresenterImpl(PluginView pluginView) {
        mPluginView = pluginView;
    }

    @Override
    public void loginout() {
        //环信的登出
        EMClient.getInstance().logout(true, new EmClientCallBackListener() {
            @Override
            public void onMainSuccess() {
                mPluginView.onLogout(EMClient.getInstance().getCurrentUser(), true, null);
            }

            @Override
            public void onMainError(int i, String s) {
                mPluginView.onLogout(EMClient.getInstance().getCurrentUser(), false, s);
            }
        });
    }
}
