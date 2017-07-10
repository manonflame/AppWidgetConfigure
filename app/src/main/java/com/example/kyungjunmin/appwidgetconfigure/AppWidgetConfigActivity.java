package com.example.kyungjunmin.appwidgetconfigure;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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



    int mAppwidgetId =  AppWidgetManager.INVALID_APPWIDGET_ID;
    RemoteViews remoteView;
    AppWidgetManager appWidgetManager;
    Context context;


    //시크바 선언
    SeekBar mSeek;
    //시크바 값을 텍스트로 표현
    TextView mTxt;




    //API
    //String TMONAPI = "https://api-qa.ticketmonster.co.kr/v2/widget/cards";
    String TMONAPI = "https://raw.githubusercontent.com/ChoiJinYoung/iphonewithswift2/master/weather.json";

    //URL 인스턴스 생성
    URL url = null;

    HttpURLConnection conn = null;

    BufferedReader Res = null;

    JSONObject jObj = null;

    JSONArray user = null;

    //10개의 검색어 키워드를 받을 ArrayList<String>





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_widget_config);

        System.out.println("Configure Activity : onCreate()");



        //텍스트뷰를 연결
        mTxt = (TextView)findViewById(R.id.SeekBarText);


        //싴바 연결
        mSeek = ((SeekBar) findViewById(R.id.configSeekBar));

        //시크바 초기값 꺼내오기
        int test = PreferenceManager.getDefaultSharedPreferences(this).getInt("degreeOfTransparency", 0);
        mSeek.setProgress(test);

        //싴바의 값을 보여주는 텍스트뷰도 프리퍼런스에서 가져와서 설정함
        String convertedText = String.valueOf(test);
        mTxt.setText(convertedText);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            mAppwidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        context = getApplicationContext();


        Button Confirm = (Button) findViewById(R.id.confirm);
        Button Cancel = (Button) findViewById(R.id.cancel);

        Confirm.setOnClickListener(this);
        Cancel.setOnClickListener(this);


        remoteView = new RemoteViews(context.getPackageName(),R.layout.activity_app_widget_config);
        appWidgetManager = AppWidgetManager.getInstance(this);


        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String convertedText = String.valueOf(progress);
                mTxt.setText(convertedText);
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
            System.out.println("취소 버튼이 눌렸다");
            setResult(RESULT_CANCELED);
        }
        else if(view.getId() == R.id.confirm){


            System.out.println("확인 버튼이 눌렸다");





            //시크바의 값을 SharedPreferences에 저장
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
            editor.putInt("degreeOfTransparency", mSeek.getProgress());
            editor.apply();
            //시크바 저장 제대로 됐는지 함 봅시다.
            int test = PreferenceManager.getDefaultSharedPreferences(this).getInt("degreeOfTransparency", 0);

            //시크바에서 받은 값
            System.out.println("SP to input : " + mSeek.getProgress());
            //저장된 값
            System.out.println("SP inputed : " + test);









            Intent intent = new Intent(AppWidgetConfigActivity.this, NewAppWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            AppWidgetConfigActivity.this.sendBroadcast(intent);


            Intent resultValue = new Intent();
            System.out.println("확인 버튼이 눌렸다2");
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppwidgetId);
            System.out.println("확인 버튼이 눌렸다3");
            setResult(RESULT_OK, resultValue);
            System.out.println("확인 버튼이 눌렸다4");





        }

        System.out.println("확인 or 취소 마지막 처리 확인");
        finish();
    }



    //API를 파싱 후 전역 ArrayList<String>에 적용할 것이라 리턴값 없음
    private class APITaker extends AsyncTask<Void, Void, Void> {

        //API 받기 전에 progressBar처리
        @Override
        protected void onPreExecute(){
            super.onPreExecute();




        }

        @Override
        protected Void doInBackground(Void... voids) {


            StringBuilder json = null;

            String line = null;

            String rJson = null;

            json = new StringBuilder();
            try{
                url = new URL(TMONAPI);
                conn=(HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                //설정은 다 default로 둘거니까 일단 생략해보자

                Res = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                while((line = Res.readLine())!=null){
                    json.append(line);
                }
                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            rJson = json.toString();
            System.out.println(rJson);

            try{
                jObj = new JSONObject(rJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }








            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            super.onPostExecute(v);

        }
    }

}
