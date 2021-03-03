package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.LivingEntity;

public class PlaceholderPlayer {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderPlayer(){

    }

    public String valueOf(LivingEntity entity, String inputString){

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
        /**最後改變的經驗值量**/
        if(inputString.toLowerCase().contains("<cd_player_change_exp_amount")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_change_exp_amount>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_change_exp_amount>");
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
        /**最後殺死的MythicMobs怪物ID**/
        if(inputString.toLowerCase().contains("<cd_player_kill_mythic_mob_id")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>");
            }
        }
        /**最後殺死的MythicMobs怪物獲得的錢**/
        if(inputString.toLowerCase().contains("<cd_player_kill_mythic_mob_money")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_money>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_money>");
            }
        }
        /**最後殺死的MythicMobs怪物獲得的經驗**/
        if(inputString.toLowerCase().contains("<cd_player_kill_mythic_mob_exp")){

            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_exp>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_exp>");
            }
        }
        /**最後殺死的MythicMobs怪物獲得的物品**/
        if(inputString.toLowerCase().contains("<cd_player_kill_mythic_mob_item")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_item>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_item>");
            }
        }
        return outputString;
    }

}
