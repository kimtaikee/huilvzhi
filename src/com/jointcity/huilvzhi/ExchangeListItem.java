package com.jointcity.huilvzhi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ExchangeListItem extends LinearLayout {

	private Context m_context;
	private CountryItem m_srcCountryItem;
	private CountryItem m_dstCountryItem;
	private ProgressBar m_pgsBar;
	private TextView m_rateTextView;
	private String m_rate;
	
	private class Querier extends AsyncTask <String, Integer, String>{

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
				m_rate = getResources().getString(R.string.na);
				exp.printStackTrace();
			}
			return m_rate;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				if (m_rate.isEmpty())
					m_rateTextView.setText(m_context.getResources().getString(R.string.info_no_rate));
				else 
					m_rateTextView.setText(m_rate);

			} catch (Exception exp) {
				exp.printStackTrace();
			}

			showProgressBar(false);

		}
	}

	private View.OnClickListener m_deleteListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
//			HuiLvZhi.getInstance().removeExchangeListItem(ExchangeListItem.this.m_index);
		}
	};

	private void init(Context context) {
		m_context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.exchange_list_item, this, true);

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

	public void setFromData(String code, String countryName) {
		m_srcCountryItem.setFlag(m_context.getResources().getIdentifier(countryName, "drawable", m_context.getPackageName()));
		m_srcCountryItem.setCurrencyCode(code);
		m_srcCountryItem.setCountryName(countryName);
	}

	public void setToData(String code, String countryName) {
		m_dstCountryItem.setFlag(m_context.getResources().getIdentifier(countryName, "drawable", m_context.getPackageName()));
		m_dstCountryItem.setCurrencyCode(code);
		m_dstCountryItem.setCountryName(countryName);
	}

	public String getFromCode() {
		return m_srcCountryItem.getCurrencyCode();
	}

	public String getToCode() {
		return m_dstCountryItem.getCurrencyCode();
	}
	
	public boolean isValid() {
		String rate = m_rateTextView.getText().toString();
		boolean valid = !rate.isEmpty() && !TextUtils.equals(rate, getResources().getString(R.string.info_no_rate));
		return valid;
	}

	public String getRate() {
		return m_rate;
	}

	public void startQuery() {
		showProgressBar(true);
		m_rateTextView.setText("");
		new Querier().execute();
	}
}
