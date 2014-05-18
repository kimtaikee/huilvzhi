package com.jointcity.huilvzhi;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "queryitems.db";
	public static final int DB_VERSION = 1;
	
	private Context m_context;
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		m_context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	
}
