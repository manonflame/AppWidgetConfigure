package com.example.kyungjunmin.appwidgetconfigure;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;
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

import javax.net.ssl.HttpsURLConnection;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {



    //API
    //String TMONAPI = "https://api-qa.ticketmonster.co.kr/v2/widget/cards";
    String TMONAPI = "https://raw.githubusercontent.com/ChoiJinYoung/iphonewithswift2/master/weather.json";

    //URL 인스턴스 생성
    URL url = null;

    HttpsURLConnection conn = null;

    BufferedReader Res = null;

    JSONObject jObj = null;

    JSONArray user = null;

    APITaker apiTaker = null;


    //10개의 검색어 키워드를 받을 ArrayList<String>



    Context context;
    private String strOPEN_CONFIG = "android.action.OPEN_CONFIG";





    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        System.out.println("updateAppWidget()");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);


        //버튼에 이벤트 부여

        Intent it = new Intent(context, AppWidgetConfigActivity.class);

        PendingIntent openConfig = PendingIntent.getActivity(context, 0, it, 0);
        views.setOnClickPendingIntent(R.id.configure, openConfig);




        //투명도 설정
        //싴바 프레퍼런시스에서 데이터 가져오고
        int test = PreferenceManager.getDefaultSharedPreferences(context).getInt("degreeOfTransparency", 0);
        System.out.println("SeekValue received : " + test);
        //투명도 설정
        views.setInt(R.id.appWidget, "setBackgroundColor", Color.argb(test*2,22,22,22));




        //api받기






        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        System.out.println("onUpdate()");

        this.context = context;


        //반복이 계속 일어나는 이유
        System.out.println("The length of appWidgetIds : " + appWidgetIds.length);

        // There may be multiple widgets active, so update all of hem
        for (int appWidgetId : appWidgetIds) {

            System.out.println("for state in onUpdate()");



            apiTaker = new APITaker();
            apiTaker.execute();


            updateAppWidget(context, appWidgetManager, appWidgetId);

        }


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

        //API 받기 전에 progressBar처리
        @Override
        protected void onPreExecute(){
            super.onPreExecute();




        }

        @Override
        protected Void doInBackground(Void... voids) {

            InputStream is = null;

            System.out.println("doInBackground()");

            StringBuilder json = null;

            String line = null;

            String rJson = null;

            json = new StringBuilder();
            try{

                System.out.println("doInBackground() :: try() ");

                url = new URL(TMONAPI);
                conn=(HttpsURLConnection) url.openConnection();

                //설정은 다 default로 둘거니까 일단 생략해보자
                conn.setRequestProperty("User-Agent","my-rest-app-v0.1");
                conn.setRequestProperty("Accept","application/vnd.github.v3+json");
                conn.setRequestProperty("Contact-Me", "hathibelagal@example.com");

                System.out.println("doInBackground() :: after openConnection() ");

                is = conn.getInputStream();

                System.out.println("after conn.getInputStream() ");
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    System.out.println("성공");
                }
                else{
                    System.out.println("실패");
                }
                System.out.println(" Before access to conn ");
                Res = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                //Res = new BufferedReader(new InputStreamReader(is, "UTF-8"));

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


                //뭐 대강 이런 식으로요
                jObj = new JSONObject(rJson);
                JSONObject rjObj = jObj.getJSONObject("weatherinfo");


                JSONArray jArr = rjObj.getJSONArray("local");


                for(int i = 0 ; i < jArr.length() ; i++){
                    jObj = jArr.getJSONObject(i);
                    String country = jObj.getString("country");
                    String weather = jObj.getString("weather");

                    System.out.println("country : "+country +" - weather : " + weather );

                }




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

