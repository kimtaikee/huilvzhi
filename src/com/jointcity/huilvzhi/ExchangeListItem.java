package com.jointcity.huilvzhi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ExchangeListItem extends LinearLayout {

	private Context m_context;
	private ImageButton m_deleteButton;
	private CountryItem m_srcCountryItem;
	private CountryItem m_dstCountryItem;
	private ProgressBar m_pgsBar;
	private TextView m_rateTextView;
	private class Querier extends AsyncTask <String, Integer, String>{

		private String m_rate;
		@Override
		protected String doInBackground(String... params) {
			try {
				String mergeStr = m_srcCountryItem.getCurrencyCode() + m_dstCountryItem.getCurrencyCode();
				URL yahooFinance = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22" + mergeStr + "%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
				HttpURLConnection urlConnection = (HttpURLConnection) yahooFinance.openConnection();
				urlConnection.setDoOutput(true);
				String line;
				StringBuilder builder = new StringBuilder();
				InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
				BufferedReader reader = new BufferedReader(isr);
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				JSONObject json = new JSONObject(builder.toString());
				m_rate = json.getJSONObject("query").getJSONObject("results").getJSONObject("rate").getString("Rate");
			}catch (Exception exp) {
				exp.printStackTrace();
			}
			return m_rate;
		}
	}

	private View.OnClickListener m_deleteListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(m_context, "Delete", Toast.LENGTH_LONG).show();
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
		m_pgsBar = (ProgressBar) findViewById(R.id.pgsbar);
		m_rateTextView = (TextView) findViewById(R.id.textview_rate);
		m_rateTextView.setTypeface(Typeface.DEFAULT_BOLD);
	}

	private void showProgressBar(boolean show) {
		m_pgsBar.setVisibility(show ? View.VISIBLE : View.GONE);
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

	void setFromData(String code, String countryName) {
		m_srcCountryItem.setFlag(m_context.getResources().getIdentifier(countryName, "drawable", m_context.getPackageName()));
		m_srcCountryItem.setCurrencyCode(code);
	}
	
	void setToData(String code, String countryName) {
		m_dstCountryItem.setFlag(m_context.getResources().getIdentifier(countryName, "drawable", m_context.getPackageName()));
		m_dstCountryItem.setCurrencyCode(code);
	}
	
	public String getFromCode() {
		return m_srcCountryItem.getCurrencyCode();
	}
	
	public String getToCode() {
		return m_dstCountryItem.getCurrencyCode();
	}
	
	public void setEditable(boolean editable) {
		m_deleteButton.setVisibility(editable ? View.VISIBLE : View.GONE);
	}

	public void startQuery() {
		showProgressBar(true);

		try {
			m_rateTextView.setText(new Querier().execute().get());
			showProgressBar(false);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
