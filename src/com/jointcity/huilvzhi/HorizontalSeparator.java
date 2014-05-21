package com.jointcity.huilvzhi;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class HorizontalSeparator extends LinearLayout {
	
	public HorizontalSeparator(Context context) {
		this(context, null);
	}
	
	public HorizontalSeparator(Context context, AttributeSet attrset) {
		this(context, attrset, 0);
	}
	
	public HorizontalSeparator(Context context, AttributeSet attrset, int defstyle) {
		super(context, attrset, defstyle);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.horizontal_separator, this, true);
	}
}
