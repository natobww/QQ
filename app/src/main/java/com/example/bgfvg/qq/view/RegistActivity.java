package com.example.bgfvg.qq.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bgfvg.qq.R;
import com.example.bgfvg.qq.presenter.RegistPresenter;
import com.example.bgfvg.qq.presenter.impl.RegistPresenterImpl;
import com.example.bgfvg.qq.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity implements TextView.OnEditorActionListener, RegistView {

    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.til_username)
    TextInputLayout mTilUsername;
    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;
    @BindView(R.id.til_pwd)
    TextInputLayout mTilPwd;
    @BindView(R.id.regist)
    Button mRegist;

    private RegistPresenter mRegistPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 沉浸式状态栏
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        mRegistPresenter = new RegistPresenterImpl(this);
        //给键盘绑定监听事件
        mEdtPwd.setOnEditorActionListener(this);
    }

    @OnClick(R.id.regist)
    public void onClick() {
        regist();
    }

    private void regist() {
        String username = mEdtUsername.getText().toString().trim();
        String password = mEdtPwd.getText().toString().trim();
        if (!StringUtils.checkUserName(username)) {
            mTilUsername.setErrorEnabled(true);
            mTilUsername.setError("用户名不合法");
            mTilUsername.requestFocus(View.FOCUS_RIGHT);
            return;
        } else {
            mTilUsername.setErrorEnabled(false);
        }

        if (!StringUtils.checkPassWord(password)) {
            mTilPwd.setErrorEnabled(true);
            mTilPwd.setError("密码不合法");
            mEdtPwd.requestFocus(View.FOCUS_RIGHT);
        } else {
            mTilPwd.setErrorEnabled(false);
        }
        showDialog("正在注册中...");
        mRegistPresenter.regist(username, password);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.edt_pwd && actionId == EditorInfo.IME_ACTION_DONE) {
            regist();
            return true;
        }
        return false;
    }

    @Override
    public void onRegist(String username, String password, boolean isSuccess, String message) {
        hideDialog();
        if (isSuccess) {
            /**
             * 注册成功,保存信息到本地,跳转到登陆界面
             */
            saveUser(username, password);
            startActivity(LoginActivity.class, true);
        } else {
            showToast("注册失败: " + message);
        }

    }
}
