package com.example.bgfvg.qq.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bgfvg.qq.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.til_username)
    TextInputLayout mTilUsername;
    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;
    @BindView(R.id.til_pwd)
    TextInputLayout mTilPwd;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.tv_newuser)
    TextView mTvNewuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 沉浸式状态栏
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        echo();

    }

    private void echo() {
        /**
         * 数据的回显
         */
        mEdtUsername.setText(getUserName());
        mEdtPwd.setText(getPassWord());
    }

    /**
     * 再次启动activity的时候接受新的intent对象
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        echo();
    }

    @OnClick({R.id.login, R.id.tv_newuser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                break;
            case R.id.tv_newuser:
                startActivity(RegistActivity.class, false);
                break;
        }
    }
}
