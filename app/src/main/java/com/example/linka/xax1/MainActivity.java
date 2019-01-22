package com.example.linka.xax1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Random random=new Random();
    private List<Integer> list=new ArrayList<>();
    private LinearLayout linearLayout1, linerLayaout2,linearLayout;
    private Button button, button2;
    private int count=0, score=0, record=0, rand, item1=0, t=21;
    private Intent intent_get;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private TextView textView;
    private CountDownTimer countDownTimer;
    private String img="false";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("savedrecord", 0);
        editor =sharedPreferences.edit();
//        setContentView(R.layout.activity_main);
        linearLayout=new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout1=new LinearLayout(getApplicationContext());
        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        linearLayout1.setBackgroundColor(Color.BLUE);
        linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        linearLayout1.setWeightSum(2);
        linerLayaout2=new LinearLayout(getApplicationContext());
        linerLayaout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        linerLayaout2.setOrientation(LinearLayout.VERTICAL);
        linerLayaout2.setGravity(Gravity.END);
        linerLayaout2.setBackgroundColor(Color.BLUE);
        linerLayaout2.setWeightSum(1);
        button2=new Button(getApplicationContext());
        button2.setText("Restart game");
        button2.setBackgroundColor(Color.GREEN);
        button2.setId(R.id.restart);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                count=0;
                score=0;
                countDownTimer.cancel();
                onDestroy();
                onCreate(savedInstanceState);
            }
        });
        textView= new TextView(getApplicationContext());
        textView.setId(R.id.timerview);
        textView.setTextSize(30);
        textView.setTextColor(Color.YELLOW);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linerLayaout2.addView(button2);
        linerLayaout2.addView(textView);

        intent_get=getIntent();
        record=Integer.valueOf(intent_get.getStringExtra("record"));

        Add(button,linearLayout1);

        linearLayout.addView(linerLayaout2);
        linearLayout.addView(linearLayout1);
        setContentView(linearLayout);
        Collections.sort(list);
        Log.i("list",list.toString());
        if (list.isEmpty()){
            onDestroy();
            onCreate(savedInstanceState);
        }
       setTimer(t);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==list.get(0)){
            score+=view.getId();
            linearLayout1.removeView(view);
            list.remove(0);
            Log.i("list",list.toString());
        }
        else{
            Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.combofalse);
            view.startAnimation(animation);
            count++;
        }
        Log.i("count", String.valueOf(count));
        if (count==2){
            if (record<score) {
                editor.putString("record", String.valueOf(score));
                editor.apply();
                img="true";
            }
            countDownTimer.cancel();
            intent = new Intent(getApplicationContext(),Main2Activity.class);
            if (img.equals("true")){
                intent.putExtra("img", "true");
                Log.i("img","" + true);
            }
            intent.putExtra("score",String.valueOf(score));
            intent.putExtra("record",String.valueOf(record));
            startActivity(intent);
        }
        if (list.isEmpty()){
            list.clear();
            item1=0;
            countDownTimer.cancel();
            Add(button,linearLayout1);
            Collections.sort(list);
            Log.i("list", list.toString());
            if (t!=8) {
                t -= 2;
            }
            setTimer(t);
        }
    }

    private  void Add(Button buttonadd, LinearLayout linearLayoutadd ){
        for (int i=1; i<=5; i++)
        {
            buttonadd=new Button(getApplicationContext());
            buttonadd.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rand=random.nextInt(100);
            for (int item:list) {
                if (rand==item) {
                    item1++;
                }
            }
            if (item1>0){
                rand =random.nextInt(100);
            }
            list.add(rand);
            buttonadd.setText(String.valueOf(rand));
            buttonadd.setBackgroundColor(Color.YELLOW);
            buttonadd.setTextColor(getResources().getColor(R.color.mycolor));
            buttonadd.setId(rand);
            buttonadd.setOnClickListener(this);
            linearLayoutadd.addView(buttonadd);
        }
    }

    private void setTimer(int time){
        countDownTimer = new CountDownTimer(time*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(millisUntilFinished/1000));
            }

            public void onFinish() {
                intent = new Intent(getApplicationContext(), Main2Activity.class);
                if (record<score) {
                    editor.putString("record", String.valueOf(score));
                    editor.apply();
                    img="true";
                }
                intent = new Intent(getApplicationContext(),Main2Activity.class);
                if (img.equals("true")){
                    intent.putExtra("img", "true");
                    Log.i("img","" + true);
                }
                intent.putExtra("score",String.valueOf(score));
                intent.putExtra("record",String.valueOf(record));
                startActivity(intent);
               Log.i("timer", "onFinish");
            }
        }.start();
    }
}
