package com.daxton.customdisplay;




import com.daxton.customdisplay.api.character.ReplaceTrigger;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.other.StringFind;

import java.text.DecimalFormat;
import java.util.*;




public class Test {
    public static final int MIN = 1;

    private static String[] suffix = new String[]{"","k","m","b","t"};
    private static int MAX_LENGTH = 4;

    public static void main(String[] args) {
        String test = "CraftBlockData{minecraft:lever[face=wall,facing=north,powered=false]}";
        int start = test.indexOf("powered=")+8;
        int end = test.indexOf("]", start);
        String name = test.substring(start, end);
        System.out.println(test);
        System.out.println(name);
        System.out.println(test.length()+" : "+start);
    }





}
