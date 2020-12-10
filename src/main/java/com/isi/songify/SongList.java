package com.isi.songify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class SongList extends AppCompatActivity {

    ArrayList<CustomArrayList> songList = new ArrayList<>();
    ListView listView;
    private  DataBaseHelper db;
    customAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        listView = findViewById(R.id.listView);

        //songList.add(new CustomArrayList(R.drawable.ic_launcher_background, "Live Before I die",   "song"));

        db = new DataBaseHelper(SongList.this);
        try {
            db.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDatabase();
        songList= db.getAppCategoryDetail();
        customAdapter = new customAdapter(SongList.this, songList);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Activity", "Click");

               // Bundle b = new Bundle();
               // b.putStringArrayList("list",songList);
                Intent intent = new Intent(SongList.this, UI.class);

               // intent.putExtras(b);
              /*  intent.putExtra("songTitle", songList.get(position).getArtist());
                intent.putExtra("image",songList.get(position).getImage());*/
                intent.putExtra("id",songList.get(position).getId());
                startActivity(intent);

            }
        });
    }


}