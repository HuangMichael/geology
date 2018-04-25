package com.yhb.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

/**
 * 处理几何元素数据转换
 */
public class GeoUtils {


    /**
     * @param wktText
     * @return
     */
    public static Geometry wktToGeometry(String wktText) {
        WKTReader wktReader = new WKTReader();
        Geometry geometry = null;
        try {
            geometry = wktReader.read(wktText);
        } catch (ParseException e) {
            throw new RuntimeException("Not a WKT string:" + wktText);
        }
        return geometry;
    }


    /**
     * @param geometry
     * @return
     */
    public static String geometryToWkt(Geometry geometry) {
        WKTWriter wktWriter = new WKTWriter();
        String wktText = null;
        wktText = wktWriter.write(geometry);
        return wktText;
    }


}
