package com.example.bgfvg.qq.presenter.impl;

import com.example.bgfvg.qq.db.DBUtils;
import com.example.bgfvg.qq.presenter.ContactPresenter;
import com.example.bgfvg.qq.utils.ThreadUtils;
import com.example.bgfvg.qq.view.ContactView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by BGFVG on 2017/3/15.
 */

public class ContactPresenterImpl implements ContactPresenter {
    public ContactPresenterImpl(ContactView contactView) {
        mContactView = contactView;
    }

    private ContactView mContactView;
    private List<String> mContactList = new ArrayList<>();

    @Override
    public void initContacts() {
        /**
         * 首先是读取本地的缓存联系人数据
         * 没有开辟子线程网络获取
         * 更新本地缓存联系人数据
         */
        final String currentUser = EMClient.getInstance().getCurrentUser();
        final List<String> contacts = DBUtils.getContacts(currentUser);
        mContactList.clear();
        mContactList.addAll(contacts);
        mContactView.onInitContacts(mContactList);

        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    //allContactsFromServer排序
                    Collections.sort(allContactsFromServer, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                    //更新本地的缓存
                    DBUtils.updateContacts(currentUser, allContactsFromServer);
                    //更新UI界面
                    mContactList.clear();
                    mContactList.addAll(allContactsFromServer);
                    //通知更新UI
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.updateContacts(true, null);
                        }
                    });

                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.updateContacts(false,e.getMessage());
                        }
                    });
                }
            }
        });


    }
}
