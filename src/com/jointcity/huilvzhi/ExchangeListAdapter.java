package com.jointcity.huilvzhi;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ExchangeListAdapter extends BaseAdapter {

	private Context m_context;
	private ArrayList<ExchangeListItem> m_items;
	
	public ExchangeListAdapter(Context context, ArrayList<ExchangeListItem> items) {
		m_context = context;
		m_items = items;
	}
	
	@Override
	public int getCount() {
		return m_items.size();
	}

	@Override
	public ExchangeListItem getItem(int index) {
		return m_items.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		return getItem(pos);
	}

}
