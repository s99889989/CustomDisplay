package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;

import java.text.DecimalFormat;
import java.util.List;

public class NumberUtil {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String decimalString;

    private String nineString;

    private String nineThreeString;

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


    /**單數字轉換三種版**/
    public NumberUtil(String string, List<String> stringHead, List<String> stringDouble, List<String> stringUnits){
        String lastString = "";
        char[] c = string.toCharArray();
        String[] strings = new String[c.length];
        for(int i = 0 ; i < c.length; i++){
            if(i == 0){
                strings[i] = getString(String.valueOf(c[i]),stringHead);
            }else if((i+1)%2 == 0){
                strings[i] = getString(String.valueOf(c[i]),stringDouble);
            }else {
                strings[i] = getString(String.valueOf(c[i]),stringUnits);
            }
        }
        for(String value : strings){
            lastString = lastString + value;
        }
        nineThreeString = lastString;
    }

    public String getString(String string, List<String> stringList){
        for(String value : stringList){
            String[] sv = value.split(",");
            string = string.replace(sv[0],sv[1]);
        }
        return string;
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

    public String getNineThreeString() {
        return nineThreeString;
    }

    public String getTenString() {
        return tenString;
    }
}
