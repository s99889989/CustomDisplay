package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class StringConversion2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String folderName = "";
    private String firstString = "";
    private LivingEntity target = null;
    private LivingEntity self = null;

    public StringConversion2(LivingEntity self, LivingEntity target, String firstString, String folderName){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.folderName = folderName;
        valueConv();

    }

    public String valueConv(){
        if(firstString.contains("&")){
            int num = NumberUtil.appearNumber(firstString, "&");
            for(int i = 0; i < num/2 ; i++){
                int head = firstString.indexOf("&");
                int tail = firstString.indexOf("&",head+1);
                String change = valueChange(firstString.substring(head,tail+1));
                firstString = firstString.replace(firstString.substring(head,tail+1),change);
            }
        }
        return firstString;
    }

    public String valueChange(String changeString){
        changeString = changeString.replace(" ","").replace("&","");
        String finalString = "";
        for(String stringList : new ConfigFind().getCharacterMessageList(folderName,changeString)){
            String headKey = new StringFind().getAction(stringList);
            String content = new StringFind().getContent(stringList);
            String targetKey = new StringFind().getTarget(stringList);

            if(targetKey.toLowerCase().contains("@=target")){
                if(target == null){
                    return " X"+changeString+"X ";
                }else {
                    finalString = valueHandle(target,headKey,content,finalString);
                }
            }else {
                finalString = valueHandle(self,headKey,content,finalString);
            }

        }

        return finalString;
    }

    public String valueHandle(LivingEntity livingEntity ,String headKey,String content,String finalString){
        if(headKey.toLowerCase().contains("customcharacter") || headKey.toLowerCase().contains("ccharacter")){
            if(content.contains("<") && content.contains(">")){
                content = pluginString(content,livingEntity);
                return content;
            }
        }

        if(headKey.toLowerCase().contains("papi") || headKey.toLowerCase().contains("placeholder")){
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                content = PlaceholderAPI.setPlaceholders(player,content);
            }else {
                content = " X"+content+"X ";
            }
            return content;
        }

        if(headKey.toLowerCase().contains("math")){
            if(content.toLowerCase().contains("%")){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;
                    content = PlaceholderAPI.setPlaceholders(player,content);
                }
                if(content.contains("<") && content.contains(">")){
                    content = pluginString(content,livingEntity);
                }
            }else {
                if(content.contains("<") && content.contains(">")){
                    content = pluginString(content,livingEntity);
                }
            }
            try {
                double number = Arithmetic.eval(content);
                content = String.valueOf(number);
                return content;
            }catch (Exception exception){
                content = ChatColor.RED+ "'&"+ content + "& has a problem in computing.'" + ChatColor.WHITE;
                return content;
            }

        }

        if(headKey.toLowerCase().contains("decimal") || headKey.toLowerCase().contains("dec")){
            try{
                double number = Double.valueOf(finalString);
                content = new NumberUtil(number,content).getDecimalString();
                return content;
            }catch (NumberFormatException exception){

            }

        }

        if(headKey.toLowerCase().contains("converhead") || headKey.toLowerCase().contains("chead")){
            finalString = new NumberUtil().NumberHead(finalString,content);
            return finalString;
        }

        if(headKey.toLowerCase().contains("converunits") || headKey.toLowerCase().contains("cunits")){
            finalString = new NumberUtil().NumberUnits(finalString,content);
            return finalString;
        }

        if(headKey.toLowerCase().contains("converdouble") || headKey.toLowerCase().contains("cdouble")){
            finalString = new NumberUtil().NumberDouble(finalString,content);
            return finalString;
        }

        if(headKey.toLowerCase().contains("convercontains") || headKey.toLowerCase().contains("ccontains")){
            String[] stl2 = content.split(",");
            if(finalString.contains(stl2[0])){
                finalString = stl2[1];
                return finalString;
            }
        }

        if(headKey.toLowerCase().contains("converall")){
            String[] stl2 = content.split(";");
            for(String stringList2 : stl2){
                String[] stl3 = stringList2.split(",");
                if(finalString.replace(stl3[0],"").length() == 0){
                    finalString = finalString.replace(stl3[0],stl3[1]);
                    if(finalString.equals(stl3[1])){
                        break;
                    }
                }
            }
            return finalString;
        }

        if(headKey.toLowerCase().contains("conver")){
            String[] stl2 = content.split(";");
            for(String stringList2 : stl2){
                String[] stl3 = stringList2.split(",");
                finalString = finalString.replace(stl3[0],stl3[1]);
            }
            return finalString;
        }

        return content;
    }

    /**對伺服器內的<>進行處理**/
    public String pluginString(String content,LivingEntity target){
        String outputString = "";
        int numHead = NumberUtil.appearNumber(content, "<");
        int numTail = NumberUtil.appearNumber(content, ">");
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


}
