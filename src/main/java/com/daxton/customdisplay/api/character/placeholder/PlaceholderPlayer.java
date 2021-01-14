package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.LivingEntity;

public class PlaceholderPlayer {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderPlayer(){

    }

    public String valueOf(LivingEntity entity, String inputString){
        cd.getLogger().info(inputString);
        String outputString = "";
        String uuidString = entity.getUniqueId().toString();
        if(inputString.toLowerCase().contains("<cd_player_last_chat")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>");
            }
        }
        if(inputString.toLowerCase().contains("<cd_player_cast_command")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>");
            }
        }
        if(inputString.toLowerCase().contains("<cd_player_kill_mob_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>");
            }
        }
        if(inputString.toLowerCase().contains("<cd_player_up_exp_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>");
            }
        }
        if(inputString.toLowerCase().contains("<cd_player_down_exp_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>");
            }
        }
        if(inputString.toLowerCase().contains("<cd_player_up_level_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>");
            }
        }
        if(inputString.toLowerCase().contains("<cd_player_down_level_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>");
            }
        }
        return outputString;
    }

}
