package com.example.localizao;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends BD {
    private static final String TAG = "MainActivity";

    Context ctx;
    private boolean finishing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ctx = this;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            @@ -67,7 +81,7 @@ public void onClick(View v) {
                public void onClick(View v) {
                    Intent intent = new Intent(ctx.getApplicationContext(), MainActivity.class);
                    intent.putExtra("extra", "Extra novamente passado a partir activity principal");
                    startActivityForResult(intent,324);
                    startActivityForResult(intent, 324);
                }
            });

            @@ -79,7 +93,12 @@ public void onClick(View v) {
            }
        });

        Log.d(TAG,"onCreate");
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        ctx.registerReceiver();

        Log.d(TAG, "onCreate");
    }

    @Override
    @@ -92,31 +111,38 @@ protected <onStart, Inten> void onActivityResult(int requestCode, int resultCode, @Nullable Inten
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        Log.d(TAG, "onPause");
        if(isFinishing()){
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        Log.d(TAG, "onDestroy");
    }

    @Override
    @@ -140,4 +166,73 @@ public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("AirplaneMode", "Service state changed. Action:  " + intent.getAction());


            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                Log.e(TAG, "" + intent.getBooleanExtra("state", false));
                if (intent.getBooleanExtra("state", false)) {
                    createNotificationChannel();
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(ctx, "ID_DO_CANAL")
                                    .setSmallIcon(R.drawable.baseline_android_black_18)
                                    .setContentTitle("Minha notificação")
                                    .setContentText("Minha primeira notificação");
                    Intent resultIntent = new Intent(ctx, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());
                }
            }
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Object value = bundle.get(key);
                    Log.d(TAG, String.format("%s %s (%s)", key,
                            value.toString(), value.getClass().getName()));
                }
            }
        }
    };

    private void createNotificationChannel() {
        CharSequence name = "Canal";
        String description = "Descrição";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("ID_DO_CANAL", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    public void setFinishing(boolean finishing) {
        this.finishing = finishing;
    }
}