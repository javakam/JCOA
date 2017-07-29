package com.vitek.jcoa.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.misdk.base.BaseListAdapter;
import com.misdk.util.TimeUtil;
import com.vitek.jcoa.R;
import com.vitek.jcoa.net.entity.ShowTaskEntity;

import java.util.List;

/**
 * 代办公文
 * <p>
 * Created by javakam on 2017/5/30 0030.
 */
public class ShowTaskAdapter extends BaseListAdapter<ShowTaskEntity.ModelsBean> {
    private final static String NEXT_LINE = "\r\n";

    public ShowTaskAdapter(Context mContext
            , List<ShowTaskEntity.ModelsBean> mList, LayoutInflater mInflater) {
        super(mContext, mList, mInflater);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_show_task, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ShowTaskEntity.ModelsBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(ShowTaskEntity.ModelsBean entity, ViewHolder holder) {
        holder.tvGwTitle.setText(entity.getTitle());
        holder.tvGwNum.setText(entity.getNumber() + "");
        holder.tvGwCreator.setText(entity.getCreate_people());
        holder.tvGwDate.setText(TimeUtil.longToDate8(entity.getSource_time()));
    }

    protected class ViewHolder {
        private LinearLayout linearGw;
        private TextView tvGwTitle;
        private TextView tvGwNum;
        private TextView tvGwCreator;
        private TextView tvGwDate;

        public ViewHolder(View view) {
            linearGw= (LinearLayout) view.findViewById(R.id.linearGw);
            tvGwTitle = (TextView) view.findViewById(R.id.tvGwTitle);
            tvGwNum = (TextView) view.findViewById(R.id.tvGwNum);
            tvGwCreator = (TextView) view.findViewById(R.id.tvGwCreator);
            tvGwDate = (TextView) view.findViewById(R.id.tvGwDate);
        }
    }
}