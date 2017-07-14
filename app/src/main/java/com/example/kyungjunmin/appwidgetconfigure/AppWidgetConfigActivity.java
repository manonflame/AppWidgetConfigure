package com.example.kyungjunmin.appwidgetconfigure;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AppWidgetConfigActivity extends AppCompatActivity implements View.OnClickListener {




    /*
        < AppWidgetManager >
            1. AppWidget의 상태를 업데이트함
            2. AppWidgetProvider에 대한 정보와 위젯 상태 정볼르 얻을 수 있음
     */
    int mAppwidgetId =  AppWidgetManager.INVALID_APPWIDGET_ID;

    AppWidgetManager appWidgetManager;




    int initAlpha;
    //시크바 선언
    SeekBar mSeek;
    //시크바 값을 텍스트로 표현
    TextView mTxt;



    LinearLayout sampleAlpha;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀을 없앰
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //xml과 묶음
        setContentView(R.layout.activity_app_widget_config);


        System.out.println("Configure Activity : onCreate()");

        //이미지 뷰에 배경화면을 그림
        ImageView imgView = (ImageView)findViewById(R.id.configBackground);
        imgView.setImageDrawable(WallpaperManager.getInstance(this).getDrawable());





        //텍스트뷰를 연결
        mTxt = (TextView)findViewById(R.id.SeekBarText);

        //싴바 연결
        mSeek = (SeekBar) findViewById(R.id.configSeekBar);

        //컨피그에서 보여 줄 앱 위젯 화면의 투명도
        sampleAlpha =  (LinearLayout) findViewById(R.id.sampleAlpha);

        //시크바 초기값 꺼내오기
        initAlpha = PreferenceManager.getDefaultSharedPreferences(this).getInt("degreeOfTransparency", 0);
        mSeek.setProgress(initAlpha);
        sampleAlpha.setBackgroundColor(Color.argb(initAlpha*2, 255, 255, 255));

        //싴바의 값을 보여주는 텍스트뷰도 프리퍼런스에서 가져와서 설정함
        String convertedText = String.valueOf(initAlpha);
        mTxt.setText(convertedText);


        //뷰의 레이아웃이 바뀔 때 호출되는 콜백 인터페이스
        mSeek.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                int padding = mSeek.getPaddingLeft() + mSeek.getPaddingRight();
                int sPos = mSeek.getLeft() + mSeek.getPaddingLeft();
                int xPos = (mSeek.getWidth() - padding)*initAlpha/mSeek.getMax() + sPos - (mTxt.getWidth()/2);


                mTxt.setX(xPos);
            }
        });



        //앱 위젯ID를 액티비티를 발생시킨 INTENT로 부터 받음
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            mAppwidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }




        //버튼과 리스너 생성
        Button Confirm = (Button) findViewById(R.id.confirm);
        Button Cancel = (Button) findViewById(R.id.cancel);
        Confirm.setOnClickListener(this);
        Cancel.setOnClickListener(this);


        //시크바 리스너
        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String convertedText = String.valueOf(progress);

                //앱 위젯의 프리뷰의 알파값 조정
                int alphaValue = progress;
                mTxt.setText(convertedText);
                int padding = seekBar.getPaddingLeft() + seekBar.getPaddingRight();
                int sPos = seekBar.getLeft() + seekBar.getPaddingLeft();
                int xPos = (seekBar.getWidth() - padding)*seekBar.getProgress()/seekBar.getMax() + sPos - (mTxt.getWidth()/2);
                mTxt.setX(xPos);

                sampleAlpha.setBackgroundColor(Color.argb(alphaValue*2, 255, 255, 255));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.cancel){

            setResult(RESULT_CANCELED);
        }
        else if(view.getId() == R.id.confirm){
            System.out.println("CONFIRM BUTTON PRESSED");


            //시크바의 값을 SharedPreferences에 저장
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
            editor.putInt("degreeOfTransparency", mSeek.getProgress());
            editor.apply();
            Intent intent = new Intent(AppWidgetConfigActivity.this, NewAppWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            AppWidgetConfigActivity.this.sendBroadcast(intent);



            //인텐트 생성해서 넘김
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppwidgetId);
            setResult(RESULT_OK, resultValue);

        }

        finish();
    }

}
