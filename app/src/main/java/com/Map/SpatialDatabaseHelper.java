package com.Map;

import android.content.Context;
import android.util.Log;

import com.Map.entity.Heat;
import com.Map.entity.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;

import jsqlite.Constants;
import jsqlite.Database;
import jsqlite.Exception;
import jsqlite.Stmt;

public class SpatialDatabaseHelper {
    private static String TAG = "空间数据库";
    private static String path;
    private Database database;



    private static class ClassHolder {
        private static final SpatialDatabaseHelper INSTANCE = new SpatialDatabaseHelper();
    }

    private SpatialDatabaseHelper() {
//        Database database=openDatabase();
//        //创建空间索引
//        createIndex(database,"Line");
//        createIndex(database,"Device");
//        createIndex(database,"marker");
//        createIndex(database,"heat");
//        closeDatabase(database);
    }

    public static final SpatialDatabaseHelper getInstance(Context context) {

        if (path == null) {
            path = createDB(context);
        }
        return ClassHolder.INSTANCE;
    }

    /**
     * 向marker表中插入数据
     * @param points 带图标点数据
     */
    public void addMarker(List<Point> points) {
        database=openDatabase();
        for (Point point:points){
            doSQL(database, "insert into marker (serial, icon, geom)values('" + point.getSerial() + "', '" + point.getIcon() + "', MakePoint(" + point.getLat() + " ," + point.getLng() + ", 4326))");
        }
        createIndex(database,"marker");
    }

    /**
     * 热力图数据库插入数据
     * @param list 热力数据
     */
    public void addHeat(List<Heat> list) {
        database=openDatabase();
        for(Heat heat:list){
            doSQL(database, "insert into heat (value, geom)values('" + heat.getValue() + "', MakePoint(" + heat.getLat() + " ," + heat.getLng() + ", 4326))");
        }
        createIndex(database,"heat");
    }

    /**
     * 清除热力图数据
     */
    public void clearHeatData() {
        removeIndex(database,"heat");
        clearTable(database,"heat");
        closeDatabase(database);
    }
    /**
     * 清除热力图数据
     */
    public void clearMarkerData() {
        removeIndex(database,"marker");
        clearTable(database,"marker");
        closeDatabase(database);
    }

    /**
     * 根据范围查询热力图数据
     * @param latMin 纬度最小值
     * @param lngMin 经度最小值
     * @param latMax 纬度最大值
     * @param lngMax 经度最大值
     * @return 返回范围内热力图点数据
     */
    public List<Heat> queryHeatPoint(double latMin, double lngMin, double latMax, double lngMax){
        List<Heat> list=new ArrayList<>();
        String sql="SELECT value, X(geom), Y(geom) FROM heat WHERE ROWID IN" +
                " (SELECT pkid FROM idx_heat_geom WHERE" +
                "  xmin > "+latMin+" AND xmax < "+latMax+" AND ymin > "+lngMin+" AND ymax < "+lngMax+");";
        try {
            Stmt stmt = database.prepare(sql);
            while (stmt.step()) {
                float value = (float) stmt.column_double(0);
                double lat =  stmt.column_double(1);
                double lng =  stmt.column_double(2);
                Heat heat=new Heat(lat,lng,value);
                list.add(heat);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"查询热力图错误");
            Log.e(TAG,e.toString());
        }
        return list;
    }

    /**
     * 根据范围查询点数据
     * @param latMin 纬度最小值
     * @param lngMin 经度最小值
     * @param latMax 纬度最大值
     * @param lngMax 经度最大值
     * @return 返回范围内点数据
     */
    public List<Point> queryPoint(double latMin, double lngMin, double latMax, double lngMax){
        List<Point> points=new ArrayList<>();
        String sql="SELECT serial, icon, X(geom), Y(geom) FROM marker WHERE ROWID IN" +
                " (SELECT pkid FROM idx_marker_geom WHERE" +
                "  xmin > "+latMin+" AND xmax < "+latMax+" AND ymin > "+lngMin+" AND ymax < "+lngMax+");";
        try {
            Stmt stmt = database.prepare(sql);
            while (stmt.step()) {
                Integer serial=stmt.column_int(0);
                String icon = stmt.column_string(1);
                double lat =  stmt.column_double(2);
                double lng =  stmt.column_double(3);
                Point point=new Point(lat,lng,icon,serial);
                points.add(point);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"查询海量点数据错误");
            Log.e(TAG,e.toString());
        }
        return points;
    }
    /**
     * 打开Spatialite数据库
     * @return Spatialite数据库
     */
    private Database openDatabase() {
        Database database = new Database();
        File dbFile = new File(path);
        try {
            //不存在就创建数据库，以读写模式打开
            database.open(dbFile.toString(), Constants.SQLITE_OPEN_READWRITE | Constants.SQLITE_OPEN_CREATE);
            return database;
        } catch (Exception e) {
            e.printStackTrace();

        }
        Log.e(TAG, "打开数据库失败");
        return null;
    }

    /**
     * 无需返回值的SQL操作
     *
     * @param database 目标数据库
     * @param sql      sql语句
     */
    private void doSQL(Database database, String sql) {
        try {
            Stmt stmt = database.prepare(sql);
            stmt.step();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("数据库执行错误",e.toString());
        }
    }

    /**
     * 对表中的geom字段创建空间索引
     * @param database 空间数据库
     * @param tableName 表名
     */
    private void createIndex(Database database,String tableName){
        doSQL(database,"SELECT CreateSpatialIndex('"+tableName+"', 'geom')");
    }

    /**
     * 移除对标中的geom字段创建空间索引
     * @param database 空间数据库
     * @param tableName 表名
     */
    private void removeIndex(Database database,String tableName){
        doSQL(database,"SELECT DisableSpatialIndex('"+tableName+"', 'geom')");
    }


    /**
     * 清空表中的记录
     *
     * @param database  空间数据库
     * @param tableName 表名
     */
    private void clearTable(Database database, String tableName) {
        doSQL(database, "delete from " + tableName + ";");
    }

    /**
     * 关闭数据库
     */
    public void clearDatabase() {
        Database database=openDatabase();
        try {
            clearTable(database,"Line");
            clearTable(database,"Device");
            clearTable(database,"heat");
            clearTable(database,"marker");
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭数据库
     */
    private void closeDatabase(Database database){
        try {
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 每次自动从Assets复制空间数据库
     *
     * @param context 上下文对象
     * @return 生成的数据库路径
     */
    private static String createDB(Context context) {
        String filePath = context.getFilesDir().getPath() + "/" + context.getPackageName() + "/databases/";
        String dbName = "db.sqlite";
        File dir = new File(filePath);
        // 如果目录不中存在，创建这个目录
        if (!dir.exists()) dir.mkdirs();
        try {
            File target = new File(filePath + dbName);
            if (target.exists()) {
                target.delete();
            }
            InputStream is = context.getResources().getAssets()
                    .open(dbName);
            FileOutputStream fos = new FileOutputStream(filePath + dbName);
            byte[] buffer = new byte[7168];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath + dbName;
    }

}
