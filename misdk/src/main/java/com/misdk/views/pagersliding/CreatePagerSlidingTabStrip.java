package com.misdk.views.pagersliding;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.misdk.R;


/**
 * 生成PagerSlidingTabStrip+ViewPager视图<p>
 * Created by javakam on 2016/6/27.
 */
public class CreatePagerSlidingTabStrip {
    private String[] titles;
    private Fragment[] fragments;

    private PagerSlidingTabStrip tabStrip;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private ViewPager.OnPageChangeListener listener;
    //add viewpager animation
    private static final float MIN_SCALE = 0.9f;//0.75f
    private static final float MIN_ALPHA = 0.9f;

    /**
     * 在Fragment中生成pagersliding+viewpager
     *
     * @param context
     * @param fragment
     * @param view
     * @param titles
     * @param fragments
     */
    public CreatePagerSlidingTabStrip(Context context, Fragment fragment, View view, String[] titles, Fragment[] fragments) {
        this.titles = titles;
        this.fragments = fragments;
        createIndicatorViewInFragment(context, fragment, view);
    }

    /**
     * 在FragmentActivity中生成pagersliding+viewpager
     * 注：AppCompatActivity 是 FragmentActivity的子类.
     *
     * @param activity
     * @param titles
     * @param fragments
     */
    public CreatePagerSlidingTabStrip(FragmentActivity activity, String[] titles, Fragment[] fragments) {
        this.titles = titles;
        this.fragments = fragments;
        createIndicatorViewInfragmentActivity(activity);
    }

    private void createIndicatorViewInFragment(Context context, Fragment fragment, View view) {
        tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.ps_tabs_com);
        tabStrip.setShouldExpand(true);
        pager = (ViewPager) view.findViewById(R.id.vp_with_ps_com);
        adapter = new MyPagerAdapter(fragment.getChildFragmentManager(), titles);//
        pager.setAdapter(adapter);
//        animViewPager(pager);
        //viewpager 间距   4
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabStrip.setViewPager(pager);
        if (listener != null)
            tabStrip.setOnPageChangeListener(listener);
    }

    private void createIndicatorViewInfragmentActivity(FragmentActivity fragmentActivity) {
        tabStrip = (PagerSlidingTabStrip) fragmentActivity.findViewById(R.id.ps_tabs_com);
        tabStrip.setShouldExpand(true);
        pager = (ViewPager) fragmentActivity.findViewById(R.id.vp_with_ps_com);
        adapter = new MyPagerAdapter(fragmentActivity.getSupportFragmentManager(), titles);
        pager.setAdapter(adapter);
//        animViewPager(pager);
        //viewpager 间距
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, fragmentActivity.getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabStrip.setViewPager(pager);
        if (listener != null)
            tabStrip.setOnPageChangeListener(listener);
    }

    private class MyPagerAdapter extends BasePagerAdapter {

        private MyPagerAdapter(FragmentManager fm, String[] titles) {
            super(fm, titles);
        }

        @Override
        public Fragment getItem(int position) {
            //if cause ArrayIndexOutOfBoundsException error
            // , ensure you put right fragments into the Fragment array.
            return fragments[position];
        }
    }

    private void animViewPager(ViewPager pager) {
        pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);
                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as
                    // well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationY(vertMargin - horzMargin / 2);
                    } else {
                        view.setTranslationY(-vertMargin + horzMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }
        });
    }

    public PagerSlidingTabStrip getTabStrip() {
        return tabStrip;
    }

    public void setTabStrip(PagerSlidingTabStrip tabStrip) {
        this.tabStrip = tabStrip;
    }

    public void setOnTabsPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.listener = listener;
    }

    public ViewPager getPager() {
        return pager;
    }

    public void setPager(ViewPager pager) {
        this.pager = pager;
    }
}
