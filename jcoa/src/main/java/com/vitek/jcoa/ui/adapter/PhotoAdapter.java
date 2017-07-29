package com.vitek.jcoa.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vitek.jcoa.R;

import java.util.List;

import static com.vitek.jcoa.ui.activity.gw.GCommitTaskActivity.NUT_NONE;

/**
 * PhotoAdapter
 * Created by Yancy on 2016/9/29.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<String> result;
    private final static String TAG = "PhotoAdapter";
    private final static int NORMAL_TYPE = 0;
    private final static int END_TYPE = 1;
    private OnAddPhotoBTClickListener onAddPhotoBTClickListener;

    public PhotoAdapter(Context context, List<String> result) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.result = result;
    }

    public PhotoAdapter(Context context, List<String> result, OnAddPhotoBTClickListener onAddPhotoBTClickListener) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.result = result;
        this.onAddPhotoBTClickListener = onAddPhotoBTClickListener;
    }

    /**
     * RecyclerView添加Item
     *
     * @param bean
     * @param position
     */
    public void addItem(String bean, int position) {
        result.add(position, bean);
        notifyItemInserted(position);
    }

    /**
     * RecyclerView删除Item
     *
     * @param bean
     */
    public void deleteItem(String bean) {
        if (bean != NUT_NONE) {
            int pos = result.indexOf(bean);
            result.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public int getItemCount() {
        if (result == null || result.size() == 0) {
            return 0;
        }
        return result.size();
    }

    //    @Override
//    public int getItemViewType(int position) {
//        if (getItemCount() == 1 || position == getItemCount() - 1) {
//            return END_TYPE;
//        } else {
//            return NORMAL_TYPE;
//        }
//    }
    protected String getItem(int position) {
        return result.get(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (getItem(position).equals(NUT_NONE)) {
            holder.ivPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.add_img));
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddPhotoBTClickListener.onAddClick(v);
                }
            });
        } else {
            holder.ivPhoto.setOnClickListener(null);
            holder.ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onAddPhotoBTClickListener.onImageLongClick(getItem(position), holder, position);
                    return false;
                }
            });
            Glide.with(context)
                    .load(result.get(position))
                    .centerCrop()
                    .into(holder.ivPhoto);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
        }

    }

    public interface OnAddPhotoBTClickListener {
        void onAddClick(View v);

        void onImageLongClick(String path, ViewHolder holder, int position);
    }

}
/*
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */