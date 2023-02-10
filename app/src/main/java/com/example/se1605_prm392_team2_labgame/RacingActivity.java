package com.example.se1605_prm392_team2_labgame;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RacingActivity extends AppCompatActivity {
    List<Horse> horses = new ArrayList<>();
    int[] progress = {10000, 10000, 10000};
    Button btnPlay;
    Button btnPlayAgain;
    Button btnClear;
    TextView tvMoney;
    TextView tvBet;
    TextView tvResult;
    private MediaPlayer mySound;
    ImageButton btnVolume;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_racing);

        btnPlay = findViewById(R.id.btnPlay);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnClear = findViewById(R.id.btnClear);
        btnVolume = findViewById(R.id.btnVolume);

        tvMoney = findViewById(R.id.tvMoney);
        tvBet = findViewById(R.id.tvBet);
        tvResult = findViewById(R.id.tvResult);

        horses.add(new Horse("Horse Red",
                findViewById(R.id.sbRed),
                findViewById(R.id.etRed)));
        horses.add(new Horse("Horse Green",
                findViewById(R.id.sbGreen),
                findViewById(R.id.etGreen)));
        horses.add(new Horse("Horse Blue",
                findViewById(R.id.sbBlue),
                findViewById(R.id.etBlue)));

        horses.forEach(item -> item.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (getTextViewInput(horses.get(0).getEditText()) + getTextViewInput(horses.get(1).getEditText()) + getTextViewInput(horses.get(2).getEditText())
                            > getTextViewInput(tvMoney)) {
                        item.getEditText().setError("You don't have enough money!");
                        item.getEditText().requestFocus();
                        btnPlay.setEnabled(false);
                    } else {
                        tvBet.setText(String.valueOf(getTextViewInput(horses.get(0).getEditText()) + getTextViewInput(horses.get(1).getEditText()) + getTextViewInput(horses.get(2).getEditText())));
                        btnPlay.setEnabled(true);
                    }
                } catch (Exception ex) {
                }
            }
        }));

        btnPlayAgain.setAlpha(0);
        horses.forEach((item -> {
            item.getSeekBar().setMax(10000);
            item.getSeekBar().setProgress(9500);
            item.getSeekBar().setOnTouchListener((v, event) -> true);
        }));

        mySound = MediaPlayer.create(this, R.raw.r1);
        mySound.setLooping(true);
        if ("1".equals(getIntent().getExtras().get("isMuted").toString())) {
            btnVolume.setImageResource(R.drawable.baseline_volume_off_24);
        } else {
            mySound.start();
        }
        btnVolume.setOnClickListener(v -> {
            if (mySound.isPlaying()) {
                mySound.pause();
                btnVolume.setImageResource(R.drawable.baseline_volume_off_24);
            } else {
                mySound.start();
                btnVolume.setImageResource(R.drawable.baseline_volume_up_24);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mySound.release();
    }


    public void startGame(View v) {
        init();
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                int index = 0;
                for (Horse horse : horses) {
                    int random = new Random().nextInt(100);
                    progress[index] -= random;
                    if (progress[index] < 0 && tvResult.getText().toString().equals("")) {
                        int currentBalance = getTextViewInput(tvMoney)
                                - getTextViewInput(tvBet)
                                + getTextViewInput((TextView) horses.get(index).getEditText()) * 2;
                        tvResult.setText(horses.get(index).getName() + " won.");
                        tvMoney.setText(String.valueOf(currentBalance));
                    }
                    horse.getSeekBar().setProgress(progress[index++]);
                }
                if (progress[0] < 0 && progress[1] < 0 && progress[2] < 0) {
                    btnPlayAgain.setAlpha(1);
                    t.cancel();
                    horses.forEach((item -> {
                        ((AnimationDrawable) item.getSeekBar().getThumb()).stop();
                    }));
                }
            }
        }, 0, 50);
    }

    private void clearBet() {
        tvBet.setText("0");
        horses.forEach(item -> item.getEditText().setText("0"));
    }

    public void clearBet(View v) {
        clearBet();
    }

    public void restartGame(View v) {
        horses.forEach((item -> {
            item.getSeekBar().setProgress(9500);
            item.getEditText().setEnabled(true);
        }));

        String tempBalance = tvMoney.getText().toString();
        tvMoney.setText("100");
        clearBet();
        tvMoney.setText(tempBalance);

        tvResult.setText("");
        btnPlay.setAlpha(1);
        btnClear.setAlpha(1);
        btnPlayAgain.setAlpha(0);

    }

    private int getTextViewInput(TextView textView) {
        try {
            return (Integer.parseInt(textView.getText().toString()));
        } catch (Exception ex) {
            return 0;
        }
    }

    private void init() {
        for (int i = 0; i < 3; i++) {
            progress[i] = 9500;
        }
        btnPlay.setAlpha(0);
        btnClear.setAlpha(0);
        horses.forEach((item -> {
            ((AnimationDrawable) item.getSeekBar().getThumb()).start();
            item.getEditText().setEnabled(false);
        }));
    }
}
