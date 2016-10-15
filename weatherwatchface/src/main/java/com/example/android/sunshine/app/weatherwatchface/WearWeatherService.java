package com.example.android.sunshine.app.weatherwatchface;

/**
 * Created by kishorandroid on 08/10/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class WearWeatherService extends WearableListenerService {

    private static final String TAG = WearWeatherService.class.getSimpleName();

    private static final String WEATHER_PATH = "/weather";
    private static final String WEATHER_INFO_PATH = "/weather_info";

    private static final String KEY_UUID = "uuid";
    private static final String KEY_HIGH = "high";
    private static final String KEY_LOW = "low";
    private static final String KEY_WEATHER_ID = "weatherId";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();
                Log.d(TAG, path);
                String mWeatherHigh = null;
                String mWeatherLow = null;
                int weatherId = -1;
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                if (path.equals(WEATHER_INFO_PATH)) {
                    if (dataMap.containsKey(KEY_HIGH)) {
                        mWeatherHigh = dataMap.getString(KEY_HIGH);
                        Log.d(TAG, "Wear High = " + mWeatherHigh);
                    } else {
                        Log.d(TAG, "Wear What? No high?");
                    }

                    if (dataMap.containsKey(KEY_LOW)) {
                        mWeatherLow = dataMap.getString(KEY_LOW);
                        Log.d(TAG, "Wear Low = " + mWeatherLow);
                    } else {
                        Log.d(TAG, "Wear What? No low?");
                    }

                    if (dataMap.containsKey(KEY_WEATHER_ID)) {
                        weatherId = dataMap.getInt(KEY_WEATHER_ID);
                        Log.d(TAG, "Wear weatherId = " + weatherId);
                    } else {
                        Log.d(TAG, "What? no weatherId?");
                    }
                }
                if (mWeatherHigh != null && mWeatherLow != null && weatherId != -1) {
                    Intent intent = new Intent("weather-data-changed");
                    Bundle bundle = new Bundle();
                    bundle.putString("HIGH", mWeatherHigh);
                    bundle.putString("LOW", mWeatherLow);
                    bundle.putInt("ICON", weatherId);
                    intent.putExtras(bundle);
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                }
            }
        }
    }
}
