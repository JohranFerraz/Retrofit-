package com.example.localizao;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class BD extends MainActivity {
    private static final String TAG = "Activity2";
    private Object MainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MainActivity);


        TextView texto = findViewById(Texto);
        texto.setText(getIntent().getStringExtra("extra"));
        handler.sendEmptyMessageDelayed(0, 3000);

        Log.d(TAG,"onCreate");
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:{
                    Intent intent = new Intent();
                    intent.putExtra("resultado", "meu resultado de execução");
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    private class MainActivity {
    }

    private class Texto {
    }
}
