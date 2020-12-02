package com.daxton.customdisplay;

import com.daxton.customdisplay.api.character.Calculator;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.Iterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    private static Map<String,Double> doubleMap = new HashMap<>();

    public static void main(String[] args){
        String firstString = "&player_health_now&/%player_max_health%\uF839\uF80B&player_health_proportion&\uF839%mmocore_mana%/%mmocore_stat_max_mana%\uF80A&player_mana_proportion&";
        String findText = "&";
        int num = appearNumber(firstString, findText);
        int head;
        int tail = 0;
        for(int i = 0; i < num/2;i++){
            if(i == 0){
                head = firstString.indexOf("&",0);
            }else {
                head = firstString.indexOf("&",tail+1);
            }
            tail = firstString.indexOf("&",head+1);
            System.out.println(firstString.substring(head,tail+1));
//            firstString = firstString.replace(firstString.substring(head,tail+1),"轉換");
        }
        //System.out.println(firstString+":"+num);

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
