package com.daxton.customdisplay.api.location;

import com.daxton.customdisplay.api.entity.Filte;
import com.daxton.customdisplay.api.entity.RadiusTarget;
import com.daxton.customdisplay.api.other.StringFind;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AimsLocation {

    public AimsLocation(){

    }

    public List<Location> valueOf(LivingEntity self, LivingEntity target, String firstString, double x, double y, double z){
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


        List<Location> locationList = new ArrayList<>();
        if(aims.toLowerCase().contains("selfradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities(self,radius);
            for(LivingEntity le : livingEntityList){
                if(filters.equals("null")){
                    locationList.add(le.getLocation().add(x, y, z));
                }else {
                    if(Filte.valueOf(le,filters)){
                        locationList.add(le.getLocation().add(x, y, z));
                    }
                }

            }
        }else if(target != null && aims.toLowerCase().contains("targetradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities2(self,target,radius);
            for(LivingEntity le : livingEntityList){
                if(filters.equals("null")){
                    locationList.add(le.getLocation().add(x, y, z));
                }else {
                    if(Filte.valueOf(le,filters)){
                        locationList.add(le.getLocation().add(x, y, z));
                    }
                }
            }
        }else if(aims.toLowerCase().contains("target")){
            if(target != null){
                if(filters.equals("null")){
                    locationList.add(target.getLocation().add(x, y, z));
                }else {
                    if(Filte.valueOf(target,filters)){
                        locationList.add(target.getLocation().add(x, y, z));
                    }
                }
            }
        }else if(aims.toLowerCase().contains("world")){
            locationList.add(new Location(self.getWorld(),x,y,z));
        }else {
            if(filters.equals("null")){
                locationList.add(self.getLocation().add(x, y, z));
            }else {
                if(Filte.valueOf(self,filters)){
                    locationList.add(self.getLocation().add(x, y, z));
                }
            }
        }

        return locationList;
    }

    public Map<String,Location> valueOfMap(LivingEntity self, LivingEntity target, String firstString, String taskID, double x, double y, double z, Map<String,Location> inputLocationMap){
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

        Map<String,Location > locationMap = new HashMap<>();

        if(aims.toLowerCase().contains("selfradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities(self,radius);
            for(LivingEntity le : livingEntityList){
                if(Filte.valueOf(le,filters)){
                    String uuidString = le.getUniqueId().toString();
                    locationMap.put(uuidString,le.getLocation().add(x, y, z));
                }
            }
        }else if(target != null && aims.toLowerCase().contains("targetradius")){
            List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities2(self,target,radius);
            for(LivingEntity le : livingEntityList){
                if(Filte.valueOf(le,filters)){
                    String uuidString = le.getUniqueId().toString();
                    locationMap.put(uuidString,le.getLocation().add(x, y, z));

                }
            }
        }else if(target != null && aims.toLowerCase().contains("target")){

            if(Filte.valueOf(target,filters)){
                String uuidString = target.getUniqueId().toString();
                locationMap.put(uuidString,target.getLocation().add(x, y, z));
            }

        }else if(aims.toLowerCase().contains("world")){
            locationMap.put(taskID,new Location(self.getWorld(),x,y,z));
        }else {
            if(Filte.valueOf(self,filters)){
                String uuidString = self.getUniqueId().toString();
                locationMap.put(uuidString,self.getLocation().add(x, y, z));
            }
        }

        return locationMap;
    }

}
