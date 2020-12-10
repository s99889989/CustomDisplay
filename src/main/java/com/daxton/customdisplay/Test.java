package com.daxton.customdisplay;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    private static Map<String,Double> doubleMap = new HashMap<>();

    public static void main(String[] args){
        String firstString = "<cd_target_name><cd_target_uuid><cd_target_type>";
        int numHead = appearNumber(firstString, "<");
        int numTail = appearNumber(firstString, ">");
        if(numHead == numTail){
            for(int i = 0; i < numHead ; i++){
                int head = firstString.indexOf("<");
                int tail = firstString.indexOf(">");
                firstString = firstString.replace(firstString.substring(head,tail+1)," change ");
            }
        }

        System.out.println(firstString);
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



}
