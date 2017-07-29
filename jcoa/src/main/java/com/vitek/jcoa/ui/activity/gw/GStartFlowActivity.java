package com.vitek.jcoa.ui.activity.gw;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.StringUtils;
import com.misdk.util.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.JCOAApplication;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.StartFlowEntity;
import com.vitek.jcoa.ui.view.MyCalendarView;


/**
 * 新建公文
 * Created by javakam on 2017-05-26 17:20:08.
 */
public class GStartFlowActivity extends GBaseAppCompatActivity<StartFlowEntity> {

    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;
    private SharePreUtil spUtil;

    private TextView gwTvNumber//公文编号
            , tvGwSource//公文来源
            , tvGwHandleType//事件类别
            ;
    private EditText edtGwDNumber//公文文号
            , edtGwSNumber;//公文序号
    private EditText edtGWDSource//来文机关
            , edtGWTitle      //公文主题
            , edtZhengJian     //正件
            , edtFuJian        //附件
            , edtFuYinJian     //复印件
            , edtGwBeiZhu     //备注
            ;
    private MaterialSpinner spGwSource  //公文来源Spinner
            , spHandleType;//事件类别 Spinner
    //时间选择
    private MyCalendarView myCalendarView;//日历控件
    private String commitDate = "";//用于提交的日期格式,默认今日  TimeUtil.getStringDateyyyy_MM_dd()

    private Button btCommitGW;

    @Override
    protected void initVariables() {
        spUtil = new SharePreUtil(this, Constant.SPF_USER_INFO);


    }

    @Override
    protected void initPtr() {
//        super.initPtr();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gstart_flow;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {

        titleBack = (FrameLayout) findViewById(R.id.titleBack);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("新建公文");
//        tvTitle.requestFocusFromTouch();
//        tvTitle.requestFocus();
        tvTitle.setFocusableInTouchMode(true);
        tvTitleNowday = (TextView) findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText("请选择创建时间");//TimeUtil.getTitleDate()
        tvTitleNowday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendarView.showCalendarViewAsDropDown(v);
            }
        });
        myCalendarView = new MyCalendarView(this);
        myCalendarView.initCalendarView(2017, 0, 1);
        myCalendarView.materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (myCalendarView.mPopupWindow != null && myCalendarView.mPopupWindow.isShowing()) {
                    myCalendarView.mPopupWindow.dismiss();
                    myCalendarView.mPopupWindow = null;
                }
                commitDate = MyCalendarView.formatDateToyyyy_MM_dd(date.getDate());
                tvTitleNowday.setText(commitDate);
            }
        });
        gwTvNumber = (TextView) findViewById(R.id.gwTvNumber);
        edtGwDNumber = (EditText) findViewById(R.id.edtGwDNumber);
        edtGwSNumber = (EditText) findViewById(R.id.edtGwSNumber);
        edtGWDSource = (EditText) findViewById(R.id.edtGWDSource);
        edtGWTitle = (EditText) findViewById(R.id.edtGWTitle);
        edtZhengJian = (EditText) findViewById(R.id.edtZhengJian);
        edtFuJian = (EditText) findViewById(R.id.edtFuJian);
        edtFuYinJian = (EditText) findViewById(R.id.edtFuYinJian);
        edtGwBeiZhu = (EditText) findViewById(R.id.edtGwBeiZhu);
//        recyclerImg = (RecyclerView) findViewById(R.id.recyclerImg);
        btCommitGW = (Button) findViewById(R.id.btCommitGW);
        btCommitGW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judgeDatas()) {
                    getDialog();
                    showDialog();
                    net();
                }
            }
        });
        spGwSource = (MaterialSpinner) findViewById(R.id.spGwSource);
        spHandleType = (MaterialSpinner) findViewById(R.id.spHandleType);
        tvGwSource = (TextView) findViewById(R.id.tvGwSource);
        tvGwHandleType = (TextView) findViewById(R.id.tvGwHandleType);
        initSpinner(spGwSource, tvGwSource, "公文来源", Constant.STR_GSOURCE);
        initSpinner(spHandleType, tvGwHandleType, "事件类别", Constant.STR_GHANGDLE_TYPE);

    }

    /**
     * 公文来源、事件类别 Spinner
     */
    private void initSpinner(final MaterialSpinner spinner, final TextView textView
            , final String title, String... items) {
//        spGwSource.setBackgroundColor(getResources().getColor(R.color.white));
        spinner.setItems(items);
        spinner.setText(title);
        spinner.getPopupWindow().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.bgColor)));
        spinner.setPadding(Utils.dp2px(this, 15f)
                , Utils.dp2px(this, 10f), Utils.dp2px(this, 15f), Utils.dp2px(this, 10f));
        textView.setText(items[0]);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                spGwSource.performClick();//无效
                spinner.expand();
            }
        });

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                view.setText(title);
                //view.getText() 和 item.toString() 是一样的！
                textView.setText(item.toString());
            }
        });
    }

    private boolean judgeDatas() {
        if (StringUtils.isBlank(commitDate)) {
            showShortToast("请选择创建时间");
            return false;
        }
        if (StringUtils.isBlank(edtGwDNumber.getText().toString())
                || StringUtils.isBlank(edtGwSNumber.getText().toString())
                || StringUtils.isBlank(edtGWDSource.getText().toString())
                || StringUtils.isBlank(edtGWTitle.getText().toString())) {
            return false;
        }
        return true;
    }

    @Override
    protected void getNetData() {
        //新建公文/jc_oa/flow/startFlow (GET))   StartFlowEntity
/*
@param        number        编号
     * @param d_number      文号
     * @param s_number      序号
     * @param source        公文来源（上级交办、其他部门移交）
     * @param d_source      来文机关
     * @param handle_type   公文主题类别（预售违法类、违法施工类） 就是事件类别
     * @param this_d        正件（份数）
     * @param enclosure     附件（份数）
     * @param ecpy_d        复印件（份数）
     * @param source_time   收到时间
     * @param title         题目                                 就是公文主题
     * @param remarks       备注
     * @param create_people 创建人
     * @param the_file      1，不归档，0，归档（创建时传1）
 */
        String z = edtZhengJian.getText().toString(), f = edtFuJian.getText().toString(), fy = edtFuYinJian.getText().toString();
        if (StringUtils.isBlank(z)) {
            z = "0";
        }
        if (StringUtils.isBlank(f)) {
            f = "0";
        }
        if (StringUtils.isBlank(fy)) {
            fy = "0";
        }
        //     OK
        CloudPlatformUtil.getInstance().getStartFlow(
                JCOAApi.getStartFlow(Constant.GW_NUMBER
                        , edtGwDNumber.getText().toString().trim()  //文号  没有设计
                        , edtGwSNumber.getText().toString().trim() //序号
                        , StringUtils.utf8Encode(tvGwSource.getText().toString())
                        , StringUtils.utf8Encode(edtGWDSource.getText().toString().trim())
                        , StringUtils.utf8Encode(tvGwHandleType.getText().toString())
                        , z, f, fy
                        , commitDate// MyCalendar Right : "2017-5-25"
                        , StringUtils.utf8Encode(edtGWTitle.getText().toString().trim())
                        , StringUtils.utf8Encode(edtGwBeiZhu.getText().toString())  //  备注
                        , StringUtils.utf8Encode(spUtil.getValue(Constant.SP_USER_NAME)) + ""
                        , 1 + "")
                , this);
    }


    @Override
    protected void onNetError(VolleyError error) {
//       VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
        showShortToast(VolleyErrorManager.getErrorMsg(error));
        dismissDialog();
    }

    @Override
    protected void onNetSuccess(StartFlowEntity entity) {
        if (entity == null) return;
        showShortToast(entity.getDefaultMessage());
        edtGwDNumber.setText("");
        edtGwSNumber.setText("");
        edtGWDSource.setText("");
        edtGWTitle.setText("");
        edtGwBeiZhu.setText("");
        JCOAApplication.getInstance().notifyDataChange(JCOAApplication.UPDATE_NOHANDLE, null, null);
        dismissDialog();
    }
}
