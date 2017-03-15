package com.example.bgfvg.qq.presenter.impl;

import com.example.bgfvg.qq.model.User;
import com.example.bgfvg.qq.presenter.RegistPresenter;
import com.example.bgfvg.qq.utils.StringUtils;
import com.example.bgfvg.qq.utils.ThreadUtils;
import com.example.bgfvg.qq.view.RegistView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by BGFVG on 2017/3/10.
 */

public class RegistPresenterImpl implements RegistPresenter {


    private RegistView mRegistView;

    public RegistPresenterImpl(RegistView registView) {
        mRegistView = registView;
    }

    @Override
    public void regist(final String username, final String password) {

        //开始注册
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                //回调在主线程
                if (e == null) {
                    //success next huanxin
                    ThreadUtils.runOnSubThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().createAccount(username, password);
                                //huanxin success
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRegistView.onRegist(username, password, true, null);
                                    }
                                });
                            } catch (final HyphenateException e1) {
                                e1.printStackTrace();
                                //huanxin fail
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //环信注册失败 删除bmob的数据
                                        user.delete();
                                        mRegistView.onRegist(username, password, false, e1.toString());
                                    }
                                });
                            }
                        }
                    });

                } else {
                    ///fail
                    mRegistView.onRegist(username, password, false, e.getMessage());
                }
            }
        });
    }
}
