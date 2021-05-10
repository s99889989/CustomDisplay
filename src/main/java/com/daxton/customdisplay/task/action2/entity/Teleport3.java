package com.daxton.customdisplay.task.action2.entity;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.location.DirectionLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

public class Teleport3 {

    public Teleport3(){

    }

    public static void setTp(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, target);


        List<LivingEntity> livingEntityList = actionMapHandle2.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity -> {

            ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, livingEntity);

            //座標向量偏移
            String[] vecAdds = actionMapHandle.getStringList(new String[]{"VectorAdd","va"},new String[]{"self","true","true","0","0","0"},"\\|",6);
            String directionT = vecAdds[0];
            boolean targetPitch = Boolean.parseBoolean(vecAdds[1]);
            boolean targetYaw = Boolean.parseBoolean(vecAdds[2]);
            double addPitch = 0;
            double addYaw = 0;
            double addDistance = 0;
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
            String[] locAdds = actionMapHandle.getStringList(new String[]{"LocationAdd","la"},new String[]{"0","0","0"},"\\|",3);
            double addX = 0;
            double addY = 0;
            double addZ = 0;
            try {
                addX = Double.parseDouble(locAdds[0]);
                addY = Double.parseDouble(locAdds[1]);
                addZ = Double.parseDouble(locAdds[2]);
            }catch (NumberFormatException exception){
                addX = 0;
                addY = 0;
                addZ = 0;
            }

            boolean onblock = actionMapHandle.getBoolean(new String[]{"onblock","ob"}, false);

            Location location = null;
            if(target != null && directionT.toLowerCase().contains("target")){
                location = DirectionLocation.getDirectionLoction(livingEntity.getLocation(), livingEntity.getLocation(), targetPitch, targetYaw, addPitch, addYaw, addDistance).add(addX, addY, addZ);
            }else {
                location = DirectionLocation.getDirectionLoction(livingEntity.getLocation(), self.getLocation(), targetPitch, targetYaw, addPitch, addYaw, addDistance).add(addX, addY, addZ);
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

            if(location != null){
                goTp(livingEntity, location);
            }

        });

    }


    public static void goTp(LivingEntity livingEntity, Location location){

        livingEntity.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

}
