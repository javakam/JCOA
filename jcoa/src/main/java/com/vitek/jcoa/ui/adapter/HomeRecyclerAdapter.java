package com.vitek.jcoa.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.misdk.util.SharePreUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.net.entity.BeanRecycler;
import com.vitek.jcoa.ui.activity.DraftActivity;
import com.vitek.jcoa.ui.activity.MyDairyActivity;
import com.vitek.jcoa.ui.activity.ReturnBoxActivity;
import com.vitek.jcoa.ui.activity.WorkRecordActivity;
import com.vitek.jcoa.ui.activity.gw.GApprovalStatusActivity;
import com.vitek.jcoa.ui.activity.gw.GDocumentApprovalActivity;
import com.vitek.jcoa.ui.activity.gw.GOfficeFilingActivity;
import com.vitek.jcoa.ui.activity.gw.GShowTaskActivity;

import java.util.List;

/**
 * Created by javakam on 2017/5/18 0018.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<BeanRecycler> mData;
    private SharePreUtil sharePreUtil;

    public HomeRecyclerAdapter(Context context, List<BeanRecycler> beans) {
        this.context = context;
        this.mData = beans;
        sharePreUtil = new SharePreUtil(context, Constant.SPF_USER_INFO);
    }

    /**
     * RecyclerView添加Item
     *
     * @param bean
     * @param position
     */
    public void addItem(BeanRecycler bean, int position) {
        mData.add(position, bean);
        notifyItemInserted(position);
    }

    /**
     * RecyclerView删除Item
     *
     * @param bean
     */
    public void deleteItem(BeanRecycler bean) {
        int pos = mData.indexOf(bean);
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BeanRecycler bean = mData.get(position);
        holder.imgRecycler.setImageResource(bean.getResId());
        holder.tvRecycler.setText(bean.getName());
        LinearLayout ll = (LinearLayout) holder.tvRecycler.getParent();
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobName = sharePreUtil.getValue(Constant.SP_USER_JOB);
                switch (bean.get_id()) {
                    case 0://我的日志
                        Intent intent1 = new Intent(context, MyDairyActivity.class);
                        context.startActivity(intent1);
                        break;
                    case 1://工作记录
                        Intent intent2 = new Intent(context, WorkRecordActivity.class);
                        context.startActivity(intent2);
                        break;
                    case 2://草稿箱
                        Intent intent3 = new Intent(context, DraftActivity.class);
                        context.startActivity(intent3);
                        break;
                    case 3://退件箱
                        Intent intent4 = new Intent(context, ReturnBoxActivity.class);
                        context.startActivity(intent4);
                        break;
                    case 4://公文审批
                        if (jobName.equals(Constant.STR_JOBS[3])) break;
                        Intent intent5 = new Intent(context, GDocumentApprovalActivity.class);
                        context.startActivity(intent5);
                        break;
                    case 5://待办公文
                        if (jobName.equals(Constant.STR_JOBS[3])) break;
                        Intent intent6 = new Intent(context, GShowTaskActivity.class);
                        context.startActivity(intent6);
                        break;
                    case 6://审批状态
                        if (jobName.equals(Constant.STR_JOBS[3])) break;
                        Intent intent7 = new Intent(context, GApprovalStatusActivity.class);
                        context.startActivity(intent7);
                        break;
                    case 7://公文归档
                        if (jobName.equals(Constant.STR_JOBS[3])) break;
                        Intent intent8 = new Intent(context, GOfficeFilingActivity.class);
                        context.startActivity(intent8);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgRecycler;
        private TextView tvRecycler;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgRecycler = (ImageView) itemView.findViewById(R.id.imgRecycler);
            tvRecycler = (TextView) itemView.findViewById(R.id.tvRecycler);
        }
    }
}
