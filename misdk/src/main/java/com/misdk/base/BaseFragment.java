package com.misdk.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.misdk.util.ToastUtil;
import com.misdk.views.loadding.CustomDialog;


/**
 * Fragment基类
 * <p>Created by kam on 2016/6/25.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * Fragment backstack
     */
    public static final String FM_BACKSTACK = "FM_BACKSTACK";

    public Context mContext;
    public LayoutInflater mInflater;
    private CustomDialog dialog;//进度条

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mInflater = LayoutInflater.from(mContext);

        //此处可以给Fragment设置主题
        //mContext = new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light);
    }

    public void onMsgObtain(Message msg) {
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            onMsgObtain(msg);
        }
    };

    /**
     * @return the handler
     */
    public Handler getHandler() {
        return handler;
    }

    public void showShortToast(String text) {
        ToastUtil.shortToast(mContext, text);
    }

    public void showLongToast(String text) {
        ToastUtil.longToast(mContext, text);
    }

    public void goIntent(Context context, Class<?> cla) {
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
    }

    //=============================Dialog================================//
// dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(getActivity());
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
//        getDialog().show();
        if (dialog != null || !dialog.isShowing())
            dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 处理回退事件<p>
     * 对于导航条上RadioGroup+Fragment实现的切换，无视此方法 ；
     * 其他Fragment需要自己处理返回键问题
     * （注）并且需要在onResume()里面调用才会生效
     */
    public void handleBackEvent() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back event
                    getFragmentManager().popBackStack(FM_BACKSTACK,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }
}
