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
        String firstString = "&player_health_proportion&    &player_mana_proportion&";
        String findText = "&";
        int num = appearNumber(firstString, findText);
        for(int i = 0; i < num/2;i++){
            int head = firstString.indexOf("&");
            int tail = firstString.indexOf("&",head+1);
            System.out.println(firstString.substring(head,tail+1));
            firstString = firstString.replace(firstString.substring(head,tail+1),"123");
        }
        System.out.println(firstString);

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
