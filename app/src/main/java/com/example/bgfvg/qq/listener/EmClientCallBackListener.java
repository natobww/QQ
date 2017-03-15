package com.example.bgfvg.qq.listener;

import com.example.bgfvg.qq.utils.ThreadUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by BGFVG on 2017/3/15.
 */

public abstract class EmClientCallBackListener implements EMCallBack {
    public abstract void onMainSuccess();

    public abstract void onMainError(int i, String s);

    @Override
    public void onSuccess() {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onMainSuccess();
            }
        });
    }

    @Override
    public void onError(final int i, final String s) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onMainError(i, s);
            }
        });
    }

    @Override
    public void onProgress(int i, String s) {

    }
}
