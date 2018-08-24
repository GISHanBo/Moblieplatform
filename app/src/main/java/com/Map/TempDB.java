package com.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TempDB {

    private SQLiteDatabase sqLiteDatabase;
    private static String[] columns=new String[]{"lng","lat","serial","icon"};
    private static String[] heatColumns=new String[]{"lng","lat","value"};
    private boolean state=false;
    public TempDB(Context context) {
        String dirPath=context.getFilesDir().getPath()+"/"+ context.getPackageName()+ "/databases";
        File dir = new File(dirPath);
        if(!dir.exists())
        { dir.mkdirs();}
        sqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(dirPath+"/pingyuan.db",null);
        sqLiteDatabase.execSQL("create table if not exists point(id integer primary key, lat double,lng double,icon text,serial integer);");
        sqLiteDatabase.execSQL("CREATE INDEX if not exists idx_test_lat_lng ON point (lat, lng);");
        sqLiteDatabase.execSQL("create table if not exists heat(id integer primary key, lat double,lng double,value float);");
        sqLiteDatabase.execSQL("CREATE INDEX if not exists idx_heat_lat_lng ON heat (lat, lng);");
        state=true;

    }
    public List<Point> queryPoint(double latMin,double lngMin,double latMax,double lngMax){
        List<Point> points=new ArrayList<>();
        try {
            Cursor cursor = sqLiteDatabase.query("point", columns, "lat between ? and ?  and lng between ? and  ?", new String[]{String.valueOf(latMin), String.valueOf(latMax), String.valueOf(lngMin), String.valueOf(lngMax)}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    double lat=cursor.getDouble(cursor.getColumnIndex("lat"));
                    double lng=cursor.getDouble(cursor.getColumnIndex("lng"));
                    String icon=cursor.getString(cursor.getColumnIndex("icon"));
                    Integer serial=cursor.getInt(cursor.getColumnIndex("serial"));
                    points.add(new Point(lat,lng,icon,serial));
                }while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }
    public List<HeatPoint> queryHeatPoint(double latMin,double lngMin,double latMax,double lngMax){
        List<HeatPoint> points=new ArrayList<>();
        try {
            Cursor cursor = sqLiteDatabase.query("heat", heatColumns, "lat between ? and ?  and lng between ? and  ?", new String[]{String.valueOf(latMin), String.valueOf(latMax), String.valueOf(lngMin), String.valueOf(lngMax)}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    double lat=cursor.getDouble(cursor.getColumnIndex("lat"));
                    double lng=cursor.getDouble(cursor.getColumnIndex("lng"));
                    float value=cursor.getFloat(cursor.getColumnIndex("value"));
                    points.add(new HeatPoint(lat,lng,value));
                }while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }
    public void addPoint(Point p){
        sqLiteDatabase.execSQL("insert into point (lng, lat, serial, icon)values(?, ?, ?, ?)", new Object[]{p.getLng(),p.getLat(),p.getSerial(),p.getIcon()});
    }
    public void addHeatPoint(HeatPoint heatPoint){
        sqLiteDatabase.execSQL("insert into heat (lng, lat, value)values(?, ?, ?)", new Object[]{heatPoint.getLng(),heatPoint.getLat(),heatPoint.getValue()});
    }
    public void reIndex(){
        sqLiteDatabase.execSQL("REINDEX idx_test_lat_lng ;");
    }
    public void reIndexHeat(){
        sqLiteDatabase.execSQL("REINDEX idx_heat_lat_lng ;");
    }
    public void close(){
        if(state){
            sqLiteDatabase.execSQL("delete from point");
            sqLiteDatabase.close();
            state=false;
        }
    }
    public void closeHeatMap(){
        if(state){
            sqLiteDatabase.execSQL("delete from heat");
            sqLiteDatabase.close();
            state=false;
        }
    }

    /**
     * 数据库是否关闭
     * @return true为未关闭
     */
    public boolean getState() {
        return state;
    }
}
