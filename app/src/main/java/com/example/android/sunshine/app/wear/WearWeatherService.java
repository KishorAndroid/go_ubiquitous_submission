package com.example.android.sunshine.app.wear;

/**
 * Created by kishorandroid on 08/10/16.
 */

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.android.sunshine.app.sync.SunshineSyncAdapter;
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
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                if (path.equals(WEATHER_INFO_PATH)) {
                    if (dataMap.containsKey(KEY_HIGH)) {
                        String mWeatherHigh = dataMap.getString(KEY_HIGH);
                        Log.d(TAG, "High = " + mWeatherHigh);
                    } else {
                        Log.d(TAG, "What? No high?");
                    }

                    if (dataMap.containsKey(KEY_LOW)) {
                        String mWeatherLow = dataMap.getString(KEY_LOW);
                        Log.d(TAG, "Low = " + mWeatherLow);
                    } else {
                        Log.d(TAG, "What? No low?");
                    }

                    if (dataMap.containsKey(KEY_WEATHER_ID)) {
                        int weatherId = dataMap.getInt(KEY_WEATHER_ID);

                    } else {
                        Log.d(TAG, "What? no weatherId?");
                    }
                }
                if (path.equals(WEATHER_PATH)) {
                    SunshineSyncAdapter.syncImmediately(this);
                }
            }
        }
    }
}
