package com.daxton.customdisplay.util;

import java.text.DecimalFormat;
import java.util.List;

public class NumberUtil {

    private String decimalString;

    private String nineString;

    private String tenString;

//    public NumberUtil(){
//
//    }

    /**位數轉換**/
    public NumberUtil(double number, String decimal){
        DecimalFormat decimalFormat = new DecimalFormat(decimal);
        String string = decimalFormat.format(number);
        if(string.endsWith(".")){
            string = string.replace(".0","");
        }
        decimalString = string;
    }

    /**單數字轉換**/
    public NumberUtil(String string, List<String> stringList){
        for(String value : stringList){
            String[] sv = value.split(",");
            string = string.replaceAll(sv[0],sv[1]);
        }
        nineString = string;
    }

    /**數字1~10轉換**/
    public NumberUtil(int number, List<String> stringList){
        String first = String.valueOf(number);
        for(String value : stringList){
            String[] sv = value.split(",");
            if(number == 10){
                first = first.replaceAll(sv[0],sv[1]);
                break;
            }
            first = first.replaceAll(sv[0],sv[1]);
        }
        tenString = first;
    }


    public String getDecimalString() {
        return decimalString;
    }

    public String getNineString() {
        return nineString;
    }

    public String getTenString() {
        return tenString;
    }
}
