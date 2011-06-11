package net.skweez.wifisync;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.util.Log;

public class SyncOnWifiStateReceiver extends BroadcastReceiver {

	/** The log tag. */
	private static String TAG = "WifiSync";

	@Override
	public void onReceive(final Context context, final Intent intent) {

		final String action = intent.getAction();

		if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {

			final NetworkInfo networkInfo = intent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

			if (networkInfo.getState().equals(State.CONNECTED)) {
				enableSync();
			} else if (networkInfo.getState().equals(State.DISCONNECTED)) {
				disableSync();
			}

		} else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {

			final int wifiState = intent.getIntExtra(
					WifiManager.EXTRA_WIFI_STATE,
					WifiManager.WIFI_STATE_ENABLED);

			if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
				disableSync();
			}

		}
	}

	/**
	 * 
	 */
	private void enableSync() {
		ContentResolver.setMasterSyncAutomatically(true);
		Log.d(TAG, "Auto sync enabled.");
	}

	/**
	 * 
	 */
	private void disableSync() {
		ContentResolver.setMasterSyncAutomatically(false);
		Log.d(TAG, "Auto sync disabled.");
	}

}
