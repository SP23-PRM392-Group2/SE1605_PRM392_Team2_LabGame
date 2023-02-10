package com.example.se1605_prm392_team2_labgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mySound;
    ImageButton btnVolume;

    @Override
    protected void onStop() {
        super.onStop();
        mySound.release();
        mySound = null;
    }

    public void switchScreen(View v) {
        Intent intent = new Intent(MainActivity.this, RacingActivity.class);
        intent.putExtra("isMuted", btnVolume.getTag().toString());
        startActivity(intent);
        mySound.release();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        btnVolume = (ImageButton) findViewById(R.id.btnVolume1);

        imageView.setBackgroundResource(R.drawable.horse);
        ((AnimationDrawable) imageView.getBackground()).start();
        if (mySound == null) {
            createSound();
        }

        mySound.start();

        btnVolume.setOnClickListener(v -> {
            if (mySound != null && mySound.isPlaying()) {
                mySound.pause();
                btnVolume.setTag("1");
                btnVolume.setImageResource(R.drawable.baseline_volume_off_24);
            } else {
                if (mySound == null) {
                    createSound();
                }
                mySound.start();
                btnVolume.setTag("0");
                btnVolume.setImageResource(R.drawable.baseline_volume_up_24);
            }
        });
    }

    private void createSound() {
        mySound = MediaPlayer.create(this, R.raw.r1);
        mySound.setLooping(true);
    }
}