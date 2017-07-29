package com.vitek.jcoa.util;

import android.content.Context;

import com.misdk.cube.loadmore.LoadMoreGridViewContainer;
import com.misdk.cube.loadmore.LoadMoreHandler;
import com.misdk.cube.loadmore.LoadMoreListViewContainer;
import com.vitek.jcoa.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by javakam on 2016/7/20.
 */
public class UltraUtil {
    /**
     * init pull to refresh
     *
     * @param context
     * @param mPtrFrame
     * @param ptrHandler
     */
    public static void initPtr(Context context, PtrClassicFrameLayout mPtrFrame, PtrHandler ptrHandler) {
        if (mPtrFrame == null) {
//            Log.e("123", "Please ensure your layout contains 'R.layout.com_ultral_listivew' ");
            return;
        }
        mPtrFrame.setBackgroundColor(context.getResources().getColor(R.color.bgColor));
        mPtrFrame.setLastUpdateTimeRelateObject(context);
        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setPtrHandler(ptrHandler);

        // the following are default settings

//        mPtrFrame.setResistance(1.7f);
//        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
//        mPtrFrame.setDurationToClose(200);
//        mPtrFrame.setDurationToCloseHeader(1000);

        // default is false
//        mPtrFrame.setPullToRefresh(false);

        // default is true
//        mPtrFrame.setKeepHeaderWhenRefresh(true);
    }

    /**
     * ListView
     *
     * @param loadMoreContainer
     * @param loadMoreHandler
     */
    public static void initLm(LoadMoreListViewContainer loadMoreContainer, LoadMoreHandler loadMoreHandler) {
        if (loadMoreContainer == null) {
//            System.out.println("Please ensure your layout contains 'R.layout.com_ultral_listivew' ");
//            Log.e("123", "Please ensure your layout contains 'R.layout.com_ultral_listivew' ");
//            new RuntimeException("Please ensure your layout contains 'R.layout.com_ultral_listivew' ");
            return;
        }
        loadMoreContainer.useCCKFooter();
        // binding view and data
        loadMoreContainer.setLoadMoreHandler(loadMoreHandler);
    }

    /**
     * GridView
     *
     * @param loadMoreContainer
     * @param loadMoreHandler
     */
    public static void initLmGv(LoadMoreGridViewContainer loadMoreContainer, LoadMoreHandler loadMoreHandler) {
        loadMoreContainer.useCCKFooter();
        // binding view and data
        loadMoreContainer.setLoadMoreHandler(loadMoreHandler);
    }
}
