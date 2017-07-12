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

    static ArrayList<String> Ranking = new ArrayList<String>(){
        {
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");

            add("6");
            add("7");
            add("8");
            add("9");
            add("10");

        }
    };

    ArrayList<String> newRanking;


    //API
    String TMONAPI = "https://api-qa.ticketmonster.co.kr/v2/widget/cards";
   // String TMONAPI = "https://raw.githubusercontent.com/ChoiJinYoung/iphonewithswift2/master/weather.json";

    //URL 인스턴스 생성
    URL url = null;

    HttpsURLConnection conn = null;

    BufferedReader Res = null;

    JSONObject jObj = null;

    APITaker apiTaker = null;


    //10개의 검색어 키워드를 받을 ArrayList<String>



    Context context;
    private String strOPEN_CONFIG = "android.action.OPEN_CONFIG";





    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        System.out.println("updateAppWidget()");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setViewVisibility(R.id.getAPIprogress, View.GONE);
        views.setViewVisibility(R.id.ranking, View.VISIBLE);

        //버튼에 이벤트 부여

        Intent it = new Intent(context, AppWidgetConfigActivity.class);

        PendingIntent openConfig = PendingIntent.getActivity(context, 0, it, 0);
        views.setOnClickPendingIntent(R.id.configure, openConfig);




        int id[] = {R.id.firstWeb, R.id.secondWeb, R.id.thirdWeb, R.id.fourthWeb, R.id.fifthWeb, R.id.sixthWeb, R.id.seventhWeb, R.id.eighthWeb, R.id.ninethWeb, R.id.tenthWeb};


        System.out.println("after Config intent");
//        //API 출력
//        views.setTextViewText(R.id.firstWeb, Ranking.get(0));
//        views.setTextViewText(R.id.secondWeb, Ranking.get(1));
//        views.setTextViewText(R.id.thirdWeb, Ranking.get(2));
//        views.setTextViewText(R.id.fourthWeb, Ranking.get(3));
//        views.setTextViewText(R.id.fifthWeb, Ranking.get(4));
//
//        views.setTextViewText(R.id.sixthWeb, Ranking.get(5));
//        views.setTextViewText(R.id.seventhWeb, Ranking.get(6));
//        views.setTextViewText(R.id.eighthWeb, Ranking.get(7));
//        views.setTextViewText(R.id.ninethWeb, Ranking.get(8));
//        views.setTextViewText(R.id.tenthWeb, Ranking.get(9));

        for(int i = 0 ; i < newRanking.size() ; i++){
            System.out.println("LET'S CHECK OUT NEWRANKINGSIZE : " + i + "then Ranking : " + newRanking.get(i));
            views.setTextViewText(id[i], newRanking.get(i));

        }
        for(int i = newRanking.size() ; i < 10 ; i++ ){
            views.setTextViewText(id[i], " ");
        }


        System.out.println("after setTextViewText");





        Intent it1 = new Intent(context, WebActivity.class);
//        it1.setData(Uri.parse(Ranking.get(0)));
//        PendingIntent open1st = PendingIntent.getActivity(context, 0, it1, 0);
//        views.setOnClickPendingIntent(R.id.firstWeb, open1st);

        //2nd
        Intent it2 = new Intent(context, WebActivity.class);
//        it2.setData(Uri.parse(Ranking.get(1)));
//        PendingIntent open2nd = PendingIntent.getActivity(context, 0, it2, 0);
//        views.setOnClickPendingIntent(R.id.secondWeb, open2nd);

        //3rd
        Intent it3 = new Intent(context, WebActivity.class);
//        it3.setData(Uri.parse(Ranking.get(2)));
//        PendingIntent open3rd = PendingIntent.getActivity(context, 0, it3, 0);
//        views.setOnClickPendingIntent(R.id.thirdWeb, open3rd);



        System.out.println("CHECK POINT 3");

        //4th
        Intent it4 = new Intent(context, WebActivity.class);
//        it4.setData(Uri.parse(Ranking.get(3)));
//        PendingIntent open4th = PendingIntent.getActivity(context, 0, it4, 0);
//        views.setOnClickPendingIntent(R.id.fourthWeb, open4th);

        System.out.println("CHECK POINT 4");

        //5th
        Intent it5 = new Intent(context, WebActivity.class);
//        it5.setData(Uri.parse(Ranking.get(4)));
//        PendingIntent open5th = PendingIntent.getActivity(context, 0, it5, 0);
//        views.setOnClickPendingIntent(R.id.fifthWeb, open5th);


        System.out.println("CHECK POINT 5");
        //6th
        Intent it6 = new Intent(context, WebActivity.class);
//        it6.setData(Uri.parse(Ranking.get(5)));
//        PendingIntent open6th = PendingIntent.getActivity(context, 0, it6, 0);
//        views.setOnClickPendingIntent(R.id.sixthWeb, open6th);


        System.out.println("CHECK POINT 6");
        //7th
        Intent it7 = new Intent(context, WebActivity.class);
//        it7.setData(Uri.parse(Ranking.get(6)));
//        PendingIntent open7th = PendingIntent.getActivity(context, 0, it7, 0);
//        views.setOnClickPendingIntent(R.id.seventhWeb, open7th);



        System.out.println("CHECK POINT 7");
        //8th
        Intent it8 = new Intent(context, WebActivity.class);
//        it8.setData(Uri.parse(Ranking.get(7)));
//        PendingIntent open8th = PendingIntent.getActivity(context, 0, it8, 0);
//        views.setOnClickPendingIntent(R.id.eighthWeb, open8th);

        System.out.println("CHECK POINT 8");
        //9th
        Intent it9 = new Intent(context, WebActivity.class);
//        it9.setData(Uri.parse(Ranking.get(8)));
//        PendingIntent open9th = PendingIntent.getActivity(context, 0, it9, 0);
//        views.setOnClickPendingIntent(R.id.ninethWeb, open9th);

        System.out.println("CHECK POINT 9");
        //10th
        Intent it10 = new Intent(context, WebActivity.class);
//        it10.setData(Uri.parse(Ranking.get(9)));
//        PendingIntent open10th = PendingIntent.getActivity(context, 0, it10, 0);
//        views.setOnClickPendingIntent(R.id.tenthWeb, open10th);

        System.out.println("CHECK POINT 10");




        Intent intentArray[] = {
                it1, it2, it3, it4, it5, it6, it7, it8, it9, it10
        };

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



        PendingIntent pItArr[] = {
                pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10
        };

        for(int i = 0 ; i < newRanking.size(); i++){
            System.out.println("=====================================");
            System.out.println("this is for state : " + i );
            System.out.println("size of intentArray : " + intentArray.length );
            System.out.println("size of pendingIntentArray : " + pItArr.length );
            System.out.println("newRanking.get("+i+") : " + newRanking.size());
            System.out.println("=====================================");
            intentArray[i].setData(Uri.parse(newRanking.get(i)));
            pItArr[i] = PendingIntent.getActivity(context, 0, intentArray[i], 0);
            views.setOnClickPendingIntent(id[i], pItArr[i]);
        }

        //TEXT VIEW - WEB VIEW 연결



//        //1st
//        if(newRanking.size()>=1){
//            Intent it1 = new Intent(context, WebActivity.class);
//            it1.setData(Uri.parse(Ranking.get(0)));
//            PendingIntent open1st = PendingIntent.getActivity(context, 0, it1, 0);
//            views.setOnClickPendingIntent(R.id.firstWeb, open1st);
//        }
//
//
//        //2nd
//        Intent it2 = new Intent(context, WebActivity.class);
//        it2.setData(Uri.parse(Ranking.get(1)));
//        PendingIntent open2nd = PendingIntent.getActivity(context, 0, it2, 0);
//        views.setOnClickPendingIntent(R.id.secondWeb, open2nd);
//
//        //3rd
//        Intent it3 = new Intent(context, WebActivity.class);
//        it3.setData(Uri.parse(Ranking.get(2)));
//        PendingIntent open3rd = PendingIntent.getActivity(context, 0, it3, 0);
//        views.setOnClickPendingIntent(R.id.thirdWeb, open3rd);
//
//        //4th
//        Intent it4 = new Intent(context, WebActivity.class);
//        it4.setData(Uri.parse(Ranking.get(3)));
//        PendingIntent open4th = PendingIntent.getActivity(context, 0, it4, 0);
//        views.setOnClickPendingIntent(R.id.fourthWeb, open4th);
//
//        //5th
//        Intent it5 = new Intent(context, WebActivity.class);
//        it5.setData(Uri.parse(Ranking.get(4)));
//        PendingIntent open5th = PendingIntent.getActivity(context, 0, it5, 0);
//        views.setOnClickPendingIntent(R.id.fifthWeb, open5th);
//
//        //6th
//        Intent it6 = new Intent(context, WebActivity.class);
//        it6.setData(Uri.parse(Ranking.get(5)));
//        PendingIntent open6th = PendingIntent.getActivity(context, 0, it6, 0);
//        views.setOnClickPendingIntent(R.id.sixthWeb, open6th);
//
//        //7th
//        Intent it7 = new Intent(context, WebActivity.class);
//        it7.setData(Uri.parse(Ranking.get(6)));
//        PendingIntent open7th = PendingIntent.getActivity(context, 0, it7, 0);
//        views.setOnClickPendingIntent(R.id.seventhWeb, open7th);
//
//        //8th
//        Intent it8 = new Intent(context, WebActivity.class);
//        it8.setData(Uri.parse(Ranking.get(7)));
//        PendingIntent open8th = PendingIntent.getActivity(context, 0, it8, 0);
//        views.setOnClickPendingIntent(R.id.eighthWeb, open8th);
//
//
//        //9th
//        Intent it9 = new Intent(context, WebActivity.class);
//        it9.setData(Uri.parse(Ranking.get(8)));
//        PendingIntent open9th = PendingIntent.getActivity(context, 0, it9, 0);
//        views.setOnClickPendingIntent(R.id.ninethWeb, open9th);
//
//
//        //10th
//        Intent it10 = new Intent(context, WebActivity.class);
//        it10.setData(Uri.parse(Ranking.get(9)));
//        PendingIntent open10th = PendingIntent.getActivity(context, 0, it10, 0);
//        views.setOnClickPendingIntent(R.id.tenthWeb, open10th);






//        //TEXT VIEW - WEB VIEW 연결
//        //1st
//        Intent it1 = new Intent(context, WebActivity.class);
//        it1.setData(Uri.parse(Ranking.get(0)));
//        PendingIntent open1st = PendingIntent.getActivity(context, 0, it1, 0);
//        views.setOnClickPendingIntent(R.id.firstWeb, open1st);
//
//        //2nd
//        Intent it2 = new Intent(context, WebActivity.class);
//        it2.setData(Uri.parse(Ranking.get(1)));
//        PendingIntent open2nd = PendingIntent.getActivity(context, 0, it2, 0);
//        views.setOnClickPendingIntent(R.id.secondWeb, open2nd);
//
//        //3rd
//        Intent it3 = new Intent(context, WebActivity.class);
//        it3.setData(Uri.parse(Ranking.get(2)));
//        PendingIntent open3rd = PendingIntent.getActivity(context, 0, it3, 0);
//        views.setOnClickPendingIntent(R.id.thirdWeb, open3rd);
//
//        //4th
//        Intent it4 = new Intent(context, WebActivity.class);
//        it4.setData(Uri.parse(Ranking.get(3)));
//        PendingIntent open4th = PendingIntent.getActivity(context, 0, it4, 0);
//        views.setOnClickPendingIntent(R.id.fourthWeb, open4th);
//
//        //5th
//        Intent it5 = new Intent(context, WebActivity.class);
//        it5.setData(Uri.parse(Ranking.get(4)));
//        PendingIntent open5th = PendingIntent.getActivity(context, 0, it5, 0);
//        views.setOnClickPendingIntent(R.id.fifthWeb, open5th);
//
//        //6th
//        Intent it6 = new Intent(context, WebActivity.class);
//        it6.setData(Uri.parse(Ranking.get(5)));
//        PendingIntent open6th = PendingIntent.getActivity(context, 0, it6, 0);
//        views.setOnClickPendingIntent(R.id.sixthWeb, open6th);
//
//        //7th
//        Intent it7 = new Intent(context, WebActivity.class);
//        it7.setData(Uri.parse(Ranking.get(6)));
//        PendingIntent open7th = PendingIntent.getActivity(context, 0, it7, 0);
//        views.setOnClickPendingIntent(R.id.seventhWeb, open7th);
//
//        //8th
//        Intent it8 = new Intent(context, WebActivity.class);
//        it8.setData(Uri.parse(Ranking.get(7)));
//        PendingIntent open8th = PendingIntent.getActivity(context, 0, it8, 0);
//        views.setOnClickPendingIntent(R.id.eighthWeb, open8th);
//
//
//        //9th
//        Intent it9 = new Intent(context, WebActivity.class);
//        it9.setData(Uri.parse(Ranking.get(8)));
//        PendingIntent open9th = PendingIntent.getActivity(context, 0, it9, 0);
//        views.setOnClickPendingIntent(R.id.ninethWeb, open9th);
//
//
//        //10th
//        Intent it10 = new Intent(context, WebActivity.class);
//        it10.setData(Uri.parse(Ranking.get(9)));
//        PendingIntent open10th = PendingIntent.getActivity(context, 0, it10, 0);
//        views.setOnClickPendingIntent(R.id.tenthWeb, open10th);



        //투명도 설정
        //싴바 프레퍼런시스에서 데이터 가져오고
        int alpha = PreferenceManager.getDefaultSharedPreferences(context).getInt("degreeOfTransparency", 0);
        System.out.println("SeekValue received : " + alpha);
        //투명도 설정
        views.setInt(R.id.appWidget, "setBackgroundColor", Color.argb(alpha*2,225,225,225));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        System.out.println("onUpdate()");

        this.context = context;


        apiTaker = new APITaker(appWidgetManager, appWidgetIds);
        apiTaker.execute();
        //반복이 계속 일어나는 이유
        System.out.println("The length of appWidgetIds : " + appWidgetIds.length);


        // There may be multiple widgets active, so update all of hem


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        System.out.println("onEnabled()");
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
        else if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            System.out.println("onReceive의 ACTION_APPWIDGET_UPDATE");
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
        }
        super.onReceive(context,intent);

    }

    //API를 파싱 후 전역 ArrayList<String>에 적용할 것이라 리턴값 없음
    public class APITaker extends AsyncTask<Void, Void, Void> {

        AppWidgetManager appWidgetManager;
        int[] appWidgetIds;

        //**이거 되는지 확인
        RemoteViews rViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);


        public APITaker(AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            this.appWidgetManager = appWidgetManager;
            this.appWidgetIds = appWidgetIds;
        }

        //API 받기 전에 progressBar처리
        @Override
        protected void onPreExecute(){


            super.onPreExecute();

            //**
            rViews.setViewVisibility(R.id.getAPIprogress, View.VISIBLE);
            rViews.setViewVisibility(R.id.ranking, View.INVISIBLE);



        }

        @Override
        protected Void doInBackground(Void... voids) {


            newRanking = new ArrayList<String>();

            System.out.println("doInBackground()");

            StringBuilder json = null;

            String line = null;

            String rJson = null;

            json = new StringBuilder();
            try{

                System.out.println("doInBackground() :: try() ");

                url = new URL(TMONAPI);
                conn=(HttpsURLConnection) url.openConnection();



                System.out.println("doInBackground() :: after openConnection() ");


                Log.e("code",conn.getResponseCode()+"");

                System.out.println("after conn.getInputStream() ");
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    System.out.println("성공");
                }
                else{
                    System.out.println("실패");
                }
                System.out.println(" Before access to conn ");
                Res = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                System.out.println("doInBackground() :: after BufferedReader construct() ");
                while((line = Res.readLine())!=null){
                    json.append(line);
                }
                System.out.println("doInBackground() :: after readLine() ");
                conn.disconnect();



            } catch (MalformedURLException e) {
                System.out.println("doInBackground() :: MalformedURLException  ");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("doInBackground() :: IOException ");
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

                    Ranking.set(i,title);
                    newRanking.add(i,title);
                    System.out.println("title renew : " + Ranking.get(i));

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }








            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            super.onPostExecute(v);

            for (int appWidgetId : appWidgetIds) {

                System.out.println("for state in onUpdate()");
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }


        }
    }

}

