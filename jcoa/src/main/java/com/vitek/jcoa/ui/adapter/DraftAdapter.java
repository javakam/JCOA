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
import com.vitek.jcoa.net.entity.FindIsPublishedEntity;
import com.vitek.jcoa.ui.activity.DairyDetailActivity;
import com.vitek.jcoa.ui.activity.MyDairyActivity;

import java.util.List;

import static com.vitek.jcoa.Constant.FLAG_DRFT;

/**
 * 草稿箱DraftAdapter
 * <p>
 * Created by javakam on 2017/5/22 0022.
 */
public class DraftAdapter extends BaseListAdapter<FindIsPublishedEntity.ModelsBean> {

    public DraftAdapter(Context mContext, List<FindIsPublishedEntity.ModelsBean> mList, LayoutInflater mInflater) {
        super(mContext, mList, mInflater);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv2, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((FindIsPublishedEntity.ModelsBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final FindIsPublishedEntity.ModelsBean entity, ViewHolder holder) {
       /*
            "id": 200,
            "date": 1493913600000,
            "username": "weixiao",
            "content": "今天，去办了一件公务。",
            "ispublished": 0,
            "workstatus": "单位办公"
         */
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DairyDetailActivity.class);
                intent.putExtra(DairyDetailActivity.INTENT_CONTENT, entity);
                intent.setFlags(Constant.FLAG_DRFT);
                mContext.startActivity(intent);
            }
        });
        holder.tvShortContent.setText(entity.getContent().toString());
        holder.tvDate.setText(TimeUtil.longToDate7(entity.getDate()));
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //7.更新日志草稿/jc_oa/up_mylog (POST))       UpdateMyLogEntity
                // TODO: 2017/5/21 0021  onNetError --- 服务器响应失败  ！？？？
                // TODO: 2017/5/22 0022  服务器响应失败
                //// TODO: 2017/6/1 0001  时间格式问题   牛笔牛笔真尼玛牛笔 ，一个日期上传 n 种格式，会玩
                //ispublished		1,0（1：发布，0：保存为草稿）
//                workstatus		工作状态 不为空,填二级选项的字符串
                Intent intent=new Intent(mContext, MyDairyActivity.class);
                intent.putExtra(MyDairyActivity.ACT_MYDAIRY,entity);
                intent.setFlags(FLAG_DRFT);
                mContext.startActivity(intent);

//                CloudPlatformUtil.getInstance().updateMyLog(
//                        JCOAApi.getUpdateMylogMap(
//                                entity.getId() + ""
//                                , entity.getUsername()
//                                , "111今天，去办了一件公务。。" + System.currentTimeMillis()
//                                , entity.getWorkstatus()
//                                , TimeUtil.getStringDateyyyy_MM_dd()
//                                , "1")//是否发布（1:发布，0：继续保存）
//                        , new ResponseListener<UpdateMyLogEntity>() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(mContext, VolleyErrorManager.getErrorMsg(error), Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onResponse(UpdateMyLogEntity response) {
//                                Toast.makeText(mContext, response.getDefaultMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
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
//                                VLogUtil.e("删除日志草稿 --- 异常 --- " + VolleyErrorManager.getErrorMsg(error));
                            }

                            @Override
                            public void onResponse(DeleteMyLogEntity response) {
                                ToastUtil.shortToast(mContext, response.getDefaultMessage());
                                JCOAApplication.getInstance().notifyDataChange(JCOAApplication.UPDATE_DRAFT, null, null);
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


