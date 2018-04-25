package com.yhb.utils;

/**
 * gis 地图展示工具类
 *
 * @author huangbin
 * @version 1.0 2017-3-21 15:04:21
 */
public class GisUtils {
    private final static double M_PI = Math.PI;

    /**
     * WGS84经纬度转web Mercator
     *
     * @param lon
     * @param lat
     * @return double[]
     */
    public static double[] lonLat2Mercator(double lon, double lat) {
        double[] xy = new double[2];
        double x = lon * 20037508.342789 / 180;
        double y = Math.log(Math.tan((90 + lat) * M_PI / 360)) / (M_PI / 180);
        y = y * 20037508.34789 / 180;
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

    /**
     * web Mercator转WGS84经纬度
     *
     * @param mercatorX
     * @param mercatorY
     * @return double[]
     */
    public static double[] mercator2LonLat(double mercatorX, double mercatorY) {
        double[] xy = new double[2];
        double x = mercatorX / 20037508.34 * 180;
        double y = mercatorY / 20037508.34 * 180;
        y = 180 / M_PI * (2 * Math.atan(Math.exp(y * M_PI / 180)) - M_PI / 2);
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("WGS84经纬度转web Mercator");
        System.out.println("mercatorX:  " + GisUtils.lonLat2Mercator(100, 29)[0]);
        System.out.println("mercatorY:  " + GisUtils.lonLat2Mercator(100, 29)[1]);

        System.out.println("web Mercator转WGS84经纬度");
        System.out.println("lat:  " + GisUtils.mercator2LonLat(1514250.5963000003, 3591017.1143999994)[0]);
        System.out.println("lon:  " + GisUtils.mercator2LonLat(1514250.5963000003, 3591017.1143999994)[1]);
    }


    /**
     * 功能：         度-->度分秒
     *
     * @param d 传入待转化格式的经度或者纬度
     */
    public void DDtoDMS(Double d) {

        String[] array = d.toString().split("[.]");
        String degrees = array[0];//得到度

        Double m = Double.parseDouble("0." + array[1]) * 60;
        String[] array1 = m.toString().split("[.]");
        String minutes = array1[0];//得到分

        Double s = Double.parseDouble("0." + array1[1]) * 60;
        String[] array2 = s.toString().split("[.]");
        String seconds = array2[0];//得到秒
        System.out.println(degrees + "  " + minutes + "  " + seconds);
    }

    /**
     * 功能：  度-->度分秒（满足图片格式）
     *
     * @param d 传入待转化格式的经度或者纬度
     * @return
     */
    public String DDtoDMS_photo(Double d) {

        String[] array = d.toString().split("[.]");
        String D = array[0];//得到度

        Double m = Double.parseDouble("0." + array[1]) * 60;
        String[] array1 = m.toString().split("[.]");
        String M = array1[0];//得到分

        Double s = Double.parseDouble("0." + array1[1]) * 60 * 10000;
        String[] array2 = s.toString().split("[.]");
        String S = array2[0];//得到秒
        return D + "/1," + M + "/1," + S + "/10000";
    }

}