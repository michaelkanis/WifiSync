/*
 * Copyright (c) 2011 Michael Kanis
 * 
 * This file is part of WifiSync.
 *
 * WifiSync is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * WifiSync is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with WifiSync.  If not, see <http://www.gnu.org/licenses/>.
 */

// @ConQAT.Rating GREEN Hash: DC8C0A47067AD0F5C64D9DE93D637954

package net.skweez.wifisync;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * A {@link BroadcastReceiver} that receives WiFi state change broadcasts and
 * changes the auto sync setting accodring to the WiFi state.
 */
public class SyncOnWifiStateReceiver extends BroadcastReceiver {

	/** The log tag. */
	private static String TAG = "WifiSync";

	/** Changes the auto sync setting when WiFi state changes. */
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

	/** Enables auto sync. */
	private void enableSync() {
		ContentResolver.setMasterSyncAutomatically(true);
		Log.d(TAG, "Auto sync enabled.");
	}

	/** Disables auto sync. */
	private void disableSync() {
		ContentResolver.setMasterSyncAutomatically(false);
		Log.d(TAG, "Auto sync disabled.");
	}

}
