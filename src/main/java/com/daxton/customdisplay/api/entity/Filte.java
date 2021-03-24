package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.MobManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class Filte {

    public Filte(){

    }
    /**過濾不要的目標**/
    public static boolean valueOf(LivingEntity livingEntity, String filteName){



        if(filteName.equals("null")){
            return true;
        }


        boolean b = true;

        FileConfiguration TargetFiltersConfig = ConfigMapManager.getFileConfigurationMap().get("Character_System_TargetFilters.yml");
        List<String> filteList = TargetFiltersConfig.getStringList(filteName+".TargetFilters");

        for(String filteKey : filteList){

            /**篩選掉派系目標**/
            if(filteKey.toLowerCase().contains("factions")){
                if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
                    String[] filteKey1 = filteKey.split("=");
                    if(filteKey1.length == 3){
                        String uuidString = livingEntity.getUniqueId().toString();
                        String faction = MobManager.mythicMobs_Faction_Map.get(uuidString);
                        if(filteKey1[0].toLowerCase().equals("remove")){
                            b = true;
                            if(filteKey1[2].equals(faction)){
                                b = false;
                            }
                        }else if(filteKey1[0].toLowerCase().equals("add")){
                            b = false;
                            if(filteKey1[2].equals(faction)){
                                b = true;
                            }
                        }

                    }
                }

            }
            /**篩選生物類別目標**/
            if(filteKey.toLowerCase().contains("entitytypelist")){
                String[] filteKey1 = filteKey.split("=");
                if(filteKey1.length == 3){
                    FileConfiguration entityTypeListConfig = ConfigMapManager.getFileConfigurationMap().get("Character_System_EntityTypeList.yml");
                    List<String> entityTypeList = entityTypeListConfig.getStringList(filteKey1[2]+".entityTypeList");
                    if(filteKey1[0].toLowerCase().equals("remove")){
                        b = true;
                        if(entityTypeList.contains(livingEntity.getType().toString())){
                            b = false;
                        }
                    }else if(filteKey1[0].toLowerCase().equals("add")){
                        b = false;
                        if(entityTypeList.contains(livingEntity.getType().toString())){
                            b = true;
                        }
                    }

                }
            }

            /**篩選生物MM類別目標**/
            if(filteKey.toLowerCase().contains("mythictypelist")){
                if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
                    String[] filteKey1 = filteKey.split("=");
                    if(filteKey1.length == 3){
                        FileConfiguration mythicTypeListConfig = ConfigMapManager.getFileConfigurationMap().get("Character_System_MythicTypeList.yml");
                        List<String> mythicTypeList = mythicTypeListConfig.getStringList(filteKey1[2]+".mythicTypeList");
                        String uuidString = livingEntity.getUniqueId().toString();
                        String id = MobManager.mythicMobs_mobID_Map.get(uuidString);;
                        if(filteKey1[0].toLowerCase().equals("remove")){
                            b = true;
                            if(mythicTypeList.contains(id)){

                                b = false;
                            }
                        }else if(filteKey1[0].toLowerCase().equals("add")){
                            b = false;
                            if(mythicTypeList.contains(id)){
                                b = true;
                            }
                        }

                    }
                }
            }



        }


        return b;




    }

}
