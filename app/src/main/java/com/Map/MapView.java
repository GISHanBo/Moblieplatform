package com.Map;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

/**
 * 地图类
 */
public class MapView extends WebView {
    /**
     * 地图是否已经加载，true为加载
     */

    private static String TAG = "地图";
    private MapListener mapListener;
    private TempDB tempDB;

    /**
     * MapView使用方法
     * <包名.MapView
     * android:id="@+id/mapView"
     * android:layout_width="match_parent"
     * android:layout_height="match_parent">
     * </包名.MapView>
     *
     * @param context
     */
    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    public void setListener(MapListener mapListener) {
        this.mapListener = mapListener;
        init();
    }

    /**
     * 初始化,必须执行此方法后，方可执行其他操作
     */
    public void init() {


        WebSettings webSettings = getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");

        webSettings.setBuiltInZoomControls(true);// 隐藏缩放按钮
        webSettings.setUseWideViewPort(true);// 可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        webSettings.setJavaScriptEnabled(true);
        addJavascriptInterface(new JsInteration(), "Android");
        webSettings.setAppCacheEnabled(true);//缓存
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(true);// 新加
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.getAllowFileAccessFromFileURLs();
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        setDrawingCacheEnabled(true);
        buildDrawingCache();
        buildLayer();
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setWebViewClient(new MWebView());
        loadUrl("file:///android_asset/index.html");
    }

    /**
     * 放大地图
     */
    public void enlarge() {
        loadMethod("zoomIn()");
    }

    /**
     * 缩小地图
     */
    public void narrow() {
        loadMethod("zoomOut()");
    }

    /**
     * 设置地图中心
     *
     * @param lat 纬度
     * @param lng 经度
     */
    public void setCenter(double lat, double lng) {
        loadMethod("setCenter(" + lat + "," + lng + ")");
    }

    /**
     * 根据坐标范围设置地图中心
     *
     * @param latMin 最小纬度
     * @param lngMin 最小经度
     * @param latMax 最大纬度
     * @param lngMax 最大经度
     */
    public void setView(double latMin, double lngMin, double latMax, double lngMax) {
        loadMethod("setView(" + latMin + "," + lngMin + "," + latMax + "," + lngMax + ")");
    }

    /**
     * 设置地图缩放等级
     *
     * @param level 缩放等级
     */
    public void setZoom(Integer level) {
        loadMethod("setZoom(" + level + ")");
    }

    /**
     * 设置地图的最大和最小缩放级别
     *
     * @param minZoom 最小地图缩放等级
     * @param maxZoom 最大地图缩放等级
     */
    public void setZoomLimit(Integer minZoom, Integer maxZoom) {
        loadMethod("setZoomLimit(" + minZoom + "," + maxZoom + ")");
    }

    /**
     * 添加地图定位结果，支持角度旋转角度，默认箭头向下，顺时针旋转
     *
     * @param lat 纬度
     * @param lng 经度
     * @param rotate 旋转角度 0-360
     */
    public void addHighlight(Double lat, Double lng,float rotate) {
        loadMethod("addHighlight(" + lat + "," + lng + ","+rotate+")");
    }

    /**
     * 清除高亮显示
     */
    public void clearHighlight() {
        loadMethod("clearHighlight()");
    }

    /**
     * 开启/关闭点击地图弹出经纬度
     *
     * @param isShow true开启，false关闭
     */
    public void setShowLatLng(boolean isShow) {
        if (isShow) {
            loadMethod("showLatLng()");
        } else {
            loadMethod("clearShowLatLng()");
        }
    }

    /**
     * 添加GeoJson到地图
     *
     * @param geoJson "{'type': 'FeatureCollection','features': [{'type': 'Feature','properties': {},'geometry': {'type': 'Point','coordinates': [35.5078125,69.53451763078358]}},{'type': 'Feature','properties': {},'geometry': {'type': 'Point','coordinates': [20.7421875,48.22467264956519]}}]}"
     */
    public void addGeoJson(String geoJson) {
        loadMethod("addGeoJson(" + geoJson + ")");
    }

    /**
     * 移除geoJson图层
     */
    public void removeGeoJson() {
        loadMethod("removeGeoJson()");
    }

    /**
     * 添加带图标点
     *
     * @param lat  经度
     * @param lng  纬度
     * @param icon 图标名称/test.png，图标必须放在assets/icon目录下，分辨率为48*48，图标中心与坐标对齐
     * @param id   对象id ，点击图标会返回该值
     */
    public void addIcon(double lat, double lng, String icon, Integer id) {
        loadMethod("addIcon(" + lat + "," + lng + ",\"" + icon + "\"," + id + ")");
    }

    /**
     * 移除全部带图标点
     */
    public void removeIcons() {
        loadMethod("removeIcons()");
    }

    /**
     * 添加WMS图层
     *
     * @param url     服务地址
     * @param layers  图层名称
     * @param format  调用图片格式
     * @param name    自定义图层名称，方便关闭图层，最好使用唯一值
     * @param minZoom 最小缩放等级
     * @param maxZoom 最大缩放等级 minZoom<maxZoom
     */
    public void addWMSLayer(String url, String layers, String format, String name, Integer minZoom, Integer maxZoom) {
        loadMethod("addWMSLayer(\"" + url + "\",\"" + layers + "\",\"" + format + "\",\"" + name + "\"," + minZoom + "," + maxZoom + ")");
    }

    /**
     * 添加WMTS图层
     *
     * @param url           服务地址
     * @param layer         图层名称
     * @param tileMatrixSet 切片使用的网格名称
     * @param format        图片格式
     * @param name          自定义图层名称
     * @param minZoom       最小缩放等级
     * @param maxZoom       最大缩放等级
     * @param tileSize      切片大小
     */
    public void addWMTSLayer(String url, String layer, String tileMatrixSet, String format, String name, Integer minZoom, Integer maxZoom, Integer tileSize) {
        loadMethod("addWMTSLayer(\"" + url + "\",\"" + layer + "\",\"" + tileMatrixSet + "\",\"" + format + "\",\"" + name + "\"," + minZoom + "," + maxZoom + "," + tileSize + ")");
    }

    /**
     * 根据图层名称移除图层
     *
     * @param name 图层名称
     */
    public void removeLayerByName(String name) {
        loadMethod("removeLayerByName(\"" + name + "\")");
    }

    /**
     * 切换地图底图
     *
     * @param source 来源-MapBox/高德/谷歌/天地图
     * @param type   地图类型-道路图/卫星图
     */
    public void switchBaseLayer(String source, String type) {
        loadMethod("switchBaseLayer(\"" + source + "\",\"" + type + "\")");
    }

    /**
     * 添加MbTiles离线地图,<br/>
     * <strong>不支持以file开头的路径</strong>
     *
     * @param path    离线地图路径
     * @param maxZoom 最大图层缩放等级
     * @param minZoom 最小图层缩放等级
     * @deprecated 已废弃
     */
    @Deprecated
    public void addMbTiles(String path, Integer maxZoom, Integer minZoom) {
        loadMethod("addMbTiles(\"" + path + "\"," + maxZoom + "," + minZoom + ")");
    }

    /**
     * 移除MbTiles离线地图
     *
     * @deprecated 已废弃
     */
    @Deprecated
    public void removeMbTiles() {
        loadMethod("removeMbTiles()");
    }

    /**
     * 添加Tms图层，支持离线地图<br/>
     * 添加assets目录下离线TMS-mapView.addTms("osfflineMap/{z}/{x}/{y}.png",6,2);<br/>
     * 添加Android SD卡TMS切片文件<br/>
     * String path= "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"offlineMap/{z}/{x}/{y}.png";<br/>
     * path="file:///storage/emulated/0/offlineMap/{z}/{x}/{y}.png";<br/>
     * mapView.addTms(path,6,2);
     *
     * @param path    地图路径
     * @param maxZoom 最大图层缩放等级
     * @param minZoom 最小图层缩放等级
     */
    public void addTms(String path, Integer maxZoom, Integer minZoom) {
        loadMethod("addTms(\"" + path + "\"," + maxZoom + "," + minZoom + ")");
    }

    /**
     * 移除Tms图层
     */
    public void removeTms() {
        loadMethod("removeTms()");
    }

    private void loadMethod(final String method) {
        post(new Runnable() {
            @Override
            public void run() {
                loadUrl("javascript:" + method + "");
            }
        });

    }

    /**
     * 开启撒点模式
     */
    public void openMultiPointMode() {
        tempDB = new TempDB(getContext());
        loadMethod("setMultiPointMode(" + true + ")");
    }

    /**
     * 关闭撒点模式
     */
    public void closeMultiPointMode() {
        tempDB.close();
        loadMethod("removeIcons()");
        loadMethod("setMultiPointMode(" + false + ")");
    }

    /**
     * 添加海量带图标点
     *
     * @param points 海量带图标点
     */
    public void addMultiPoint(List<Point> points) {
        if(!tempDB.getState()){
            openMultiPointMode();
        }
        for (Point point : points) {
            tempDB.addPoint(point);
        }
        tempDB.reIndex();
        loadMethod("getBounds()");
    }

    /**
     * 在地图上指定位置添加显示城市名称和设备数量
     * @param lat 纬度
     * @param lng 经度
     * @param cityName 设备名称
     * @param deviceNum 设备数量
     */
    public void addCityResult(double lat,double lng,String cityName,Integer deviceNum) {
        loadMethod("addPopup(" + lat + "," + lng + ",\"" + cityName + "\"," + deviceNum + ")");
    }

    /**
     * 移除城市设备的统计结果
     */
    public void removeCityResult() {
        loadMethod("removePopup()");
    }

    /**
     * 添加热力图数据
     * @param heatPoints 热力图数据
     */
    public void addHeatData(List<HeatPoint> heatPoints){
        tempDB = new TempDB(getContext());
        for (HeatPoint point : heatPoints) {
            tempDB.addHeatPoint(point);
        }
        tempDB.reIndexHeat();
        loadMethod("addHeatMap()");
    }
    /**
     * 移除热力图
     */
    public void removeHeatMap() {
        tempDB.closeHeatMap();
        loadMethod("removeHeatMap()");
    }

    private void showPointsIn(double latMin, double lngMin, double latMax, double lngMax) {
        removeIcons();
        List<Point> points = tempDB.queryPoint(latMin, lngMin, latMax, lngMax);
        int size = points.size();
        int step = 1;
        if (size > 100) {
            double result = ((double) size) / 100;
            step = (int) Math.ceil(result);
        }
        for (int i = 0; i < size; ) {
            Point point = points.get(i);
            addIcon(point.getLat(), point.getLng(), point.getIcon(), point.getSerial());
            i = i + step;
        }

    }
    private void showHeatIn(double latMin, double lngMin, double latMax, double lngMax) {
        List<HeatPoint> points = tempDB.queryHeatPoint(latMin, lngMin, latMax, lngMax);
        int size = points.size();
        int step = 1;
        if (size > 100) {
            double result = ((double) size) / 100;
            step = (int) Math.ceil(result);
        }
        Log.e(TAG, String.valueOf(size));
        StringBuilder str= new StringBuilder("[");

        for (int i = 0; i < size; ) {
            HeatPoint point = points.get(i);
            str.append("{'lat': ").append(point.getLat()).append(",'lon': ").append(point.getLng()).append(",'value': ").append(point.getValue()).append("},");
            i = i + step;
        }
        str = new StringBuilder(str.substring(0, str.length()-1));
        str.append("]");
        Log.e(TAG, String.valueOf(str));
        addHeatData(str.toString());
    }

    /**
     * 添加热力图数据显示
     * @param json json格式数据
     */
    private void addHeatData(String json){
        loadMethod("heatMapAddData("+json+")");
    }

    /**
     * 加载完成事件
     */
    class MWebView extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //mapListener.onMapLoaded();
        }
    }

    class JsInteration {
        /**
         * 地图图标点击事件
         *
         * @param lat 纬度
         * @param lng 经度
         * @param id  图标编号
         */
        @JavascriptInterface
        public void iconClick(double lat, double lng, String id) {
            mapListener.onIconClick(lat, lng, Integer.valueOf(id));
        }

        /**
         * 地图初始化完成
         */
        @JavascriptInterface
        public void onMapLoad(){
            mapListener.onMapLoaded();
            Log.d(TAG,"加载完成");
        }
        /**
         * 地图缩放等级改变事件
         *
         * @param zoomLevel 地图缩放等级
         */
        @JavascriptInterface
        public void onZoomChange(String zoomLevel) {
            mapListener.onZoomChange(Double.parseDouble(zoomLevel));
        }

        /**
         * 地图中心改变事件
         *
         * @param lat 纬度
         * @param lng 经度
         */
        @JavascriptInterface
        public void onCenterChange(double lat, double lng) {
            mapListener.onCenterChange(lat, lng);
        }


        @JavascriptInterface
        public void onViewChange(double latMin, double lngMin, double latMax, double lngMax) {
            showPointsIn(latMin, lngMin, latMax, lngMax);
        }

        @JavascriptInterface
        public void onHeatViewChange(double latMin, double lngMin, double latMax, double lngMax){
            showHeatIn(latMin, lngMin, latMax, lngMax);
        }
    }

}
