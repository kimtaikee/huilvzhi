package com.jointcity.huilvzhi;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CountryItem extends LinearLayout {

	Context m_context;
	ImageView m_flagImageView;
	TextView m_currencyCodeTextView;
	TextView m_countryNameTextView;
	int m_flagId;
	String m_currencyCode;
	String m_countryName;
	private static final String SEPARATOR = "_";
	
	private void init(Context context) {
		m_context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.country_item, this, true);
		
		m_flagImageView = (ImageView) findViewById(R.id.imageview_country_flag);
		m_currencyCodeTextView = (TextView) findViewById(R.id.textview_currency_code);
		m_countryNameTextView = (TextView) findViewById(R.id.textview_country_name);
	}
	
	public void update() {
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
	}
	
	public int getFlag() {
		return m_flagId;
	}
	
	public void setCurrencyCode(CharSequence code) {
		m_currencyCode = String.valueOf(code);
	}
	
	public String getCurrencyCode() {
		return m_currencyCode;
	}
	 
	public static String getRealName(Context context, CharSequence name) {
		String strName = name.toString();
		int leftUnderscoreIndex = strName.indexOf(SEPARATOR);
		String namePart = TextUtils.substring(name, leftUnderscoreIndex + 1, strName.length());
		int id = context.getResources().getIdentifier(namePart, "string", context.getPackageName());
		String realName = context.getResources().getString(id);
		return realName;
	}
	
	public void setCountryName(CharSequence name) {
		m_countryName = name.toString();
		m_countryNameTextView.setText(getRealName(m_context, name));
	}
	
	public String getCountryName() {
		return m_countryName;
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
