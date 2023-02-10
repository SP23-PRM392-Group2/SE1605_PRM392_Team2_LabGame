package com.example.se1605_prm392_team2_labgame;

import android.widget.EditText;
import android.widget.SeekBar;

public class Horse {
    private String name;
    private SeekBar seekBar;
    private EditText editText;

    public Horse() {
    }

    public Horse(String name, SeekBar seekBar, EditText editText) {
        this.name = name;
        this.seekBar = seekBar;
        this.editText = editText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}
