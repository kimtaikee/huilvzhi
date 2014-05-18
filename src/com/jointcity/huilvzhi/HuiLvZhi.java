package com.jointcity.huilvzhi;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

public class HuiLvZhi extends Activity {

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
		Toast.makeText(this, "add", Toast.LENGTH_LONG).show();
	}
	
	private void editItem() {
		Toast.makeText(this, "edit", Toast.LENGTH_LONG).show();
	}
	
	private void showAboutInfo() {
		Toast.makeText(this, "about", Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		configOverflowMenu();
		setContentView(R.layout.activity_hui_lv_zhi);
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
			editItem();
			break;
			
		case R.id.action_about:
			showAboutInfo();
			break;
		}
		return true;
	}
}
