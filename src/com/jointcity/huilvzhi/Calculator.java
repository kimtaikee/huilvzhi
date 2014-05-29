package com.jointcity.huilvzhi;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Calculator extends SlideActivity implements OnClickListener {

	private float m_rate = 99;
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
	private Button m_buttonClear;

	private void init() {
		m_codeTextView = (TextView) findViewById(R.id.textview_code);
		m_rateTextView = (TextView) findViewById(R.id.textview_rate);
		m_resultTextView = (TextView) findViewById(R.id.textview_result);
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
		m_buttonClear = (Button) findViewById(R.id.button_clear);

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
		m_buttonClear.setOnClickListener(this);
	}

	private void calcResult() {
		try {
			double num = Double.parseDouble(m_resultTextView.getText().toString());
			double result = num * m_rate;
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

		case R.id.button_clear:
			m_resultTextView.setText("0");
			return;
		}

		m_resultTextView.setText(value);
	}
}
