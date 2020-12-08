package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConversion {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public StringConversion(){

    }

    public String getString(String folderName, String firstString, Player player){

        if(firstString.contains("&")){
            String findText = "&";
            int num = appearNumber(firstString, findText);
            for(int i = 0; i < num/2 ; i++){
                int head = firstString.indexOf("&");
                int tail = firstString.indexOf("&",head+1);
                String change = customString(folderName,firstString.substring(head,tail+1),player);
                firstString = firstString.replace(firstString.substring(head,tail+1),change);
            }
        }
        if(firstString.contains("%")){
            firstString = PlaceholderAPI.setPlaceholders(player,firstString);
        }
        return firstString;

    }

    /**對自訂字串進行關於Character資料夾內的設定處理**/
    public String customString(String folderName, String firstString2,Player player){
        firstString2 = firstString2.replace("&","");
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
                double number = 0;
                try {
                    number = Arithmetic.eval(outputString);
                    outputString = new NumberUtil(number,stl[1]).getDecimalString();
                }catch (Exception exception){
                    outputString = ChatColor.RED+ "'&"+ firstString2 + "& has a problem in computing.'" + ChatColor.WHITE;
                }

            }
            if(string.contains("conver=")){
                String[] stl1 = string.split("=");
                String[] stl2 = stl1[1].split(";");
                for(String stringList2 : stl2){
                    String[] stl3 = stringList2.split(",");
                    if(outputString.replace(stl3[0],"").length() == 0){
                        outputString = outputString.replace(stl3[0],stl3[1]);
                        if(outputString.equals(stl3[1])){
                            break;
                        }
                    }
                }
            }
        }
        return outputString;
    }

    /**計算指定單字出現次數**/
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }


}
