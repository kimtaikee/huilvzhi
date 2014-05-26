package com.jointcity.huilvzhi;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ExchangeListItem extends LinearLayout {

	private Context m_context;
	private ImageButton m_deleteButton;
	private CountryItem m_srcCountryItem;
	private CountryItem m_dstCountryItem;

	private View.OnClickListener m_deleteListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// do delete stuff
			Toast.makeText(m_context, "Delete", Toast.LENGTH_LONG).show();
		}
	};
	private View.OnClickListener m_exchangeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// do exchange stuff
		}
	};
	private View.OnClickListener m_calcListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// do calc stuff
		}
	};

	private void init(Context context) {
		m_context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.exchange_list_item, this, true);

		m_deleteButton = (ImageButton) findViewById(R.id.button_delete);
		m_deleteButton.setOnClickListener(m_deleteListener);

		m_srcCountryItem = (CountryItem) findViewById(R.id.countryitem_src_currency);
		m_dstCountryItem = (CountryItem) findViewById(R.id.countryitem_dst_currency);
	}

	public ExchangeListItem(Context context) {
		this(context, null);
	}

	public ExchangeListItem(Context context, AttributeSet attrset) {
		this(context, attrset, 0);
	}

	public ExchangeListItem(Context context, AttributeSet attrset, int defstyle) {
		super(context, attrset, defstyle);
		init(context);
	}

	public void setFromCode(String code) {
		m_srcCountryItem.setCurrencyCode(code);
	}

	public void setToCode(String code) {
		m_dstCountryItem.setCurrencyCode(code);
	}
	
	public void setFromFlag(String countryName) {
		Log.d("from flag", countryName);
		m_srcCountryItem.setFlag(m_context.getResources().getIdentifier(countryName, "drawable", m_context.getPackageName()));
	}
	
	public void setToFlag(String countryName) {
		Log.d("to flag", countryName);
		m_dstCountryItem.setFlag(m_context.getResources().getIdentifier(countryName, "drawable", m_context.getPackageName()));
	}

	public void setEditable(boolean editable) {
		m_deleteButton.setVisibility(editable ? View.VISIBLE : View.GONE);
	}
}
