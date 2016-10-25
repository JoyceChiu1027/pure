package com.bupt.chengde.util;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class MediaHandler {
	static MediaHandler mediaHandler;
	AudioManager andioManager;
	Context context;
	HashMap<Integer, Integer> map = new HashMap();
	SoundPool pool;

	private MediaHandler(Context paramContext) {
		this.context = paramContext;
		this.pool = new SoundPool(5, 2, 0);
		this.andioManager = ((AudioManager) paramContext
				.getSystemService("audio"));
	}

	public static MediaHandler getInstance(Context paramContext) {
		if (mediaHandler == null)
			mediaHandler = new MediaHandler(paramContext);
		return mediaHandler;
	}

	public void addSound(int paramInt1, int paramInt2) {
		this.map.put(Integer.valueOf(paramInt1),
				Integer.valueOf(this.pool.load(this.context, paramInt2, 1)));
	}

	public void pause(int paramInt) {
		this.pool.pause(paramInt);
	}

	public int play(int paramInt1, int paramInt2) {
		if (this.map.containsKey(Integer.valueOf(paramInt1))) {
			float f = this.andioManager.getStreamMaxVolume(2);
					// this.andioManager.getStreamVolume(2);
			Log.i("MediaHandler", "play id is " + paramInt1);
			return this.pool.play(((Integer) this.map.get(Integer
					.valueOf(paramInt1))).intValue(), f, f, 1, paramInt2, 1.0F);
		}
		return -1;
	}

	public void play(int paramInt) {
		play(paramInt, 1);
	}

	public void resume(int paramInt) {
		this.pool.resume(paramInt);
	}

	public void stop(int paramInt) {
		this.pool.stop(paramInt);
	}
}
