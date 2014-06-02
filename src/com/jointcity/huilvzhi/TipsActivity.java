package com.jointcity.huilvzhi;

import android.os.Bundle;
import android.widget.TextView;

public class TipsActivity extends SlideActivity {

	private TextView m_tipsTextView;
	
	private void init() {
		m_tipsTextView = (TextView) findViewById(R.id.textview_tips);
		m_tipsTextView.setText("Calculator: double tap the result area to erase it");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips);
		init();
	}
}
