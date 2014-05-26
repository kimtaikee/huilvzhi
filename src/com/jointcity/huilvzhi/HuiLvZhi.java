package com.jointcity.huilvzhi;

import java.lang.reflect.Field;
import java.security.PublicKey;
import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ListView;

public class HuiLvZhi extends Activity {

	private ExchangeListAdapter m_itemsAdapter;
	private ListView m_itemsList;
	private ArrayList<ExchangeListItem> m_exchangeItems;
	private boolean m_isInEditMode;
	private DataSource m_dataSource;

	private void init() {
		m_dataSource = new DataSource(this);
		m_dataSource.open();
		
		ArrayList<ExchangeItem> exchangeItems = m_dataSource.getAllItems();
		Log.d("Exchange Item Count", String.valueOf(exchangeItems.size()));
		
		m_isInEditMode = false;
		m_itemsList = (ListView) findViewById(R.id.listview_exchange_list);
		m_exchangeItems = new ArrayList<ExchangeListItem>();
		
		for (int i = 0; i < exchangeItems.size(); ++i) {
			ExchangeListItem eli = new ExchangeListItem(this);
			// order really matters
			eli.setFromFlag(exchangeItems.get(i).getFromCountry());
			eli.setFromCode(exchangeItems.get(i).getFromCode());
			eli.setToFlag(exchangeItems.get(i).getToCountry());
			eli.setToCode(exchangeItems.get(i).getToCode());
			m_exchangeItems.add(eli);
		}
		Log.d("populated item count:", String.valueOf(m_exchangeItems.size()));
		m_itemsAdapter = new ExchangeListAdapter(this, m_exchangeItems);
		m_itemsAdapter.notifyDataSetChanged();
		m_itemsList.setAdapter(m_itemsAdapter);
	}

	private void setItemsEditable(boolean editable) {
		for (int i = 0; i < m_exchangeItems.size(); ++i) 
			m_exchangeItems.get(i).setEditable(editable);
	}

	private void updateMenuItemTitle(MenuItem item) {
		if (m_isInEditMode) 
			item.setTitle(R.string.action_finish_item);
		else 
			item.setTitle(R.string.action_edit_item);
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

	private void addItem() {
		AddExchangeItemDialog dialog = new AddExchangeItemDialog(this);
		dialog.show();
	}

	private void editItem() {
		m_isInEditMode = !m_isInEditMode;
		setItemsEditable(m_isInEditMode);
	}

	private void showAboutInfo() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.title_about);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.about_dialog, null);
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		configOverflowMenu();
		setContentView(R.layout.activity_hui_lv_zhi);
		init();
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

		case R.id.action_edit_item: 
		{
			editItem();
			updateMenuItemTitle(item);
		}
		break;

		case R.id.action_about:
			showAboutInfo();
			break;
		}
		return true;
	}
	
	@Override
	public void onDestroy() {
		m_dataSource.close();
		super.onDestroy();
	}
}
