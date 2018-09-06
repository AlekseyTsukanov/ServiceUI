package com.acukanov.serviceui;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceSimpleUi extends Service {
    private static final String LOG_TAG = ServiceSimpleUi.class.getCanonicalName();
    //private TestView view;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private LayoutInflater mInflater;

    public ServiceSimpleUi() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(LOG_TAG, "service onCreate");
        //view = new TestView(this);
        mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        /*mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );*/
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.OPAQUE
        );
        mLayoutParams.width = (int) dipToPixels(this, 200);
        mLayoutParams.height = (int) dipToPixels(this, 200);
        mLayoutParams.gravity = Gravity.CENTER;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        View view = mInflater.inflate(R.layout.activity_main, null);
        //mWindowManager.addView(view, mLayoutParams);
        final int[] counter = {0};

        Button btnClick = view.findViewById(R.id.btn_click);
        final TextView textTicker = view.findViewById(R.id.text_ticker);
        textTicker.setText(getResources().getString(R.string.text_ticker, counter[0]));
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0]++;
                textTicker.setText(getResources().getString(R.string.text_ticker, counter[0]));
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopSelf();
            }
        }, 10000);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(LOG_TAG, "Service has been destroyed");
    }

    private float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
