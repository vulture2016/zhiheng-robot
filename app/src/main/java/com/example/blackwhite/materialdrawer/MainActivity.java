package com.example.blackwhite.materialdrawer;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.blackwhite.materialdrawer.fragment.ChatFragment;
import com.example.blackwhite.materialdrawer.fragment.RecordFragment;
import com.example.blackwhite.materialdrawer.fragment.SettingFragment;
import com.example.blackwhite.materialdrawer.fragment.SysInfoFragment;
import com.example.blackwhite.materialdrawer.fragment.TempletFragment;
import com.example.blackwhite.materialdrawer.util.ToastUtil;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    private List<Fragment> fragments;
    private Fragment chatFragment;
    private Fragment settingFragment;
    private Fragment subscribeFragment;
    private Fragment templetFragmet;
    private Fragment RecordFragment;
    private Drawer mainDrawer;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        fragments = new ArrayList<>();
//        initFragments();
        toolbar.setBackgroundResource(R.color.md_toolbar_bg);
        toolbar.setTitle("聊天");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(4.0f);
        }
        initSlidingDrawer(savedInstanceState);


    }


    private void initSlidingDrawer(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            chatFragment = getSupportFragmentManager().findFragmentByTag(ChatFragment.class.getName());
            settingFragment = getSupportFragmentManager().findFragmentByTag(SettingFragment.class.getName());
            subscribeFragment = getSupportFragmentManager().findFragmentByTag(SysInfoFragment.class.getName());
            templetFragmet = getSupportFragmentManager().findFragmentByTag(TempletFragment.class.getName());
            RecordFragment = getSupportFragmentManager().findFragmentByTag(RecordFragment.class.getName());
            // 解决重叠问题
            getSupportFragmentManager().beginTransaction()
                    .show(chatFragment)
                    .hide(settingFragment)
                    .hide(subscribeFragment)
                    .hide(templetFragmet)
                    .hide(RecordFragment)
                    .commit();
        } else {  // 正常时
            chatFragment = new ChatFragment();
            settingFragment = new SettingFragment();
            subscribeFragment = new SysInfoFragment();
            templetFragmet = new TempletFragment();
            RecordFragment = new RecordFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_content, chatFragment, chatFragment.getClass().getName())
                    .add(R.id.fragment_content, settingFragment, settingFragment.getClass().getName())
                    .add(R.id.fragment_content, subscribeFragment, subscribeFragment.getClass().getName())
                    .add(R.id.fragment_content, templetFragmet, templetFragmet.getClass().getName())
                    .add(R.id.fragment_content, RecordFragment, RecordFragment.getClass().getName())
                    .hide(settingFragment)
                    .hide(subscribeFragment)
                    .hide(templetFragmet)
                    .hide(RecordFragment)
                    .commit();
        }
        // Handle Toolbar
        //与toolbar关联起来
        mainDrawer = new DrawerBuilder()
                .withActivity(this)
                //跟drawerlayout用法相同，用这个drawer替换覆盖掉原来drawerlayout的位置
//                .withRootView(R.id.drawer_container)
                .withHeader(R.layout.view_drawer_header)
                .withHeaderDivider(true)//设置是否有分割线
                .withTranslucentStatusBar(false)//设置是否启用沉浸式状态栏
                .withSavedInstance(savedInstanceState)
                .withToolbar(toolbar)//与toolbar关联起来
                .withActionBarDrawerToggleAnimated(true)//启用toolbar的ActionBarDrawerToggle动画
                .withDisplayBelowStatusBar(false)
                .withDrawerLayout(R.layout.material_drawer)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("轻松聊天").withIcon(R.drawable.icon_chat).withIdentifier(1),
                        new PrimaryDrawerItem().withName("系统消息").withIcon(R.drawable.icon_subscribe).withIdentifier(2),
                        new PrimaryDrawerItem().withName("查看模板").withIcon(R.drawable.icon_templet).withIdentifier(3),
                        new PrimaryDrawerItem().withName("聊天记录").withIcon(R.drawable.icon_record).withIdentifier(4),
                        new PrimaryDrawerItem().withName("功能设置").withIcon(R.drawable.icon_setting).withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            switch ((int) drawerItem.getIdentifier()) {
                                case 1:
                                    toolbar.setTitle("轻松聊天");
                                    getSupportFragmentManager().beginTransaction()
                                            .show(chatFragment).hide(settingFragment)
                                            .hide(subscribeFragment).hide(templetFragmet)
                                            .hide(RecordFragment).commit();
                                    break;
                                case 2:
                                    toolbar.setTitle("系统消息");
                                    getSupportFragmentManager().beginTransaction()
                                            .show(subscribeFragment).hide(settingFragment)
                                            .hide(chatFragment).hide(templetFragmet)
                                            .hide(RecordFragment).commit();
                                    break;
                                case 3:
                                    toolbar.setTitle("查看模板");
                                    getSupportFragmentManager().beginTransaction()
                                            .show(templetFragmet).hide(chatFragment)
                                            .hide(settingFragment).hide(RecordFragment)
                                            .hide(subscribeFragment).commit();
                                    break;
                                case 4:
                                    toolbar.setTitle("聊天记录");
                                    getSupportFragmentManager().beginTransaction()
                                            .show(RecordFragment).hide(chatFragment)
                                            .hide(settingFragment).hide(templetFragmet)
                                            .hide(subscribeFragment).commit();
                                    break;
                                case 5:
                                    toolbar.setTitle("功能设置");
                                    getSupportFragmentManager().beginTransaction()
                                            .show(settingFragment).hide(chatFragment)
                                            .hide(subscribeFragment).hide(templetFragmet)
                                            .hide(RecordFragment).commit();
                                    break;
                                default:
                                    break;
                            }
                        }
                        return false;
                    }
                }).build();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mainDrawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private int mBackKeyPressedTimes = 0;//记录回退键点击次数

    /**
     * 双击退出程序
     */
    @Override
    public void onBackPressed() {
        if (mBackKeyPressedTimes == 0) {
            ToastUtil.infoToast(getApplicationContext(), "再按一次退出程序");
            mBackKeyPressedTimes = 1;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mBackKeyPressedTimes = 0;
                    }
                }
            }.start();
            return;
        } else {
            exitApp();
        }
        super.onBackPressed();
    }

}
