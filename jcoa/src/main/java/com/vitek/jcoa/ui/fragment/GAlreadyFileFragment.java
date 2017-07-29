package com.vitek.jcoa.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.vitek.jcoa.Constant;
import com.vitek.jcoa.ui.activity.gw.GShowOfficeFilingActivity;
import com.vitek.jcoa.ui.activity.gw.GwBaseFragment;
import com.vitek.jcoa.ui.view.AlertDialogUtil;

/**
 * 审批状态  : GApprovalStatusActivity    已归档的公文   STR_GW_TYPE_GD   FLAG_GW_YIGUIDANG
 */
public class GAlreadyFileFragment extends GwBaseFragment {

    private static String[] args;
    private AlertDialogUtil dialogUtil;

    public GAlreadyFileFragment() {
        // Required empty public constructor
    }

    public static GAlreadyFileFragment newInstance(int position, String... type) {
        args = type;
        Bundle args = new Bundle();
        args.putInt(APR_POSITION, position);
        GAlreadyFileFragment fragment = new GAlreadyFileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String[] getGwType() {
        return args;
    }

    @Override
    protected void initViews(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        net(pageSize, mode);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                dialogUtil = new AlertDialogUtil(mContext, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(mContext, GShowOfficeFilingActivity.class);
                        intent.putExtra(GShowOfficeFilingActivity.INTENT_GSHOWOFFICE, mDatas.get(position));
                        intent.setFlags(Constant.FLAG_GW_YIGUIDANG);
                        startActivity(intent);
                    }
                });
                dialogUtil.getAlertDialog(1, true);
                dialogUtil.showAlertDialog();
            }
        });
    }
}