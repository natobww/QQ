package com.example.bgfvg.qq.utils;

import com.example.bgfvg.qq.view.BaseFragment;
import com.example.bgfvg.qq.view.ContactFragment;
import com.example.bgfvg.qq.view.ConversationFragment;
import com.example.bgfvg.qq.view.PluginFragment;

/**
 * Created by BGFVG on 2017/3/14.
 */

public class FragmentFactory {
    private static ConversationFragment sConversationFragment;
    private static ContactFragment sContactFragment;
    private static PluginFragment sPluginFragment;

    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                if (sConversationFragment == null) {
                    sConversationFragment = new ConversationFragment();
                }
                baseFragment = sConversationFragment;
                break;
            case 1:
                if (sContactFragment == null) {
                    sContactFragment = new ContactFragment();
                }
                baseFragment = sContactFragment;
                break;
            case 2:
                if (sPluginFragment == null) {
                    sPluginFragment = new PluginFragment();
                }
                baseFragment = sPluginFragment;
                break;
            default:
                break;

        }
        return baseFragment;
    }
}
