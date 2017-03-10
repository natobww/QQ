package com.example.bgfvg.qq.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
