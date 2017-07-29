package com.vitek.jcoa.ui.activity.gw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.misdk.ObserverListener;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.JCOAApplication;
import com.vitek.jcoa.net.entity.ShowTaskEntity;

/**
 * 公文归档
 */
public class GOfficeFilingActivity extends GwBaseActivity {
    private static final String INTENT_FROM_GOFFICE = "GOfficeFilingActivity";
    private ShowTaskEntity.ModelsBean modelsBean;

    @Override
    protected String[] getGwType() {
        return new String[]{Constant.STR_GW_TYPE_GD};
    }

    @Override
    protected String getTitleName() {
        return "公文归档";
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        net(pageSize, mode);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modelsBean = mDatas.get(position);
                Intent intent = new Intent(GOfficeFilingActivity.this, GShowOfficeFilingActivity.class);
                intent.putExtra(GShowOfficeFilingActivity.INTENT_GSHOWOFFICE, modelsBean);
                startActivity(intent);
            }
        });
        JCOAApplication.getInstance().registerObserver(JCOAApplication.UPDATE_OFFICE_FILING, new ObserverListener() {
            @Override
            public void notifyChange(Bundle bundle, Object object) {
                getDialog();
                showDialog();
                net(pageSize, mode);
            }
        });
    }
}