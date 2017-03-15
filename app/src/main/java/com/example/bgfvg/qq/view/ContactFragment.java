package com.example.bgfvg.qq.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bgfvg.qq.R;
import com.example.bgfvg.qq.adapter.ContactAdater;
import com.example.bgfvg.qq.presenter.ContactPresenter;
import com.example.bgfvg.qq.presenter.impl.ContactPresenterImpl;
import com.example.bgfvg.qq.view.widget.ContactLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment implements ContactView {

    public ContactLayout mContactLayout;
    private ContactPresenter mContactPresenter;
    public ContactAdater mContactAdater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContactLayout = (ContactLayout) view.findViewById(R.id.contact_layout);
        mContactPresenter = new ContactPresenterImpl(this);
        /**
         * 初始化联系人界面
         */
        mContactPresenter.initContacts();
    }

    @Override
    public void onInitContacts(List<String> contactList) {
        mContactAdater = new ContactAdater(contactList);
        mContactLayout.setAdapter(mContactAdater);
    }

    @Override
    public void updateContacts(boolean b, String message) {
        mContactAdater.notifyDataSetChanged();
    }
}
