package com.acukanov.serviceui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Settings.canDrawOverlays(this)) {
            Intent intent2 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent2, 0);
        } else {
            getApplicationContext().startService(new Intent(getApplicationContext(), ServiceSimpleUi.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 0:
                getApplicationContext().startService(new Intent(getApplicationContext(), ServiceSimpleUi.class));
                finish();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
