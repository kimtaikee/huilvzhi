package com.jointcity.huilvzhi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

class CountryAdapter extends AbstractWheelTextAdapter {
	
	private Context m_context;
	// Countries names
	private String m_countries[];
	// Countries flags
	private int m_flags[];

	private String capitalize(String ori) {
		StringBuilder rackingSystemSb = new StringBuilder(ori.toLowerCase());
		rackingSystemSb.setCharAt(0, Character.toUpperCase(rackingSystemSb.charAt(0)));
		ori = rackingSystemSb.toString();
		return ori;
	}
	
	private void init() {
		List<String> strlist = Arrays.asList(readStringFromResource(m_context, R.raw.countries).split("\n"));
		Log.d("item count", String.valueOf(strlist.size()));
		
		m_countries = new String[strlist.size()];
		for (int i = 0; i < strlist.size(); ++i)
			m_countries[i] =  capitalize(strlist.get(i));
		
		m_flags = new int[strlist.size()];
		for (int i = 0; i < strlist.size(); ++i) 
			m_flags[i] = m_context.getResources().getIdentifier(strlist.get(i), "drawable", m_context.getPackageName());
	}
	/**
	 * Constructor
	 */
	protected CountryAdapter(Context context) {
		super(context, R.layout.country_layout, NO_RESOURCE);
		m_context = context;
		setItemTextResource(R.id.country_name);
		init();
	}

	static public String readStringFromResource(Context ctx, int resourceID) {
		StringBuilder contents = new StringBuilder();
		String sep = System.getProperty("line.separator");

		try {			
			InputStream is = ctx.getResources().openRawResource(resourceID);

			BufferedReader input =  new BufferedReader(new InputStreamReader(is), 1024*8);
			try {
				String line = null; 
				while (( line = input.readLine()) != null){
					contents.append(line);
					contents.append(sep);
				}
			}
			finally {
				input.close();
			}
		}
		catch (FileNotFoundException ex) {
			Log.e("CountryAdapter", "Couldn't find the file " + resourceID  + " " + ex);
			return null;
		}
		catch (IOException ex){
			Log.e("CountryAdapter", "Error reading file " + resourceID + " " + ex);
			return null;
		}

		return contents.toString();
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		View view = super.getItem(index, cachedView, parent);
		ImageView img = (ImageView) view.findViewById(R.id.flag);
		img.setImageResource(m_flags[index]);
		return view;
	}

	@Override
	public int getItemsCount() {
		return m_countries.length;
	}

	@Override
	protected CharSequence getItemText(int index) {
		return m_countries[index];
	}
}

public class AddExchangeItemDialog extends Dialog {

	private Context m_context;
	private WheelView m_srcCountryWheelView;
	private WheelView m_dstCountryWheelView;
	private CountryAdapter m_srcCountryAdapter;
	private CountryAdapter m_dstCountryAdapter;
	private Button m_cancelButton;
	private Button m_okButton;

	private View.OnClickListener m_clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.button_cancel) {
				dismiss();
			} else if (v.getId() == R.id.button_ok) {
				dismiss();
			}
		}
	};

	private void init() {
		m_srcCountryAdapter = new CountryAdapter(m_context);
		m_dstCountryAdapter = new CountryAdapter(m_context);

		m_srcCountryWheelView = (WheelView) findViewById(R.id.wheelview_src_country);
		m_srcCountryWheelView.setViewAdapter(m_srcCountryAdapter);
		m_srcCountryWheelView.setVisibleItems(3);

		m_dstCountryWheelView = (WheelView) findViewById(R.id.wheelview_dst_country);
		m_dstCountryWheelView.setViewAdapter(m_dstCountryAdapter);
		m_dstCountryWheelView.setVisibleItems(3);

		m_cancelButton = (Button) findViewById(R.id.button_cancel);
		m_cancelButton.setOnClickListener(m_clickListener);
		m_okButton = (Button) findViewById(R.id.button_ok);
		m_okButton.setOnClickListener(m_clickListener);

		setTitle(R.string.dialog_title_add_exchange_item);
	}

	public AddExchangeItemDialog(Context context) {
		super(context);
		m_context = context;
		setContentView(R.layout.add_exchange_item_dialog);
		init();
	}
}
