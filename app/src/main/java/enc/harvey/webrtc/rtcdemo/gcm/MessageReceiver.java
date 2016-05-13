package enc.harvey.webrtc.rtcdemo.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import enc.harvey.webrtc.rtcdemo.utils.Constants;

/**
 * Created by harold on 10/05/2016.
 */
public class MessageReceiver extends WakefulBroadcastReceiver {
    private final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "==============================================================================");
        JSONObject message = new JSONObject();
        for (String key: bundle.keySet()) {
            Log.d (TAG, "KEY: " + key + " : " + bundle.get(key));
            try {
                message.put(key, bundle.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "==============================================================================");
        final String callerId = bundle.getString(Constants.KEY_CALLER_ID);
        if (callerId != null) {
            Intent broadcastIntent = new Intent("GCMMessage");
            broadcastIntent.putExtra(Constants.KEY_CALLER_ID, callerId);
            LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        }
        if (message.toString() != null && !message.toString().trim().equals("")) {
            Intent broadcastIntent = new Intent("gcmMsgReceiver");
            broadcastIntent.putExtra(Constants.KEY_JSON_MSG, message.toString());
            LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        }
    }
}
