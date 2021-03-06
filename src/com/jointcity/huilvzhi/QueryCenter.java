package com.jointcity.huilvzhi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class QueryCenter {

	private static QueryCenter m_instance;
	private Context m_context;
	
	private QueryCenter() {
		
	}
	
	public static QueryCenter getInstance() {
		if (m_instance == null) {
			m_instance = new QueryCenter();
		}
		return m_instance;
	}
	
	void setContext(Context context) {
		m_context = context;
	}
	
	public boolean isNetworkDown() {
		ConnectivityManager cm = (ConnectivityManager) m_context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}
}
