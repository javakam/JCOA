package com.vitek.jcoa.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.views.loadding.CustomDialog;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.LoginUtil;
import com.vitek.jcoa.net.entity.WebChecklogEntity;
import com.vitek.jcoa.ui.fragment.HomeFragment;
import com.vitek.jcoa.ui.fragment.MyAccountFragment;
import com.vitek.jcoa.ui.fragment.PublishFragment;
import com.vitek.jcoa.ui.fragment.WorkStateFragment;
import com.vitek.jcoa.util.BottomNavigationViewHelper;
import com.vitek.jcoa.util.VLogUtil;

public class MainActivity extends GBaseAppCompatActivity<WebChecklogEntity> {

    private HomeFragment homeFragment;//home
    private PublishFragment publishFragment;//pubulish
    private WorkStateFragment workStateFragment;//workstate
    private MyAccountFragment myAccountFragment;//myaccount

    public static String currentTab = HomeFragment.ARG_HOMEFRAGMENT;
    private static final String INIT_SQLITE = "InitSQLiteDB";

    private LoginUtil loginUtil;
    private SharePreUtil sharePreUtil;

    private CustomDialog customDialog;

    @Override
    protected void initVariables() {
        setTranslucentStatus(R.color.titleColor);
        sharePreUtil = new SharePreUtil(this, Constant.SPF_USER_INFO);
//        getNetData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPtr() {
//        super.initPtr();
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        //默认选中首页 Tips:顺序！
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        net();
    }


    //        }
    @Override
    protected void getNetData() {
        //9.查询当前登录用户今天是否已发布日志/jc_oa/web_checklog (POST))
        //                                          WebChecklogEntity           OK 2017-05-21
        CloudPlatformUtil.getInstance().getWebChecklogToday(JCOAApi.getWebChecklogMap(), this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e("LoginError --- " + VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(WebChecklogEntity response) {
        showLongToast("今天的日志还没有上传,请尽快上传!");

//        今天的日志还没有上传,请尽快上传!

//        Dialog dialog=new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setTitle("今天的日志还没有上传,请尽快上传!");
//        dialog.show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    if (currentTab != HomeFragment.ARG_HOMEFRAGMENT && currentTab != null) {
                        Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTab);
                        if (lastFragment != null) {
                            transaction.hide(lastFragment);
                        }
                    }
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                        transaction.add(R.id.fragContent, homeFragment, HomeFragment.ARG_HOMEFRAGMENT);
                    } else {
                        transaction.show(homeFragment);
                    }
                    transaction.commit();
                    mFragmentManager.executePendingTransactions();
                    currentTab = HomeFragment.ARG_HOMEFRAGMENT;

                    return true;
                case R.id.navigation_publish:
                    if (currentTab != PublishFragment.ARG_PUBLISHFRAGMENT) {
                        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
                        if (currentTab != null) {
                            Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTab);
                            if (lastFragment != null) {
                                fTransaction.hide(lastFragment);
                            }
                        }
                        if (publishFragment == null) {
                            publishFragment = new PublishFragment();
                            fTransaction.add(R.id.fragContent, publishFragment, PublishFragment.ARG_PUBLISHFRAGMENT);
                        } else {
                            fTransaction.show(publishFragment);
                        }
                        fTransaction.commit();
                        mFragmentManager.executePendingTransactions();
                        currentTab = PublishFragment.ARG_PUBLISHFRAGMENT;
                    }
                    return true;
                case R.id.navigation_wkstate:
                    if (currentTab != WorkStateFragment.ARG_WORKSTATE) {
                        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
                        if (currentTab != null) {
                            Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTab);
                            if (lastFragment != null) {
                                fTransaction.hide(lastFragment);
                            }
                        }
                        if (workStateFragment == null) {
                            workStateFragment = new WorkStateFragment();
                            fTransaction.add(R.id.fragContent, workStateFragment, WorkStateFragment.ARG_WORKSTATE);
                        } else {
                            fTransaction.show(workStateFragment);
                        }
                        fTransaction.commit();
                        mFragmentManager.executePendingTransactions();
                        currentTab = WorkStateFragment.ARG_WORKSTATE;
                    }
                    return true;
                case R.id.navigation_myaccount:
                    if (currentTab != MyAccountFragment.ARG_MYACCOUNT) {
                        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
                        if (currentTab != null) {
                            Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTab);
                            if (lastFragment != null) {
                                fTransaction.hide(lastFragment);
                            }
                        }
                        if (myAccountFragment == null) {
                            myAccountFragment = new MyAccountFragment();
                            fTransaction.add(R.id.fragContent, myAccountFragment,MyAccountFragment.ARG_MYACCOUNT);
                        } else {
                            fTransaction.show(myAccountFragment);
                        }
                        fTransaction.commit();
                        mFragmentManager.executePendingTransactions();
                        currentTab = MyAccountFragment.ARG_MYACCOUNT;
                    }
                    return true;
            }
            return false;
        }

    };
    private long time = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time < 2000) {
            super.onBackPressed();
        } else {
            showShortToast("再次点击退出程序");
            time = System.currentTimeMillis();
        }
    }
}
/*
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */