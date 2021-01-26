package com.daxton.customdisplay;



import com.daxton.customdisplay.api.other.StringFind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Test {


    public static void main(String[] args){
        String firstString = "Command[message=/mm egg give s99889989 Test1 1]";

        String message = getKeyValue(firstString,"[]", "message=","m=");
        System.out.println("--------");
        System.out.println(message);
        System.out.println("--------");

    }

    public static String getKeyValue(String string,String cut,String... key){
        List<String> KeyList = getBlockList(string,cut);
        String[] itemIDStrings = KeyList.stream().filter(s -> Arrays.stream(key).anyMatch(s::contains)).collect(Collectors.joining()).split("=");
        String outPut = "";
        if(itemIDStrings.length == 2){
            outPut = itemIDStrings[1];
        }
        return outPut;
    }

    public static List<String> getBlockList(String string,String key){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,key);
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }

}
