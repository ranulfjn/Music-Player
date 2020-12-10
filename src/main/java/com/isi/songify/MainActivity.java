package com.isi.songify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread =  new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5000);
                    Intent intent= new Intent(MainActivity.this,SongList.class);
                    startActivity(intent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        thread.start();


    }
}