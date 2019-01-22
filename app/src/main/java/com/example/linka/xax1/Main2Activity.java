package com.example.linka.xax1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private TextView scoredig, recorddig;
    private Button button;
    private String scdig, rcdig, img=null;
    private SharedPreferences sharedPreferences;
    private ImageView imageView;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        scoredig=findViewById(R.id.scoredig);
        recorddig=findViewById(R.id.recorddig);
        imageView=findViewById(R.id.recordimage);
        imageView.setVisibility(View.INVISIBLE);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myscale);
        button=findViewById(R.id.startnew);
        button.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("savedrecord",0);
        rcdig = sharedPreferences.getString("record", "0");
        Intent intent_get=getIntent();
        scdig=intent_get.getStringExtra("score");
        img=intent_get.getStringExtra("img");

        if (img!=null){
            Log.i("img", "setVisibility");
            imageView.setVisibility(View.VISIBLE);
            imageView.setAnimation(animation);
        }

        if (scdig!=null) {
            scoredig.setText(scdig);
        }
        recorddig.setText(rcdig);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("record",rcdig);
        startActivity(intent);
    }
}
