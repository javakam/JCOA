package com.vitek.jcoa.ui.activity.gw;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.util.SharePreUtil;
import com.misdk.util.TimeUtil;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.entity.ShowTaskEntity;
import com.vitek.jcoa.ui.adapter.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.vitek.jcoa.Constant.FLAG_GW_YICHULI;
import static com.vitek.jcoa.Constant.FLAG_GW_YIGUIDANG;

/**
 * 公文归档 Detail
 */
public class GShowOfficeFilingActivity extends GBaseAppCompatActivity {
    private static String SESSION;
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;
    private SharePreUtil spUtil;
    public static final String INTENT_GSHOWOFFICE = "GShowOfficeFilingActivity";
    private ShowTaskEntity.ModelsBean showTaskBean;
    private int flag;

    private RecyclerView recyclerImg;
    public List<String> picPathInternet = new ArrayList<>();
    public List<String> picPath = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private TextView tvGwTitle//公文主题
            , gwTvCreater//创建者
            , tvGwLaiwen//来文机关
            , gwTvShenpi//现审批部门
            ;
    private EditText edtZhengJian     //正件
            , edtFuJian        //附件
            , edtFuYinJian     //复印件
            ;
    private NetGwPresenter presenter;

    @Override
    protected void initVariables() {
        showTaskBean = (ShowTaskEntity.ModelsBean) getIntent().getSerializableExtra(INTENT_GSHOWOFFICE);
        flag = getIntent().getFlags();//FLAG_GW_YICHULI
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gshow_office_filing;
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
        if (flag == FLAG_GW_YICHULI) {
            tvTitle.setText("已处理的公文");
        } else if (flag == FLAG_GW_YIGUIDANG) {
            tvTitle.setText("已归档的公文");
        } else {
            tvTitle.setText("公文归档");
        }

        tvTitle.requestFocusFromTouch();
        tvTitle.setFocusableInTouchMode(true);
        tvTitleNowday = (TextView) findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());

        tvGwTitle = (TextView) findViewById(R.id.tvGwTitle);
        gwTvCreater = (TextView) findViewById(R.id.gwTvCreater);
        tvGwLaiwen = (TextView) findViewById(R.id.tvGwLaiwen);
        gwTvShenpi = (TextView) findViewById(R.id.gwTvShenpi);
        edtZhengJian = (EditText) findViewById(R.id.edtZhengJian);
        edtFuJian = (EditText) findViewById(R.id.edtFuJian);
        edtFuYinJian = (EditText) findViewById(R.id.edtFuYinJian);
        edtZhengJian.setText(showTaskBean.getThis_d() + "");
        edtFuJian.setText(showTaskBean.getEnclosure() + "");
        edtFuYinJian.setText(showTaskBean.getEcpy_d() + "");
        edtZhengJian.setEnabled(false);
        edtFuJian.setEnabled(false);
        edtFuYinJian.setEnabled(false);
        recyclerImg = (RecyclerView) findViewById(R.id.recyclerImg);
        recyclerImg.setLayoutManager(new GridLayoutManager(this, 3));

        tvGwTitle.setText(showTaskBean.getTitle());
        gwTvCreater.setText(showTaskBean.getCreate_people());
        tvGwLaiwen.setText(showTaskBean.getD_source());
        gwTvShenpi.setText(showTaskBean.getSource());
        presenter = new NetGwPresenter(this, getDialog()
                , showTaskBean, spUtil);
        presenter.findCFiles();
    }

    @Override
    protected void getNetData() {

    }

    @Override
    protected void onNetError(VolleyError error) {

    }

    @Override
    protected void onNetSuccess(Object response) {

    }

    //==========================================照片Start================================================//
    public void initRecyclerView() {
        if (picPathInternet.size() > 0) {
            picPath.addAll(picPathInternet);
        }
        if (photoAdapter == null) {
            photoAdapter = new PhotoAdapter(this, picPath);
            recyclerImg.setAdapter(photoAdapter);
        } else {
            photoAdapter.notifyDataSetChanged();
        }
//        if (flag == FLAG_GW_YIGUIDANG) {//已归档的公文
//
//        } else  if (flag == FLAG_GW_YICHULI) {//已处理的公文
//
//        }
    }
    //==========================================照片End================================================//
}
