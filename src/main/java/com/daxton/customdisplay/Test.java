package com.daxton.customdisplay;



import com.daxton.customdisplay.api.other.StringFind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Test {


    public static void main(String[] args){
        String inputString = "{#f77703}劍";

        String key1 = inputString.substring(2,3);
        String key2 = inputString.substring(3,4);
        String key3 = inputString.substring(4,5);
        String key4 = inputString.substring(5,6);
        String key5 = inputString.substring(6,7);
        String key6 = inputString.substring(7,8);
        inputString = "§x§"+key1+"§"+key2+"§"+key3+"§"+key4+"§"+key5+"§"+key6;

        System.out.println(inputString);

    }



}
