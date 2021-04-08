package com.daxton.customdisplay;




import com.daxton.customdisplay.api.character.ReplaceTrigger;
import com.daxton.customdisplay.api.other.NumberUtil;

import java.util.*;




public class Test {




    public static void main(String[] args) {
        Set<String> stringSet = new HashSet<>();
        List<String> stringList = new ArrayList<>();
        Map<String ,String> stringStringMap = new HashMap<>();
        String s1 = "A";
        String s2 = "B";
        String s3 = s1 +s2+"123";

        for (int i = 0; i < 10; i++) {
            stringStringMap.put(s3, s3);
//            if(stringSet.add(s3)){
//                stringList.add(s3);
//            }
        }

        stringStringMap.forEach((s, s21) -> {
            System.out.println(s+" : "+s21);
        });

//        stringList.forEach(s -> {
//            System.out.println(s);
//        });
    }




}
