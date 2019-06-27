package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

/**
 * @author pengkuanwang
 * @date 2019-06-26
 */
public class Demo6Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo6);
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Demo6Activity", "onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("Demo6Activity", "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("Demo6Activity", "onStopTrackingTouch");
            }
        });
    }
}
