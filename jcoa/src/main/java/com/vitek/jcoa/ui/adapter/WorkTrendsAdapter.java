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
import com.vitek.jcoa.net.entity.StatisticalPersonalEntity;
import com.vitek.jcoa.ui.activity.WorkStateDetailActivity;

import java.util.List;

/**
 * 工作动态
 * <p>
 * Created by javakam on 2017/5/17 0017.
 */
public class WorkTrendsAdapter extends BaseListAdapter<StatisticalPersonalEntity.DstateModelBean.StateModelsBean> {
    private StatisticalPersonalEntity statisticalPersonalEntity;

    public WorkTrendsAdapter(Context mContext, StatisticalPersonalEntity statisticalPersonalEntity, List<StatisticalPersonalEntity.DstateModelBean.StateModelsBean> mList, LayoutInflater mInflater) {
        super(mContext, mList, mInflater);
        this.statisticalPersonalEntity = statisticalPersonalEntity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_workstate, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((StatisticalPersonalEntity.DstateModelBean.StateModelsBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final StatisticalPersonalEntity.DstateModelBean.StateModelsBean entity, ViewHolder holder) {
     /*
                "username": "weixiao",
              y  "work_day": 9,
                "notwork_day": 0,
             y   "shijia": 0,
             y   "bingjia": 0,
             y   "waichu": 0,
             y   "gongshang": 0,
             y   "danweibangong": 9,
             y   "teshuxiujia": 0,
             y   "waidiao": 0
      */
        String[] types = Constant.STR_POST_NOPOST;
        /*
        public static final String[] STR_POST_NOPOST = {
            "单位办公", "外出", "事假", "病假", "工伤"
            , "特殊休假", "外调"
         */
        holder.tvShortContent.setText("姓名：" + entity.getUsername() + "\t\t" +
                "在岗天数（天）" + entity.getWork_day() + "\t\t" +
                types[0] + entity.getDanweibangong() + "\t\t" +
                types[1] + entity.getWaichu() + "\t\t" +
                types[2] + entity.getShijia() + "\t\t" +
                types[3] + entity.getBingjia() + "\t\t" +
                types[4] + entity.getGongshang() + "\t\t" +
                types[5] + entity.getTeshuxiujia() + "\t\t" +
                types[6] + entity.getWaidiao());
        holder.tvDate.setText(TimeUtil.getStringDateyyyy_MM_dd());
        LinearLayout ll = (LinearLayout) holder.tvDate.getParent();
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkStateDetailActivity.class);
                intent.putExtra(WorkStateDetailActivity.INTENT_STATEBEAN, entity);
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
