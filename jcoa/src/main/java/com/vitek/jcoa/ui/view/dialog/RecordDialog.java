package com.vitek.jcoa.ui.view.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.misdk.util.SDCardUtil;
import com.vitek.jcoa.R;

/**
 * @Description:录音的dialog
 * @ClassName: RecordDialog
 * @author:liuyf
 * @Copyright: Copyright(c)jky 2015
 * @version 1.0
 */
public class RecordDialog extends DialogManager implements OnClickListener {
	/**
	 * 麦克风的图标
	 */
	private ImageView micImage;
	/**
	 * 录音时长
	 */
	private TextView timeText;
	/**
	 * 录音工具类
	 */
	private RecorderController recorder;

	/**
	 * 计时器
	 */
	private MyTimer timer;

	private Handler handler;

	public RecordDialog(Context context, String savePath) {
		super(context);
		handler = new Handler();
		recorder = new RecorderController(savePath);
	}

	@Override
	protected View init() {
		View view = View.inflate(context, R.layout.record_dialog, null);
		micImage = (ImageView) view.findViewById(R.id.dialog_img);
		timeText = (TextView) view.findViewById(R.id.tv_record_time);
		this.setStyle(R.style.DialogStyle);
//		view.findViewById(R.id.stop_btn).setOnClickListener(this);
		this.setTouchCancelOutside(false);
		return view;
	}

	/**
	 * 开始录音
	 */
	public void start() {
		if (SDCardUtil.detectAvailable()) {
			show();// 显示dialog
			startTimer();// 开始计时
			recorder.start();
		} else {
			Toast.makeText(context, "SD卡不可用", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 开始计时,并每隔一秒钟将时间回调到主线程
	 */
	private void startTimer() {
		timer = new MyTimer();
		timer.setOnTimeIsUpListener(new MyTimer.onTimeIsUpListener() {

			@Override
			public void onTimeIsUp() {
				updateTime((int) timer.getCurTimeLong() / 1000);
			}
		});
		timer.start();
	}

	/**
	 * 更新界面上时间及声音分贝数
	 */
	private void updateTime(final int time) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				timeText.setText(time + "");
				setMicImage(recorder.getAmplitude());
				if (time >= 60) {
					// ToastMaker.show(context,Type.WARN,"时间到了，将自动停止录音并保存");
					Toast.makeText(context, "时间到了，自动停止录音并保存", Toast.LENGTH_SHORT).show();
					stop();
				}
			}
		});
	}

	/**
	 * 麦克风图片随声音分贝大小切换
	 */
	private void setMicImage(double amplitude) {
		if (0 <= amplitude && amplitude <= 800) {
			micImage.setImageResource(R.drawable.record_animate_01);
		} else if (800 < amplitude && amplitude <= 5000) {
			micImage.setImageResource(R.drawable.record_animate_02);
		} else if (5000 < amplitude && amplitude <= 14000) {
			micImage.setImageResource(R.drawable.record_animate_03);
		} else if (14000 < amplitude && amplitude <= 28000) {
			micImage.setImageResource(R.drawable.record_animate_04);
		} else if (amplitude > 28000) {
			micImage.setImageResource(R.drawable.record_animate_05);
		}
	}

	/**
	 * 停止录音
	 */
	private void stop() {
		timer.stop();// 停止计时
		recorder.stop();// 停止录音并保存录音文件
		dismiss();// dialog消失
//		if (listener != null) {// 将停止事件回调到主线程
//			listener.onStopClick(null);
//		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.stop_btn: // 停止录音按钮点击事件
//			stop();
//			break;
		default:
			break;
		}
	}
	public void onStop() {

	}
}