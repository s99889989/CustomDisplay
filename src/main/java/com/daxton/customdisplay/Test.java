package com.daxton.customdisplay;

import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args){
        String firstString = "\uF80B&player_&%player_health%/&proportion&%player_max_health%&player_health_proportion&\uF80B";


            if(firstString.contains("&")){
                String findText = "&";
                int num = appearNumber(firstString, findText);
                int head;
                int tail = 0;
                for(int i = 0 ; i < num/2 ; i++){
                    head = firstString.indexOf("&",tail+1);
                    tail = firstString.indexOf("&",head+1);
                    String change = changeString(firstString.substring(head,tail+1));
                    firstString = firstString.replace(firstString.substring(head,tail+1),change);
                }
            }

            System.out.println(firstString);
    }

    public static String changeString(String string){
        string = "'改變'";

        return string;
    }

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
