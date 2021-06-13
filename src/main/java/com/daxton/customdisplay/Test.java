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
        String test = "Optional[TextComponent{text='', siblings=[TextComponent{text='ME', siblings=[], style=Style{ color=null, bold=null, italic=null, underlined=null, strikethrough=null, obfuscated=null, clickEvent=null, hoverEvent=null, insertion=null, font=minecraft:default}}], style=Style{ color=null, bold=null, italic=null, underlined=null, strikethrough=null, obfuscated=null, clickEvent=null, hoverEvent=null, insertion=null, font=minecraft:default}}]";
        int start = test.indexOf("s=[TextComponent{text='")+23;
        int end = test.indexOf("',", start);
        String name = test.substring(start, end);
        System.out.println(test);
        System.out.println(name);
    }





}
