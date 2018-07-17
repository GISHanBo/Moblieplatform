package com.example.administrator.moblieplatform;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    public static String TAG="测试";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView=findViewById(R.id.mapView);
        //打开地图首页
        mapView.setListener(new mMapListener());//设置地图监听

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path= "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"offlineMap/{z}/{x}/{y}.png";
//                path="file:///storage/emulated/0/offlineMap/{z}/{x}/{y}.png";
                //                mapView.addTms("osfflineMap/{z}/{x}/{y}.png",6,2);
//                mapView.addTms(path,6,2);
//                final double lat=21.156638593954;
//                final double lng=110.366251766682;
//                mapView.openMultiPointMode();

                //添加行政区域设备统计结果
//                mapView.addCityResult(21,110,"城市1",22);
//                mapView.addCityResult(22,111,"城市2",55);
//                mapView.addCityResult(20,109,"城市3",100);
//                mapView.addCityResult(21,109,"城市4555",440);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<HeatPoint> list=new ArrayList<>();
//                        for (int i=0;i<1;i++){
//                            list.add(new HeatPoint(lat+0.000001*i,lng+0.000001*i,1));
//                        }
//                        mapView.addHeatData(list);
//                    }
//                }).start();
//                mapView.addHighlight(45.0,111.0,90f);
                final double lat=21.156638593954;
                final double lng=110.366251766682;
                mapView.openMultiPointMode();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Point> list=new ArrayList<>();
                        for (int i=0;i<800;i++){
                            list.add(new Point(lat+0.000001*i,lng+0.000001*i,"test.png",i));
                            Log.e(TAG, "run: "+i );
                        }
                        mapView.addMultiPoint(list);
                    }
                }).start();


            }
        });
        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.closeMultiPointMode();
//                mapView.clearHighlight();
//                mapView.removeHeatMap();//移除热力图
//                mapView.removeTms();
//               mapView.addMbTiles("offlineMap/china3857.mbtiles",6,2);
//                mapView.closeMultiPointMode();
                //移除行政区域设备统计结果
//                mapView.removeCityResult();
            }
        });
    }
    class mMapListener implements MapListener{
        /**
         * 地图加载完成执行操作
         */
        @Override
        public void onMapLoaded() {
            final double lat=21.156638593954;
            final double lng=110.366251766682;
            mapView.setZoom(14);
            mapView.setCenter(lat,lng);

//            mapView.setZoomLimit(1,5);




        }

        /**
         * 点击marker监听
         * @param lat 图标纬度
         * @param lng 图标经度
         * @param id 图标点编号
         */
        @Override
        public void onIconClick(double lat,double lng,Integer id) {
            Log.e("main", "onIconClick: "+lat+","+lng);
            Log.e("main", "id: "+id);
        }

        /**
         * 地图缩放等级改变事件
         * @param zoomLevel 改变后地图缩放等级
         */
        @Override
        public void onZoomChange(double zoomLevel) {
            Log.e(TAG, "onZoomChange: "+zoomLevel);
        }

        /**
         * 地图中心改变事件
         *
         * @param lat 纬度
         * @param lng 经度
         */
        @Override
        public void onCenterChange(double lat, double lng) {

        }
    }
}
