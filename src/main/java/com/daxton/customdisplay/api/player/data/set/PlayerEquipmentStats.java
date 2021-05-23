package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class PlayerEquipmentStats {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();
    /**裝備狀態**/
    public PlayerEquipmentStats(){

    }


    public static void setMap(Player player, Map<String,String> attributes_Stats_Map,FileConfiguration playerConfig, Map<String,String> name_Equipment_Map){
        String uuidString = player.getUniqueId().toString();

        List<String> attrStatsList = playerConfig.getStringList(uuidString+".Equipment_Stats");
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                FileConfiguration attrConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_EquipmentStats_"+attrName+".yml");
                List<String> attrStatsNameList = new ArrayList<>(attrConfig.getConfigurationSection(attrName).getKeys(false));
                for(String attrName2 : attrStatsNameList){
                    String value = attrConfig.getString(attrName+"."+attrName2+".base");
                    String name = attrConfig.getString(attrName+"."+attrName2+".name");
                    if(name != null){
                        if(name.contains("|")){
                            List<String> nameList = Arrays.asList(name.split("\\|"));
                            nameList.forEach(s -> name_Equipment_Map.put(s,attrName2));
                        }else {
                            name_Equipment_Map.put(name,attrName2);
                        }
                    }
                    if(value != null){
                        value = ConversionMain.valueOf(player,null,value);
                        attributes_Stats_Map.put(attrName2,value);
                    }else {
                        attributes_Stats_Map.put(attrName2,"0");
                    }

                }

            }

        }

    }

    public static void setDefault(Player player, Map<String,String> attributes_Stats_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();

        List<String> attrStatsList = playerConfig.getStringList(uuidString+".Equipment_Stats");
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                FileConfiguration attrConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_EquipmentStats_"+attrName+".yml");
                List<String> attrStatsNameList = new ArrayList<>(attrConfig.getConfigurationSection(attrName).getKeys(false));
                for(String attrName2 : attrStatsNameList){
                    String value = attrConfig.getString(attrName+"."+attrName2+".base");
                    if(value != null){
                        value = ConversionMain.valueOf(player,null,value);
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
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
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
                            value = ConversionMain.valueOf(player,null,value);

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
