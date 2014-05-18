package com.jointcity.huilvzhi;

import android.R.integer;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExchangeListItem extends LinearLayout {

	private Context m_context;
	private Button m_deleteButton;
	private Button m_exchangeButton;
	private Button m_calcButton;
	private View.OnClickListener m_deleteListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// do delete stuff
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
		
		m_deleteButton = (Button) findViewById(R.id.button_delete);
		m_deleteButton.setOnClickListener(m_deleteListener);
		
		m_exchangeButton = (Button) findViewById(R.id.button_exchange);
		m_exchangeButton.setOnClickListener(m_exchangeListener);
		
		m_calcButton = (Button) findViewById(R.id.button_calc);
		m_calcButton.setOnClickListener(m_calcListener);
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
	
}
