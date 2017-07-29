package com.vitek.jcoa.ui.activity.gw;

import android.graphics.Bitmap;

import java.util.List;

/**
 * AlertDialog  0  添加意见  1  添加图片  2 归档  3 转交下一部门
 * if (flag == Constant.GW_SHOW_TYPE_0) {}
 * else if (flag == Constant.GW_SHOW_TYPE_1) {}
 * else if (flag == Constant.GW_SHOW_TYPE_2) {}
 * else if (flag == Constant.GW_SHOW_TYPE_3) {}
 * <p>
 * Created by javakam on 2017/6/6 0006.
 */
public interface IGwInterface {
    void getSession();

    void addOpinion();

    /**
     * 上传公文图片
     * UploadUtil.uploadImgList(showTaskBean.getC_id() + "", "this_d"  // // TODO: 2017/6/3 0003 全当正件传的
     * //                    , bitmapList,
     */
    void addImage(List<Bitmap> bitmapList,String picType);

    void addFile();

    void addNextDept();

    /**
     * //获取回退及指派的提交值以及是否可以归档/jc_oa/flow/get_taskbranch（GET）      GetBranchEntity
     * CloudPlatformUtil.getInstance().getBranch(JCOAApi.GET_BRANCH + SESSION
     */
    void getTaskBranch();

    /**
     * //查看公文可流转的下一部门/jc_oa/flow/Workflow_Department（GET）   WorkFlowDepartmentEntity
     * CloudPlatformUtil.getInstance().getWorkFlowDepartment(
     * JCOAApi.WORKFLOW_DEPARTMENT + SESSION
     */
    void getWorkflowDepartment();

    /**
     * //获得某个部门可以接受公文的人员/jc_oa/flow/find_workflow_user_d（GET）    FindWorkflowUserdEntity
     * //    + ";jsessionid=" + LoginUtil.getLocalCookie()
     * CloudPlatformUtil.getInstance().getFindWorkflowUserD(
     * JCOAApi.getFindWorkflowUserD(bean.getDepartmentid() + "")
     */
    void findWorkflowUserD(String departmentid);

    /**
     * //获取批文附件/jc_oa/flow/find_cfiles（GET）  图片
     * CloudPlatformUtil.getInstance().findCFiles(JCOAApi.getFindCfiles(showTaskBean.getC_id() + "")
     */
    void findCFiles();

    /**
     * /**
     * 提交公文  /jc_oa/flow/complete（GET）          CompleteEntity  type 0归档
     * CloudPlatformUtil.getInstance().complete(
     * JCOAApi.getGwCompleteUrl(
     */
    void complete(int type);

}
