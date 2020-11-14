package com.daxton.customdisplay.util;

import com.daxton.customdisplay.CustomDisplay;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class ContentUtil {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String outputString;

    public ContentUtil(String inputString, Player player,String folderName){
        String[] inputList = inputString.split(",");
        String addString = "";
        for(String inputString1 : inputList){
            if(inputString1.contains("%")){
                inputString1 = PlaceholderAPI.setPlaceholders(player,inputString1);
            }
            if(inputString1.contains("&")){
                inputString1 = setCustomString(player,inputString1,folderName);
            }
            addString = addString + inputString1;
        }
        outputString = addString;
    }

    /**自訂字串的處理**/
    public String setCustomString(Player player,String string,String folderName){
        /**依序傳入資料夾內檔案名稱**/
        for (String key : folderName(folderName)) {
            /**讀取丟進來的檔案**/
            File filekey = new File(cd.getDataFolder(),folderName+"\\"+key);
            /**獲取給定資料夾名稱的設定檔案**/
            FileConfiguration config = YamlConfiguration.loadConfiguration(filekey);
            /**獲取比對設定裡的第一排名稱**/
            ConfigurationSection configSection = config.getConfigurationSection("");
            for (String keySection : configSection.getKeys(false)) {
                if (string.contains(keySection)) {
                    string = changeContent(string, keySection, config, player);
                }
            }

        }
        return string;
    }
    /**自訂字串的內容處理**/
    public String changeContent(String inputString,String key,FileConfiguration config,Player player){
        String outputString = "";
        List<String> stringList = config.getStringList(key+".message");
        for(String string : stringList){
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

    /**傳入資料夾，回傳資料夾裡面的文件名稱陣列**/
    public String[] folderName(String path){
        File loadFile = new File(cd.getDataFolder(),path);
        String[] result = loadFile.list();
        return result;
    }

    /**傳入數學字串，回傳運算結果字串**/
    public String math(String inputString){
        String[] stringMath = inputString.split("math=");
        inputString = stringMath[1];
        inputString = new ArithmeticUtil().valueof(inputString);
        return inputString;
    }

    public String getOutputString() {
        return outputString;
    }
}
