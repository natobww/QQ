package com.example.bgfvg.qq.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bgfvg.qq.MainActivity;
import com.example.bgfvg.qq.R;
import com.example.bgfvg.qq.presenter.LoginPresenter;
import com.example.bgfvg.qq.presenter.impl.LoginPresenterImpl;
import com.example.bgfvg.qq.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements TextView.OnEditorActionListener, LoginView {

    private static final int REQUEST_ALL = 1000;
    private static final int REQUEST_SD = 1001;
    private static final int REQUEST_READPHONE = 1002;
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
    private LoginPresenter mLoginPresenter;

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
        mEdtPwd.setOnEditorActionListener(this);
        mLoginPresenter = new LoginPresenterImpl(this);
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
     * 前提是lauchMode是singleTask
     *
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
                login();
                break;
            case R.id.tv_newuser:
                startActivity(RegistActivity.class, false);
                break;
        }
    }

    private void login() {
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
        //检查动态权限的申请处理
        boolean SDPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED;
        boolean ReadPhonePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PermissionChecker.PERMISSION_GRANTED;
        boolean ReadSMSPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PermissionChecker.PERMISSION_GRANTED;
        if (SDPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ALL);
            return;
        }
        showDialog("正在玩命登录中...");
        /**
         * MVP实现登陆的逻辑
         */
        try {
            mLoginPresenter.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ALL) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                login();
            } else {
                showToast("您没有授予该应用访问SD权限");
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.edt_pwd && actionId == EditorInfo.IME_ACTION_DONE) {
            login();
            return true;
        }
        return false;
    }

    @Override
    public void login(String username, String password, boolean isSuccess, String message) {
        hideDialog();
        if (isSuccess) {
            //登陆成功保存SP
            saveUser(username, password);
            startActivity(MainActivity.class, true);
        } else {
            //登陆失败 Toast
            showToast("登陆失败: " + message);
        }
    }
}
