package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.MobManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Aims {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    /**選擇目標**/
    public List<LivingEntity> valueOf(LivingEntity self, LivingEntity target, String firstString){

        /**目標**/
        String aims = new StringFind().getAimsValue(firstString,"aims","self");

        /**距離**/
        double radius = 2;
        try {
            radius = Double.valueOf(new StringFind().getAimsValue(firstString,"r","1"));
        }catch (NumberFormatException exception){
            radius = 2;
        }

        /**瞄準目標**/
        String filters = new StringFind().getAimsValue(firstString,"filters","null");

        List<LivingEntity> targetList = new ArrayList<>();
        if(aims.toLowerCase().contains("selfradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities(self,radius);
            for(LivingEntity le : livingEntityList){
                if(filters.equals("null")){
                    targetList.add(le);
                }else {
                    if(Filte(le,filters)){
                        targetList.add(le);
                    }
                }

            }
        }else if(target != null && aims.toLowerCase().contains("targetradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities(target,radius);
            for(LivingEntity le : livingEntityList){
                if(filters.equals("null")){
                    targetList.add(le);
                }else {
                    if(Filte(le,filters)){
                        targetList.add(le);
                    }
                }
            }
        }else if(aims.toLowerCase().contains("target")){
            if(target != null){
                if(filters.equals("null")){
                    targetList.add(target);
                }else {
                    if(Filte(target,filters)){
                        targetList.add(target);
                    }
                }
            }
        }else {
            if(filters.equals("null")){
                targetList.add(self);
            }else {
                if(Filte(self,filters)){
                    targetList.add(self);
                }
            }
        }

        return targetList;
    }

    /**過濾不要的目標**/
    public boolean Filte(LivingEntity livingEntity,String filteName){
        boolean b = true;

        FileConfiguration TargetFiltersConfig = ConfigMapManager.getFileConfigurationMap().get("Character_System_TargetFilters.yml");
        List<String> filteList = TargetFiltersConfig.getStringList(filteName+".TargetFilters");

        for(String filteKey : filteList){

            /**篩選掉派系目標**/
            if(filteKey.toLowerCase().contains("factions")){
                if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
                    String[] filteKey1 = filteKey.split("=");
                    if(filteKey1.length == 2){
                        String uuidString = livingEntity.getUniqueId().toString();
                        String faction = MobManager.mythicMobs_Faction_Map.get(uuidString);
                        if(filteKey1[1].equals(faction)){

                            b = false;
                        }
                    }


                }

            }
            /**篩選生物類別目標**/
            if(filteKey.toLowerCase().contains("entitytypelist")){
                String[] filteKey1 = filteKey.split("=");
                if(filteKey1.length == 2){
                    FileConfiguration entityTypeListConfig = ConfigMapManager.getFileConfigurationMap().get("Character_System_EntityTypeList.yml");
                    List<String> entityTypeList = entityTypeListConfig.getStringList(filteKey1[1]+".entityTypeList");
                    if(entityTypeList.contains(livingEntity.getType().toString())){
                        b = false;
                    }
                }
            }

            /**篩選生物MM類別目標**/
            if(filteKey.toLowerCase().contains("mythictypelist")){
                if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
                    String[] filteKey1 = filteKey.split("=");
                    if(filteKey1.length == 2){
                        FileConfiguration mythicTypeListConfig = ConfigMapManager.getFileConfigurationMap().get("Character_System_MythicTypeList.yml");
                        List<String> mythicTypeList = mythicTypeListConfig.getStringList(filteKey1[1]+".mythicTypeList");
                        String uuidString = livingEntity.getUniqueId().toString();
                        String id = MobManager.mythicMobs_mobID_Map.get(uuidString);;
                        if(mythicTypeList.contains(id)){

                            b = false;
                        }
                    }
                }
            }



        }


        return b;
    }

}
