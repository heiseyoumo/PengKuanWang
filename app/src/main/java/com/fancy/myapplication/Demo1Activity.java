package com.fancy.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author pengkuanwang
 * @date 2019-06-22
 */
public class Demo1Activity extends Activity {
    public String remoteUrl1 = "https://mov.bn.netease.com/open-movie/nos/mp4/2017/05/31/SCKR8V6E9_hd.mp4";
    public String remoteUrl = "https://mov.bn.netease.com/open-movie/nos/mp4/2017/12/04/SD3SUEFFQ_hd.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Demo1Activity.this, VideoViewActivity.class);
                intent.putExtra("url", remoteUrl);
                startActivity(intent);
            }
        });
    }
}
