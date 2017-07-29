package com.vitek.jcoa.ui.activity.gw;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.volley.VolleyError;
import com.misdk.base.BaseAppCompatActivity;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.StringUtils;
import com.misdk.util.ToastUtil;
import com.misdk.views.loadding.CustomDialog;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.LoginUtil;
import com.vitek.jcoa.net.UploadUtil;
import com.vitek.jcoa.net.entity.CompleteTaskEntity;
import com.vitek.jcoa.net.entity.FindCFilesEntity;
import com.vitek.jcoa.net.entity.FindWorkflowUserdEntity;
import com.vitek.jcoa.net.entity.GetBranchEntity;
import com.vitek.jcoa.net.entity.ShowTaskEntity;
import com.vitek.jcoa.net.entity.WorkFlowDepartmentEntity;
import com.vitek.jcoa.util.VLogUtil;

import java.util.List;

/**
 * AlertDialog  0  添加意见  1  添加图片  2 归档  3 转交下一部门
 * if (flag == Constant.GW_SHOW_TYPE_0) {}
 * else if (flag == Constant.GW_SHOW_TYPE_1) {}
 * else if (flag == Constant.GW_SHOW_TYPE_2) {}
 * else if (flag == Constant.GW_SHOW_TYPE_3) {}
 * <p>
 * Created by javakam on 2017/6/6 .
 */
public class NetGwPresenter implements IGwInterface {
    private GCommitTaskActivity gCommitTaskActivity;
    private BaseAppCompatActivity activity;
    private CustomDialog dialog;
    private static String SESSION;
    private ShowTaskEntity.ModelsBean showTaskBean;
    private GetBranchEntity branchEntity;
    private SharePreUtil spUtil;

    private String[] strsNull = new String[]{""};

    public NetGwPresenter(BaseAppCompatActivity activity, CustomDialog dialog, ShowTaskEntity.ModelsBean showTaskBean
            , SharePreUtil spUtil) {
        this.activity = activity;
        this.dialog = dialog;
//        spUtil = new SharePreUtil(activity, Constant.SPF_USER_INFO);
//        showTaskBean = (ShowTaskEntity.ModelsBean) activity.getIntent().getSerializableExtra(INTENT_GCOMMITTASK);
        this.showTaskBean = showTaskBean;
        this.spUtil = spUtil;
    }

    @Override
    public void getSession() {
        SESSION = ";jsessionid=" + LoginUtil.getLocalCookie();
    }

    @Override
    public void addOpinion() {
        getSession();
    }

    @Override
    public void addImage(List<Bitmap> bitmapList, @NonNull String picType) {
        getDialog();
        showDialog();
        getSession();
        // 测试  c_id   99
        //file_type		enclosure          this_d:正件,enclosure：附件,ecpy_d:复印件
        // file    文件
        /**
         * 上传公文图片
         */
        UploadUtil.uploadImgList(showTaskBean.getC_id() + "", picType  // // TODO: 2017/6/3 0003 全当正件传的
                , bitmapList, CompleteTaskEntity.class, new ResponseListener<CompleteTaskEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissDialog();
                        VLogUtil.e("上传图片异常  ---   " + VolleyErrorManager.getErrorMsg(error));
                    }

                    @Override
                    public void onResponse(CompleteTaskEntity entity1) {
                        ToastUtil.shortToast(activity, entity1.getDefaultMessage());
//                            JCOAApplication.getInstance().notifyDataChange(JCOAApplication.UPDATE_DOCUMENT_APPROVAL, null, null);
                        dismissDialog();
                        activity.finish();
                    }
                });

    }

    @Override
    public void addFile() {
        getSession();
    }

    @Override
    public void addNextDept() {
        getSession();
    }

    @Override
    public void getTaskBranch() {
        getSession();
        //获取回退及指派的提交值以及是否可以归档/jc_oa/flow/get_taskbranch（GET）      GetBranchEntity
        CloudPlatformUtil.getInstance().getBranch(JCOAApi.GET_BRANCH + SESSION
                , new ResponseListener<GetBranchEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissDialog();
                        VLogUtil.e("get_taskbranch  error  ---   " + VolleyErrorManager.getErrorMsg(error));
                    }

                    @Override
                    public void onResponse(GetBranchEntity response) {
                        if (response == null) {
//                            showShortToast(response.getDefaultMessage());
                            activity.finish();
                            return;
                        }
                        branchEntity = response;
                        if (branchEntity.getModels() == null) return;
                        if (branchEntity.getModels().get(0) == null) return;
                        complete(0);//0 归档
                    }
                });
    }

    @Override
    public void getWorkflowDepartment() {
        getSession();
        //查看公文可流转的下一部门/jc_oa/flow/Workflow_Department（GET）   WorkFlowDepartmentEntity
        CloudPlatformUtil.getInstance().getWorkFlowDepartment(
                JCOAApi.WORKFLOW_DEPARTMENT + SESSION
                , new ResponseListener<WorkFlowDepartmentEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: 2017/6/2 0002  调用数据库
                        dismissDialog();
                        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
                    }

                    @Override
                    public void onResponse(WorkFlowDepartmentEntity response) {
                        if (response == null) return;
                        if (response.getModels() == null) return;
                        gCommitTaskActivity = (GCommitTaskActivity) activity;
                        gCommitTaskActivity.wfdItems.clear();
                        gCommitTaskActivity.wfdBeans = response.getModels();
                        gCommitTaskActivity.spinnerGwDeparment.setItems(strsNull);
                        for (WorkFlowDepartmentEntity.ModelsBean bean : gCommitTaskActivity.wfdBeans) {
                            gCommitTaskActivity.wfdItems.add(bean.getDepartment());
                        }
                        gCommitTaskActivity.spinnerGwDeparment.setItems(gCommitTaskActivity.wfdItems);
                        //左边的1放好数据了，请求右边的
                        findWorkflowUserD(gCommitTaskActivity.wfdBeans.get(0).getDepartmentid() + "");
                    }
                });
    }

    @Override
    public void findWorkflowUserD(String departmentid) {
        getDialog().setCancelable(false);
        showDialog();
        getSession();
        ///获得某个部门可以接受公文的人员/jc_oa/flow/find_workflow_user_d（GET）    FindWorkflowUserdEntity
        //    + ";jsessionid=" + LoginUtil.getLocalCookie()
        CloudPlatformUtil.getInstance().getFindWorkflowUserD(
                JCOAApi.getFindWorkflowUserD(departmentid)
                , new ResponseListener<FindWorkflowUserdEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: 2017/6/2 0002  调用数据库
                        dismissDialog();
                        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
                    }

                    @Override
                    public void onResponse(FindWorkflowUserdEntity response) {
                        if(response==null)return;
                        gCommitTaskActivity = (GCommitTaskActivity) activity;
                        gCommitTaskActivity.spinnerGwUser.setVisibility(View.VISIBLE);
                        gCommitTaskActivity.spinnerGwUser.setItems(strsNull);
                        gCommitTaskActivity.fwfuItems.clear();
                        for (FindWorkflowUserdEntity.ModelsBean bean : response.getModels()) {
                            gCommitTaskActivity.fwfuItems.add(bean.getUsername());
                        }
                        gCommitTaskActivity.spinnerGwUser.setItems(gCommitTaskActivity.fwfuItems);
                        dismissDialog();
                        //右边的弄好了，加载图片gogo
                    }
                });
    }

    @Override
    public void findCFiles() {
        getSession();
        //获取批文附件/jc_oa/flow/find_cfiles（GET）  图片
        CloudPlatformUtil.getInstance().findCFiles(JCOAApi.getFindCfiles(showTaskBean.getC_id() + "")
                , new ResponseListener<FindCFilesEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        dismissDialog();
                        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
                    }

                    @Override
                    public void onResponse(FindCFilesEntity entity) {
                        List<FindCFilesEntity.ModelsBean> files = entity.getModels();
                        //eg :  http://www.vitection.com/jc_oa  /cfiles/5bb6719f-d5ff-4779-a582-9db4960ba3d5.png
                        /*
                          "id": 76,
                          "c_id": 96,
                          "filename": "img1496065691430.png",
                          "file_type": "this_d",
                          "file_path": "/cfiles/0d1fcfdb-87b9-4054-a90f-fc4fa8a96e17.png"
                         */
                        if (activity instanceof GCommitTaskActivity) {
                            gCommitTaskActivity = (GCommitTaskActivity) activity;
                            for (FindCFilesEntity.ModelsBean bean : files) {
                                gCommitTaskActivity.picPathInternet.add(JCOAApi.BASE_CFILES + bean.getFile_path());
                            }
                            //初始化相册
                            gCommitTaskActivity.initGalleyCallBack();
                            gCommitTaskActivity.initGalley();
                        } else if (activity instanceof GShowOfficeFilingActivity) {
                            GShowOfficeFilingActivity gShowOfficeFilingActivity = (GShowOfficeFilingActivity) activity;
                            for (FindCFilesEntity.ModelsBean bean : files) {
                                gShowOfficeFilingActivity.picPathInternet.add(JCOAApi.BASE_CFILES + bean.getFile_path());
                            }
                            //初始化相册
                            gShowOfficeFilingActivity.initRecyclerView();
                        }
                        dismissDialog();
                    }
                });
    }

    @Override
    public void complete(int type) {///CompleteEntity
        getSession();
        gCommitTaskActivity = (GCommitTaskActivity) activity;
        String usernames = "";
        String did_or_end = "";
        //1，不归档，0，归档（创建时传1）
        if (type == Constant.GW_SHOW_TYPE_2) {
            usernames = spUtil.getValue(Constant.SP_USER_NAME);//// TODO: 2017/6/3 0003 如果失败，去掉此字段再试
            did_or_end = "0";
        } else if (type == Constant.GW_SHOW_TYPE_3) {
            usernames = gCommitTaskActivity.spinnerGwUser.getText().toString();
            did_or_end = branchEntity.getModels().get(0).getSubmit().get(0);//
        }

        CloudPlatformUtil.getInstance().complete(
                JCOAApi.getGwCompleteMap(
                        StringUtils.utf8Encode(showTaskBean.getNumber() + "")
                        , showTaskBean.getD_number() + ""
                        , StringUtils.utf8Encode(showTaskBean.getSource()),
                        StringUtils.utf8Encode(showTaskBean.getTitle()),
                        gCommitTaskActivity.z, gCommitTaskActivity.f, gCommitTaskActivity.fy,
                        StringUtils.utf8Encode(showTaskBean.getRemarks())
                        , StringUtils.utf8Encode(showTaskBean.getCreate_people()), type + ""//the_file
                        , showTaskBean.getC_id() + "", StringUtils.utf8Encode(showTaskBean.getAssignees())
                        , showTaskBean.getMytask().getId() + ""
                        , StringUtils.utf8Encode(usernames), did_or_end
                        , showTaskBean.getInstid() + "",
                        StringUtils.utf8Encode(showTaskBean.getD_usernames() + "")
                ), gCommitTaskActivity);//加上Cookie！！！     + ";jsessionid=" + LoginUtil.getLocalCookie()
    }

    //=====================================================================================//
    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(activity);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
//        getDialog().show();
        if (dialog != null)
            dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
