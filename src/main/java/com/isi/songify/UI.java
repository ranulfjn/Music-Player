package com.isi.songify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class UI extends AppCompatActivity  {


    SeekBar seek_bar;
    MediaPlayer player;
    ImageView image,play_pause,prev,next;
    TextView title,artist,currentTime,totalTime;
    Handler seekHandler = new Handler();
    Bitmap imagePlayer;
    int id,length;
    boolean paused = false;
    private  DataBaseHelper db;
    String Title, Artist,currentT,totalT;
    ArrayList<CustomArrayList> songList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i);
        Intent intent=getIntent();

        id=intent.getIntExtra("id",0);

        db = new DataBaseHelper(UI.this);
        try {
            db.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDatabase();
        songList= db.getAppCategoryDetail();
        length=songList.size();
        imagePlayer= songList.get(id-1).getImage();
        Artist=songList.get(id-1).getArtist();
        Title=songList.get(id-1).getSongTitle();

        getInit();


        seekUpdation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();

        switch (id){
            case R.id.playlist: Intent intent= new Intent(this,SongList.class);
                                startActivity(intent);
                                player.stop();
                                return  true;
            case R.id.exit:exit();
                            return  true;

            default:return super.onOptionsItemSelected(item);
        }

    }

    public void getInit()
    {
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);

        currentTime=findViewById(R.id.currentTime);
        totalTime=findViewById(R.id.totalTime);


        play_pause= findViewById(R.id.play_pause);
        //play_pause.setImageResource(R.drawable.play);

        prev= findViewById(R.id.prev);
        prev.setImageResource(R.drawable.back);

        play_pause.setImageResource(R.drawable.pause);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id--;
                if(id<=0){
                    id=length;
                }
               nextPrevSetters();
                player.stop();
                player(id);
                play_pause.setImageResource(R.drawable.pause);
                player.start();
            }
        });

        next=findViewById(R.id.next);
        next.setImageResource(R.drawable.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id++;


                Log.d("len", "value: " + id);
                if (id > length) {
                    id=1;
                }
               nextPrevSetters();
                player.stop();
                player(id);
                play_pause.setImageResource(R.drawable.pause);
                player.start();
            }
        });


        player(id);
        player.start();

        play_pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(paused){
                    player.start();
                    paused=false;
                    play_pause.setImageResource(R.drawable.pause);
                }else {
                    player.pause();
                    paused=true;
                    play_pause.setImageResource(R.drawable.play);
                }
            }

        });

        image= (ImageView) findViewById(R.id.image);
        image.setImageBitmap(imagePlayer);

        title=findViewById(R.id.title);
        title.setText(Title);
        artist= findViewById(R.id.artist);
        artist.setText(Artist);



        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    player.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextPrevSetters();
                player(id);
                player.start();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            player.stop();

            Intent intent = new Intent(UI.this,SongList.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



    Runnable run = new Runnable() {
        @Override public void run() {
            seekUpdation(); }
    };


    public void seekUpdation()
    {
        seek_bar.setProgress(player.getCurrentPosition());
        Message msg= new Message();
        msg.what=player.getCurrentPosition();
        int curr=msg.what;
        currentT=timeLable(curr);
        currentTime.setText(currentT);

        seekHandler.postDelayed(run, 1000);

    }



    public  void player(int i){
        switch (i){
            case 1:player = MediaPlayer.create(this,R.raw.song2);
            break;
            case 2:player = MediaPlayer.create(this,R.raw.song);break;
        }

        seek_bar.setMax(player.getDuration());
        totalT=timeLable(player.getDuration());
        totalTime.setText(totalT);
    }





    public void nextPrevSetters(){

        imagePlayer= songList.get(id-1).getImage();
        image.setImageBitmap(imagePlayer);
        Title=songList.get(id-1).getSongTitle();
        title.setText(Title);
        Artist=songList.get(id-1).getArtist();
        artist.setText(Artist);

    }


    public String timeLable(int duration){
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

    public  void exit(){
        new AlertDialog.Builder(this)
                .setTitle("Exit ")
                .setMessage("Are you sure you want to Exit ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}