package com.jointcity.huilvzhi;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class HuiLvZhi extends Activity {

	private ExchangeListAdapter m_itemsAdapter;
	private ListView m_itemsList;
	private ArrayList<ExchangeListItem> m_exchangeItems;
	private DataSource m_dataSource;
	private static long m_backPressed;
	private ImageButton m_closeBannerButton;
	private static HuiLvZhi sInstance;
	private View m_bannerView;
	private AdView m_adView;
	private static final String SAVE_TAG = "ExchangeItems";

	private void init() {
		sInstance = this;
		m_dataSource = new DataSource(this);
		m_dataSource.open();

		ArrayList<ExchangeItem> exchangeItems = m_dataSource.getAllItems();

		m_itemsList = (ListView) findViewById(R.id.listview_exchange_list);
		registerForContextMenu(m_itemsList);
		m_exchangeItems = new ArrayList<ExchangeListItem>();

		for (int i = 0; i < exchangeItems.size(); ++i) {
			ExchangeListItem eli = new ExchangeListItem(this);
			// order really matters
			eli.setFromData(exchangeItems.get(i).getFromCode(), exchangeItems.get(i).getFromCountry());
			eli.setToData(exchangeItems.get(i).getToCode(), exchangeItems.get(i).getToCountry());
			
			if (!eli.isValid())
				eli.startQuery();
			m_exchangeItems.add(eli);
		}

		m_itemsAdapter = new ExchangeListAdapter(this, m_exchangeItems);
		m_itemsAdapter.notifyDataSetChanged();
		m_itemsList.setAdapter(m_itemsAdapter);

		m_adView = (AdView) findViewById(R.id.adview);
		m_adView.setVisibility(View.VISIBLE);

		AdRequest adRequest = new AdRequest();
		m_adView.loadAd(adRequest);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}	

	public void configOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HuiLvZhi getInstance() {
		return sInstance;
	}

	private void addItem() {
		AddExchangeItemDialog dialog = new AddExchangeItemDialog(this);
		dialog.setOnItemAddedListener(new AddExchangeItemDialog.OnItemAddedListener() {
			@Override
			public void OnItemAdded(String from, String fromCountry, String to, String toCountry) {
				ExchangeListItem eli = new ExchangeListItem(HuiLvZhi.this);
				eli.setFromData(from, fromCountry);
				eli.setToData(to, toCountry);
				eli.startQuery();
				m_exchangeItems.add(eli);
				m_itemsAdapter.notifyDataSetChanged();
				scrollDownList();
			}
		});
		dialog.show();
	}

	private void showAboutInfo() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.title_about);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.about_dialog, null);
		TextView tv = (TextView) v.findViewById(R.id.textview_about_info);
		tv.setText(AddExchangeItemDialog.readStringFromResource(this, R.raw.copyright));
		builder.setView(v);
		final AlertDialog dialog = builder.create();
		Button closeButton = (Button) v.findViewById(R.id.button_close);
		closeButton.setOnClickListener(new View.OnClickListener(){ 
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private void showTips() {
		Intent intent = new Intent(this, TipsActivity.class);
		startActivity(intent);
	}

	private void scrollDownList() {
		m_itemsList.post(new Runnable(){
			public void run() {
				m_itemsList.setSelection(m_itemsList.getCount() - 1);
			}});
	}

	private void queryHistoricalChart(ExchangeListItem eli) {
		Intent intent = new Intent(this, HistoricalChartActivity.class);
		intent.putExtra(Calculator.CURRENCY_FROM_CODE, eli.getFromCode());
		intent.putExtra(Calculator.CURRENCY_TO_CODE, eli.getToCode());
		startActivity(intent);
	}

	public void removeExchangeListItem(final int index) {
		final ExchangeListItem eli = m_exchangeItems.get(index);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setPositiveButton(R.string.text_yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				m_exchangeItems.remove(index);
				m_dataSource.deleteItem(eli.getFromCode(), eli.getToCode());
				m_itemsAdapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton(R.string.text_no, null);
		String msg = getResources().getString(R.string.prompt_delete_item) + eli.getFromCode() + "=>" + eli.getToCode();
		builder.setMessage(msg);
		builder.setTitle(R.string.title_delete_item);
		builder.create().show();
	}

	public void updateInvalidItems() {
		m_adView.loadAd(new AdRequest());
		for (int i = 0; i < m_exchangeItems.size(); ++i) {
			if (!m_exchangeItems.get(i).isValid()) {
				m_exchangeItems.get(i).startQuery();
			}
		}
	}

	public void hideNoNetworkBanner() {
		if (m_bannerView != null) 
			m_bannerView.setVisibility(View.GONE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		configOverflowMenu();
		setContentView(R.layout.activity_hui_lv_zhi);
		init(); 

		if (!isNetworkAvailable()) {
			final ViewStub vs = (ViewStub) findViewById(R.id.viewstub_nonetworkbanner);
			m_bannerView = vs.inflate();
			m_closeBannerButton = (ImageButton) m_bannerView.findViewById(R.id.imagebutton_close);
			m_closeBannerButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					vs.setVisibility(View.GONE);
				}
			});
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		ArrayList<ExchangeListItemData> saveData = new ArrayList<ExchangeListItemData>();
		for (int i = 0; i < m_exchangeItems.size(); ++i) {
			ExchangeListItem eli = m_exchangeItems.get(i);
			ExchangeListItemData data = new ExchangeListItemData(eli.getRate(), eli.getFromFlag(), eli.getToFlag(),
																 eli.getFromCode(), eli.getToCode(), eli.getFromCountryName(), eli.getToCountryName());
			saveData.add(data);
		}
		
		outState.putParcelableArrayList(SAVE_TAG, saveData);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		m_exchangeItems.clear();
		ArrayList<ExchangeListItemData> saveData = savedInstanceState.getParcelableArrayList(SAVE_TAG);
		
		for (int i = 0; i < saveData.size(); ++i) {
			ExchangeListItemData elid = saveData.get(i);
			ExchangeListItem eli = new ExchangeListItem(this);
			eli.setRate(elid.getRate());
			eli.setFromData(elid.getFromCode(), elid.getFromCountryName());
			eli.setToData(elid.getToCode(), elid.getToCountryName());
			m_exchangeItems.add(eli);
		}
		
		m_itemsAdapter.notifyDataSetChanged();
		
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("Tag", "onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("Tag", "onResume");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hui_lv_zhi, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_item:
			addItem();
			break;

		case R.id.action_about:
			showAboutInfo();
			break;

		case R.id.action_tips:
			showTips();
			break;
		}
		return true;
	}

	@Override
	public void onDestroy() {
		m_dataSource.close();
		super.onDestroy();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listview_exchange_list) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.exchange_list_context_menu, menu);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int exchangeItemIndex = info.position;
		ExchangeListItem exchangeItem = m_exchangeItems.get(exchangeItemIndex);

		switch (item.getItemId()) {
		case R.id.action_calculator:
			if (exchangeItem.isValid()) {
				Intent intent = new Intent(HuiLvZhi.this, Calculator.class);
				intent.putExtra(Calculator.CURRENCY_FROM_CODE, exchangeItem.getFromCode());
				intent.putExtra(Calculator.CURRENCY_TO_CODE, exchangeItem.getToCode());
				intent.putExtra(Calculator.RATE, exchangeItem.getRate());
				startActivity(intent);
			} else {
				Toast.makeText(this, R.string.prompt_invalid_rate, Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.action_delete:
			removeExchangeListItem(exchangeItemIndex);
			break;

		case R.id.action_update:
			exchangeItem.startQuery();
			break;

		case R.id.action_historical_chart:
			queryHistoricalChart(exchangeItem);
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if(m_backPressed +2000 > System.currentTimeMillis())
			super.onBackPressed();
		else
			Toast.makeText(getBaseContext(), getResources().getString(R.string.text_double_press_quit),Toast.LENGTH_SHORT).show();
		m_backPressed =System.currentTimeMillis();
	}
}
