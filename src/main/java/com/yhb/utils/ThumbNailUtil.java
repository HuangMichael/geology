package com.yhb.utils;

/**
 * Created by huangbin on 2017/11/9.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;


/**
 * 压缩图片缩略图
 */
public class ThumbNailUtil {


    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        //这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        //则将下面的if else语句注释即可
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());

        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());

        }
        if (type == BufferedImage.TYPE_CUSTOM) { //handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);

        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        //smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;

    }

    public static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight)
            throws Exception {
        BufferedImage srcImage;
        String imgType = "JPEG";
        if (fromFileStr.toLowerCase().endsWith(".png")) {
            imgType = "PNG";

        }
        File saveFile = new File(saveToFileStr);
        File fromFile = new File(fromFileStr);
        srcImage = ImageIO.read(fromFile);
        if (width > 0 || hight > 0) {
            srcImage = resize(srcImage, width, hight);
        }
        ImageIO.write(srcImage, imgType, saveFile);
    }

    /**
     * @param fromFileStr
     * @param saveToFileStr
     * @param width
     * @param hight
     * @throws Exception 压缩图片保存为文件后，返回该压缩图（缩略图）---路丽民
     */
    public static boolean saveThumbNailImage(String fromFileStr, String saveToFileStr, int width, int hight) throws Exception {
        BufferedImage srcImage;
        // String ex = fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
        String imgType = "JPEG";
        if (fromFileStr.toLowerCase().endsWith(".png")) {
            imgType = "PNG";

        }
        File saveFile = new File(saveToFileStr);
        File fromFile = new File(fromFileStr);
        srcImage = ImageIO.read(fromFile);
        if (width > 0 || hight > 0) {
            srcImage = resize(srcImage, width, hight);

        }
        return ImageIO.write(srcImage, imgType, saveFile);
    }


    public static void main(String argv[]) {
        try {
            //参数1(from),参数2(to),参数3(宽),参数4(高)
            ThumbNailUtil.saveImageAsJpg("d:/2222.png", "d:/2222_sm.png", 100, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
