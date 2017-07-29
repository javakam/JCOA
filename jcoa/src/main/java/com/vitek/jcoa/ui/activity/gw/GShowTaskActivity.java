package com.vitek.jcoa.ui.activity.gw;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.misdk.ObserverListener;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.JCOAApplication;
import com.vitek.jcoa.ui.view.AlertDialogUtil;

/**
 * 待办公文  新建的公文
 */
public class GShowTaskActivity extends GwBaseActivity {

    @Override
    protected String[] getGwType() {
        return new String[]{Constant.STR_GW_TYPE_ZH};
    }

    @Override
    protected String getTitleName() {
        return "待办公文";
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        net(pageSize, mode);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialogUtil dialogUtil = new AlertDialogUtil(GShowTaskActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            //AlertDialog  0  添加意见  1  添加图片  2 归档  3 转交下一部门
                            case 0:
//                                go(position, Constant.GW_SHOW_TYPE_0);
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
                dialogUtil.showAlertDialog();
            }
        });
        JCOAApplication.getInstance().registerObserver(JCOAApplication.UPDATE_DOCUMENT_APPROVAL, new ObserverListener() {
            @Override
            public void notifyChange(Bundle bundle, Object object) {
                getDialog();
                showDialog();
                net(pageSize, mode);
            }
        });
    }

    private void go(int position, int type) {
        Intent intent = new Intent(this, GCommitTaskActivity.class);
        intent.putExtra(GCommitTaskActivity.INTENT_GCOMMIT, mDatas.get(position));
        intent.setFlags(type);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        JCOAApplication.getInstance().unRegisterObserver(JCOAApplication.UPDATE_DOCUMENT_APPROVAL);
        super.onDestroy();
    }
}