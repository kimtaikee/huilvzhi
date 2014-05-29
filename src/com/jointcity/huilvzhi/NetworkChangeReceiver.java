package com.jointcity.huilvzhi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm.getActiveNetworkInfo()!= null && cm.getActiveNetworkInfo().isConnected()) {
			HuiLvZhi.getInstance().updateInvalidItems();
			HuiLvZhi.getInstance().hideNoNetworkBanner();
		}
	}

}
