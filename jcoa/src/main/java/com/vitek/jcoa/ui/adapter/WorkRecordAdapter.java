package com.vitek.jcoa.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.base.BaseListAdapter;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.TimeUtil;
import com.misdk.util.ToastUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.JCOAApplication;
import com.vitek.jcoa.R;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.DeleteMyLogEntity;
import com.vitek.jcoa.net.entity.FindlogEntity;
import com.vitek.jcoa.ui.activity.DairyDetailActivity;
import com.vitek.jcoa.util.VLogUtil;

import java.util.List;

/**
 * 工作记录WorkRecordAdapter
 * <p>
 * Created by javakam on 2017/5/22 0022.
 */
public class WorkRecordAdapter extends BaseListAdapter<FindlogEntity.LogExtendsBean> {

    public WorkRecordAdapter(Context mContext, List<FindlogEntity.LogExtendsBean> mList, LayoutInflater mInflater) {
        super(mContext, mList, mInflater);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv2, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((FindlogEntity.LogExtendsBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final FindlogEntity.LogExtendsBean entity, ViewHolder holder) {
        /*
             "id": 178,
            "date": 1493654400000,
            "username": "李炳州",
            "content": "1、起草申报国家住建系统先进集体事迹材料。2、准备2016年度财务审计工作。",
            "ispublished": 1,
            "workstatus": "单位办公",
            "job": "行政主任",
            "job_type": null,
            "powerid": 2,
            "department": "行政主任"
         */
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, DairyDetailActivity.class);
//                intent.putExtra(DairyDetailActivity.INTENT_CONTENT, entity.getContent());
//                mContext.startActivity(intent);
            }
        });
        holder.tvShortContent.setText(entity.getContent().toString());
        holder.tvDate.setText(TimeUtil.longToDate7(entity.getDate()));
        holder.btEdit.setText("查看");
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DairyDetailActivity.class);
                intent.putExtra(DairyDetailActivity.INTENT_CONTENT, entity);
                intent.setFlags(Constant.FLAG_WORK_RECORD);
                mContext.startActivity(intent);


                //ispublished		1,0（1：发布，0：保存为草稿）
//                workstatus		工作状态 不为空,填二级选项的字符串
//                Intent intent=new Intent(mContext, MyDairyActivity.class);
//                intent.putExtra(MyDairyActivity.ACT_MYDAIRY,entity);
//                intent.setFlags(Constant.FLAG_WORK_RECORD);
//                mContext.startActivity(intent);
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //8.删除日志草稿/jc_oa/del_log (POST))        DeleteMyLogEntity           OK 2017-05-21
                CloudPlatformUtil.getInstance().deleteMyLog(JCOAApi.getDeleteMylogMap(entity.getId() + "")
                        , new ResponseListener<DeleteMyLogEntity>() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                ToastUtil.shortToast(mContext, VolleyErrorManager.getErrorMsg(error));
                                VLogUtil.e("删除日志草稿 --- 异常 --- " + VolleyErrorManager.getErrorMsg(error));
                            }

                            @Override
                            public void onResponse(DeleteMyLogEntity response) {
                                ToastUtil.shortToast(mContext, response.getDefaultMessage());
                                JCOAApplication.getInstance().notifyDataChange(JCOAApplication.UPDATE_LOG, null, null);
                            }
                        });
            }
        });
    }

    protected class ViewHolder {
        private LinearLayout linearLayout;
        private TextView tvShortContent;
        private TextView tvDate;
        private Button btEdit;
        private Button btDelete;

        public ViewHolder(View view) {
            tvShortContent = (TextView) view.findViewById(R.id.tvShortContent);
            linearLayout = (LinearLayout) tvShortContent.getParent();
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            btEdit = (Button) view.findViewById(R.id.btEdit);
            btDelete = (Button) view.findViewById(R.id.btDelete);
        }
    }
}
