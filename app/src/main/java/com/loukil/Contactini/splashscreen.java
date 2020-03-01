package com.loukil.Contactini;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread thread = new Thread(){

            @Override
            public void run() {
                try {

                    sleep(2000);
                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(splashscreen.this,TESTTEST.class);
                    startActivity(intent);

                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
