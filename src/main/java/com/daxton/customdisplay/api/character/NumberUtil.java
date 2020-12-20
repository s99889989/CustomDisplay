package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String decimalString;

    private String nineThreeString;

    public NumberUtil(){

    }

    /**位數轉換**/
    public NumberUtil(double number, String decimal){
        DecimalFormat decimalFormat = new DecimalFormat(decimal);
        String string = decimalFormat.format(number);
        if(string.endsWith(".")){
            string = string.replace(".0","");
        }
        decimalString = string;
    }


    /**對首位字做處理**/
    public String NumberHead(String string, String content){
        String lastString = "";
        char[] c = string.toCharArray();
        String[] strings = new String[c.length];
        for(int i = 0 ; i < c.length; i++){
            if(i == 0){
                strings[i] = NumberChange(String.valueOf(c[i]),content);

            }else {
                strings[i] = String.valueOf(c[i]);
            }
        }
        for(String value : strings){
            lastString = lastString + value;
        }
        return lastString;
    }

    /**對單位字做處理，不包括首位字**/
    public String NumberUnits(String string, String content){
        String lastString = "";

        char[] c = string.toCharArray();
        String[] strings = new String[c.length];
        for(int i = 0 ; i < c.length; i++){
            if(i != 0 & (i+1)%2 != 0){
                strings[i] = NumberChange(String.valueOf(c[i]),content);

            }else {
                strings[i] = String.valueOf(c[i]);
            }
        }
        for(String value : strings){
            lastString = lastString + value;
        }

        return lastString;
    }

    /**對雙位字做處理，不包括首位字**/
    public String NumberDouble(String string, String content){

        String lastString = "";
        char[] c = string.toCharArray();
        String[] strings = new String[c.length];
        for(int i = 0 ; i < c.length; i++){
            if( i != 0 & (i+1)%2 == 0 ){

                strings[i] = NumberChange(String.valueOf(c[i]),content);

            }else {
                strings[i] = String.valueOf(c[i]);
            }
        }
        for(String value : strings){
            lastString = lastString + value;
        }
        return lastString;
    }

    public String NumberChange(String string,String content){

        String[] stl2 = content.split(";");
        for(String stringList2 : stl2){
            String[] stl3 = stringList2.split(",");
            if(string.replace(stl3[0],"").length() == 0){
                string = string.replace(stl3[0],stl3[1]);

            }
        }
        return string;
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

    /**計算指定單字出現次數**/
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    public String getDecimalString() {
        return decimalString;
    }


    public String getNineThreeString() {
        return nineThreeString;
    }


}
