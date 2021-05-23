package com.daxton.customdisplay.api.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.entity.*;
import com.daxton.customdisplay.api.other.StringFind;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AimsLocation {

    public AimsLocation(){

    }

    public List<Location> valueOf(LivingEntity self, LivingEntity target, String firstString, double x, double y, double z){
        //目標
        String aims = new StringFind().getAimsValue(firstString,"aims","self");

        //距離
        double radius;
        try {
            radius = Double.parseDouble(new StringFind().getAimsValue(firstString,"r","1"));
        }catch (NumberFormatException exception){
            radius = 2;
        }

        //瞄準目標
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

    public Location valueOf2(LivingEntity self, LivingEntity target, String firstString, String defalut, Location inputLocation){
        Location location = null;
        LivingEntity livingEntity = Aims.getOneLivingEntity(self, target, firstString, defalut);
        if(livingEntity != null){
            location = livingEntity.getLocation();
            return location;
        }
        if(inputLocation != null){
            location = inputLocation;
            return location;
        }
        if(location == null){
            location = new Location(Bukkit.getWorld("world"),0,0,0);
        }

        return location;
    }

    public static Location getOneLocation(LivingEntity self, LivingEntity inputTarget, String inputString, String defaultTarget, Location inputLocation){
        Location location = null;

        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        String firstString = inputString;
        if(firstString == null){
            firstString = defaultTarget;
        }

        Map<String, String> targetMap = SetActionMap.setTargetMap(firstString);

        TargetMapHandle targetMapHandle = new TargetMapHandle(self, inputTarget, targetMap);

        //瞄準目標
        String targetKey = targetMapHandle.getString(new String[]{"targettype"},"null");

        //距離
        double distance = targetMapHandle.getDouble(new String[]{"distance","d"},0);

        LivingEntity target = inputTarget;
        if(target == null && distance > 0){
            target = LookTarget.getLivingTarget(self,distance);
        }

        //座標向量偏移
        String[] vecAdds = targetMapHandle.getStringList(new String[]{"VectorAdd","va"},new String[]{"null","true","true","0","0","0"},"\\|",6);
        String directionT = vecAdds[0];
        boolean targetPitch = Boolean.parseBoolean(vecAdds[1]);
        boolean targetYaw = Boolean.parseBoolean(vecAdds[2]);
        double addPitch;
        double addYaw;
        double addDistance;
        try {
            addPitch = Double.parseDouble(vecAdds[3]);
            addYaw = Double.parseDouble(vecAdds[4]);
            addDistance = Double.parseDouble(vecAdds[5]);
        }catch (NumberFormatException exception){
            addPitch = 0;
            addYaw = 0;
            addDistance = 0;
        }

        //座標偏移
        String[] locAdds = targetMapHandle.getStringList(new String[]{"LocationAdd","la"},new String[]{"0","0","0"},"\\|",3);
        double addX;
        double addY;
        double addZ;
        try {
            addX = Double.parseDouble(locAdds[0]);
            addY = Double.parseDouble(locAdds[1]);
            addZ = Double.parseDouble(locAdds[2]);
        }catch (NumberFormatException exception){
            addX = 0;
            addY = 0;
            addZ = 0;
        }

        boolean onblock = targetMapHandle.getBoolean(new String[]{"onblock","ob"}, false);

        switch (targetKey){
            case "locself":
                if(directionT.toLowerCase().contains("target") && target != null){
                    location = DirectionLocation.getDirectionLoction(self.getLocation(), target.getLocation(), targetPitch, targetYaw, addPitch, addYaw, addDistance).add(addX, addY, addZ);
                }else {
                    location = DirectionLocation.getDirectionLoction(self.getLocation(), self.getLocation(), targetPitch, targetYaw, addPitch, addYaw, addDistance).add(addX, addY, addZ);
                }
                break;
            case "loctarget":
                if(target != null){
                    if(directionT.toLowerCase().contains("target")){
                        location = DirectionLocation.getDirectionLoction(target.getLocation(), target.getLocation(), targetPitch, targetYaw, addPitch, addYaw, addDistance).add(addX, addY, addZ);
                    }else {
                        location = DirectionLocation.getDirectionLoction(target.getLocation(), self.getLocation(), targetPitch, targetYaw, addPitch, addYaw, addDistance).add(addX, addY, addZ);
                    }
                }
                break;
            case "locadd":
                if(inputLocation != null){
                    if(directionT.toLowerCase().contains("target") && target != null){
                        location = DirectionLocation.getDirectionLoction(inputLocation, target.getLocation(), targetPitch, targetYaw, addPitch, addYaw, addDistance).add(addX, addY, addZ);
                    }else if(directionT.toLowerCase().contains("self")){
                        location = DirectionLocation.getDirectionLoction(inputLocation, self.getLocation(), targetPitch, targetYaw, addPitch, addYaw, addDistance).add(addX, addY, addZ);
                    }else {
                        location = inputLocation.add(addX, addY, addZ);
                    }
                }
                break;
        }

        if(onblock && location != null){
            while(!location.getBlock().getTranslationKey().equals("block.minecraft.air")){
                location.setY(location.getBlockY()+1);

            }
            while(location.getBlock().getTranslationKey().equals("block.minecraft.air")){
                location.setY(location.getY()-0.1);

            }
            while(!location.getBlock().getTranslationKey().equals("block.minecraft.air")){
                location.setY(location.getBlockY()+1);

            }
        }
        if(location == null){
            location = inputLocation;
        }

        return location;
    }

}
