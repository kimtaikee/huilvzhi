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
	private static final String SEPARATOR = "_";
	
	private void init(Context context) {
		m_context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.country_item, this, true);
		
		m_flagImageView = (ImageView) findViewById(R.id.imageview_country_flag);
		m_currencyCodeTextView = (TextView) findViewById(R.id.textview_currency_code);
		m_countryNameTextView = (TextView) findViewById(R.id.textview_country_name);
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
	 
	public static String getRealName(CharSequence name) {
		String strName = name.toString();
		int leftUnderscoreIndex = strName.indexOf(SEPARATOR);
		String namePart = TextUtils.substring(name, leftUnderscoreIndex + 1, strName.length());
		String[] parts = namePart.split(SEPARATOR);
		String realName = new String();
		for (String part : parts) { 
			if (TextUtils.equals(part, "and"))
				realName += "& ";
			else
				realName += part.substring(0, 1).toUpperCase() + part.substring(1) + " ";
		}
		
		return realName;
	}
	
	public void setCountryName(CharSequence name) {
		m_countryNameTextView.setText(getRealName(name));
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
