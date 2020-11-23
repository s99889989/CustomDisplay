package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.util.ArithmeticUtil;
import com.daxton.customdisplay.util.NumberUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringConversion {

    private Player player;

    /**丟入要比對的資料夾 字串 玩家資料**/
    public StringConversion(String folderName, String firstString, Player player){
        this.player = player;
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,",");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        String stringAdd = "";
        for(String inputString : stringList){
            if(inputString.contains("%")){
                inputString = PlaceholderAPI.setPlaceholders(player,inputString);
            }
            if(inputString.contains("&")){
                /**自訂字串的處理**/
                inputString = customString(folderName,inputString);
            }
            stringAdd = stringAdd + inputString;
        }
    }

    /**對自訂字串進行關於Character資料夾內的設定處理**/
    public String customString(String folderName, String firstString2){
        String outputString = "";
        List<String> list = new ConfigFind().getCharacterMessageList(folderName,firstString2);
        for(String string : list){
            if(string.contains("papi;")){
                String[] stl = string.split(";");
                outputString = PlaceholderAPI.setPlaceholders(player,stl[1]);
            }
            if(string.contains("math;")){
                String[] stl = string.split(";");
                outputString = PlaceholderAPI.setPlaceholders(player,stl[2]);
                outputString = new ArithmeticUtil().valueof(outputString);
                double number = Double.valueOf(outputString);
                outputString = new NumberUtil(number,stl[1]).getDecimalString();
            }
            if(string.contains("conver=")){
                String[] stl1 = string.split("=");
                String[] stl2 = stl1[1].split(";");
                for(String stringList2 : stl2){
                    String[] stl3 = stringList2.split(",");
                    outputString = outputString.replaceAll(stl3[0],stl3[1]);
                }
            }
        }
        return outputString;
    }


}
