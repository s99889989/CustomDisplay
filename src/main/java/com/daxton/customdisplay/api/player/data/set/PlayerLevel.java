package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerLevel {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();
    /**設置等級**/
    public PlayerLevel(){

    }


    public void setMap(Player player, Map<String,String> level_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();

        List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Level").getKeys(false));
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                int value = playerConfig.getInt(uuidString+".Level."+attrName);
                level_Map.put(attrName,String.valueOf(value));
            }
        }

    }

    /**設定單個等級**/
    public void setLevelMap(Player player,String levelName,int amount){

        UUID uuid = player.getUniqueId();
        String uuidString = uuid.toString();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(uuid);
        FileConfiguration levelConfig = new LoadConfig().getLevelConfig(levelName);
        if(playerData != null){
            Map<String,String> level_Map = playerData.level_Map;
            int nowLevel = Integer.valueOf(level_Map.get(levelName+"_level_now"));
            int maxLevel = Integer.valueOf(level_Map.get(levelName+"_level_max"));

            if(nowLevel <= maxLevel){
                level_Map.put(levelName+"_level_now",String.valueOf(amount));
                level_Map.put(levelName+"_exp_now","0");
                if(amount > nowLevel){
                    int count = amount - nowLevel;
                    for(int i = 0; i < count;i++){
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>",levelName);

                        new PlayerTrigger(player).onTwo(player, null, "~onlevelup");
                    }
                }else {
                    int count = nowLevel - amount;
                    for(int i = 0; i < count;i++){
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_level_type>",levelName);

                        new PlayerTrigger(player).onTwo(player, null, "~onleveldown");
                    }
                }
            }

        }

    }

    /**增減單個等級**/
    public void addLevelMap(Player player,String levelName,int amount){
        UUID uuid = player.getUniqueId();
        String uuidString = uuid.toString();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(uuid);
        FileConfiguration levelConfig = new LoadConfig().getLevelConfig(levelName);
        if(playerData != null){
            Map<String,String> level_Map = playerData.level_Map;

            int nowLevel = Integer.valueOf(level_Map.get(levelName+"_level_now"));
            int maxLevel = Integer.valueOf(level_Map.get(levelName+"_level_max"));
            int newLevel = nowLevel + amount;
            if(newLevel <= maxLevel){
                level_Map.put(levelName+"_level_now",String.valueOf(newLevel));
                level_Map.put(levelName+"_exp_now","0");
                if(amount > 0){
                    for(int i = 0; i < amount ;i++){
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>",levelName);
                        new PlayerTrigger(player).onTwo(player, null, "~onlevelup");
                    }
                }else {
                    for(int i = 0; i < amount*-1 ;i++){
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_level_type>",levelName);
                        new PlayerTrigger(player).onTwo(player, null, "~onleveldown");
                    }
                }
            }
        }
    }

    /**增加單個經驗值**/
    public void addExpMap(Player player,String levelName,int amount){

        UUID uuid = player.getUniqueId();
        String uuidString = uuid.toString();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(uuid);
        FileConfiguration levelConfig = new LoadConfig().getLevelConfig(levelName);
        if(playerData != null){
            Map<String,String> level_Map = playerData.level_Map;
            if(!(level_Map.isEmpty()) && level_Map.size() > 0){

                int nowLevel = Integer.valueOf(level_Map.get(levelName+"_level_now"));
                int nowExp = Integer.valueOf(level_Map.get(levelName+"_exp_now"));


                int needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                int nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));
                int beforeExp = levelConfig.getInt("Exp-Amount."+(nowLevel-1));



                int newExp = 0;
                if (nextExp != 0){
                    newExp = nowExp + amount;
                    level_Map.put(levelName+"_exp_now",String.valueOf(newExp));

                    if(amount < 0){
                        /**當經驗值減少時**/
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_exp_type>",levelName);
                    }else {
                        /**當經驗值增加時**/
                        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_exp_type>",levelName);
                    }

                }

                while (beforeExp != 0 && newExp < 0){
                    nowLevel = nowLevel - 1;
                    needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                    newExp = newExp + needExp;

                    level_Map.put(levelName+"_exp_now",String.valueOf(newExp));
                    level_Map.put(levelName+"_level_now",String.valueOf(nowLevel));

                    needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                    level_Map.put(levelName+"_exp_max",String.valueOf(needExp));

                    PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_level_type>",levelName);
                }

                while(nextExp != 0 && newExp >= needExp){
                    newExp = newExp - needExp;
                    nowLevel = nowLevel + 1;
                    level_Map.put(levelName+"_exp_now",String.valueOf(newExp));
                    level_Map.put(levelName+"_level_now",String.valueOf(nowLevel));

                    needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                    nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));
                    level_Map.put(levelName+"_exp_max",String.valueOf(needExp));

                    PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_up_level_type>",levelName);
                    new PlayerTrigger(player).onTwo(player, null, "~onlevelup");
                }


            }

        }


    }

}
