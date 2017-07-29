package com.vitek.jcoa.ui.activity.gw;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.StringUtils;
import com.misdk.util.TimeUtil;
import com.misdk.util.ToastUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.entity.CompleteEntity;
import com.vitek.jcoa.net.entity.FindWorkflowUserdEntity;
import com.vitek.jcoa.net.entity.ShowTaskEntity;
import com.vitek.jcoa.net.entity.WorkFlowDepartmentEntity;
import com.vitek.jcoa.ui.adapter.PhotoAdapter;
import com.vitek.jcoa.util.BitmapUtils;
import com.vitek.jcoa.util.VLogUtil;
import com.vitek.jcoa.util.gallerypick.GlideImageLoader;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 提交公文      todo  6.3
 */
public class GCommitTaskActivity extends GBaseAppCompatActivity<CompleteEntity> {
    public static final String INTENT_GCOMMIT = "GCommitTaskActivity";
    private int flag;

    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;
    private String titleStr;
    private SharePreUtil spUtil;
    private ShowTaskEntity.ModelsBean showTaskBean;

    private LinearLayout linearSpinner;
    public MaterialSpinner spinnerGwDeparment, spinnerGwUser;//部门，用户
    public List<WorkFlowDepartmentEntity.ModelsBean> wfdBeans = new ArrayList<>();
    public List<String> wfdItems = new ArrayList<>();// WorkFlowDepartmentEntity
    public List<FindWorkflowUserdEntity.ModelsBean> fwfuBeans = new ArrayList<>();
    public List<String> fwfuItems = new ArrayList<>();// FindWorkflowUserdEntity

    private TextView tvGwTitle//公文主题
            , gwTvCreater//创建者
            , tvGwLaiwen//来文机关
            , gwTvShenpi//现审批部门
            ;
    private EditText edtZhengJian     //正件
            , edtFuJian        //附件
            , edtFuYinJian     //复印件
            ;
    private Button btGwCommit;//归档
    //公文意见
    private EditText edtChuliOpin;
    private EditText edtDeptOpin;
    private EditText edtLeaderOpin;
    //照片
    private RecyclerView recyclerImg;
    public List<String> picPathInternet = new ArrayList<>();
    public List<String> picPath = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    private List<Bitmap> bitmapList = new ArrayList<>();
    public static final String NUT_NONE = "notnull";
    private NetGwPresenter presenter;
    public String z = "0", f = "0", fy = "0";

    @Override
    protected void initVariables() {

        spUtil = new SharePreUtil(this, Constant.SPF_USER_INFO);
        wfdItems.add("部门");
        fwfuItems.add("用户");
        flag = getIntent().getFlags();
        showTaskBean = (ShowTaskEntity.ModelsBean) getIntent().getSerializableExtra(INTENT_GCOMMIT);

        //AlertDialog  0  添加意见  1  添加图片  2 归档  3 转交下一部门
        if (flag == Constant.GW_SHOW_TYPE_0) {
            titleStr = "添加意见";
        } else if (flag == Constant.GW_SHOW_TYPE_1) {
            titleStr = "添加图片";
        } else if (flag == Constant.GW_SHOW_TYPE_2) {
            titleStr = "归档";
        } else if (flag == Constant.GW_SHOW_TYPE_3) {
            titleStr = "转交下一部门";
        }
//        OkHttpClient okHttpClient = new OkHttpClient();
//        //构建一个请求对象
//        Request request = new Request.Builder().url("http//wthrcdn.etouch.cn/weather_mini?citykey=101010100").build();
//        //发送请求
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            //打印服务端传回的数据
//            VLogUtil.i("okHttpClient  Response  ---    " + response.body().string());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gcommit_task;
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
        tvTitle.setText(titleStr);
        tvTitle.requestFocusFromTouch();
        tvTitle.setFocusableInTouchMode(true);
        tvTitleNowday = (TextView) findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());

        linearSpinner = (LinearLayout) findViewById(R.id.linearSP);
        spinnerGwDeparment = (MaterialSpinner) findViewById(R.id.spinnerGwDeparment);
        spinnerGwUser = (MaterialSpinner) findViewById(R.id.spinnerGwUser);
        tvGwTitle = (TextView) findViewById(R.id.tvGwTitle);
        gwTvCreater = (TextView) findViewById(R.id.gwTvCreater);
        tvGwLaiwen = (TextView) findViewById(R.id.tvGwLaiwen);
        gwTvShenpi = (TextView) findViewById(R.id.gwTvShenpi);
        edtZhengJian = (EditText) findViewById(R.id.edtZhengJian);
        edtFuJian = (EditText) findViewById(R.id.edtFuJian);
        edtFuYinJian = (EditText) findViewById(R.id.edtFuYinJian);

//        edtChuliOpin = (EditText) findViewById(R.id.edtChuliOpin);
//        edtDeptOpin = (EditText) findViewById(R.id.edtDeptOpin);
//        edtLeaderOpin = (EditText) findViewById(R.id.edtLeaderOpin);
        recyclerImg = (RecyclerView) findViewById(R.id.recyclerImg);
        btGwCommit = (Button) findViewById(R.id.btGwCommit);
//        btGwNextDept = (Button) findViewById(btGwNextDept);
        edtZhengJian.setText(showTaskBean.getThis_d() + "");
        edtFuJian.setText(showTaskBean.getEnclosure() + "");
        edtFuYinJian.setText(showTaskBean.getEcpy_d() + "");
        //默认不能用   , edtChuliOpin, edtDeptOpin, edtLeaderOpin
        setEditText(false, new EditText[]{edtZhengJian, edtFuJian, edtFuYinJian});

        tvGwTitle.setText(showTaskBean.getTitle());
        gwTvCreater.setText(showTaskBean.getCreate_people());
        tvGwLaiwen.setText(showTaskBean.getD_source());
        gwTvShenpi.setText(showTaskBean.getSource());


        presenter = new NetGwPresenter(this, getDialog()
                , showTaskBean, spUtil);
        //获取批文附件/jc_oa/flow/find_cfiles（GET）  图片
        presenter.findCFiles();

        //AlertDialog  0  添加意见  1  添加图片  2 归档  3 转交下一部门
        if (flag == Constant.GW_SHOW_TYPE_0) {
//            edtChuliOpin

        } else if (flag == Constant.GW_SHOW_TYPE_1) {
            linearSpinner.setVisibility(View.GONE);
            //btGwCommit  触发 调用添加图片的接口即可
            edtZhengJian.setEnabled(true);
            edtFuJian.setEnabled(true);
            edtFuYinJian.setEnabled(true);
        } else if (flag == Constant.GW_SHOW_TYPE_2) {
            linearSpinner.setVisibility(View.GONE);
            btGwCommit.setText("归档");
        } else if (flag == Constant.GW_SHOW_TYPE_3) {
            btGwCommit.setText("转交");
            //只有转交下一部门  才有这个sp
            initSpinner();
        }
        btGwCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgeString();
            }
        });
    }

    private void setEditText(boolean enable, EditText... editTexts) {
        for (EditText edit : editTexts) {
            edit.setEnabled(enable);
        }
    }

    /**
     * 提交时候用来判断参数正确性的
     */
    private boolean judgeString() {
        if (flag == Constant.GW_SHOW_TYPE_0) {

        } else if (flag == Constant.GW_SHOW_TYPE_1) {
            z = edtZhengJian.getText().toString();
            f = edtFuJian.getText().toString();
            fy = edtFuYinJian.getText().toString();
            if (!StringUtils.isBlank(z)) {
                if (Integer.valueOf(z) < showTaskBean.getThis_d()) {
                    showShortToast("公文正件数有误");
                    return false;
                }
            }
            if (!StringUtils.isBlank(f)) {
                if (Integer.valueOf(f) < showTaskBean.getEnclosure()) {
                    showShortToast("附件数有误");
                    return false;
                }
            }
            if (!StringUtils.isBlank(fy)) {
                if (Integer.valueOf(fy) < showTaskBean.getEcpy_d()) {
                    showShortToast("复印件数有误");
                    return false;
                }
            }
            /**
             * 上传公文图片
             *    测试  c_id   99
             *    file_type		enclosure          this_d:正件,enclosure：附件,ecpy_d:复印件
             *    file    文件
             */
            picPath.removeAll(picPathInternet);
            //如果有新加入的图片，再去调用上传的接口
            if (picPath.size() >= 0) {
                for (String p : picPath) {
                    bitmapList.add(BitmapUtils.scaleBitmap(p));
                }
            }
            //添加图片 // TODO: 2017/6/6 0006  for  test
            presenter.addImage(bitmapList, Constant.IMG_FILE_TYPE[1]);
            return true;
        } else if (flag == Constant.GW_SHOW_TYPE_2) {
            //获取回退及指派的提交值以及是否可以归档/jc_oa/flow/get_taskbranch（GET）      GetBranchEntity
            presenter.getTaskBranch();
            return true;
        } else if (flag == Constant.GW_SHOW_TYPE_3) {
            presenter.complete(1);
            return true;
        }
        getDialog();
        showDialog();
        return true;
    }

    private void initSpinner() {
//        spinnerGwUser.setVisibility(View.INVISIBLE);
        getDialog();
        showDialog();
        //查看公文可流转的下一部门/jc_oa/flow/Workflow_Department（GET）   WorkFlowDepartmentEntity
        presenter.getWorkflowDepartment();

        // 每次点击部门  刷新一次用户Spinner
        spinnerGwDeparment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                getDialog();
//                showDialog();
                if (wfdBeans != null) {
                    ///获得某个部门可以接受公文的人员/jc_oa/flow/find_workflow_user_d（GET）    FindWorkflowUserdEntity
                    presenter.findWorkflowUserD(wfdBeans.get(position).getDepartmentid() + "");
                }
            }
        });
    }

    @Override
    protected void getNetData() {
        /**
         * 提交公文  /jc_oa/flow/complete（GET）
         * <p>
         * 归档和转交下一部门的接口
         * 参数名称	参数类型	参数说明
         * number		taskDetail.number
         * d_number		taskDetail.d_number
         * source		公文来源（上级交办、其他部门移交）taskDetail.source
         * title		题目taskDetail.title
         * this_d		正件（份数）                  ------------- 动态传参
         * enclosure		附件（份数）              ------------- 动态传参
         * ecpy_d		复印件（份数）                ------------- 动态传参
         * remarks		备注
         * create_people		创建人（创建人的username）
         * the_file		1，不归档，0，归档（创建时传1）      ------------- 动态传参
         * c_id		当前公文的c_id
         * assignees		taskDetail.assignees
         * taskid		taskDetail.mytask.id
         * usernames   公文接受人的usernames（如果回退填taskDetail.assignees）如果归档此字段无效，可以传user.username   --- 动态传参
         * did_or_end		提交值（在get_taskbranch接口中有返回）（归档传0）    --- 动态传参
         * instid		taskDetail.instid
         * d_usernames		taskDetail.d_usernames
         */
        //1，不归档，0，归档（创建时传1）
        //AlertDialog  0  添加意见  1  添加图片  2 归档  3 转交下一部门
        if (flag == Constant.GW_SHOW_TYPE_2) {
            presenter.complete(0);
        } else if (flag == Constant.GW_SHOW_TYPE_3) {
            presenter.complete(1);
        }
    }


    @Override
    protected void onNetError(VolleyError error) {
        dismissDialog();
        VLogUtil.e("getNet()跟新失败  === " + VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(CompleteEntity entity) {
        if (!entity.ispass()) {
            dismissDialog();
            showShortToast(entity.getDefaultMessage());
        }
        VLogUtil.e("Success --- " + entity.toString());
        //归档or转交下一部门成功后，在上传图片！！！
        /**
         * 上传公文图片
         *    测试  c_id   99
         *    file_type		enclosure          this_d:正件,enclosure：附件,ecpy_d:复印件
         *    file    文件
         */
    }

    //==========================================照片Start================================================//
    public void initGalley() {
        if (picPathInternet.size() > 0) {
            picPath.addAll(picPathInternet);
        }
        if (flag == Constant.GW_SHOW_TYPE_2 || flag == Constant.GW_SHOW_TYPE_3) {
            if (photoAdapter == null) {
                recyclerImg.setLayoutManager(new GridLayoutManager(this, 3));
                photoAdapter = new PhotoAdapter(this, picPath);
                recyclerImg.setAdapter(photoAdapter);
            } else {
                photoAdapter.notifyDataSetChanged();
            }
            return;
        }
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.vitek.jcoa.fileprovider")   // provider(必填)
//                .pathList(picPath)         // 记录已选的图片
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
//                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否显示相机按钮  默认：false
                .filePath(Constant.APP_IMG_PATH)        // 图片存放路径   "/Vitek/jcoa/pic"
//                .imageLoader(new GlideImageLoader())    // 设置图片加载框架
                .build();

        recyclerImg.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerImg.addItemDecoration(new DividerGridItemDecoration(this));
        //联网返回
        if (picPath.size() == 0) {
            picPath.add(NUT_NONE);
        } else {//在末尾添加按钮
            picPath.add(picPath.size(), NUT_NONE);
        }
        if (photoAdapter == null) {
            photoAdapter = new PhotoAdapter(this, picPath, new PhotoAdapter.OnAddPhotoBTClickListener() {
                @Override
                public void onAddClick(View v) {
                    initPermissions();
                }

                @Override
                public void onImageLongClick(String path, PhotoAdapter.ViewHolder holder, int position) {
//                v.setBackgroundColor(getResources().getColor(R.color.gallery_yellow));
                    ToastUtil.shortToast(GCommitTaskActivity.this, "path === " + path);
//                    VLogUtil.i( "path === " + path);

                    //长按删除
//                if (path != NUT_NONE) {
////                    photoAdapter.deleteItem(picPath.get(position));
//                    picPath.remove(path);
//                    photoAdapter.notifyItemRemoved(position);
//                }
                }
            });
            recyclerImg.setAdapter(photoAdapter);
        } else {
            photoAdapter.notifyDataSetChanged();
        }
    }

    public void initGalleyCallBack() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
//                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
//                Log.i(TAG, "onSuccess: 返回数据");
                if (photoList.size() == 0) {
                    if (picPath.size() == 0) {
                        picPath.add(NUT_NONE);
                    }
                } else {
                    picPath.clear();//还有之前的图片。。
                    picPath.addAll(picPathInternet);
                    for (String s : photoList) {
                        picPath.add(s);
                    }
                    picPath.add(NUT_NONE);//最后一个框
                }
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel(List<String> photoList) {
                Log.i("123", "onCancel: 取消" + photoAdapter.getItemCount() + " PicPath " + picPath.toString());
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinish() {
                Log.i("123", "onFinish: 结束" + photoAdapter.getItemCount());
            }

            @Override
            public void onError() {
                Log.i("123", "onError: 出错");
            }
        };

    }

    // 授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            Log.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Log.i(TAG, "拒绝过了");
                ToastUtil.shortToast(this, "请在 设置-应用管理 中开启此应用的储存授权。");
            } else {
//                Log.i(TAG, "进行授权");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
//            Log.i(TAG, "不需要授权 ");
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Log.i(TAG, "同意授权");
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
            } else {
//                Log.i(TAG, "拒绝授权");
            }
        }
    }
    //==========================================照片End================================================//
}