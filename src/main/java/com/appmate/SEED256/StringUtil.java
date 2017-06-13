package com.appmate.SEED256;

/**
 * Created by uujc0207 on 2017. 3. 26..
 */
public class StringUtil {

    /**
     * (length - str.length) 만큼 앞에 0을 추가한다.
     * @param str
     * @param length
     * @return
     */
    public static String addZero (String str, int length) {
        String temp = "";
        for (int i = str.length(); i < length; i++)
            temp += "0";
        temp += str;
        return temp;
    }

    public static boolean isEmpty(String str) {
        if(str == null || str.length() == 0) {
            return true;
        }else {
            return false;
        }
    }

}