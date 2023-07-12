package com.example.newversion;


import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;


public class AppUpdateService extends Service {

    private static final int REQUEST_CODE_INSTALL_PERMISSION = 1001;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkAndRequestInstallPermission();
        return START_NOT_STICKY;
    }

    private void checkAndRequestInstallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasPermission = getPackageManager().canRequestPackageInstalls();

            if (!hasPermission) {
                Intent permissionIntent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                permissionIntent.setData(Uri.parse("package:" + getPackageName()));
                permissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(permissionIntent);
            } else {
                performAppUpdate();
            }
        } else {
            performAppUpdate();
        }
    }

    private void performAppUpdate() {

        stopSelf();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
