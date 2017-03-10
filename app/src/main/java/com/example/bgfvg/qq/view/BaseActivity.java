package com.example.bgfvg.qq.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by BGFVG on 2017/3/7.
 */

public class BaseActivity extends AppCompatActivity {

    public void startActivity(Class clazz, boolean isFinish) {
        startActivity(new Intent(this, clazz));
        if (isFinish) {
            finish();
        }
    }
}
