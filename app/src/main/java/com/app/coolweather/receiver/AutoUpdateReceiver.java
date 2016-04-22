package com.app.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReceiver extends BroadcastReceiver {
    public AutoUpdateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    Intent i=new Intent(context,AutoUpdateReceiver.class);
        context.startService(i);
    }
}
