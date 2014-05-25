package com.jointcity.huilvzhi;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DataSource implements Serializable {

	private Context m_context;
	private SQLiteDatabase m_database;
	private DatabaseHelper m_dbHelper;
	
	public DataSource(Context context) {
		m_dbHelper = new DatabaseHelper(context);
		m_context = context;
	}
	
	public void open() throws SQLException {
		m_database = m_dbHelper.getWritableDatabase();
	}
	
	public void close() {
		m_database.close();
	}
	
	public ArrayList<ExchangeItem> getAllItems() {
		ArrayList<ExchangeItem> items = new ArrayList<ExchangeItem>();
		String query = "SELECT " + DatabaseHelper.COLUMN_FROM_COUNTRY + "," + DatabaseHelper.COLUMN_TO_COUNTRY + "," + 
					   DatabaseHelper.COLUMN_REVERSE + " FROM " + DatabaseHelper.TABLE_NAME;
		Cursor cursor = m_database.rawQuery(query, null);
		try {
			cursor.moveToFirst();
			
			while (cursor.moveToNext()) {
				String from = cursor.getString(0);
				String to = cursor.getString(1);
				boolean reversed = cursor.getInt(2) == 0 ? false : true;
				ExchangeItem item = new ExchangeItem(from, to, reversed);
				items.add(item);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		return items;
	}
	
	public void addItem(final String from, final String to) {
		if (hasItem(from, to))
			return;
		
		ContentValues cons = new ContentValues();
		cons.put(DatabaseHelper.COLUMN_FROM_COUNTRY, from);
		cons.put(DatabaseHelper.COLUMN_TO_COUNTRY, to);
		cons.put(DatabaseHelper.COLUMN_REVERSE, 0);
		
		m_database.insert(DatabaseHelper.TABLE_NAME, null, cons);
	}
	
	public boolean hasItem(final String from, final String to) {
		
		String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + 
						DatabaseHelper.COLUMN_FROM_COUNTRY + "='" + from + "' and " +
						DatabaseHelper.COLUMN_TO_COUNTRY + "='" + to + "'";
		
		Cursor cursor = m_database.rawQuery(query, null);
		return cursor.getCount() != 0;
	}
	
	public boolean isItemReversed(final String from, final String to) {
		
		String query = "SELECT " + DatabaseHelper.COLUMN_REVERSE + " FROM " + DatabaseHelper.TABLE_NAME +
					   " WHERE " + DatabaseHelper.COLUMN_FROM_COUNTRY + "='" + from +"' and " + 
					   DatabaseHelper.COLUMN_TO_COUNTRY + "='" + to + "'";
		
		Cursor cursor = m_database.rawQuery(query, null);
		boolean reverserd = false;
		
		try {
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				reverserd = cursor.getInt(0) == 1 ? true : false;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		return reverserd;
	}
	
	public boolean setItemReversed(final String from, final String to, boolean reverse) {
		ContentValues cons = new ContentValues();
		cons.put(DatabaseHelper.COLUMN_REVERSE, reverse ? 1 : 0);
		String clause = DatabaseHelper.COLUMN_FROM_COUNTRY + "=? and " + DatabaseHelper.COLUMN_TO_COUNTRY + "=?";
		String[] args = new String[] { from, to };
		return m_database.update(DatabaseHelper.TABLE_NAME, cons, clause, args) > 0;
	}
	
}
