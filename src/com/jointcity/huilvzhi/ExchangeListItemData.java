package com.jointcity.huilvzhi;

import android.R.integer;
import android.os.Parcel;
import android.os.Parcelable;

public class ExchangeListItemData implements Parcelable {

	private String m_rate;
	private int m_fromFlag;
	private int m_toFlag;
	private String m_fromCode;
	private String m_toCode;
	private String m_fromCountryName;
	private String m_toCountryName;
	
	public ExchangeListItemData(String rate, int fromFlag, int toFlag, 
								 String fromCode, String toCode, 
								 String fromCountryName, String toCountryName) {
		m_rate = rate;
		m_fromFlag = fromFlag;
		m_toFlag = toFlag;
		m_fromCode = fromCode;
		m_toCode = toCode;
		m_fromCountryName = fromCountryName;
		m_toCountryName = toCountryName;
	}
	
	public ExchangeListItemData(Parcel source) {
		m_rate = source.readString();
		m_fromFlag = source.readInt();
		m_toFlag = source.readInt();
		m_fromCode = source.readString();
		m_toCode = source.readString();
		m_fromCountryName = source.readString();
		m_toCountryName = source.readString();
	}
	
	public String getRate() {
		return m_rate;
	}
	
	public int getFromFlag() {
		return m_fromFlag;
	}
	
	public int getToFlag() {
		return m_toFlag;
	}
	
	public String getFromCode() {
		return m_fromCode;
	}
	
	public String getToCode() {
		return m_toCode;
	}
	
	public String getFromCountryName() {
		return m_fromCountryName;
	}
	
	public String getToCountryName() {
		return m_toCountryName;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel obj, int flags) {
		obj.writeString(m_rate);
		obj.writeInt(m_fromFlag);
		obj.writeInt(m_toFlag);
		obj.writeString(m_fromCode);
		obj.writeString(m_toCode);
		obj.writeString(m_fromCountryName);
		obj.writeString(m_toCountryName);
	}
	
	public static final Parcelable.Creator<ExchangeListItemData> CREATOR = new Creator<ExchangeListItemData>() {
		
		@Override
		public ExchangeListItemData[] newArray(int size) {
			return new ExchangeListItemData[size];
		}
		
		@Override
		public ExchangeListItemData createFromParcel(Parcel source) {
			return new ExchangeListItemData(source);
		}
	};
}
