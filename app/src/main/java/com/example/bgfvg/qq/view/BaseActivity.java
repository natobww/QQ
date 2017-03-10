package com.example.bgfvg.qq.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.bgfvg.qq.utils.Constant;
import com.example.bgfvg.qq.utils.ToastUtils;

/**
 * Created by BGFVG on 2017/3/7.
 */

public class BaseActivity extends AppCompatActivity {


    private ProgressDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;

    public void startActivity(Class clazz, boolean isFinish) {
        startActivity(new Intent(this, clazz));
        if (isFinish) {
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
    }

    protected void saveUser(String username, String password) {
        mSharedPreferences.edit().putString(Constant.SP_KEY_USERNAME, username)
                .putString(Constant.SP_KEY_PASSWORD, password).commit();
    }

    public String getUserName(){
        return mSharedPreferences.getString(Constant.SP_KEY_USERNAME,"");
    }

    public String getPassWord(){
        return mSharedPreferences.getString(Constant.SP_KEY_PASSWORD,"");
    }

    protected void showDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    protected void hideDialog() {
        mProgressDialog.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
    }

    protected void showToast(String message) {
        ToastUtils.showToast(this, message);
    }
}
