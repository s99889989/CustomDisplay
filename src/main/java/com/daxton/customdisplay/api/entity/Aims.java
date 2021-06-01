package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Aims {

    /**選擇目標-數量不限**/
    public List<LivingEntity> valueOf(LivingEntity self, LivingEntity target, String firstString){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        //目標
        String aims = new SetValue(self,target,firstString,"[]; ","","@").getString();

        //距離
        double radius = 2;
        try {
            radius = Double.valueOf(new StringFind().getAimsValue(firstString,"r","1"));
        }catch (NumberFormatException exception){
            radius = 2;
        }

        //瞄準目標
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

    //選擇目標-不限數量
    public static List<LivingEntity> getLivintEntityList(LivingEntity self, LivingEntity inputTarget, String inputString, String defaultTarget){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        String firstString = inputString;
        if(firstString == null){
            firstString = defaultTarget;
        }

        Map<String, String> targetMap = SetActionMap.setTargetMap(firstString);

        TargetMapHandle targetMapHandle = new TargetMapHandle(self, inputTarget, targetMap);

        //瞄準目標
        String targetKey = targetMapHandle.getString(new String[]{"targettype"},"null");

        //半徑
        double radius = targetMapHandle.getDouble(new String[]{"radius","r"},1);

        //距離
        double distance = targetMapHandle.getDouble(new String[]{"distance","d"},0);

        //篩選目標
        String filters = targetMapHandle.getString(new String[]{"filters","f"},"null");
        LivingEntity target = inputTarget;
        if(target == null && distance > 0){
            target = LookTarget.getLivingTarget(self,distance);
        }

        List<LivingEntity> targetList = new ArrayList<>();

        switch (targetKey){
            case "selfradius":
                List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities(self,radius);
                for(LivingEntity le : livingEntityList){
                        targetList.add(le);
                }
                break;
            case "targetradius":
                if(target != null){
                    List<LivingEntity> livingEntityList2 = RadiusTarget.getRadiusLivingEntities2(self,target,radius);
                    for(LivingEntity le : livingEntityList2){
                        targetList.add(le);
                    }
                }
                break;
            case "selfline":
                List<LivingEntity> livingEntityList3 = Line.getMulti(self,radius,0.2);
                for(LivingEntity le : livingEntityList3){
                    targetList.add(le);
                }
                break;
            case "target":
                if(target != null){
                    targetList.add(target);
                }
                break;
            case "self":
                targetList.add(self);
                break;
            case "server":
                cd.getServer().getWorlds().forEach(world -> {
                    world.getEntities().forEach(entity -> {
                        if(entity instanceof LivingEntity){
                            LivingEntity livingEntity = (LivingEntity) entity;
                            targetList.add(livingEntity);
                        }
                    });
                });
            case "selfinworld":
                self.getWorld().getEntities().forEach(entity -> {
                    if(entity instanceof LivingEntity){
                        LivingEntity livingEntity = (LivingEntity) entity;
                        targetList.add(livingEntity);
                    }
                });
                break;

        }

        //篩選目標
        List<LivingEntity> targetFiltList = new ArrayList<>();

        if(!filters.equals("null")){
            for(LivingEntity livingEntity : targetList){
                if(Filte.valueOf(livingEntity, filters)){
                    targetFiltList.add(livingEntity);
                }
            }
        }else {
            targetFiltList = targetList;
        }


        return targetFiltList;
    }

    /**選擇目標-單目標**/
    public static LivingEntity getOneLivintEntity2(LivingEntity self, LivingEntity inputTarget, String inputString, String defaultTarget){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        String firstString = inputString;
        if(firstString == null){
            firstString = defaultTarget;
        }

        Map<String, String> targetMap = SetActionMap.setTargetMap(firstString);

        TargetMapHandle targetMapHandle = new TargetMapHandle(self, inputTarget, targetMap);

        //瞄準目標
        String targetKey = targetMapHandle.getString(new String[]{"targettype"},"null");

        //半徑
        double radius = targetMapHandle.getDouble(new String[]{"radius","r"},1);

        //距離
        double distance = targetMapHandle.getDouble(new String[]{"distance","d"},0);

        //篩選目標
        String filters = targetMapHandle.getString(new String[]{"filters"},"null");

        LivingEntity target = inputTarget;
        if(target == null && distance > 0){
            target = LookTarget.getLivingTarget(self,distance);

        }

        List<LivingEntity> targetList = new ArrayList<>();

        switch (targetKey){
            case "selfradius":
                List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities(self,radius);
                for(LivingEntity le : livingEntityList){
                    targetList.add(le);
                }
                break;
            case "targetradius":
                if(target != null){
                    List<LivingEntity> livingEntityList2 = RadiusTarget.getRadiusLivingEntities2(self,target,radius);
                    for(LivingEntity le : livingEntityList2){
                        targetList.add(le);
                    }
                }
                break;
            case "target":
                if(target != null){
                    targetList.add(target);
                }
                break;
            case "self":
                targetList.add(self);
                break;
            case "server":
                cd.getServer().getWorlds().forEach(world -> {
                    world.getEntities().forEach(entity -> {
                        if(entity instanceof LivingEntity){
                            LivingEntity livingEntity = (LivingEntity) entity;
                            targetList.add(livingEntity);
                        }
                    });
                });
            case "selfinworld":
                self.getWorld().getEntities().forEach(entity -> {
                    if(entity instanceof LivingEntity){
                        LivingEntity livingEntity = (LivingEntity) entity;
                        targetList.add(livingEntity);
                    }
                });
                break;

        }

        //篩選目標
        List<LivingEntity> targetFiltList = new ArrayList<>();

        if(!filters.equals("null")){
            for(LivingEntity livingEntity : targetList){
                if(Filte.valueOf(livingEntity, filters)){
                    targetFiltList.add(livingEntity);
                }
            }
        }else {
            targetFiltList = targetList;
        }
//        if(targetFiltList.size() > 1){
//            for(int k = 1 ; k < targetFiltList.size() ; k++){
//                targetFiltList.remove(k);
//            }
//        }
        LivingEntity outputlivingentity = null;
        if(targetFiltList.size() > 0){
            outputlivingentity = targetFiltList.get(0);
        }


        return outputlivingentity;
    }

    /**選擇目標-只選一個**/
    public static LivingEntity getOneLivingEntity(LivingEntity self, LivingEntity target, String inputTarget, String defaultTarget){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
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
