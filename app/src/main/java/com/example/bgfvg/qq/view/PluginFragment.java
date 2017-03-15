package com.example.bgfvg.qq.view;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bgfvg.qq.MainActivity;
import com.example.bgfvg.qq.R;
import com.example.bgfvg.qq.presenter.PluginPresenter;
import com.example.bgfvg.qq.presenter.impl.PluginPresenterImpl;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PluginFragment extends BaseFragment implements PluginView {


    @BindView(R.id.loginout)
    Button mLoginout;
    private PluginPresenter mPluginPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plugin, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mLoginout.setText("退("+EMClient.getInstance().getCurrentUser()+")出");
        //对象的手动注入
        mPluginPresenter = new PluginPresenterImpl(this);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }

    @OnClick(R.id.loginout)
    public void onClick() {
        mProgressDialog.setMessage("正在退出...");
        mProgressDialog.show();
        mPluginPresenter.loginout();
    }

    @Override
    public void onLogout(String username, boolean success, String message) {
        mProgressDialog.hide();
        FragmentActivity activity = getActivity();
        MainActivity activity1 = (MainActivity) activity;
        if (success) {
            activity1.startActivity(LoginActivity.class, true);
        } else {
            activity1.showToast(message);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mProgressDialog.dismiss();
    }
}
