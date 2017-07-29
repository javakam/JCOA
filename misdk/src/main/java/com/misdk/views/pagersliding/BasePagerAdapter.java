package com.misdk.views.pagersliding;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * 用于配合PagerSlidingTabStrip使用的适配器
 * <p>Created by javakam on 2016/6/27.
 */
public abstract class BasePagerAdapter extends FragmentPagerAdapter {
    /**
     * 传入tabs标题
     */
    private String[] TITLES;

    public BasePagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.TITLES = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    /*
       在ViewPager中嵌套使用Fragment，他会预加载第二页的数据，
       但是滑动到第三页的Fragment，前面的Fragment的数据又被清空了
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
