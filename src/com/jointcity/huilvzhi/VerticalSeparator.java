package com.jointcity.huilvzhi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class VerticalSeparator extends LinearLayout {
	public VerticalSeparator(Context context) {
		this(context, null);
	}

	public VerticalSeparator(Context context, AttributeSet attrset) {
		this(context, attrset, 0);
	}

	public VerticalSeparator(Context context, AttributeSet attrset, int defstyle) {
		super(context, attrset, defstyle);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.vertical_separator, this, true);
	}
}
