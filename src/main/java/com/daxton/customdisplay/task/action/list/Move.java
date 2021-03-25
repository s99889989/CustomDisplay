package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class Move {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    LivingEntity self;

    public Move(){

    }

    public void setVelocity(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        String function = new SetValue(self,target,firstString,"[];","self","function=","f=").getString();

        double hight = new SetValue(self,target,firstString,"[];","0","hight=","h=").getDouble(0);

        double angle = new SetValue(self,target,firstString,"[];","0","angle=","a=").getDouble(0);

        double distance = new SetValue(self,target,firstString,"[];","1","distance=","d=").getDouble(1);

        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);
        if(!(targetList.isEmpty())){
            for (LivingEntity livingEntity : targetList){
                move(self, livingEntity, function, hight, angle, distance);
            }
        }

    }

    public void move(LivingEntity self, LivingEntity livingEntity, String function, double hight, double angle, double distance){

        if(function.toLowerCase().equals("self")){

            double pitch = ((self.getLocation().getPitch() + 90) * Math.PI) / 180;
            double yaw  = ((self.getLocation().getYaw() + 90+angle)  * Math.PI) / 180;
            double x = Math.sin(pitch) * Math.cos(yaw);
            //double y = Math.cos(pitch);
            double z = Math.sin(pitch) * Math.sin(yaw);

            Vector vector = new Vector(x, 0, z).multiply(distance);
            Vector vector1 = new Vector(0,hight,0);
            livingEntity.setVelocity(vector.add(vector1));
        }else if(function.toLowerCase().equals("target")){
            double pitch = ((livingEntity.getLocation().getPitch() + 90) * Math.PI) / 180;
            double yaw  = ((livingEntity.getLocation().getYaw() + 90+angle)  * Math.PI) / 180;
            double x = Math.sin(pitch) * Math.cos(yaw);
            double z = Math.sin(pitch) * Math.sin(yaw);
            Vector vector = new Vector(x, 0, z).multiply(distance);
            Vector vector1 = new Vector(0,hight,0);
            livingEntity.setVelocity(vector.add(vector1));
        }else if(function.toLowerCase().equals("selfaway")){

            Location locuser = self.getEyeLocation();
            Location loctarget = livingEntity.getEyeLocation();
            Vector vec = loctarget.subtract(locuser).toVector().normalize().multiply(distance);
            Vector vector1 = new Vector(0,hight,0);
            livingEntity.setVelocity(vec.add(vector1));
        }else if(function.toLowerCase().equals("targetaway")){

            Location locuser = livingEntity.getEyeLocation();
            Location loctarget = self.getEyeLocation();
            Vector vec = loctarget.subtract(locuser).toVector().normalize().multiply(distance);
            Vector vector1 = new Vector(0,hight,0);
            self.setVelocity(vec.add(vector1));
        }else {
            double pitch = ((self.getLocation().getPitch() + 90) * Math.PI) / 180;
            double yaw  = ((self.getLocation().getYaw() + 90+angle)  * Math.PI) / 180;
            double x = Math.sin(pitch) * Math.cos(yaw);
            //double y = Math.cos(pitch);
            double z = Math.sin(pitch) * Math.sin(yaw);

            Vector vector = new Vector(x, 0, z).multiply(distance);
            Vector vector1 = new Vector(0,hight,0);
            livingEntity.setVelocity(vector.add(vector1));
        }


//        if(self instanceof Player){
//            Player player = (Player) self;
//            Location locuser = player.getPlayer().getEyeLocation();
//            Location loctarget = livingEntity.getEyeLocation();
//            Vector vec = loctarget.subtract(locuser).toVector().normalize();
//            player.sendMessage(vec.getX()+" : "+vec.getY()+" : "+vec.getZ());
//            livingEntity.setVelocity(vec.multiply(-5));
//        }



    }

}
