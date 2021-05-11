package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class RadiusTarget {

    //獲得圓半徑目標(不包含自己)
    public static List<LivingEntity> getRadiusLivingEntities(LivingEntity self, double radius) {
        List<Entity> targetEntityList = self.getNearbyEntities(radius, radius, radius);
        double sX = self.getLocation().getX();
        double sY = self.getLocation().getY();
        double sZ = self.getLocation().getZ();
        List<LivingEntity> livingEntityList = new ArrayList<>();
        if(targetEntityList.size() > 0){
            for(Entity targetEntity : targetEntityList){
                if(targetEntity instanceof LivingEntity){
                    double tX = targetEntity.getLocation().getX();
                    double tY = targetEntity.getLocation().getY();
                    double tZ = targetEntity.getLocation().getZ();
                    double dd = Math.sqrt(Math.pow((tX-sX),2) + Math.pow((tY-sY),2) +Math.pow((tZ-sZ),2));
                    if(dd <= radius){
                        livingEntityList.add((LivingEntity) targetEntity);
                    }
                }
            }
        }
        return livingEntityList;
    }

    //獲得圓半徑目標(包含自己)
    public static List<LivingEntity> getRadiusLivingEntities2(LivingEntity self, LivingEntity target, double radius) {
        List<Entity> targetEntityList = target.getNearbyEntities(radius, radius, radius);
        double sX = target.getLocation().getX();
        double sY = target.getLocation().getY();
        double sZ = target.getLocation().getZ();
        List<LivingEntity> livingEntityList = new ArrayList<>();
        if(targetEntityList.size() > 0){
            for(Entity targetEntity : targetEntityList){
                if(targetEntity instanceof LivingEntity){
                    double tX = targetEntity.getLocation().getX();
                    double tY = targetEntity.getLocation().getY();
                    double tZ = targetEntity.getLocation().getZ();
                    double dd = Math.sqrt(Math.pow((tX-sX),2) + Math.pow((tY-sY),2) +Math.pow((tZ-sZ),2));
                    if(dd <= radius){
                        livingEntityList.add((LivingEntity) targetEntity);
                    }
                }
            }
        }
        if(!livingEntityList.contains(target)){
            livingEntityList.add(target);
        }
        if(livingEntityList.contains(self)){
            livingEntityList.remove(self);
        }

        return livingEntityList;
    }

    //獲得圓半徑目標(不包含自己)
    public static List<LivingEntity> getRadiusLivingEntities3(LivingEntity self, Location location, double radius) {
        //CustomDisplay.getCustomDisplay().getLogger().info(self.getName()+" : "+location.getX()+" : "+location.getY()+" : "+radius);
        List<Entity> targetEntityList = new ArrayList<>(location.getWorld().getNearbyEntities(location,radius,radius,radius));

        double sX = location.getX();
        double sY = location.getY();
        double sZ = location.getZ();
        List<LivingEntity> livingEntityList = new ArrayList<>();
        if(targetEntityList.size() > 0){
            for(Entity targetEntity : targetEntityList){
                if(targetEntity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) targetEntity;
                    if(livingEntity.getCustomName() != null && livingEntity.getCustomName().equals("ModleEngine")){
                        continue;
                    }
                    //CustomDisplay.getCustomDisplay().getLogger().info("範圍"+livingEntity.getName());
                    double tX = livingEntity.getEyeLocation().getX();
                    double tY = livingEntity.getEyeLocation().getY();
                    double tZ = livingEntity.getEyeLocation().getZ();
                    double dd = Math.sqrt(Math.pow((tX-sX),2) + Math.pow((tY-sY),2) +Math.pow((tZ-sZ),2));
                    if(dd <= radius){
                        //CustomDisplay.getCustomDisplay().getLogger().info("範圍內"+livingEntity.getName());
                        livingEntityList.add((LivingEntity) targetEntity);
                    }
                    //livingEntityList.add(livingEntity);
                }
            }
        }
        if(livingEntityList.contains(self)){
            livingEntityList.remove(self);
        }
        return livingEntityList;
    }



}
