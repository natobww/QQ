package com.example.bgfvg.qq.presenter.impl;

import com.example.bgfvg.qq.listener.EmClientCallBackListener;
import com.example.bgfvg.qq.presenter.LoginPresenter;
import com.example.bgfvg.qq.utils.ThreadUtils;
import com.example.bgfvg.qq.view.LoginView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by BGFVG on 2017/3/10.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void login(final String username, final String password) {
        /**
         * 调用环信的登陆方法
         * 注意环信的登陆方法全都在子线程里
         * 要格外注意子线程里面的封信UI问题
         */
        EMClient.getInstance().login(username, password, new EmClientCallBackListener() {
            @Override
            public void onMainSuccess() {
                mLoginView.login(username, password, true, null);
            }

            @Override
            public void onMainError(int i, String s) {
                mLoginView.login(username, password, false, s);
            }
        });
    }
}
