package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Level3 {

    public Level3(){

    }

    public static void setLevel(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        String function = actionMapHandle.getString(new String[]{"function","fc"},"minecraft");



        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity -> {
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;

                ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, player);

                int amount = actionMapHandle2.getInt(new String[]{"amount","a"},1);

                if(type.contains("minecraft")){
                    if(function.toLowerCase().contains("set")){
                        //設定原版等級
                        player.setLevel(amount);
                    }else {
                        //增加原版等級
                        player.giveExpLevels(amount);
                    }
                }else {
                    if(function.toLowerCase().contains("set")){
                        //設定自訂等級
                        setLevelMap(player,type,amount);
                    }else {
                        //增加自訂等級
                        addLevelMap(player,type,amount);
                    }
                }

            }
        });


    }

    /**設定單個等級**/
    public static void setLevelMap(Player player,String levelName,int amount){


        String uuidString = player.getUniqueId().toString();

        if(PlayerManager.player_Data_Map.get(uuidString) != null){
            PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
            int nowLevel = playerData.getLevel(levelName+"_level_now");
            int maxLevel = playerData.getLevel(levelName+"_level_max");

            if(nowLevel <= maxLevel){
                playerData.setLevel(levelName+"_level_now", amount);
                playerData.setLevel(levelName+"_exp_now",0);

                if(amount > nowLevel){
                    int count = amount - nowLevel;
                    for(int i = 0; i < count;i++){
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>",levelName);

                        PlayerTrigger.onPlayer(player, null, "~onlevelup");
                    }
                }else {
                    int count = nowLevel - amount;
                    for(int i = 0; i < count;i++){
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_level_type>",levelName);

                        PlayerTrigger.onPlayer(player, null, "~onleveldown");
                    }
                }
            }

        }

    }

    /**增減單個等級**/
    public static void addLevelMap(Player player,String levelName,int amount){
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.player_Data_Map.get(uuidString) != null){
            PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
            int nowLevel = playerData.getLevel(levelName+"_level_now");
            int maxLevel = playerData.getLevel(levelName+"_level_max");
            int newLevel = nowLevel + amount;
            if(newLevel <= maxLevel){
                playerData.setLevel(levelName+"_level_now", newLevel);
                playerData.setLevel(levelName+"_exp_now",0);

                if(amount > 0){
                    for(int i = 0; i < amount ;i++){
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>",levelName);
                        PlayerTrigger.onPlayer(player, null, "~onlevelup");
                    }
                }else {
                    for(int i = 0; i < amount*-1 ;i++){
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_level_type>",levelName);
                        PlayerTrigger.onPlayer(player, null, "~onleveldown");
                    }
                }
            }
        }
    }



}
