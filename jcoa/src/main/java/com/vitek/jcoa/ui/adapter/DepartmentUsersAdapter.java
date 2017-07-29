package com.vitek.jcoa.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.misdk.base.BaseListAdapter;
import com.misdk.util.TimeUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.net.entity.FindDepartmentUserEntity;
import com.vitek.jcoa.ui.activity.WorkStateDetailActivity;

import java.util.List;

/**
 * 工作动态
 * <p>
 * Created by javakam on 2017/5/17 0017.
 */
public class DepartmentUsersAdapter extends BaseListAdapter<FindDepartmentUserEntity.ModelsBean> {

    public DepartmentUsersAdapter(Context mContext, List<FindDepartmentUserEntity.ModelsBean> mList, LayoutInflater mInflater) {
        super(mContext, mList, mInflater);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_workstate, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((FindDepartmentUserEntity.ModelsBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final FindDepartmentUserEntity.ModelsBean entity, ViewHolder holder) {
     /*
            "id": 94,
            "username": "李炳州",
            "password": "730619",
            "realname": "李炳州",
            "departmentid": "8",
            "job": "行政主任",
            "job_type": null,
            "phone": "13230267665",
            "rtime": null,
            "department": null,
            "role_name": null,
            "powerid": null,
            "departments": null,
            "date": null,
            "pdepartment": null,
            "actiontime": null,
            "endtime": null,
            "lusername": null,
            "log_actiontime": null,
            "log_endtime": null
      */
        String[] types = Constant.STR_POST_NOPOST;
        /*
        public static final String[] STR_POST_NOPOST = {
            "单位办公", "外出", "事假", "病假", "工伤"
            , "特殊休假", "外调"
         */
        holder.tvShortContent.setText(
                "姓名:" + "\t" + entity.getUsername() + "\t\t" +
                        "部门:" + "\t" + entity.getDepartment() + "\t\t" +
                        "职务:" + "\t" + entity.getJob() + "\r\n" +
                        "电话:" + "\t" + entity.getPhone() + "\t\t" +
                        "真实姓名:" + "\t" + entity.getRealname() + "\t\t");
        holder.tvDate.setText(TimeUtil.longToDate8(entity.getRtime()));
        LinearLayout ll = (LinearLayout) holder.tvDate.getParent();
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkStateDetailActivity.class);
                intent.putExtra(WorkStateDetailActivity.INTENT_STATEBEAN, entity);
                intent.setFlags(999);
                mContext.startActivity(intent);
            }
        });

    }

    protected class ViewHolder {
        private TextView tvShortContent;
        private TextView tvDate;

        public ViewHolder(View view) {
            tvShortContent = (TextView) view.findViewById(R.id.tvShortContent);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
        }
    }
}
