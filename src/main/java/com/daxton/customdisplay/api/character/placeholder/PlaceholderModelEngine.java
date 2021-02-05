package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.MobManager;
import org.bukkit.entity.LivingEntity;

public class PlaceholderModelEngine {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderModelEngine(){

    }

    public String valueOf(LivingEntity entity, String inputString){
        String outputString = "0";
        String uuidString = entity.getUniqueId().toString();
        String key = inputString.replace(" ","").replace("<cd_modelengine_","");
        if(MobManager.modelengine_Map.get(uuidString) != null){
            if(inputString.contains("<cd_modelengine_id")){
                outputString = MobManager.modelengine_Map.get(uuidString);
            }
            if(inputString.contains("<cd_modelengine_eye_hight")){
                outputString = MobManager.modelengine_Map.get(uuidString+"eyeHight");
            }
            if(inputString.contains("<cd_modelengine_hit_hight")){
                outputString = MobManager.modelengine_Map.get(uuidString+"hitBoxHight");
            }
            if(inputString.contains("<cd_modelengine_hit_width")){
                outputString = MobManager.modelengine_Map.get(uuidString+"hitBoxWidth");
            }
            //cd.getLogger().info(MobManager.modelengine_Map.get(uuidString));
        }else {
            if(inputString.contains("_hight")){
                outputString = String.valueOf(entity.getHeight());
            }
        }
        return outputString;
    }

}
