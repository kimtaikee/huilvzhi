package com.jointcity.huilvzhi;

import android.os.Bundle;
import android.widget.TextView;

public class TipsActivity extends SlideActivity {

	private TextView m_tipsTextView;
	
	private void init() {
		m_tipsTextView = (TextView) findViewById(R.id.textview_tips);
		String tips = AddExchangeItemDialog.readStringFromResource(this, R.raw.tips);
		m_tipsTextView.setText(tips);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips);
		init();
	}
}
