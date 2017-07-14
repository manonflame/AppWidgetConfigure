package com.example.kyungjunmin.appwidgetconfigure;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    //API로부터 받은 검색어 타이틀을 저장
    ArrayList<String> newRanking;


    //API
    String TMONAPI = "https://api-qa.ticketmonster.co.kr/v2/widget/cards";


    //API관련 인스턴스 생성
    URL url = null;
    HttpsURLConnection conn = null;
    BufferedReader Res = null;
    JSONObject jObj = null;
    APITaker apiTaker = null;




    private String strOPEN_CONFIG = "android.action.OPEN_CONFIG";


    //초기 리모트뷰를 보여주기 위한 메서드
    void initUpdateAppWidget(Context context, AppWidgetManager appWidgetManager,
                             int appWidgetId) {

        System.out.println("initUpdateAppWidget()");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setViewVisibility(R.id.getAPIprogress, View.VISIBLE);
        views.setViewVisibility(R.id.ranking, View.GONE);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    //파싱 후의 리모트 뷰를 보여주기 위한 메서드
    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        System.out.println("updateAppWidget()");

        //리모트뷰 객체 생성 및 비저빌러티 변경
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setViewVisibility(R.id.getAPIprogress, View.GONE);
        views.setViewVisibility(R.id.ranking, View.VISIBLE);


        //리프레시 이벤트
        Intent refresh = new Intent("android.appwidget.action.APPWIDGET_REFRESH");
        PendingIntent pRefresh = PendingIntent.getBroadcast(context, 0, refresh, 0);
        views.setOnClickPendingIntent(R.id.refresh, pRefresh);


        //버튼에 이벤트 부여
        Intent it = new Intent(context, AppWidgetConfigActivity.class);
        PendingIntent openConfig = PendingIntent.getActivity(context, 0, it, 0);
        views.setOnClickPendingIntent(R.id.configure, openConfig);

        int id[] = {R.id.firstWeb, R.id.secondWeb, R.id.thirdWeb, R.id.fourthWeb, R.id.fifthWeb, R.id.sixthWeb, R.id.seventhWeb, R.id.eighthWeb, R.id.ninethWeb, R.id.tenthWeb};

        for(int i = 0 ; i < newRanking.size() ; i++){
            views.setTextViewText(id[i], newRanking.get(i));
        }
        for(int i = newRanking.size() ; i < 10 ; i++ ){
            views.setTextViewText(id[i], " ");
        }

        Intent it1 = new Intent(context, WebActivity.class);
        Intent it2 = new Intent(context, WebActivity.class);
        Intent it3 = new Intent(context, WebActivity.class);
        Intent it4 = new Intent(context, WebActivity.class);
        Intent it5 = new Intent(context, WebActivity.class);
        Intent it6 = new Intent(context, WebActivity.class);
        Intent it7 = new Intent(context, WebActivity.class);
        Intent it8 = new Intent(context, WebActivity.class);
        Intent it9 = new Intent(context, WebActivity.class);
        Intent it10 = new Intent(context, WebActivity.class);
        Intent intentArray[] = { it1, it2, it3, it4, it5, it6, it7, it8, it9, it10 };

        PendingIntent pit1 = null;
        PendingIntent pit2 = null;
        PendingIntent pit3 = null;
        PendingIntent pit4 = null;
        PendingIntent pit5 = null;
        PendingIntent pit6 = null;
        PendingIntent pit7 = null;
        PendingIntent pit8 = null;
        PendingIntent pit9 = null;
        PendingIntent pit10 = null;
        PendingIntent pItArr[] = { pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10 };

        for(int i = 0 ; i < newRanking.size(); i++){

            intentArray[i].setData(Uri.parse(newRanking.get(i)));
            pItArr[i] = PendingIntent.getActivity(context, 0, intentArray[i], 0);
            views.setOnClickPendingIntent(id[i], pItArr[i]);
        }


        //투명도 설정
        //싴바 프레퍼런시스에서 데이터 가져오고
        int alpha = PreferenceManager.getDefaultSharedPreferences(context).getInt("degreeOfTransparency", 0);

        //투명도 설정
        views.setInt(R.id.appWidget, "setBackgroundColor", Color.argb(alpha*2,225,225,225));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    //초기 및
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        System.out.println("onUpdate()");


        apiTaker = new APITaker(appWidgetManager, appWidgetIds, context);
        RemoteViews rView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        rView.setViewVisibility(R.id.getAPIprogress, View.VISIBLE);
        rView.setViewVisibility(R.id.ranking, View.INVISIBLE);

        //API 수신 이전에 초기 화면을 보여줌
        for (int appWidgetId : appWidgetIds) {
            System.out.println("for state in initOnUpdate() +" + appWidgetId );
            initUpdateAppWidget(context, appWidgetManager, appWidgetId);
        }


        //API 수신 수행
        System.out.println("execute()");
        apiTaker.execute();

    }



    @Override
    public void onDisabled(Context context) {
        System.out.println("onDisabled()");
    }

    @Override
    public void onReceive(Context context, Intent intent){




        System.out.println("onReceived()");
        String action = intent.getAction();
        System.out.println("돌아온 리시브의 인텐트 : " + action);




        if(action.equals(strOPEN_CONFIG)){
            System.out.println("pendingIntent open CONFIG ACTIVTTY()");
        }
        else if(action.equals(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)){
            System.out.println("onReceive의 ACTION_APPWIDGET_CONFIGURE");

            AppWidgetManager manager = AppWidgetManager.getInstance(context);

            System.out.println("리시브가부르는 컨피겨의 확인 버튼으로 인해 업데이트 ");
            this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
        }
        else if(action.equals("android.appwidget.action.APPWIDGET_REFRESH")){

            System.out.println("onReceive의 ACTION_APPWIDGET_REFRESH");
            RemoteViews rView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            rView.setViewVisibility(R.id.getAPIprogress, View.VISIBLE);
            rView.setViewVisibility(R.id.ranking, View.INVISIBLE);

            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));

        }
        else if(action.equals("android.appwidget.action.APPWIDGET_UPDATE")){
            System.out.println("do nothing");
            return;
        }
        super.onReceive(context,intent);

    }

    //API를 파싱 후 전역 ArrayList<String>에 적용할 것이라 리턴값 없음
    public class APITaker extends AsyncTask<Void, Void, Void> {

        AppWidgetManager appWidgetManager;
        int[] appWidgetIds;
        Context context;


        public APITaker(AppWidgetManager appWidgetManager, int[] appWidgetIds, Context context) {
            this.appWidgetManager = appWidgetManager;
            this.appWidgetIds = appWidgetIds;
            this.context = context;
        }


        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            newRanking = new ArrayList<>();
            System.out.println("doInBackground()");

            StringBuilder json;
            String line;
            String rJson;

            json = new StringBuilder();

            try{
                System.out.println("APITaker start to take api");
                url = new URL(TMONAPI);
                conn=(HttpsURLConnection) url.openConnection();

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                }
                else{
                }
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
            System.out.println(" The Result of PARSING : " + rJson);

            try{
                jObj = new JSONObject(rJson);

                JSONArray jArr = jObj.getJSONArray("data");



                for(int i = 0 ; i < jArr.length() ; i++){
                    jObj = jArr.getJSONObject(i);
                    String title = jObj.getString("title");
                    newRanking.add(i,title);
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("APITaker finished taking api");

            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            super.onPostExecute(v);
            System.out.println("onPostExecute");



            for (int appWidgetId : appWidgetIds) {

                System.out.println("for state in onUpdate()");
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }




        }
    }

}

