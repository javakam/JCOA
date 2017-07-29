package com.vitek.jcoa.ui.view.dialog;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
	public long minTime = 2000;
	private double time = 0; 
	private boolean switcher = true;
	private long period=1000;
	
	private onTimeIsUpListener timeUpListener;
	
	public interface onTimeIsUpListener {
		public void onTimeIsUp();
	} 
	
	
	public void setOnTimeIsUpListener(onTimeIsUpListener l) {
		this.timeUpListener = l;
	}
	
	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			if (switcher) {
				time+=period;
				if (timeUpListener!=null) {
					if (!(getCurTimeLong()<minTime)) {
						timeUpListener.onTimeIsUp();
						return;
					}
				}
			}
		}
		
	}

	
	public MyTimer() {
	}
	
	public MyTimer(long period,long minTime) {
		this.period = period;
		this.minTime = minTime;
	}
	public void start() {
		Log.i("MyTimer", "timer is starting");
		Timer t = new Timer();
		time = 0;
		switcher = true;
		t.schedule(new MyTimerTask(), 0, period);
	}
	
	
	public double stop() {
		Log.i("MyTimer", String.format(" the timer is stoping and time==%s", time));
		time = 0;
		switcher = false;
		return time;
	}
	
	public double getCurTimeLong() {
		Log.i("MyTimer", String.format(" current time is time==%s", time));
		return time;
	}
	
	public void setMinTime(long time) {
		this.minTime = time;
	}
	
	public long getMinTime() {
		return minTime;
	}

	
}
