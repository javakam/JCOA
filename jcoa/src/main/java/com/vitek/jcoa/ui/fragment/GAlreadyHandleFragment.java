package com.vitek.jcoa.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.vitek.jcoa.ui.activity.gw.GShowOfficeFilingActivity;
import com.vitek.jcoa.ui.activity.gw.GwBaseFragment;

import static com.vitek.jcoa.Constant.FLAG_GW_YICHULI;
import static com.vitek.jcoa.ui.activity.gw.GShowOfficeFilingActivity.INTENT_GSHOWOFFICE;

/**
 * 已处理的公文
 * 1.公文审批  : GDocumentApprovalActivity  已处理的公文   STR_GW_TYPE_FD  +  STR_GW_TYPE_GD
 * 2.审批状态 {@link GAlreadyFileFragment} : GApprovalStatusActivity    已归档的公文   STR_GW_TYPE_GD
 * <p>
 */
public class GAlreadyHandleFragment extends GwBaseFragment {

    private static String[] args;

    public GAlreadyHandleFragment() {
        // Required empty public constructor
    }

    public static GAlreadyHandleFragment newInstance(int position, String... type) {
        args = type;
        Bundle args = new Bundle();
        args.putInt(APR_POSITION, position);
        GAlreadyHandleFragment fragment = new GAlreadyHandleFragment();
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GShowOfficeFilingActivity.class);
                intent.putExtra(INTENT_GSHOWOFFICE, mDatas.get(position));
                intent.setFlags(FLAG_GW_YICHULI);
                startActivity(intent);
            }
        });
    }
}