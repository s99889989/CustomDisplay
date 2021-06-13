package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.LoadConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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

                FileConfiguration attrConfig = LoadConfig.getConfig("Class_Attributes_EquipmentStats_", attrName);
                if(attrConfig != null){
                    if(attrConfig.getConfigurationSection(attrName) != null){
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

        }

    }

    public static void setDefault(Player player, Map<String,String> attributes_Stats_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();

        List<String> attrStatsList = playerConfig.getStringList(uuidString+".Equipment_Stats");
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){

                FileConfiguration attrConfig = LoadConfig.getConfig("Class_Attributes_EquipmentStats_", attrName);
                if(attrConfig != null){
                    if(attrConfig.getConfigurationSection(attrName) != null){
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

        }

    }



}
