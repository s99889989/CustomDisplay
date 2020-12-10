package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class StringConversion {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String ResultString = "";
    private String folderName = "";
    private String firstString = "";
    private Player player = null;
    private LivingEntity target = null;
    /**血條轉換**/
    private String health_conversion = "health-monster";



    public StringConversion(String folderName, String firstString, Player player,LivingEntity target){
        this.folderName = folderName;
        this.firstString = firstString;
        this.player = player;
        this.target = target;
        setString();
    }

    public StringConversion(String folderName, String firstString, Player player,LivingEntity target,String health_conversion){
        this.folderName = folderName;
        this.firstString = firstString;
        this.player = player;
        this.target = target;
        this.health_conversion = health_conversion;
        setString();
    }

    public void setString(){
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
        ResultString = firstString;
    }


    /**對伺服器內的{}盡情處理**/
    public String pluginString(String firstString3){
        firstString3 = firstString3.replace("{","").replace("}","");
        String outputString = "";
        if(firstString3.toLowerCase().contains("cd_target")){
            if(firstString3.toLowerCase().contains("_nowhealth")){
                outputString = String.valueOf(target.getHealth());
            }
            if(firstString3.toLowerCase().contains("_maxhealth")){
                outputString = String.valueOf(target.getAttribute(GENERIC_MAX_HEALTH).getValue());
            }
            if(firstString3.toLowerCase().contains("_healthconversion")){

            }
        }
        if(firstString3.toLowerCase().contains("cd_self")){

        }

        return outputString;
    }

    /**對自訂字串進行關於&Character&資料夾內的設定處理**/
    public String customString(String folderName, String firstString2,Player player){
        firstString2 = firstString2.replace("&","");
        String outputString = "";
        List<String> list = new ConfigFind().getCharacterMessageList(folderName,firstString2);
        for(String string : list){
            String headString = new StringFind().getAction(string);
            String content = string.replace(headString,"").replace("[","").replace("]","");
            cd.getLogger().info(headString);
            if(headString.toLowerCase().contains("message")){
                if(content.contains("{") && content.contains("}")){

                }
            }
            if(headString.toLowerCase().contains("papi") || headString.toLowerCase().contains("placeholder")){
                String[] stl = string.split(";");
                outputString = PlaceholderAPI.setPlaceholders(player,stl[1]);
            }
            if(headString.toLowerCase().contains("math")){
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
            if(headString.toLowerCase().contains("decimal") || headString.toLowerCase().contains("dec")){

            }
            if(headString.toLowerCase().contains("converall=")){
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
            if(headString.toLowerCase().contains("conver=")){
                String[] stl1 = string.split("=");
                String[] stl2 = stl1[1].split(";");
                for(String stringList2 : stl2){
                    String[] stl3 = stringList2.split(",");
                    outputString = outputString.replace(stl3[0],stl3[1]);
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

    /**設定怪物血量顯示**/
    public String targetHealth(){

        double maxhealth = target.getAttribute(GENERIC_MAX_HEALTH).getValue();
        double nowhealth = target.getHealth();
        int counthealth = (int) nowhealth*10/(int) maxhealth;
        String mhealth = new NumberUtil(counthealth, ConfigMapManager.getFileConfigurationMap().get("Character_System_Health.yml").getStringList(health_conversion+".conversion")).getTenString();
        return mhealth;
    }

    public String getResultString() {
        return ResultString;
    }
}
