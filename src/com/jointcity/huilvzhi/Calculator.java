package com.jointcity.huilvzhi;

import java.math.BigDecimal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Calculator extends SlideActivity implements OnClickListener {

	public static final String CURRENCY_FROM_CODE = "CurrencyFromCode";
	public static final String CURRENCY_TO_CODE = "CurrencyToCode";
	public static final String RATE = "Rate";
	
	private float m_rate;
	private float m_converseRate;
	private String m_fromCode;
	private String m_toCode;
	private boolean m_isExchanged;
	private TextView m_codeTextView;
	private TextView m_rateTextView;
	private TextView m_resultTextView;
	private Button m_buttonOne;
	private Button m_buttonTwo;
	private Button m_buttonThree;
	private Button m_buttonFour;
	private Button m_buttonFive;
	private Button m_buttonSix;
	private Button m_buttonSeven;
	private Button m_buttonEight;
	private Button m_buttonNine;
	private Button m_buttonZero;
	private Button m_buttonEqual;
	private Button m_buttonDot;
	private ImageButton m_exchangeButton;
	private Vibrator m_vibrator;

	private class GestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			m_resultTextView.setText("0");
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	GestureDetector m_gestureDetector = new GestureDetector(new GestureListener());
	
	View.OnTouchListener m_touchListener = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			m_gestureDetector.onTouchEvent(event);
			return true;
		}
	};

	private void init() {
		m_isExchanged = false;
		
		m_codeTextView = (TextView) findViewById(R.id.textview_code);
		m_rateTextView = (TextView) findViewById(R.id.textview_rate);
		m_resultTextView = (TextView) findViewById(R.id.textview_result);
		m_resultTextView.setOnTouchListener(m_touchListener);
		
		m_buttonOne = (Button) findViewById(R.id.button_one);
		m_buttonTwo = (Button) findViewById(R.id.button_two);
		m_buttonThree = (Button) findViewById(R.id.button_three);
		m_buttonFour = (Button) findViewById(R.id.button_four);
		m_buttonFive = (Button) findViewById(R.id.button_five);
		m_buttonSix = (Button) findViewById(R.id.button_six);
		m_buttonSeven = (Button) findViewById(R.id.button_seven);
		m_buttonEight = (Button) findViewById(R.id.button_eight);
		m_buttonNine = (Button) findViewById(R.id.button_nine);
		m_buttonZero = (Button) findViewById(R.id.button_zero);
		m_buttonEqual = (Button) findViewById(R.id.button_equal);
		m_buttonDot = (Button) findViewById(R.id.button_dot);
		m_exchangeButton = (ImageButton) findViewById(R.id.button_exchange);
		m_exchangeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				exchange();
			}
		});

		m_buttonOne.setOnClickListener(this);
		m_buttonTwo.setOnClickListener(this);
		m_buttonThree.setOnClickListener(this);
		m_buttonFour.setOnClickListener(this);
		m_buttonFive.setOnClickListener(this);
		m_buttonSix.setOnClickListener(this);
		m_buttonSeven.setOnClickListener(this);
		m_buttonEight.setOnClickListener(this);
		m_buttonNine.setOnClickListener(this);
		m_buttonZero.setOnClickListener(this);
		m_buttonEqual.setOnClickListener(this);
		m_buttonDot.setOnClickListener(this);

		m_buttonOne.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonTwo.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonThree.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonFour.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonFive.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonSix.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonSeven.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonEight.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonNine.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonZero.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonEqual.playSoundEffect(android.view.SoundEffectConstants.CLICK);
		m_buttonDot.playSoundEffect(android.view.SoundEffectConstants.CLICK);

		Intent intent = getIntent();
		m_rate = Float.parseFloat(intent.getStringExtra(RATE));
		
		BigDecimal b = new BigDecimal(1/m_rate);
		m_converseRate = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		
		m_rateTextView.setText(String.valueOf(m_rate));
		m_fromCode = intent.getStringExtra(CURRENCY_FROM_CODE);
		m_toCode = intent.getStringExtra(CURRENCY_TO_CODE);
		
		final String combinedCode = m_fromCode + "=>" + m_toCode; 
		m_codeTextView.setText(combinedCode);
		
		m_vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	private void exchange() {
		m_isExchanged = !m_isExchanged;
		
		String combinedCode;
		if (m_isExchanged) {
			combinedCode = m_toCode + "=>" + m_fromCode;
			m_rateTextView.setText(String.valueOf(m_converseRate));
		} else {
			combinedCode = m_fromCode + "=>" + m_toCode;
			m_rateTextView.setText(String.valueOf(m_rate));
		}
		
		m_codeTextView.setText(combinedCode);
	}

	private void calcResult() {
		try {
			double num = Double.parseDouble(m_resultTextView.getText().toString());
			double result = 0;
			if (m_isExchanged) 
				result = num * m_converseRate;
			else 
				result = num * m_rate;
				
			BigDecimal b = new BigDecimal(result);
			result = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			m_resultTextView.setText(String.valueOf(result));
		} catch (Exception exp) {
			exp.printStackTrace();
			m_resultTextView.setText("0");
		} 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
		init();
	}

	private String format(String value, String num) {
		if (TextUtils.equals(value, "0")) {
			return num;
		} else {
			String newValue;
			newValue = value + num;
			return newValue;
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		String value = m_resultTextView.getText().toString();
		m_vibrator.vibrate(50);

		switch (id) {
		case R.id.button_one:
			value = format(value, "1");
			break;

		case R.id.button_two:
			value = format(value, "2");
			break;

		case R.id.button_three:
			value = format(value, "3");
			break;

		case R.id.button_four:
			value = format(value, "4");
			break;

		case R.id.button_five:
			value = format(value, "5");
			break;

		case R.id.button_six:
			value = format(value, "6");
			break;

		case R.id.button_seven:
			value = format(value, "7");
			break;

		case R.id.button_eight:
			value = format(value, "8");
			break;

		case R.id.button_nine:
			value = format(value, "9");
			break;

		case R.id.button_zero:
			if (TextUtils.equals(value, "0"))
				return;
			value += "0";
			break;

		case R.id.button_equal:
			calcResult();
			return;

		case R.id.button_dot:
			String value2 = m_resultTextView.getText().toString();
			if (value2.contains(".") || value2.isEmpty()) 
				return;
			else {
				value2 += ".";
				m_resultTextView.setText(value2);
				return;
			}
		}

		m_resultTextView.setText(value);
	}
}
