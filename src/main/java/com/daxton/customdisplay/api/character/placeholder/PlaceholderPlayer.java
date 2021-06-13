package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class PlaceholderPlayer {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderPlayer(){

    }

    public static String valueOf(LivingEntity entity, String inputString){

        String outputString = "";
        String uuidString = entity.getUniqueId().toString();
        if(inputString != null){
            String changeKey = inputString.toLowerCase();
            if(changeKey.contains("<cd_player_last_chat")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>");
                }
            }
            if(changeKey.contains("<cd_player_cast_command")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>");
                }
            }
            if(changeKey.contains("<cd_player_kill_mob_type")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>");
                }
            }

            if(changeKey.contains("<cd_player_up_exp_type")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>");
                }
            }
            if(changeKey.contains("<cd_player_down_exp_type")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>");
                }
            }
            /**最後改變的經驗值量**/
            if(changeKey.contains("<cd_player_change_exp_amount")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_change_exp_amount>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_change_exp_amount>");
                }
            }
            if(changeKey.contains("<cd_player_up_level_type")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>");
                }
            }
            if(changeKey.contains("<cd_player_down_level_type")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>");
                }
            }
            /**最後殺死的MythicMobs怪物ID**/
            if(changeKey.contains("<cd_player_kill_mythic_mob_id")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>");
                }
            }
            /**最後殺死的MythicMobs怪物獲得的錢**/
            if(changeKey.contains("<cd_player_kill_mythic_mob_money")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_money>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_money>");
                }
            }
            /**最後殺死的MythicMobs怪物獲得的經驗**/
            if(changeKey.contains("<cd_player_kill_mythic_mob_exp")){

                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_exp>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_exp>");
                }
            }
            /**最後殺死的MythicMobs怪物獲得的物品**/
            if(changeKey.contains("<cd_player_kill_mythic_mob_item")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_item>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_kill_mythic_mob_item>");
                }
            }
            if(changeKey.contains("<cd_player_enchants_")){
                try {
                    PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
                    Map<String, Integer> equipment_Enchants_Map = playerData.equipment_Enchants_Map;
                    String key = inputString.replace("<cd_player_enchants_","");
                    outputString = String.valueOf(equipment_Enchants_Map.get(key));
                }catch (NullPointerException exception){
                    outputString = "0";
                }

            }
            /**主手裝備類型**/
            if(changeKey.contains("<cd_player_equipment_type_mainhand")){
                if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_equipment_type_mainhand>") != null){
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_player_equipment_type_mainhand>");
                }
            }
            PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
            if(changeKey.contains("<cd_player_item_amount_")){
                outputString = "0";
                String key = inputString.replace("<cd_player_item_amount_","").replace(">","").trim();
                if(playerData.inventory_Item_Amount.containsKey(key)){
                    outputString = String.valueOf(playerData.inventory_Item_Amount.get(key));
                }
            }
        }


        return outputString;
    }

}
