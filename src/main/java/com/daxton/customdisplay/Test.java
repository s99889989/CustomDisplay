package com.daxton.customdisplay;

import com.daxton.customdisplay.api.character.Calculator;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args){

        //String expression = "(0*1--3)-5/-4-(3*(-2.13))";
        String expression = "100*10";
        double result = Calculator.conversion(expression);
        System.out.println(expression +  " = " +  result);
        System.out.println("----------");


    }


}
