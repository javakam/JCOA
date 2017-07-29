package com.vitek.jcoa.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.vitek.jcoa.R;

public abstract class DialogManager {

	protected CharSequence title, message, positiveText, negativeText;
	protected Context context;
	protected CharSequence[] negativeTexts;
	protected OnClickListener negativeListener, positiveListener;
	protected static Dialog mDialog;
	protected int gravity = Gravity.CENTER;
	protected int style = R.style.FullHeightDialog;
	protected int height = -2;
	protected int width = -2;
	protected int animationID = android.R.style.Animation_Dialog;
	protected View contentView;
	private Dialog dialog;
	protected float SCALE_X;

	protected float SCALE_Y;

	protected float DENSITY;

	protected int SCREEN_WIDTH;

	protected int SCREEN_HEIGHT;

	protected int DENSITY_DPI;
	public static boolean isPad = false;
	public static float SCALE_X_ALL;

	public boolean touchCancelOutside = true;

	public void setTouchCancelOutside(boolean touchCancelOutside) {
		this.touchCancelOutside = touchCancelOutside;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public DialogManager(Context context) {
		this.context = context;
	}

	public DialogManager setDialogTitle(CharSequence title) {
		this.title = title;
		return this;
	}

	public DialogManager setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	public DialogManager setNegativeButton(CharSequence negativeText,
			OnClickListener l) {
		this.negativeListener = l;
		this.negativeText = negativeText;
		return this;
	}

	public DialogManager setNegativeButtons(OnClickListener l,
			CharSequence... negativeTexts) {
		this.negativeListener = l;
		this.negativeTexts = negativeTexts;
		return this;
	}

	public DialogManager setPositiveButton(CharSequence positiveText,
			OnClickListener l) {
		this.positiveListener = l;
		this.positiveText = positiveText;
		return this;
	}

	protected Dialog create() {
		initUiParams();
		contentView = init();
		dialog = new Dialog(context, style);
		dialog.setContentView(contentView);
		dialog.setTitle(title);
		dialog.setCanceledOnTouchOutside(touchCancelOutside);
		Window window = dialog.getWindow();
		window.setWindowAnimations(animationID);
		int width = getScreenWidth(context);
		LayoutParams params = window.getAttributes();
		params.width = width;
		if (height != 0) {
			params.height = this.height;
		}
		// dialog.setOnDismissListener(listener);
		params.gravity = this.gravity;
		params.width = this.width;
		mDialog = dialog;
		window.setAttributes(params);
		return dialog;
	}

	public void show() {
		this.create().show();
	}

	public void dismiss() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	public class DismissListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			dismiss();
		}

		public void dismiss() {
			DialogManager.this.dismiss();
		}

	}

	protected abstract View init();

	private void initUiParams() {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		SCREEN_WIDTH = metrics.widthPixels;
		SCREEN_HEIGHT = metrics.heightPixels;
		if (SCREEN_WIDTH > SCREEN_HEIGHT) {
			int flag = SCREEN_WIDTH;
			SCREEN_WIDTH = SCREEN_HEIGHT;
			SCREEN_HEIGHT = flag;
		}
		DENSITY = context.getResources().getDisplayMetrics().density;
		DENSITY_DPI = context.getResources().getDisplayMetrics().densityDpi;
		double length = Math.sqrt(SCREEN_WIDTH * SCREEN_WIDTH + SCREEN_HEIGHT
				* SCREEN_HEIGHT)
				/ (160 * DENSITY);
		if (length > 6.0f) {
			isPad = true;
		} else {
			isPad = false;
		}
		if (isPad) {
			SCALE_X = 1;
		} else {
			SCALE_X = SCREEN_WIDTH / 480f;
		}
		SCALE_X_ALL = SCREEN_WIDTH / 480f;
		SCALE_Y = SCREEN_HEIGHT / 800f;
	}

	public View findViewById(int id) {
		return contentView.findViewById(id);
	}

	public void viewByVisable(int visable, View... views) {
		if (views != null && views.length > 0) {
			for (View v : views) {
				v.setVisibility(visable);
			}
		}
	}

	public void goneView(View... views) {
		viewByVisable(View.GONE, views);
	}

	public void showView(View... views) {
		viewByVisable(View.VISIBLE, views);
	}

	public void inVisable(View... views) {
		viewByVisable(View.INVISIBLE, views);
	}

	private OnDismissListener listener = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface dialog) {
			onDismissing();
		}
	};

	protected void onDismissing() {

	};

	/**
	 * 获取屏幕宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getApplicationContext().getResources()
				.getDisplayMetrics().widthPixels;
	}

}
