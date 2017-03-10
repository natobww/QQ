package com.example.bgfvg.qq.presenter;

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
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.login(username, password, true, null);
                    }
                });
            }

            @Override
            public void onError(int i, final String s) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.login(username, password, false, s);
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
