package com.daxton.customdisplay.api.other;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class StringFind {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();
    /**動作名稱**/
    private String actionName = "";
    /**觸發動作名稱**/
    private String triggerActionName = "";

    public StringFind(){

    }

    /**丟入整個動作 返回動作第一個關鍵字**/
    public String getAction(String string){
        String lastString = "";
        List<String> stringList = getStringList(string);
        if(stringList.size() > 0){
            String[] strings = stringList.toArray(new String[stringList.size()]);
            lastString = strings[0].replace(" ","");
        }
        return lastString;
    }

    /**丟入整個自訂字 返回動作第一個關鍵字**/
    public String getHead(String string){
        String lastString = "";
        List<String> stringList = getStringList2(string);
        if(stringList.size() > 0){
            String[] strings = stringList.toArray(new String[stringList.size()]);
            lastString = strings[0].replace(" ","");
        }
        return lastString;
    }

    /**丟入整個自訂字 返回自訂字內容**/
    public String getContent(String string){
        String lastString = "";
        List<String> stringList = getStringList2(string);
        if(stringList.size() > 1){
            String[] strings = stringList.toArray(new String[stringList.size()]);
            lastString = strings[1];
        }
        return lastString;
    }
    /**丟入整個自訂字 返回自訂字目標**/
    public String getTarget(String string){
        String lastString = "";
        List<String> stringList = getStringList2(string);
        if(stringList.size() == 3){
            String[] strings = stringList.toArray(new String[stringList.size()]);
            lastString = strings[2].replace(" ","");
        }
        return lastString;
    }


    /**丟入字串按照[;]轉成List**/
    public List<String> getStringList(String string){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,"[;] ");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }
    /**丟入字串按照[]轉成List**/
    public List<String> getStringList2(String string){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,"[]");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }
    /**丟入字串按照[;] 轉成List，Message專用**/
    public List<String> getStringMessageList(String string){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,"[;]");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }

    /**丟入字串,key和關鍵字 回傳值**/
    public String getAimsValue(String firstString,String key,String defaultValue){
        String outPut = defaultValue;
        if(firstString.contains("@")){
            String[] strings = firstString.split("@");
            if(key.equals("aims")){
                String[] strings1 = strings[1].split("\\{");
                outPut = strings1[0];
            }
            if(key.equals("r")){
                for(String s : getBlockList(strings[1],"{;}")){
                    if(s.toLowerCase().contains("r=")){
                        String[] strings1 = s.split("=");
                        outPut = strings1[1];
                    }
                }
            }
            if(key.equals("filters")){
                for(String s : getBlockList(strings[1],"{;}")){
                    if(s.toLowerCase().contains("filters=")){
                        String[] strings1 = s.split("=");
                        outPut = strings1[1];
                    }
                }
            }
            if(key.equals("f")){
                for(String s : getBlockList(strings[1],"{;}")){
                    if(s.toLowerCase().contains("f=")){
                        String[] strings1 = s.split("=");
                        outPut = strings1[1];
                    }
                }
            }
        }

        return outPut;
    }


    /**丟入字串和key 轉成List**/
    public static List<String> getBlockList(String string,String key){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,key);
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }

}
