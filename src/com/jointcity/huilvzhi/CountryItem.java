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
	int m_flagId;
	String m_currencyCode;
	
	private void init(Context context) {
		m_context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.country_item, this, true);
		
		m_flagImageView = (ImageView) findViewById(R.id.imageview_country_flag);
		m_currencyCodeTextView = (TextView) findViewById(R.id.textview_currency_code);
	}
	
	private void update() {
		m_flagImageView.setImageDrawable(getResources().getDrawable(m_flagId));
		m_currencyCodeTextView.setText(m_currencyCode);
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
	
	public void setFlag(int flagId) {
		m_flagId = flagId;
		update();
	}
	
	public void setCurrencyCode(CharSequence code) {
		m_currencyCode = String.valueOf(code);
		update();
	}
	
	public String getCurrencyCode() {
		return m_currencyCode;
	}
	
	public void swap(CountryItem other) {
		int tmpId = this.m_flagId;
		this.m_flagId = other.m_flagId;
		other.m_flagId = tmpId;
		
		String tmpCode = this.m_currencyCode;
		this.m_currencyCode = other.m_currencyCode;
		other.m_currencyCode = tmpCode;
		
		update();
	}
}
