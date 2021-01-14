package com.daxton.customdisplay.api.character.stringconversion;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.placeholder.PlaceholderOther;
import com.daxton.customdisplay.api.character.placeholder.PlaceholderSelf;
import com.daxton.customdisplay.api.character.placeholder.PlaceholderTarget;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.ConfigFind;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;

public class StringConversion2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String folderName = "";
    private String firstString = "";
    private LivingEntity target = null;
    private LivingEntity self = null;

    public StringConversion2(){}


    public StringConversion2(LivingEntity self, LivingEntity target, String firstString, String folderName){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.folderName = folderName;

    }

    public String valueConv(){
        if(firstString.contains("&")){
            int num = NumberUtil.appearNumber(firstString, "&");
            for(int i = 0; i < num/2 ; i++){
                int head = firstString.indexOf("&");
                int tail = firstString.indexOf("&",head+1);
                if(firstString.contains("&")){
                    String change = valueChange(firstString.substring(head,tail+1));
                    firstString = firstString.replace(firstString.substring(head,tail+1),change);
                }


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

            finalString = valueHandle(self,target,headKey,content,finalString);

        }

        return finalString;
    }

    public String valueHandle(LivingEntity self,LivingEntity target ,String headKey,String content,String finalString){

        if(headKey.toLowerCase().contains("math")){

            if(content.contains("<") && content.contains(">")){

                content = pluginString(self,target,content);

            }

            try {
                double number = Arithmetic.eval(content);
                content = String.valueOf(number);
            }catch (Exception exception){
                content = "0";
            }
            return content;
        }

        if(headKey.toLowerCase().contains("customcharacter") || headKey.toLowerCase().contains("ccharacter")){
            if(content.contains("<") && content.contains(">")){

                content = pluginString(self,target,content);

                return content;
            }
        }

        if(headKey.toLowerCase().contains("customconversion") || headKey.toLowerCase().contains("cconversion")){
            content = new StringConversion(self,target,content,"Character").valueConv();

            return content;

        }

        if(headKey.toLowerCase().contains("converall")){
            cd.getLogger().info("COVALL");
            String[] stl2 = content.split(";");
            for(String stringList2 : stl2){
                String[] stl3 = stringList2.split(",");
                if(finalString.replace(stl3[0],"").length() == 0){
                    if(stl3[1].contains("<") && stl3[1].contains(">")){
                        stl3[1] = pluginString(self,target,stl3[1]);
                    }
                    finalString = finalString.replace(stl3[0],stl3[1]);
                    if(finalString.equals(stl3[1])){
                        break;
                    }
                }
            }
            return finalString;
        }


        return content;
    }

    /**對伺服器內的<>進行處理**/
    public String pluginString(LivingEntity self,LivingEntity target,String content){

        String outputString = "";
        int numHead = NumberUtil.appearNumber(content, "<");
        int numTail = NumberUtil.appearNumber(content, ">");
        if(numHead == numTail){
            for(int i = 0; i < numHead ; i++){
                int head = content.indexOf("<");
                int tail = content.indexOf(">");
                if(content.contains("<") && content.contains(">")){
                    if(content.substring(head,tail+1).toLowerCase().contains("<cd_other_")){
                        content = content.replace(content.substring(head,tail+1),new PlaceholderOther().getOther(content.substring(head,tail+1)));
                        continue;
                    }
                    if(self != null &&content.substring(head,tail+1).toLowerCase().contains("<cd_self_")){
                        content = content.replace(content.substring(head,tail+1),new PlaceholderSelf().getSelfPlaceholder(self,content.substring(head,tail+1)));
                        continue;
                    }
                    if(target != null && content.substring(head,tail+1).toLowerCase().contains("<cd_target_")){
                        content = content.replace(content.substring(head,tail+1),new PlaceholderTarget().getTargetPlaceholder(target,content.substring(head,tail+1)));
                        continue;
                    }
                }else {
                    break;
                }
            }
        }
        outputString = content;
        return outputString;
    }


}
