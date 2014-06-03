package com.jointcity.huilvzhi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

class TypeAdapter extends AbstractWheelTextAdapter {

	private String[] m_types;

	protected TypeAdapter(Context context) {
		super(context);
		m_types = new String[] { context.getResources().getString(R.string.item_year), 
				context.getResources().getString(R.string.item_month), 
				context.getResources().getString(R.string.item_day) }; 
	}

	@Override
	public int getItemsCount() {
		return m_types.length;
	}

	@Override
	protected CharSequence getItemText(int index) {
		return m_types[index];
	}
}

class NumericWheelAdapter extends AbstractWheelTextAdapter {

	/** The default min value */
	public static final int DEFAULT_MAX_VALUE = 9;

	/** The default max value */
	private static final int DEFAULT_MIN_VALUE = 0;

	// Values
	private int minValue;
	private int maxValue;

	// format
	private String format;

	/**
	 * Constructor
	 * @param context the current context
	 */
	public NumericWheelAdapter(Context context) {
		this(context, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}

	/**
	 * Constructor
	 * @param context the current context
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public NumericWheelAdapter(Context context, int minValue, int maxValue) {
		this(context, minValue, maxValue, null);
	}

	/**
	 * Constructor
	 * @param context the current context
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 * @param format the format string
	 */
	public NumericWheelAdapter(Context context, int minValue, int maxValue, String format) {
		super(context);

		this.minValue = minValue;
		this.maxValue = maxValue;
		this.format = format;
	}

	@Override
	public CharSequence getItemText(int index) {
		if (index >= 0 && index < getItemsCount()) {
			int value = minValue + index;
			return format != null ? String.format(format, value) : Integer.toString(value);
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return maxValue - minValue + 1;
	}    
}

public class HistoricalChartActivity extends SlideActivity {

	class ImageDownloader extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... arg0) {
			// This is done in a background thread
			return downloadImage(arg0[0]);
		}

		/**
		 * Called after the image has been downloaded
		 * -> this calls a function on the main thread again
		 */
		protected void onPostExecute(Bitmap image)
		{
			m_chartImageView.setImageBitmap(image);
		}


		/**
		 * Actually download the Image from the _url
		 * @param _url
		 * @return
		 */
		private Bitmap downloadImage(String _url)
		{
			//Prepare to download image
			URL url;        
			BufferedOutputStream out;
			InputStream in;
			BufferedInputStream buf;

			//BufferedInputStream buf;
			try {
				url = new URL(_url);
				in = url.openStream();

				/*
				 * THIS IS NOT NEEDED
				 * 
				 * YOU TRY TO CREATE AN ACTUAL IMAGE HERE, BY WRITING
				 * TO A NEW FILE
				 * YOU ONLY NEED TO READ THE INPUTSTREAM 
				 * AND CONVERT THAT TO A BITMAP
	            out = new BufferedOutputStream(new FileOutputStream("testImage.jpg"));
	            int i;

	             while ((i = in.read()) != -1) {
	                 out.write(i);
	             }
	             out.close();
	             in.close();
				 */

				// Read the inputstream 
				buf = new BufferedInputStream(in);

				// Convert the BufferedInputStream to a Bitmap
				Bitmap bMap = BitmapFactory.decodeStream(buf);
				if (in != null) {
					in.close();
				}
				if (buf != null) {
					buf.close();
				}

				return bMap;

			} catch (Exception e) {
				Log.e("Error reading file", e.toString());
			}

			return null;
		}

	}

	private String m_fromCode;
	private String m_toCode;
	private ZoomableImageView m_chartImageView;
	private WheelView m_typeWheelView;
	private WheelView m_numWheelView;
	private Button m_queryButton;
	private AdView m_adView;
	
	private boolean scrolling = false;
	
	private void init() {
		setContentView(R.layout.historical_chart);

		Intent intent = getIntent();
		m_fromCode = intent.getStringExtra(Calculator.CURRENCY_FROM_CODE);
		m_toCode = intent.getStringExtra(Calculator.CURRENCY_TO_CODE);

		m_chartImageView = (ZoomableImageView) findViewById(R.id.imageview_historical_chart);
		m_typeWheelView = (WheelView) findViewById(R.id.wheelview_type);
		m_typeWheelView.setVisibleItems(3);
		m_numWheelView = (WheelView) findViewById(R.id.wheelview_num);
		m_numWheelView.setCyclic(true);
		m_numWheelView.setViewAdapter(new NumericWheelAdapter(this, 1, 10));
		m_adView = (AdView) findViewById(R.id.adview);
		m_adView.loadAd(new AdRequest());

		TypeAdapter typeAdapter = new TypeAdapter(this);
//		typeAdapter.setTextSize(30);
		m_typeWheelView.setViewAdapter(typeAdapter);

		m_typeWheelView.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateNumbers(newValue);
				}
			}
		});

		m_typeWheelView.addScrollingListener( new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}
			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				updateNumbers(m_typeWheelView.getCurrentItem());
			}
		});
		
		m_queryButton = (Button) findViewById(R.id.button_query);
		m_queryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				queryHistoricalChart();
			}
		});
	}

	private void updateNumbers(int index) {
		switch (index) {
		case 0:
			m_numWheelView.setViewAdapter(new NumericWheelAdapter(this, 1, 10));
			break;

		case 1:
			m_numWheelView.setViewAdapter(new NumericWheelAdapter(this, 1, 12));
			break;

		case 2:
			m_numWheelView.setViewAdapter(new NumericWheelAdapter(this, 1, 100));
			break;
		}
	}

	private void queryHistoricalChart() {
		String type;
		if (m_typeWheelView.getCurrentItem() == 0) {
			type = "y";
		} else if (m_typeWheelView.getCurrentItem() == 1) {
			type = "m";
		} else {
			type = "d";
		}
		
		String num = String.valueOf(m_numWheelView.getCurrentItem() + 1);
		String url = "http://chart.finance.yahoo.com/z?s=" + m_fromCode + m_toCode + "=x&t=" + num + type + "&z=m";
		new ImageDownloader().execute(url);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
}

