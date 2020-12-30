package com.daxton.customdisplay;

import com.daxton.customdisplay.api.character.NumberUtil;
import com.daxton.customdisplay.api.character.Placeholder;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Test {



    public static void main(String[] args){

        String content = "<cd_nowhealth>*<cd_nowhealth>*<cd_nowhealth>";
        int numHead = NumberUtil.appearNumber(content, "<");
        int numTail = NumberUtil.appearNumber(content, ">");
        //System.out.println(numHead+" : "+numTail);
        if(numHead == numTail){
            for(int i = 0; i < numHead ; i++){
                int head = content.indexOf("<");
                int tail = content.indexOf(">");
                if(content.contains("<") && content.substring(head,tail+1).toLowerCase().contains("<cd_other_")){
                    content = content.replace(content.substring(head,tail+1),new Placeholder(content.substring(head,tail+1)).getString());
                }else if(content.contains("<") && content.substring(head,tail+1).toLowerCase().contains("<cd_")){
                    content = content.replace(content.substring(head,tail+1),"Test");
                }else {
                    break;
                }
            }
        }
        System.out.println(content);




//        String str = "<cd_nowhealth>*<cd_nowaealth>";
//
//        char letter = '<';
//
//        int count = 0;
//        for(int i = 0; i < str.length(); i++){
//            char c = str.charAt(i);
//            if(c == letter)
//                count++;
//        }
//
//        System.out.println(letter + " Number: " + count + " :time");








    }



}
