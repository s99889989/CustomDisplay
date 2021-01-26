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

    /**丟入整個動作 返回動作第一個關鍵字**/
    public String getAction2(String string){
        String lastString = "";
        List<String> stringList = getStringMessageList(string);
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

    /**找尋動作內的觸發動作名稱**/
    public String findActionName(String firstString){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        if(stringList.size() > 0){
            for(String string : stringList){
                if(string.toLowerCase().contains("action")){
                    actionName = string;
                }
                if(string.toLowerCase().contains("action=") || string.toLowerCase().contains("a=")){
                    String[] strings = string.split("=");
                    if(strings.length == 2){
                        triggerActionName = strings[1];
                    }
                }
            }
        }

        return triggerActionName;
    }

    /**丟入字串按照[;]轉成List**/
    public List<String> getStringListClass(String string){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,",");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken().replace(" ",""));
        }
        return stringList;
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
    public String getKeyValue(LivingEntity self, LivingEntity target, String string, String cut, String... key){
        List<String> KeyList = getBlockList(string,cut);
        String[] itemIDStrings = KeyList.stream().filter(s -> Arrays.stream(key).anyMatch(s.toLowerCase()::contains)).collect(Collectors.joining()).split("=");
        String outPut = "null";
        if(itemIDStrings.length == 2){
            outPut = itemIDStrings[1];
            if(outPut.contains("&")){
                outPut = new ConversionMain().valueOf(self,target,outPut);
            }
        }
        return outPut;
    }



    /**丟入字串和key 轉成List**/
    public List<String> getBlockList(String string,String key){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,key);
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }

}
