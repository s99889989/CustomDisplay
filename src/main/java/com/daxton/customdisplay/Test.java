package com.daxton.customdisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Test {


    public static void main(String[] args){
        String firstString = "\uF80B,%player_health%/%player_max_health%,&player_health_proportion&,\uF80B";
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,",");
        while (stringTokenizer.hasMoreElements()){
            //System.out.println(stringTokenizer.nextToken());
            stringList.add(stringTokenizer.nextToken());
        }
        for(String string : stringList){
            if(string.contains("%")){
                System.out.println(string);
            }
            if(string.contains("&")){
                System.out.println(string);
            }
        }

    }

}
