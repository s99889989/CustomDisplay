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
        String stringNumber = "54328";
        char[] c = stringNumber.toCharArray();
        String[] strings = new String[c.length];
        for(int i = 0 ; i < c.length; i++){
            if(i == 0){
                strings[i] = "head:" + c[i];
            }else if((i+1)%2 == 0){
                strings[i] = "two:" + c[i];
            }else {
                strings[i] = "one:" + c[i];
            }
        }
        for(String ss : strings){
            System.out.println(ss);
        }


    }


}
