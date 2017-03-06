package com.example.bgfvg.qq.presenter;

import com.example.bgfvg.qq.view.SplashView;
import com.hyphenate.chat.EMClient;

/**
 * Created by BGFVG on 2017/3/7.
 */

public class SplashPresenterImpl implements SplashPresenter {
    private SplashView mSplashView;

    public SplashPresenterImpl(SplashView splashView) {
        mSplashView = splashView;
    }

    @Override
    public void checkLogined() {
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()) {
            //说明已登录
            mSplashView.onCheckedLogin(true);
        } else {
            //说明没登陆
            mSplashView.onCheckedLogin(false);
        }
    }
}
