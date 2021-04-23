package com.daxton.customdisplay;




import com.daxton.customdisplay.api.character.ReplaceTrigger;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.other.StringFind;

import java.util.*;




public class Test {
    public static final int MIN = 1;



    public static void main(String[] args) {
        String content = "&bsb_target_nowhealth&~0.0";
        if(content.contains(">")){
            String stringLeft = content.substring(0, content.indexOf(">"));
            String stringRight = content.substring(content.indexOf(">")+1);
        }else if(content.contains("<")){
            String stringLeft = content.substring(0, content.indexOf("<"));
            String stringRight = content.substring(content.indexOf("<")+1);

        }else if(content.contains("~")){
            String stringLeft = content.substring(0, content.indexOf("~"));
            String stringRight = content.substring(content.indexOf("~")+1);
            System.out.println(stringLeft);
            System.out.println(stringRight);
        }



    }






}
