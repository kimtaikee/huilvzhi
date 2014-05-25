package com.jointcity.huilvzhi;

public class ExchangeItem {
	private String m_fromCode;
	private String m_toCode;
	private boolean m_reversed;
	
	public final String getFromCode() {
		return m_fromCode;
	}
	
	public final String getToCode() {
		return m_toCode;
	}
	
	public boolean isReversed() {
		return m_reversed;
	}
	
	public ExchangeItem(final String from, final String to, final boolean reversed) {
		m_fromCode = from;
		m_toCode = to;
		m_reversed = reversed;
	}
}
