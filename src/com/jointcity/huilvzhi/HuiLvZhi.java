package com.jointcity.huilvzhi;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.Toast;

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
		populate();
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

	private void populate() {
		for (int i = 0; i < 10; ++i) {
			ExchangeListItem item = new ExchangeListItem(this);
			item.setFromCode("CNY");
			item.setToCode("USD");
			m_exchangeItems.add(item);
		}
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
		Toast.makeText(this, "about", Toast.LENGTH_LONG).show();
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
