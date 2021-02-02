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
        String inputString = "10|5|10";
        String[] startlocadds = inputString.split("\\|");
        for (String s : startlocadds){
            System.out.println(s);
        }
        if(startlocadds.length == 5){

            System.out.println(startlocadds[0]+" : "+startlocadds[2]+" : "+startlocadds[4]);

        }


    }



}
