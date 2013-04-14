package com.ks.android;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.StateListDrawable;

public class HighLightRowDrawable extends StateListDrawable {

	private final PaintDrawable mColor;

	public HighLightRowDrawable(int color) {
		mColor = new PaintDrawable(color);
		initialize();
	}

	private void initialize() {
		Drawable color = mColor;
		Drawable selected = new ColorDrawable(Color.TRANSPARENT);
		addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled, android.R.attr.state_window_focused }, selected);
		addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled, android.R.attr.state_window_focused, android.R.attr.state_selected }, selected);
		addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_window_focused, android.R.attr.state_selected }, selected);
		addState(new int[] {}, color);
	}

	public void setColor(int color) {
		mColor.getPaint().setColor(color);
		mColor.invalidateSelf();
	}

}
