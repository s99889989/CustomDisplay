package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Experience3 {

    public Experience3(){

    }

    public static void setExp(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},10);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        if(!(livingEntityList.isEmpty())){
            livingEntityList.forEach(livingEntity -> {
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;

                    if(type.contains("minecraft")){
                        expSet(player, target, amount);
                    }else {
                        //expOtherSet(player, type, amount);
                        addExpMap(player, type, amount);
                    }

                }
            });

        }


    }



    public static void expSet(Player player, LivingEntity target, int amount){
        String uuidString = player.getUniqueId().toString();
        if(amount < 0){
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_exp_type>","default");
            PlayerTrigger.onPlayer(player, target, "~onexpdown");
        }

        player.giveExp(amount);
    }

    public static void addExpMap(Player player,String levelName,int amount){

        String uuidString = player.getUniqueId().toString();
        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);

        if(PlayerManager.player_Data_Map.get(uuidString) != null && ConfigMapManager.getFileConfigurationMap().get("Class_Level_"+levelName+".yml") != null){
            FileConfiguration levelConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Level_"+levelName+".yml");
            int nowLevel = playerData.getLevel(levelName+"_level_now");
            int nowExp = playerData.getLevel(levelName+"_exp_now");


            int needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
            int nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));
            int beforeExp = levelConfig.getInt("Exp-Amount."+(nowLevel-1));


            int newExp = 0;
            if (nextExp != 0){
                newExp = nowExp + amount;

                playerData.setLevel(levelName+"_exp_now", newExp);
                if(amount < 0){
                    //當經驗值減少時
                    PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_exp_type>",levelName);
                }else {
                    //當經驗值增加時
                    PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_exp_type>",levelName);
                }

            }

            while (beforeExp != 0 && newExp < 0){
                nowLevel = nowLevel - 1;
                needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                newExp = newExp + needExp;


                playerData.setLevel(levelName+"_exp_now", newExp);

                playerData.setLevel(levelName+"_level_now", nowLevel);

                needExp = levelConfig.getInt("Exp-Amount."+nowLevel);

                playerData.setLevel(levelName+"_exp_max", needExp);
                //等級降低類型
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_level_type>",levelName);
            }

            while(newExp >= needExp){
                if(nextExp != 0){
                    newExp = newExp - needExp;
                    nowLevel = nowLevel + 1;
                    //CustomDisplay.getCustomDisplay().getLogger().info("經驗: "+newExp);
                    playerData.setLevel(levelName+"_exp_now", newExp);

                    playerData.setLevel(levelName+"_level_now", nowLevel);

                    needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                    nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));

                    playerData.setLevel(levelName+"_exp_max", needExp);
                    //等級上升類型
                    PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>",levelName);
                    PlayerTrigger.onPlayer(player, null, "~onlevelup");
                }else {
                    newExp = needExp -1;
                    playerData.setLevel(levelName+"_exp_now", newExp);
                }

            }

        }









    }

}
