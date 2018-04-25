package com.yhb.utils;

import com.yhb.utils.ftp.FTPUploader;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangbin on 2016/5/7.
 */
@Slf4j
public class StringUtils {


    /**
     * @param arrayStr  数组字符串  a,b,c
     * @param separator ,
     * @return 字符串转数组
     */
    public static String[] str2Array(String arrayStr, String separator) {
        String[] array = arrayStr.split(separator);
        return array;
    }


    /**
     * @param arrayStr  数组字符串  1,2,3
     * @param separator ,
     * @return 字符串转集合 Long
     */
    public static List<Long> str2List(String arrayStr, String separator) {
        List<Long> longList = new ArrayList<Long>();
        String[] array = arrayStr.split(separator);
        for (String str : array) {
            longList.add(Long.parseLong(str.trim()));
        }
        return longList;
    }


    /**
     * @param arrayStr  数组字符串  1,2,3
     * @param separator ,
     * @return 字符串转集合 Long
     */
    public static String replaceStrSeparator(String arrayStr, String separator) {

        String array[] = arrayStr.split(separator);
        String newStr = "";

        for (String s : array) {
            if (s != null && !s.equals("")) {
                newStr += s + ",";
            }
        }
        newStr = "(" + newStr.substring(0, newStr.length() - 1) + ")";
        return newStr;
    }


    /**
     * @param str
     * @return 首字母大写
     */
    public static String upperCaseFirstOne(String str) {
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (i == 0) {
                ch[0] = Character.toUpperCase(ch[0]);
            } else {
                ch[i] = Character.toLowerCase(ch[i]);
            }
        }
        StringBuffer a = new StringBuffer();
        a.append(ch);
        return a.toString();
    }

    /**
     * @param str
     * @return 首字母大写
     */
    public static String upperCaseCamel(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }


    /**
     * @param str
     * @return 首字母小写
     */
    public static String lowerCaseCamel(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
    }

    /**
     * @param array
     * @return 数组去空值
     */
    public static List<String> removeNullValue(String array[]) {
        List<String> strList = new ArrayList<String>();
        for (String str : array) {
            if (str != null && !str.equals("")) {
                strList.add(str);
            }

        }
        return strList;
    }


    /**
     * list去重复
     *
     * @param list
     */
    public static void removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }


    /**
     * @param array
     * @param split
     * @return 数组分割用自定义标点连接
     */
    public static String array2Join(String array[], String split) {
        String str = "";
        for (String s : array) {
            str += s + split;
        }
        return str;
    }


    /**
     * @return 将数据库的列名转换实体字段
     */
    public static String transformColName2ColName() {

        String dbColName = "area_no";


        return "";
    }


    /**
     * @param split       分隔符
     * @param repeatTimes 重复次数
     * @return
     */
    public static String repeatSplit(String split, int repeatTimes) {
        for (int i = 0; i < repeatTimes; i++) {
            split += split;
        }
        return split;
    }


    /**
     * @param str
     * @param n
     * @return
     */
    public static String strCopy(String str, int n) {
        String result = "";//初始化为空串
        for (int i = 0; i < n; i++) {
            result = result.concat(str);
        }
        return result;
    }


    /**
     * @param strList
     * @return 去除字符串数组中多余的元素
     */
    public static List<String> removeDuplicateElements(List<String> strList) {
        if (strList == null || strList.size() == 0) {
            return null;
        }
        List<String> listTemp = new ArrayList();
        for (int i = 0; i < strList.size(); i++) {
            if (!listTemp.contains(strList.get(i))) {
                listTemp.add(strList.get(i));
            }
        }
        return listTemp;
    }


    /**
     * @param str 判断字符串为数值型
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * @param suffix
     * @param ending
     * @param length
     * @return
     */
    public static String genServiceCode(String suffix, String ending, Long length) {
        String serviceCode = "";
        //如果ending是奇数位数，就在前面加"0"，如果ending是偶数位数，直接链接在前缀suffix后。
        if (ending.length() < length && (ending.length()) % 2 != 0) {
            ending = "0" + ending;
            serviceCode = suffix + ending;
        } else if (ending.length() < length && (ending.length()) % 2 == 0) {
            serviceCode = suffix + ending;
        }

        return serviceCode;
    }


    /**
     * @param path
     * @return
     */
    public static String getDirUrl(String path) {
        String d = path.substring(0, path.lastIndexOf("/"));
        System.out.println("--------------------" + d);
        return d;
    }


    /**
     * @param path 文件重命名
     * @return
     */
    public static String renameFile(String path) {
        String fileName;
        fileName = path.replace(".", "_sm.");
        log.info("renameFile-----  new fileName--------------" + fileName);
        return fileName;
    }


    /**
     * @param path 文件重命名
     * @return
     */
    public static String renameNgnixFile(String path) {
        String array[] = path.split("/");
        int length = array.length;
        String fileName = "";
        if (array.length > 0 && array[length - 1] != null) {
            fileName = array[length - 1].replace(".", "_sm.");
            log.info("renameNgnixFile-----  new fileName--------------" + fileName);
        }

        String url = "";
        for (int i = 0; i < array.length - 1; i++) {
            url += array[i] + "/";
        }
//        try {
//            fileName = new String((url + fileName).getBytes(FTPUploader.LOCAL_CHARSET), FTPUploader.SERVER_CHARSET);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        fileName = url + fileName;
        log.info(fileName);
        return fileName;
    }


    /**
     * @param str
     * @param code0
     * @param code1
     * @return
     */
    public static String convert2UTF8(String str, String code0, String code1) {
        log.info("before-------------" + str);
        try {
            str = new String(str.getBytes(code0), code1);
            System.out.println(str);//hello中国人
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("after-------------" + str);
        return str;
    }

}






