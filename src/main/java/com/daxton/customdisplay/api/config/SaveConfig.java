package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

public class SaveConfig {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public SaveConfig(){

    }

    /**儲存玩家設定檔**/
    public void savePlayerConfig(Player player, FileConfiguration config){
        String uuidString = player.getUniqueId().toString();
        File playerFile = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        try {
            config.save(playerFile);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
    /**把玩家記憶體資料存到yml設定檔**/
    public void setConfig(Player player){
        UUID uuid = player.getUniqueId();
        String uuidString = uuid.toString();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);

        if(playerData != null){
            Iterator levelKeys = playerData.level_Map.keySet().iterator();
            while(levelKeys.hasNext()){
                String key2 = (String)levelKeys.next();
                String levelValueString = playerData.level_Map.get(key2);
                playerConfig.set(uuidString+".Level."+key2,Integer.valueOf(levelValueString));
            }

            Iterator pointKeys = playerData.point_Map.keySet().iterator();
            while (pointKeys.hasNext()){
                String pointKey = (String) pointKeys.next();
                String pointValueString = playerData.point_Map.get(pointKey);
                playerConfig.set(uuidString+".Point."+pointKey,Integer.valueOf(pointValueString));

            }

            Iterator pointAttrKeys = playerData.attributes_Point_Map.keySet().iterator();
            while (pointAttrKeys.hasNext()){
                String pointAttr = (String) pointAttrKeys.next();
                String pointAttrValueString = playerData.attributes_Point_Map.get(pointAttr);
                playerConfig.set(uuidString+".Attributes_Point."+pointAttr,Integer.valueOf(pointAttrValueString));


            }

            Iterator skillKeys = playerData.skills_Map.keySet().iterator();
            while (skillKeys.hasNext()){
                String skillKey = (String) skillKeys.next();
                if(skillKey.contains("_level")){
                    String skillName = skillKey.replace("_level","");
                    String level = playerData.skills_Map.get(skillKey);
                    playerConfig.set(uuidString+".Skills."+skillName+".level",Integer.valueOf(level));
                }
                if(skillKey.contains("_use")){
                    String skillName = skillKey.replace("_use","");
                    String use = playerData.skills_Map.get(skillKey);
                    playerConfig.set(uuidString+".Skills."+skillName+".use",Integer.valueOf(use));
                }


            }

            Iterator bindKeys = playerData.binds_Map.keySet().iterator();
            while (bindKeys.hasNext()){
                String bindKey = (String) bindKeys.next();

                if(bindKey.contains("_use")){
                    String number = bindKey.replace("_use","");
                    String bindUse = playerData.binds_Map.get(bindKey);
                    playerConfig.set(uuidString+".Binds."+number+".UseLevel",Integer.valueOf(bindUse));
                }
                if(bindKey.contains("_skillName")){
                    String number = bindKey.replace("_skillName","");
                    String bindSkill = playerData.binds_Map.get(bindKey);
                    playerConfig.set(uuidString+".Binds."+number+".SkillName",bindSkill);
                }

            }

            new SaveConfig().savePlayerConfig(player,playerConfig);

        }

    }

}
