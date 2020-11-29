package com.daxton.customdisplay;

import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args){
        String firstString = "Condition[Compare= &player_health_10& < 5 ]";

        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[=]<> ");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());

        }
        if(stringList.size() == 4){
            //String[] strings = stringList.toArray(new String[stringList.size()]);
            //System.out.print(strings[3]);
        }
        System.out.println(Math.min(0,2)+">");




    }
}
