package com.sgp.beats;

import com.sgp.beats.util.PlayWithExo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.sgp.beats.models.Songs;
import com.sgp.beats.util.PlayWithExo;

public class PlayMusic extends AppCompatActivity {

    private PlayerView exoPlayer;
    private PlayerControlView controlView;
    private TextView songName,artistName;
    private ImageView coverImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        exoPlayer = findViewById(R.id.exo_player);
        controlView = findViewById(R.id.controls);
        songName = findViewById(R.id.txtSongName);
        artistName = findViewById(R.id.txtArtistName);
        coverImg = findViewById(R.id.coverImg);


        Intent data = getIntent();
        String url = data.getStringExtra("url");
        String song = data.getStringExtra("SongName");
        String artist = data.getStringExtra("ArtistName");

        PlayWithExo playWithExo = new PlayWithExo(exoPlayer,PlayMusic.this, url, false, controlView);

        songName.setText(song);
        artistName.setText(artist);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(data.getStringExtra("albumPic").toString())
                .into(coverImg);




    }


}