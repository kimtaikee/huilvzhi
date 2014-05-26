package com.jointcity.huilvzhi;

public class ExchangeItem {
	private String m_fromCode;
	private String m_fromCountryName;
	private String m_toCode;
	private String m_toCountryName;
	
	public final String getFromCode() {
		return m_fromCode;
	}
	
	public final String getToCode() {
		return m_toCode;
	}
	
	public final String getFromCountry() {
		return m_fromCountryName;
	}
	
	public final String getToCountry() {
		return m_toCountryName;
	}
	
	public ExchangeItem(final String from, final String fromCountry, final String to, final String toCountry) {
		m_fromCode = from;
		m_toCode = to;
		m_fromCountryName = fromCountry;
		m_toCountryName = toCountry;
	}
}
