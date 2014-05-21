package com.jointcity.huilvzhi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CountryItem extends LinearLayout {

	Context m_context;
	ImageView m_flagImageView;
	TextView m_currencyCodeTextView;
	
	private void init(Context context) {
		m_context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.country_item, this, true);
		
		m_flagImageView = (ImageView) findViewById(R.id.imageview_country_flag);
		m_currencyCodeTextView = (TextView) findViewById(R.id.textview_currency_code);
	}
	
	public CountryItem(Context context) {
		this(context, null);
	}
	
	public CountryItem(Context context, AttributeSet attrset) {
		this(context, attrset, 0);
	}
	
	public CountryItem(Context context, AttributeSet attrset, int defstyle) {
		super(context, attrset, defstyle);
		init(context);
	}
	
	public void setFlag(int flag_id) {
		m_flagImageView.setImageDrawable(getResources().getDrawable(flag_id));
	}
	
	public void setCurrencyCode(CharSequence code) {
		m_currencyCodeTextView.setText(code);
	}
}
