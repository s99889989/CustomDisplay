package com.daxton.customdisplay;

import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args){
        String firstString = "ActionBar[message=%player_health%/%player_max_health%&player_health_proportion&%mmocore_mana%/%mmocore_stat_max_mana%&player_mana_proportion&]";
        if(firstString.contains("&")){
            String findText = "&";
            int num = appearNumber(firstString, findText);
            int head;
            int tail = 0;
            for(int i = 0; i < num/2;i++){
                head = firstString.indexOf("&",tail+1);
                tail = firstString.indexOf("&",head+1);
                System.out.println(firstString.substring(head,tail+1));
//                String change = customString(folderName,firstString.substring(head,tail+1),player);
//                firstString = firstString.replace(firstString.substring(head,tail+1),change);
            }
        }
//        List<String> stringList = new ArrayList<>();
//        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
//        while (stringTokenizer.hasMoreElements()){
//            stringList.add(stringTokenizer.nextToken());
//
//        }
//        for(String allString : stringList){
//
//
//        }

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
