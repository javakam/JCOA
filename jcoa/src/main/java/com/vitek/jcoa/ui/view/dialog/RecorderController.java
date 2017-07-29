package com.vitek.jcoa.ui.view.dialog;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * @Description:录音控制器
 * @ClassName: Recorder
 * @author:abao
 * @Copyright: Copyright(c)jky 2015
 */
public class RecorderController {

	private static final String TAG = "MyRecorder";
	private MediaRecorder mRecorder;
	private String savePath;

	public RecorderController(String path) {
		mRecorder = new MediaRecorder();
		this.savePath = path;
		initRecord();
		try {
			mRecorder.prepare();
		} catch (IllegalStateException e) {
			Log.i(TAG, e.toString());
		} catch (IOException e) {
			Log.i(TAG, e.toString());
		}

		Log.i(TAG, savePath);
	}

	/**
	 * 开始录音
	 */
	public void start() {
		if (mRecorder == null) {
			initRecord();
			try {
				mRecorder.prepare();
			} catch (IllegalStateException e) {
				Log.i(TAG, e.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mRecorder.start();
	}

	/**
	 * 停止录音
	 */
	public void stop() {
		if (mRecorder == null) {
			return;
		}
		try {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			Log.i(TAG, "record is stoped");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取声音分贝
	 * 
	 * @return
	 */
	public double getAmplitude() {
		if (mRecorder != null) {
			return (mRecorder.getMaxAmplitude());
		} else
			return 0;
	}

	/**
	 * 初始化MediaRecorder
	 */
	private void initRecord() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置音频源
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB); // 设置输出文件格式
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 设置音频编码器
		mRecorder.setOutputFile(savePath);// 设置输出文件名
	}

	/**
	 * 获取保存路径
	 * 
	 * @return
	 */
	public String getSavePath() {
		return savePath;
	}

}
