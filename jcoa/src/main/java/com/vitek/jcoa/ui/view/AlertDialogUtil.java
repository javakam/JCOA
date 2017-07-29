package com.vitek.jcoa.ui.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by javakam on 2017/6/6 0006.
 */
public class AlertDialogUtil {

    private Context context;
    private DialogInterface.OnClickListener onClickListener;
    private int type = 0;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    public AlertDialogUtil(Context context, DialogInterface.OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
    }

    // dialog
    public AlertDialog getAlertDialog(@NonNull int type, boolean cancelable) {
        this.type = type;
        if (type == 0) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("提示")
                    .setCancelable(cancelable)
                    .setItems(new String[]{"添加意见", "添加图片", "归档", "转交下一部门"}
                            , onClickListener);
        } else if (type == 1) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("提示")
                    .setCancelable(cancelable)
                    .setItems(new String[]{"查看详情"}, onClickListener);
        }//等等等
        if (alertDialog == null) {
            alertDialog = builder.create();
            alertDialog.show();
        }
        return alertDialog;
    }

    public void hideAlertDialog() {
        if (alertDialog != null)
            alertDialog.hide();
    }

    public void showAlertDialog() {
        if (alertDialog != null)
            alertDialog.show();
    }

    public void dismissAlertDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
}
