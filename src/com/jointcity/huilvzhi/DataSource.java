package com.jointcity.huilvzhi;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DataSource implements Serializable {

	private Context m_context;
	private SQLiteDatabase m_database;
	private DatabaseHelper m_dbHelper;
	private static DataSource sInstance;
	
	public static DataSource getInstance() {
		return sInstance;
	}
	
	public DataSource(Context context) {
		m_dbHelper = new DatabaseHelper(context);
		m_context = context;
		sInstance = this;
	}
	
	public void open() throws SQLException {
		m_database = m_dbHelper.getWritableDatabase();
	}
	
	public void close() {
		m_database.close();
	}
	
	public ArrayList<ExchangeItem> getAllItems() {
		ArrayList<ExchangeItem> items = new ArrayList<ExchangeItem>();
		String query = "SELECT " + DatabaseHelper.COLUMN_FROM_COUNTRY + "," + DatabaseHelper.COLUMN_FROM_COUNTRY_NAME +  "," +
						DatabaseHelper.COLUMN_TO_COUNTRY  + "," + DatabaseHelper.COLUMN_TO_COUNTRY_NAME + 
					   " FROM " + DatabaseHelper.TABLE_NAME;
		Cursor cursor = m_database.rawQuery(query, null);
		try {
			cursor.moveToFirst();
			
			while (cursor.moveToNext()) {
				String from = cursor.getString(0);
				String fromCountry = cursor.getString(1);
				String to = cursor.getString(2);
				String toCountry = cursor.getString(3);
				ExchangeItem item = new ExchangeItem(from, fromCountry, to, toCountry);
				items.add(item);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		return items;
	}
	
	public void addItem(final String from, final String fromCountry, final String to, final String toCountry) {
		if (hasItem(from, to))
			return;
		
		ContentValues cons = new ContentValues();
		cons.put(DatabaseHelper.COLUMN_FROM_COUNTRY, from);
		cons.put(DatabaseHelper.COLUMN_FROM_COUNTRY_NAME, fromCountry);
		cons.put(DatabaseHelper.COLUMN_TO_COUNTRY, to);
		cons.put(DatabaseHelper.COLUMN_TO_COUNTRY_NAME, toCountry);
		
		m_database.insert(DatabaseHelper.TABLE_NAME, null, cons);
	}
	
	public void deleteItem(final String from, final String to) {
		String clause = DatabaseHelper.COLUMN_FROM_COUNTRY + "=? and " + DatabaseHelper.COLUMN_TO_COUNTRY + "=?";
		String[] args = new String[] { from, to };
		m_database.delete(DatabaseHelper.TABLE_NAME, clause, args);
	}
	
	public boolean hasItem(final String from, final String to) {
		
		String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + 
						DatabaseHelper.COLUMN_FROM_COUNTRY + "='" + from + "' and " +
						DatabaseHelper.COLUMN_TO_COUNTRY + "='" + to + "'";
		
		Log.d("hasItem query", query);
		Cursor cursor = m_database.rawQuery(query, null);
		Log.d("count", String.valueOf(cursor.getCount()));
		boolean has = false;
		try {
			cursor.moveToFirst();
			
			while (cursor.moveToNext()) {
				has = true;
				break;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		return has;
	}
}
