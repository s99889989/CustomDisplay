package com.daxton.customdisplay.api.character.stringconversion;

import com.daxton.customdisplay.api.character.placeholder.*;
import com.daxton.customdisplay.api.other.NumberUtil;
import org.bukkit.entity.LivingEntity;

public class ConversionPlaceholder {


    public ConversionPlaceholder(){

    }

    public String valueOf(LivingEntity self, LivingEntity target, String inputString){
        String outputString = "";
        int numHead = NumberUtil.appearNumber(inputString, "<");
        int numTail = NumberUtil.appearNumber(inputString, ">");
        if(numHead == numTail){
            for(int i = 0; i < numHead ; i++){
                int head = inputString.indexOf("<");
                int tail = inputString.indexOf(">");
                if(inputString.contains("<") && inputString.contains(">")){
                    if(inputString.substring(head,tail+1).toLowerCase().contains("<cd_other_")){
                        inputString = inputString.replace(inputString.substring(head,tail+1),new PlaceholderOther().getOther(inputString.substring(head,tail+1)));
                        continue;
                    }
                    if(self != null &&inputString.substring(head,tail+1).toLowerCase().contains("<cd_self_")){

                        inputString = inputString.replace(inputString.substring(head,tail+1),new PlaceholderSelf().valueOf(self,inputString.substring(head,tail+1)));
                        continue;
                    }
                    if(target != null && inputString.substring(head,tail+1).toLowerCase().contains("<cd_target_")){

                        inputString = inputString.replace(inputString.substring(head,tail+1),new PlaceholderTarget().valueOf(target,inputString.substring(head,tail+1)));
                        continue;
                    }
                }else {
                    break;
                }
            }
        }
        outputString = inputString;

        return outputString;
    }


}
