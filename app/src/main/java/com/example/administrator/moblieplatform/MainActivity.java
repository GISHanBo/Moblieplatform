package com.example.administrator.moblieplatform;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.mobile.map.CustomMultiPointL;
import com.mobile.map.DrawClickListener;
import com.mobile.map.MapListener;
import com.mobile.map.MapView;
import com.mobile.map.entity.Device;
import com.mobile.map.entity.Line;
import com.mobile.map.entity.LineStyle;
import com.mobile.map.entity.Point;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    public static String TAG = "测试";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        //打开地图首页
        mapView.setListener(new mMapListener());//设置地图监听

//        Button button=findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String path= "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"offlineMap/{z}/{x}/{y}.png";
////                path="file:///storage/emulated/0/offlineMap/{z}/{x}/{y}.png";
//                //                mapView.addTms("osfflineMap/{z}/{x}/{y}.png",6,2);
////                mapView.addTms(path,6,2);
////                final double lat=21.156638593954;
////                final double lng=110.366251766682;
////                mapView.openMultiPointMode();
//
//                //添加行政区域设备统计结果
////                mapView.addCityResult(21,110,"城市1",22);
////                mapView.addCityResult(22,111,"城市2",55);
////                mapView.addCityResult(20,109,"城市3",100);
////                mapView.addCityResult(21,109,"城市4555",440);
//                final double lat=21.156638593954;
//                final double lng=110.366251766682;
//                mapView.addCustomML(new CustomMPL());
//
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        List<Heat> list=new ArrayList<>();
////                        for (int i=0;i<100;i++){
////                            list.add(new Heat(lat+0.000001*i,lng+0.000001*i,1));
////                        }
////                        mapView.addHeatData(list);
////                    }
////                }).start();
////                mapView.addHighlight(45.0,111.0,90f);
//
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        List<Point> list=new ArrayList<>();
////                        for (int i=0;i<800;i++){
////                            list.add(new Point(lat+0.000001*i,lng+0.000001*i,"test.png",i));
////                        }
////                        mapView.addMultiPoint(list);
////                    }
////                }).start();
////                mapView.setShowLatLng(true);
//            }
//        });
//        Button button2=findViewById(R.id.button2);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mapView.removeCustomML();
////                mapView.removeHeatMap();
////                mapView.closeMultiPointMode();
////                final double lat=21.156638593954;
////                final double lng=110.366251766682;
//////                mapView.openMultiPointMode();
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        List<Point> list=new ArrayList<>();
////                        for (int i=0;i<800;i++){
////                            list.add(new Point(lat+0.000001*i,lng+0.000001*i,"test.png",i));
////                            Log.e(TAG, "run: "+i );
////                        }
////                        mapView.addMultiPoint(list);
////                    }
////                }).start();
////                mapView.clearHighlight();
////                mapView.removeHeatMap();//移除热力图
////                mapView.removeTms();
////               mapView.addMbTiles("offlineMap/china3857.mbtiles",6,2);
////                mapView.closeMultiPointMode();
//                //移除行政区域设备统计结果
////                mapView.removeCityResult();
////                 mapView.setShowLatLng(false);
//            }
//        });
    }

    public void selectPole(View view) {
        Integer type = 0;
        switch (view.getId()) {
            case R.id.pole:
                type = 0;
                break;
            case R.id.well:
                type = 1;
                break;
            case R.id.switchgear:
                type = 2;
                break;
            case R.id.box_branch:
                type = 3;
                break;
            case R.id.box_distribution:
                type = 4;
                break;
            case R.id.fold_point:
                type = 5;
                break;
        }
        mapView.drawDevice(type);
    }

    public void clearDraw(View view) {
        mapView.clearDraw();
    }

    public void nextLine(View view) {
        mapView.nextLine();
    }

    public void startDrawLine(View view) {
        mapView.startDrawLine();
    }

    class mMapListener implements MapListener {
        /**
         * 地图加载完成执行操作
         */
        @Override
        public void onMapLoaded() {
            double lat = 21.156638593954;
            double lng = 110.366251766682;
            mapView.setZoom(14);
            mapView.setCenter(lat, lng);
            mapView.setDrawListener(new DrawListener());
        }

        /**
         * 点击marker监听
         *
         * @param lat 图标纬度
         * @param lng 图标经度
         * @param id  图标点编号
         */
        @Override
        public void onIconClick(double lat, double lng, Integer id) {
            Log.e("main", "onIconClick: " + lat + "," + lng);
            Log.e("main", "id: " + id);
        }

        /**
         * 地图缩放等级改变事件
         *
         * @param zoomLevel 改变后地图缩放等级
         */
        @Override
        public void onZoomChange(double zoomLevel) {
            Log.e(TAG, "onZoomChange: " + zoomLevel);
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

    class CustomMPL implements CustomMultiPointL {
        @Override
        public void onBoundsChange(final MapView mapView, double latMin, double lngMin, double latMax, double lngMax) {
            final double lat = 21.156638593954;
            final double lng = 110.366251766682;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Point> list = new ArrayList<>();
                    for (int i = 0; i < 50; i++) {
                        list.add(new Point(lat + 0.000001 * i, lng + 0.000001 * i, "test.png", i));
                    }
                    mapView.addIcon(list);
                }
            }).start();
        }
    }

    /**
     * 点击设备和线路信息的处理
     */
    class DrawListener implements DrawClickListener {

        @Override
        public void deviceClick(Device device) {
            Intent intent = new Intent(MainActivity.this, SetDeviceInfo.class);
            intent.putExtra("id", device.getId());
            intent.putExtra("type", device.getType());
            intent.putExtra("name", device.getName());
            intent.putExtra("height", device.getHeight());
            intent.putExtra("material", device.getMaterial());
            intent.putExtra("sLine", device.getsLine());
            intent.putExtra("picture", device.getPicture());
            intent.putExtra("category", device.getCategory());
            startActivityForResult(intent, 1);
        }

        @Override
        public void lineClick(Line line) {
            Intent intent = new Intent(MainActivity.this, SetLineInfo.class);
            intent.putExtra("id", line.getId());
            intent.putExtra("type", line.getType());
            intent.putExtra("name", line.getName());
            intent.putExtra("abbreviation", line.getAbbreviation());
            intent.putExtra("length", line.getLength());
            intent.putExtra("level", line.getLevel());
            intent.putExtra("model", line.getModel());
            intent.putExtra("sort", line.getSort());
            intent.putExtra("showLabel", line.getLineStyle().getShowLabel());
            intent.putExtra("width", line.getLineStyle().getWidth());
            intent.putExtra("color", line.getLineStyle().getColor());
            intent.putExtra("opacity", line.getLineStyle().getOpacity());
            startActivityForResult(intent, 2);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1000:
                //根据ID删除设备
                Long id = data.getLongExtra("id", 0);
                mapView.deleteDevice(id);
                break;
            case 1001:
                Device device = new Device();
                device.setsLine(data.getStringExtra("sLine"));
                device.setId(data.getLongExtra("id", 0));
                device.setMaterial(data.getStringExtra("material"));
                device.setPicture(data.getStringExtra("picture"));
                device.setHeight(data.getFloatExtra("height", 0f));
                device.setType(data.getStringExtra("type"));
                device.setName(data.getStringExtra("name"));
                device.setCategory(data.getStringExtra("category"));
                //更新设备信息
                mapView.updateDevice(device);
                break;
            case 1002:
                Line line = new Line();
                line.setId(data.getLongExtra("id", 0));
                line.setType(data.getStringExtra("type"));
                line.setName(data.getStringExtra("name"));
                line.setAbbreviation(data.getStringExtra("abbreviation"));
                line.setLength(data.getFloatExtra("length",0f));
                line.setLevel(data.getStringExtra("level"));
                line.setModel(data.getStringExtra("model"));
                line.setSort(data.getStringExtra("sort"));
                LineStyle lineStyle=new LineStyle();
                lineStyle.setShowLabel(data.getBooleanExtra("showLabel",false));
                lineStyle.setWidth(data.getByteExtra("width", (byte) 3));
                lineStyle.setColor(data.getStringExtra("color"));
                lineStyle.setOpacity(data.getFloatExtra("opacity",0f));
                line.setLineStyle(lineStyle);
                mapView.updateLine(line);
                break;
            case 1003:
                //根据ID删除设备
                Long id2 = data.getLongExtra("id", 0);
                mapView.deleteLine(id2);
                break;
            default:
                break;
        }
    }
}
