package net.skweez.wifisync;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

public class SyncOnWifiStateReceiver extends BroadcastReceiver {

	/** The log tag. */
	private static String TAG = "WifiSync";

	@Override
	public void onReceive(final Context context, final Intent intent) {

		final String action = intent.getAction();

		if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {

			final NetworkInfo networkInfo = (NetworkInfo) intent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

			if (networkInfo.getState().equals(State.CONNECTED)) {
				ContentResolver.setMasterSyncAutomatically(true);
				Log.d(TAG, "Auto sync enabled.");
			}

		} else if (action
				.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {

			final boolean connected = intent.getBooleanExtra(
					WifiManager.EXTRA_SUPPLICANT_CONNECTED, true);

			if (connected == false) {
				ContentResolver.setMasterSyncAutomatically(false);
				Log.d(TAG, "Auto sync disabled.");
			}

		} else {

			Log.d(TAG, "action: " + action);
			debugExtras(intent);

		}
	}

	/**
	 * @param intent
	 */
	private void debugExtras(final Intent intent) {
		Bundle extras = intent.getExtras();
		for (String key : extras.keySet()) {
			Log.d(TAG, key + "=" + extras.get(key));
		}
	}
}
