package com.vitek.jcoa.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.misdk.ObserverListener;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.JCOAApplication;
import com.vitek.jcoa.base.GBaseNetFragment;
import com.vitek.jcoa.net.entity.ShowTaskEntity;
import com.vitek.jcoa.ui.activity.gw.GCommitTaskActivity;
import com.vitek.jcoa.ui.activity.gw.GShowOfficeFilingActivity;
import com.vitek.jcoa.ui.activity.gw.GwBaseFragment;
import com.vitek.jcoa.ui.view.AlertDialogUtil;

/**
 * 1.公文审批  : GDocumentApprovalActivity  未处理的公文  即 待办公文   STR_GW_TYPE_ZH
 * 2.审批状态  : GApprovalStatusActivity    未归档的公文   STR_GW_TYPE_ZH  +  STR_GW_TYPE_FD
 * <p>
 * A simple {@link GBaseNetFragment} subclass.
 */
public class GNoHandleFragment extends GwBaseFragment {

    private static String[] args;
    private AlertDialogUtil dialogUtil;
    private ShowTaskEntity.ModelsBean modelsBean;

    public GNoHandleFragment() {
    }

    public static GNoHandleFragment newInstance(int position, String... type) {
        args = type;
        Bundle args = new Bundle();
        args.putInt(APR_POSITION, position);
        GNoHandleFragment fragment = new GNoHandleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String[] getGwType() {
        return args;
    }

    @Override
    protected void initViews(View view, final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        net(pageSize, mode);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                modelsBean = mDatas.get(position);
                if (modelsBean.getMytask().getName().equals(Constant.STR_GW_TYPE_FD)) {//已提交
                    dialogUtil = new AlertDialogUtil(mContext, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            go(position, Constant.FLAG_GW_WEIGUIDANG);
                        }
                    });
                    dialogUtil.getAlertDialog(1, true);
                } else {//新建的
                    dialogUtil = new AlertDialogUtil(mContext, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                //AlertDialog  0  添加意见  1  添加图片  2 归档  3 转交下一部门
                                case 0:
//                                    go(position, Constant.GW_SHOW_TYPE_0);
                                    break;
                                case 1:
                                    go(position, Constant.GW_SHOW_TYPE_1);
                                    break;
                                case 2:
                                    go(position, Constant.GW_SHOW_TYPE_2);
                                    break;
                                case 3:
                                    go(position, Constant.GW_SHOW_TYPE_3);
                                    break;
                            }
                        }
                    });
                    dialogUtil.getAlertDialog(0, true);
                }
                dialogUtil.showAlertDialog();
            }
        });
        JCOAApplication.getInstance().registerObserver(JCOAApplication.UPDATE_NOHANDLE, new ObserverListener() {
            @Override
            public void notifyChange(Bundle bundle, Object object) {
                net(pageSize, true);
            }
        });
    }

    private void go(int position, int type) {
        if (type == Constant.FLAG_GW_WEIGUIDANG) {//D   ACTIVITY_NAME_APPROVAL
            Intent intent = new Intent(mContext, GShowOfficeFilingActivity.class);
            intent.putExtra(GShowOfficeFilingActivity.INTENT_GSHOWOFFICE, mDatas.get(position));
            intent.setFlags(type);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, GCommitTaskActivity.class);
            intent.putExtra(GCommitTaskActivity.INTENT_GCOMMIT, mDatas.get(position));
            intent.setFlags(type);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        JCOAApplication.getInstance().unRegisterObserver(JCOAApplication.UPDATE_NOHANDLE);
        super.onDestroy();
    }
}