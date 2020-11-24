package com.daxton.customdisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Test {


    public static void main(String[] args){
        String firstString = "createHD[m=\uF80B{cd_damage}\uF80B,x=0,y=0.5,z=0,period=1] @=targetLocation";

        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[,] ");
        int i = 0;
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        String[] toBeStored = stringList.toArray(new String[stringList.size()]);
        System.out.println(toBeStored[0]);
        System.out.println(stringList.size());
    }

}
