package com.example.bgfvg.qq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.bgfvg.qq.utils.FragmentFactory;
import com.example.bgfvg.qq.utils.Logger;
import com.example.bgfvg.qq.view.BaseActivity;
import com.example.bgfvg.qq.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mTab;
    @BindView(R.id.tv_title)
    TextView mTitle;
    private int[] titleIds = {R.string.conversation, R.string.contact, R.string.plugin};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initBottomNavigation();
        initFirstFragment();
    }


    private void initToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initBottomNavigation() {
        BottomNavigationItem conversation_Item = new BottomNavigationItem(R.mipmap.conversation_selected_2, getString(R.string.conversation));
        BottomNavigationItem contact_Item = new BottomNavigationItem(R.mipmap.contact_selected_2, getString(R.string.contact));
        BottomNavigationItem plugin_Item = new BottomNavigationItem(R.mipmap.plugin_selected_2, getString(R.string.plugin));
        mTab.setActiveColor(R.color.btn_normal);
        mTab.setInActiveColor(R.color.inactive);
        mTab.addItem(conversation_Item)
                .addItem(contact_Item)
                .addItem(plugin_Item)
                .initialise();
        mTab.setTabSelectedListener(this);
        mTab.setFirstSelectedPosition(0);
    }

    private void initFirstFragment() {
        /**
         * 如果activity中有保存老的状态数据的话
         * 先把老的Fragment移除掉
         */
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < titleIds.length; i++) {
            Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(i + "");
            if (fragmentByTag != null) {
                transaction.remove(fragmentByTag);
            }
        }
        transaction.commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_conent, FragmentFactory.getFragment(0), "0").commit();
        mTitle.setText(R.string.conversation);
    }


    /**
     * 设置菜单项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuBuilder builder = (MenuBuilder) menu;
        builder.setOptionalIconsVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_friend:
                // TODO: 2017/3/13 跳转到添加好友Activity
                break;

            case R.id.menu_scan:
                showToast("扫一扫");
                break;

            case R.id.menu_about:
                showToast("关于我们");
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * 选项卡的监听器
     *
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        /**
         * 首先判断不有没有被添加到activity
         * 已添加直接显示
         * 没有添加 先添加再显示
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment fragment = FragmentFactory.getFragment(position);
        if (!fragment.isAdded()) {
            transaction.add(R.id.fl_conent, fragment, "" + position);
        }
        transaction.show(fragment).commit();
        mTitle.setText(titleIds[position]);
    }

    @Override
    public void onTabUnselected(int position) {
        getSupportFragmentManager().beginTransaction().hide(FragmentFactory.getFragment(position)).commit();
    }

    @Override
    public void onTabReselected(int position) {

    }
}
