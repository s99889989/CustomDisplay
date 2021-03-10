package com.daxton.customdisplay;



import com.daxton.customdisplay.api.other.StringFind;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Test {


    public static void main(String[] args) {
        String outPut = "self";
        String firstString = "Damage[Type=SKILL_MELEE_PHYSICAL_ATTACK;Operate=multiply;Amount=&self_class_skill_Novice_Slam_amount&] @=selfradius{Ignore=XXX;target=XXX;R=2}";
        String key = "r";
        if(firstString.contains("@=")){
            String[] strings = firstString.split("@=");
            if(key.contains("aims")){
                String[] strings1 = strings[1].split("\\{");
                //System.out.println(strings1[0]);
                outPut = strings1[0];
            }
            if(key.contains("r")){
                for(String s : getBlockList(strings[1],"{;}")){
                    if(s.toLowerCase().contains("r=")){
                        String[] strings1 = s.split("=");
                        outPut = strings1[1];
                    }
                }
            }
            if(key.contains("ignore")){
                for(String s : getBlockList(strings[1],"{;}")){
                    if(s.toLowerCase().contains("ignore=")){
                        String[] strings1 = s.split("=");
                        outPut = strings1[1];
                    }
                }
            }
            if(key.contains("target")){
                for(String s : getBlockList(strings[1],"{;}")){
                    if(s.toLowerCase().contains("target=")){
                        String[] strings1 = s.split("=");
                        outPut = strings1[1];
                    }
                }
            }
            if(key.contains("f")){
                for(String s : getBlockList(strings[1],"{;}")){
                    if(s.toLowerCase().contains("f=")){
                        String[] strings1 = s.split("=");
                        outPut = strings1[1];
                    }
                }
            }


        }

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
