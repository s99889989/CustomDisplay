package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.api.MobData;
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

        FileConfiguration TargetFiltersConfig = ConfigMapManager.getFileConfigurationMap().get("Other_TargetFilters.yml");
        List<String> filteList = TargetFiltersConfig.getStringList(filteName+".TargetFilters");

        for(String filteKey : filteList){

            //篩選掉派系目標
            if(filteKey.toLowerCase().contains("factions")){
                b = isFactions(livingEntity, filteKey);
            }
            //篩選生物類別目標
            if(filteKey.toLowerCase().contains("entitytypelist")){
                b = isEntityType(livingEntity, filteKey);
            }

            //篩選生物MM類別目標
            if(filteKey.toLowerCase().contains("mythictypelist")){
                b = isMythicType(livingEntity, filteKey);
            }

        }

        return b;
    }

    //MM生物ID
    public static boolean isMythicType(LivingEntity livingEntity, String filteKey){
        boolean b = true;
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
            String[] filteKey1 = filteKey.split("=");
            if(filteKey1.length == 3){
                FileConfiguration mythicTypeListConfig = ConfigMapManager.getFileConfigurationMap().get("Other_MythicTypeList.yml");
                List<String> mythicTypeList = mythicTypeListConfig.getStringList(filteKey1[2]+".mythicTypeList");
                String uuidString = livingEntity.getUniqueId().toString();
                String id = MobManager.mythicMobs_mobID_Map.get(uuidString);
                if(filteKey1[0].equalsIgnoreCase("remove")){
                    b = !mythicTypeList.contains(id);
                }
                if(filteKey1[0].equalsIgnoreCase("add")){
                    b = mythicTypeList.contains(id);
                }

            }
        }
        return b;
    }

    //生物類別判定
    public static boolean isEntityType(LivingEntity livingEntity, String filteKey){
        boolean b = true;
        String[] filteKey1 = filteKey.split("=");
        if(filteKey1.length == 3){
            FileConfiguration entityTypeListConfig = ConfigMapManager.getFileConfigurationMap().get("Other_EntityTypeList.yml");
            List<String> entityTypeList = entityTypeListConfig.getStringList(filteKey1[2]+".entityTypeList");
            if(filteKey1[0].equalsIgnoreCase("remove")){
                b = !entityTypeList.contains(livingEntity.getType().toString());
            }
            if(filteKey1[0].equalsIgnoreCase("add")){
                b = entityTypeList.contains(livingEntity.getType().toString());
            }

        }
        return b;
    }

    //派系判定
    public static boolean isFactions(LivingEntity livingEntity, String filteKey){
        boolean b = true;
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
            String[] filteKey1 = filteKey.split("=");
            if(filteKey1.length == 3){
                String uuidString = livingEntity.getUniqueId().toString();
                if(MobManager.mythicMobs_mobID_Map.containsKey(uuidString)){
                    String mobID = MobManager.mythicMobs_mobID_Map.get(uuidString);
                    MobData mobData = MobManager.mob_Data_Map.get(mobID);
                    String faction = mobData.getFaction();
                    if(filteKey1[0].equalsIgnoreCase("remove")){
                        b = !filteKey1[2].equals(faction);
                    }
                    if(filteKey1[0].equalsIgnoreCase("add")){
                        b = filteKey1[2].equals(faction);
                    }
                }

            }
        }
        return b;
    }

}
