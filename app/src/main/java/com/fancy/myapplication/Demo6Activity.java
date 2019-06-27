package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.warkiz.widget.IndicatorSeekBar;

/**
 * @author pengkuanwang
 * @date 2019-06-26
 */
public class Demo6Activity extends Activity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            count++;
            Log.d("Demo6Activity", count+"");
            handler.sendEmptyMessageDelayed(10, 1000);
            super.handleMessage(msg);
        }
    };

    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo6);
        IndicatorSeekBar indicator = findViewById(R.id.indicator);
        handler.sendEmptyMessage(10);


        IndicatorSeekBar.Builder builder = new IndicatorSeekBar.Builder(this);
        builder.build();

        indicator.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                Log.d("Demo6Activity", "progress=" + progress);


            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String tickBelowText, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }
}
