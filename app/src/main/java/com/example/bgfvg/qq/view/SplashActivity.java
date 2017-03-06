package com.example.bgfvg.qq.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.bgfvg.qq.MainActivity;
import com.example.bgfvg.qq.R;
import com.example.bgfvg.qq.adapter.AnimatorListenerAdapter;
import com.example.bgfvg.qq.presenter.SplashPresenter;
import com.example.bgfvg.qq.presenter.SplashPresenterImpl;

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
        if (mSplashPresenter != null)
            mSplashPresenter.checkLogined();
    }

    @Override
    public void onCheckedLogin(boolean isLogin) {
        if (isLogin) {
            //已登录
            startActivity(MainActivity.class, true);
        } else {
            //未登录
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mIvSplash, "alpha", 0, 1).setDuration(DURATION);
            alpha.start();
            alpha.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    startActivity(LoginActivity.class, true);
                }
            });
        }
    }
}
