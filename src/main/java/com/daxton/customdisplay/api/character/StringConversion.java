package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class StringConversion {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String ResultString = "";
    private String folderName = "";
    private String firstString = "";

    public StringConversion(String folderName, String firstString, LivingEntity target){
        this.folderName = folderName;
        this.firstString = firstString;
        setString(target);
    }


    public void setString(LivingEntity target){
        if(target instanceof Player){
            Player player = (Player) target;
            if(firstString.contains("&")){
                String findText = "&";
                int num = appearNumber(firstString, findText);
                for(int i = 0; i < num/2 ; i++){
                    int head = firstString.indexOf("&");
                    int tail = firstString.indexOf("&",head+1);
                    String change = customString(folderName,firstString.substring(head,tail+1),target);
                    firstString = firstString.replace(firstString.substring(head,tail+1),change);
                }
            }
            if(firstString.contains("%") && target instanceof Player){
                firstString = PlaceholderAPI.setPlaceholders(player,firstString);
            }
        }else {
            if(firstString.contains("&")){
                String findText = "&";
                int num = appearNumber(firstString, findText);
                for(int i = 0; i < num/2 ; i++){
                    int head = firstString.indexOf("&");
                    int tail = firstString.indexOf("&",head+1);
                    String change = customString(folderName,firstString.substring(head,tail+1),target);
                    firstString = firstString.replace(firstString.substring(head,tail+1),change);
                }
            }
        }


        ResultString = firstString;
    }

    /**對自訂字串進行關於&Character&資料夾內的設定處理**/
    public String customString(String folderName, String firstString2,LivingEntity target){
        firstString2 = firstString2.replace("&","");
        String outputString = "";
        List<String> list = new ConfigFind().getCharacterMessageList(folderName,firstString2);
        if(target instanceof Player){
            Player player = (Player) target;
            for(String string : list){
                String headString = new StringFind().getAction(string);
                String content = string.replace(headString,"").replace("[","").replace("]","");

                if(headString.toLowerCase().contains("customcharacter") || headString.toLowerCase().contains("cc")){
                    if(content.contains("<") && content.contains(">")){
                        outputString = pluginString(content,target);
                    }
                    continue;
                }

                if(headString.toLowerCase().contains("papi") || headString.toLowerCase().contains("placeholder")){
                    outputString = PlaceholderAPI.setPlaceholders(player,content);
                    continue;
                }

                if(headString.toLowerCase().contains("math")){
                    if(content.toLowerCase().contains("%")){
                        outputString = PlaceholderAPI.setPlaceholders(player,content);
                        if(outputString.contains("<") && outputString.contains(">")){
                            outputString = pluginString(outputString,target);
                        }
                    }else {
                        if(content.contains("<") && content.contains(">")){
                            outputString = pluginString(content,target);
                        }
                    }
                    try {
                        double number = Arithmetic.eval(outputString);
                        outputString = String.valueOf(number);
                    }catch (Exception exception){
                        outputString = ChatColor.RED+ "'&"+ firstString2 + "& has a problem in computing.'" + ChatColor.WHITE;
                    }
                    continue;
                }

                if(headString.toLowerCase().contains("decimal") || headString.toLowerCase().contains("dec")){
                    try{
                        double number = Double.valueOf(outputString);
                        outputString = new NumberUtil(number,content).getDecimalString();
                    }catch (NumberFormatException exception){

                    }
                    continue;
                }

                if(headString.toLowerCase().contains("convercontains")){
                    String[] stl2 = content.split(",");
                    if(outputString.contains(stl2[0])){
                        outputString = stl2[1];
                    }
                    continue;
                }

                if(headString.toLowerCase().contains("converall")){
                    String[] stl2 = content.split(";");
                    for(String stringList2 : stl2){
                        String[] stl3 = stringList2.split(",");
                        if(outputString.replace(stl3[0],"").length() == 0){
                            outputString = outputString.replace(stl3[0],stl3[1]);
                            if(outputString.equals(stl3[1])){
                                break;
                            }
                        }
                    }
                    continue;
                }

                if(headString.toLowerCase().contains("conver")){
                    String[] stl2 = content.split(";");
                    for(String stringList2 : stl2){
                        String[] stl3 = stringList2.split(",");
                        outputString = outputString.replace(stl3[0],stl3[1]);
                    }
                    continue;
                }




            }
        }else {
            for(String string : list){
                String headString = new StringFind().getAction(string);
                String content = string.replace(headString,"").replace("[","").replace("]","");

                if(headString.toLowerCase().contains("customcharacter") || headString.toLowerCase().contains("cc")){
                    if(content.contains("<") && content.contains(">")){
                        outputString = pluginString(content,target);
                    }
                    continue;
                }

                if(headString.toLowerCase().contains("math")){
                    if(content.contains("<") && content.contains(">")) {
                        outputString = pluginString(content,target);
                    }

                    try {
                        double number = Arithmetic.eval(outputString);
                        outputString = String.valueOf(number);
                    }catch (Exception exception){
                        outputString = ChatColor.RED+ "'&"+ firstString2 + "& has a problem in computing.'" + ChatColor.WHITE;
                    }
                    continue;
                }

                if(headString.toLowerCase().contains("decimal") || headString.toLowerCase().contains("dec")){
                    try{
                        double number = Double.valueOf(outputString);
                        outputString = new NumberUtil(number,content).getDecimalString();
                    }catch (NumberFormatException exception){
                    }

                    continue;
                }

                if(headString.toLowerCase().contains("convercontains")){
                    String[] stl2 = content.split(",");
                    if(outputString.contains(stl2[0])){
                        outputString = stl2[1];
                    }
                    continue;
                }

                if(headString.toLowerCase().contains("converall")){
                    String[] stl2 = content.split(";");
                    for(String stringList2 : stl2){
                        String[] stl3 = stringList2.split(",");
                        if(outputString.replace(stl3[0],"").length() == 0){
                            outputString = outputString.replace(stl3[0],stl3[1]);
                            if(outputString.equals(stl3[1])){
                                break;
                            }
                        }
                    }
                    continue;
                }

                if(headString.toLowerCase().contains("conver")){
                    String[] stl2 = content.split(";");
                    for(String stringList2 : stl2){
                        String[] stl3 = stringList2.split(",");
                        outputString = outputString.replace(stl3[0],stl3[1]);
                    }
                    continue;
                }


            }
        }


        return outputString;
    }

    /**對伺服器內的<>進行處理**/
    public String pluginString(String content,LivingEntity target){
        String outputString = "";
        int numHead = appearNumber(content, "<");
        int numTail = appearNumber(content, ">");
        if(numHead == numTail){
            for(int i = 0; i < numHead ; i++){
                int head = content.indexOf("<");
                int tail = content.indexOf(">");
                if(content.substring(head,tail+1).toLowerCase().contains("<cd_other_")){
                    content = content.replace(content.substring(head,tail+1),new Placeholder(content.substring(head,tail+1)).getString());
                }else if(content.substring(head,tail+1).toLowerCase().contains("<cd_") && target != null){
                    content = content.replace(content.substring(head,tail+1),new Placeholder(target,content.substring(head,tail+1)).getString());
                }else {
                    continue;
                }
            }
        }
        outputString = content;
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

    public String getResultString() {
        return ResultString;
    }
}
