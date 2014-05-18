package com.jointcity.huilvzhi;

import android.content.Context;

public class QueryManager {

	private static QueryManager m_instance;
	
	private QueryManager() {
		
	}
	
	public static QueryManager getInstance() {
		if (m_instance == null) {
			m_instance = new QueryManager();
		}
		return m_instance;
	}
}
