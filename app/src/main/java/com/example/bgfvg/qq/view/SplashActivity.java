package com.example.bgfvg.qq.view;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.bgfvg.qq.MainActivity;
import com.example.bgfvg.qq.R;
import com.example.bgfvg.qq.presenter.SplashPresenter;
import com.example.bgfvg.qq.presenter.impl.SplashPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashView {

    private static final long DURATION = 2000;
    private SplashPresenter mSplashPresenter;

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mSplashPresenter = new SplashPresenterImpl(this);
        /**
         * 判断是否登陆
         */
        mSplashPresenter.checkLogined();
    }

    @Override
    public void onCheckedLogin(boolean isLogin) {
        if (isLogin) {
            //已登录
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(MainActivity.class, true);
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(LoginActivity.class, true);
                }
            }, 2000);
        }
    }
}
