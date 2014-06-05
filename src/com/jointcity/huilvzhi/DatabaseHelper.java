package com.jointcity.huilvzhi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "queryitems.db";
	public static final int DB_VERSION = 4;
	
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_FROM_COUNTRY = "FromColumn";
	public static final String COLUMN_FROM_COUNTRY_NAME = "FromCountryName";
	public static final String COLUMN_TO_COUNTRY = "ToColumn";
	public static final String COLUMN_TO_COUNTRY_NAME = "ToCountryName";
	public static final String TABLE_NAME = "QueryItems";
	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
										   COLUMN_FROM_COUNTRY + " TEXT NOT NULL," + COLUMN_FROM_COUNTRY_NAME + " TEXT NOT NULL," + 
										   COLUMN_TO_COUNTRY + " TEXT NOT NULL," + COLUMN_TO_COUNTRY_NAME + " TEXT NOT NULL" + ")";
	
	
	private Context m_context;
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		m_context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	
}
