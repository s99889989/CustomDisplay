package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.SetValue;
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
        String aims = new SetValue(self,target,firstString,"[]; ","","@=").getString();

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
                if(Filte.valueOf(le,filters)){
                    targetList.add(le);
                }

            }
        }else if(target != null && aims.toLowerCase().contains("targetradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities2(self,target,radius);
            for(LivingEntity le : livingEntityList){
                if(Filte.valueOf(le,filters)){

                    targetList.add(le);
                }
            }
        }else if(aims.toLowerCase().contains("selfinworld")){
            self.getWorld().getEntities().forEach(entity -> {
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if(Filte.valueOf(livingEntity,filters)){
                        targetList.add(livingEntity);
                    }
                }
            });
        }else if(aims.toLowerCase().contains("server")){
            cd.getServer().getWorlds().forEach(world -> {
                world.getEntities().forEach(entity -> {
                    if(entity instanceof LivingEntity){
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if(Filte.valueOf(livingEntity,filters)){
                            targetList.add(livingEntity);
                        }
                    }
                });
            });
        }else if(aims.toLowerCase().contains("target")){
            if(target != null){
                if(Filte.valueOf(target,filters)){
                    targetList.add(target);
                }
            }
        }else if(aims.toLowerCase().contains("self")){
            if(Filte.valueOf(self,filters)){
                targetList.add(self);
            }
        } else {

        }



        return targetList;
    }

    /**選擇目標**/
    public List<LivingEntity> valueOf2(LivingEntity self, LivingEntity target, String firstString){

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
        if(firstString.toLowerCase().contains("selfradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities(self,radius);
            for(LivingEntity le : livingEntityList){
                if(Filte.valueOf(le,filters)){
                    targetList.add(le);
                }

            }
        }else if(target != null && firstString.toLowerCase().contains("targetradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities2(self,target,radius);
            for(LivingEntity le : livingEntityList){
                if(Filte.valueOf(le,filters)){

                    targetList.add(le);
                }
            }
        }else if(firstString.toLowerCase().contains("selfinworld")){
            self.getWorld().getEntities().forEach(entity -> {
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if(Filte.valueOf(livingEntity,filters)){
                        targetList.add(livingEntity);
                    }
                }
            });
        }else if(firstString.toLowerCase().contains("server")){
            cd.getServer().getWorlds().forEach(world -> {
                world.getEntities().forEach(entity -> {
                    if(entity instanceof LivingEntity){
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if(Filte.valueOf(livingEntity,filters)){
                            targetList.add(livingEntity);
                        }
                    }
                });
            });
        }else if(firstString.toLowerCase().contains("target")){
            if(target != null){
                if(Filte.valueOf(target,filters)){
                    targetList.add(target);
                }
            }
        }else if(firstString.toLowerCase().contains("self")){
            if(Filte.valueOf(self,filters)){
                targetList.add(self);
            }
        } else {

            targetList.add(self);

        }



        return targetList;
    }

}
