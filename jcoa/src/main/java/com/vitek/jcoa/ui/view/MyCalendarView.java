package com.vitek.jcoa.ui.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.misdk.util.TimeUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.vitek.jcoa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/27 0027.
 */
public class MyCalendarView {
    private Context context;
    public MaterialCalendarView materialCalendarView;
    public PopupWindow mPopupWindow;
    private View inflaterView;
    private Calendar calendar;
//    private OnChooseDateClick onChooseDateClick;

    public MyCalendarView(Context context) {
        this.context = context;
//        this.onChooseDateClick = onChooseDateClick;
        inflaterView = LayoutInflater.from(context).inflate(R.layout.com_calendar, null);
        materialCalendarView = (MaterialCalendarView) inflaterView.findViewById(R.id.materialCalendarView);
        calendar = Calendar.getInstance();
        initCalendarView(2017, 0, 1);
    }

    public void initCalendarView(Integer... date) {

        materialCalendarView.state().edit()
                // 设置你的日历的最小的月份  月份为你设置的最小月份的下个月 比如 你设置最小为1月 其实你只能滑到2月
                .setMinimumDate(CalendarDay.from(date[0], date[1], date[2]))//2017, 0, 1
                // 同最小 设置最大  此处设置到本月
                .setMaximumDate(CalendarDay.from(calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)))
                .commit();
    }

    /**
     * 选择日期
     */
    public void showCalendarViewAsDropDown(View v) {
        materialCalendarView.clearSelection();
//        materialCalendarView.destroyDrawingCache();
//        materialCalendarView.setSelectedDate(lastDate);
//        if (mPopupWindow != null) return;
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(inflaterView, WindowManager.LayoutParams.MATCH_PARENT
                    , WindowManager.LayoutParams.MATCH_PARENT);
        /*
        , WindowManager.LayoutParams.MATCH_PARENT
                , WindowManager.LayoutParams.MATCH_PARENT
         */
            mPopupWindow.setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0xffffff); // 实例化一个ColorDrawable颜色为半透明
            mPopupWindow.setBackgroundDrawable(dw);
//                mPopupWindow.setAnimationStyle();//动画
        }
        mPopupWindow.showAsDropDown(v, 0, 0);
    }

    public void showCalendarViewAtLocation(View v) {

        materialCalendarView.clearSelection();
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(inflaterView, WindowManager.LayoutParams.MATCH_PARENT
                    , WindowManager.LayoutParams.MATCH_PARENT);
        /*
        , WindowManager.LayoutParams.MATCH_PARENT
                , WindowManager.LayoutParams.MATCH_PARENT
         */
            mPopupWindow.setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0xffffff); // 实例化一个ColorDrawable颜色为半透明
            mPopupWindow.setBackgroundDrawable(dw);
//                mPopupWindow.setAnimationStyle();//动画
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display dm = windowManager.getDefaultDisplay();
        Point point = new Point();
        dm.getSize(point);
        mPopupWindow.showAtLocation(v, Gravity.CENTER, point.x / 3 * 2, point.y / 2);
    }

    /**
     * @param startTime 开始时间  yyyy.MM.dd
     * @param endTime   结束时间  yyyy.MM.dd
     * @return true  startTime < endTime
     */
    public boolean verifyBigDate(String startTime, String endTime) {
        long longTime1 = TimeUtil.dateToLong6(startTime);
        long longTime2 = TimeUtil.dateToLong6(endTime);
        if (longTime1 > longTime2) {
            return false;
        }
        return true;
    }

    public boolean verifyBigDateDDD(String startTime, String endTime) {
        long longTime1 = TimeUtil.dateToLong6(startTime);
        long longTime2 = TimeUtil.dateToLong6(endTime);
        if (longTime1 > longTime2) {
            return false;
        }
        return true;
    }

    /**
     * 将MaterialCalendarView上点击返回的Date格式时间  转换为 yyyy-MM-dd
     *
     * @return 返回格式 yyyy-MM-dd
     */
    public static String formatDateToyyyy_MM_dd(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 将MaterialCalendarView上点击返回的Date格式时间  转换为 yyyy.MM.dd
     *
     * @return 返回格式 yyyy.MM.dd
     */
    public static String formatDateToyyyyMMdd2(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        return formatter.format(date);
    }

    /**
     * 2017年5月31日
     *
     * @param date 2017-05-31
     * @return
     */
//    public static String formatDateToyyyyNMMYddR(Date date) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月dd日");
//        return formatter.format(date);
//    }
//    public interface OnChooseDateClick {
//        /**
//         * @param date
//         * @param tag  标识具体的按钮点击   1 StartTime  click   2 EndTime
//         */
//        void onClickDate(String date, int tag);
//    }

}
