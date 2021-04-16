package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Aims {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    /**選擇目標-數量不限**/
    public List<LivingEntity> valueOf(LivingEntity self, LivingEntity target, String firstString){

        /**目標**/
        String aims = new SetValue(self,target,firstString,"[]; ","","@").getString();

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

    /**選擇目標-不限數量**/
    public List<LivingEntity> getLivintEntityList(LivingEntity self, LivingEntity target, String inputString, String defaultTarget){
        String firstString = inputString;
        if(firstString == null){
            firstString = defaultTarget;
        }

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

    /**選擇目標-只選一個**/
    public LivingEntity getOneLivingEntity(LivingEntity self, LivingEntity target, String inputTarget, String defaultTarget){
        String choseString = inputTarget;
        if(choseString == null){
            choseString = defaultTarget;
        }


        /**距離**/
        double radius = 2;
        try {
            radius = Double.valueOf(new StringFind().getAimsValue(choseString,"r","1"));
        }catch (NumberFormatException exception){
            radius = 2;
        }

        /**瞄準目標**/
        String filters = new StringFind().getAimsValue(choseString,"filters","null");

        LivingEntity outputTarget = null;

        if(choseString.toLowerCase().contains("selfradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities(self,radius);
            for(LivingEntity le : livingEntityList){
                if(Filte.valueOf(le,filters)){
                    outputTarget = le;
                }
            }
            return outputTarget;
        }
        if(target != null && choseString.toLowerCase().contains("targetradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities2(self,target,radius);
            for(LivingEntity le : livingEntityList){
                if(Filte.valueOf(le,filters)){

                    outputTarget = le;
                }
            }
            return outputTarget;
        }
        if(choseString.toLowerCase().contains("selfinworld")){
            List<Entity> entityList =  self.getWorld().getEntities();
            for (Entity entity : entityList){
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if(Filte.valueOf(livingEntity,filters)){

                        outputTarget = livingEntity;
                    }
                }
            }
            return outputTarget;
        }
        if(choseString.toLowerCase().contains("server")){
            List<World> worldList = cd.getServer().getWorlds();
            for(World world : worldList){
                List<Entity> entityList =  world.getEntities();
                for (Entity entity : entityList){
                    if(entity instanceof LivingEntity){
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if(Filte.valueOf(livingEntity,filters)){
                            outputTarget = livingEntity;
                        }
                    }
                }
            }
            return outputTarget;
        }

        if(choseString.toLowerCase().contains("target")){
            if(target != null){
                if(Filte.valueOf(target,filters)){
                    outputTarget = target;
                }
            }
            return outputTarget;
        }

        if(choseString.toLowerCase().contains("self")){
            if(Filte.valueOf(self,filters)){
                outputTarget = self;
            }
            return outputTarget;
        }

        return outputTarget;
    }

}
