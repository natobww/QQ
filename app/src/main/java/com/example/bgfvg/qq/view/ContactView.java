package com.example.bgfvg.qq.view;

import java.util.List;

/**
 * Created by BGFVG on 2017/3/15.
 */

public interface ContactView {
    void onInitContacts(List<String> contactList);

    void updateContacts(boolean success, String message);
}
