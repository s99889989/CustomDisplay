package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerEquipmentStats {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();
    /**裝備狀態**/
    public PlayerEquipmentStats(){

    }


    public void setMap(Player player, Map<String,String> attributes_Stats_Map,FileConfiguration playerConfig, Map<String,String> name_Equipment_Map){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();

        List<String> attrStatsList = playerConfig.getStringList(uuidString+".Equipment_Stats");
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                File attrFile = new File(cd.getDataFolder(),"Class/Attributes/EquipmentStats/"+attrName+".yml");
                FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(attrFile);
                List<String> attrStatsNameList = new ArrayList<>(attrConfig.getConfigurationSection(attrName).getKeys(false));
                for(String attrName2 : attrStatsNameList){
                    String value = attrConfig.getString(attrName+"."+attrName2+".base");
                    String name = attrConfig.getString(attrName+"."+attrName2+".name");
                    if(name != null){
                        name_Equipment_Map.put(name,attrName2);
                    }
                    if(value != null){
                        value = new ConversionMain().valueOf(player,null,value);
                        attributes_Stats_Map.put(attrName2,value);
                    }else {
                        attributes_Stats_Map.put(attrName2,"0");
                    }

                }

            }

        }

    }

    public void setMap(Player player){

        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            Map<String,String> attributes_EquipmentStats_Map = playerData.equipment_Stats_Map;
            Map<String,String> attributes_EquipmentStats_Map2 = playerData.equipment_Stats_Map2;
            List<String> attrStatsList = playerConfig.getStringList(uuidString+".Equipment_Stats");
            if(attrStatsList.size() > 0){
                for(String attrName : attrStatsList){
                    File attrFile = new File(cd.getDataFolder(),"Class/Attributes/EquipmentStats/"+attrName+".yml");
                    FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(attrFile);
                    List<String> attrStatsNameList = new ArrayList<>(attrConfig.getConfigurationSection(attrName).getKeys(false));
                    for(String attrName2 : attrStatsNameList){
                        String value = attrConfig.getString(attrName+"."+attrName2+".base");
                        //String setValue = attributes_EquipmentStats_Map2.get()

                        if(value != null){
                            value = new ConversionMain().valueOf(player,null,value);

                            attributes_EquipmentStats_Map.put(attrName2,value);
                            //cd.getLogger().info(attrName2+" : "+value);
                        }else {

                            attributes_EquipmentStats_Map.put(attrName2,"0");
                        }

                    }

                }

            }
        }





    }

}
