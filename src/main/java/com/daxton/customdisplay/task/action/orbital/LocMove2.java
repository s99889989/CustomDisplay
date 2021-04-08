package com.daxton.customdisplay.task.action.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class LocMove2 {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    LivingEntity self;

    public LocMove2(){

    }

    public void setVelocity(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        if(target == null){
            return;
        }
        String function = customLineConfig.getString(new String[]{"function","f"},"self",self,target);

        double hight = customLineConfig.getDouble(new String[]{"hight","h"},0,self,target);


        double angle = customLineConfig.getDouble(new String[]{"angle","a"},0,self,target);

        double distance = customLineConfig.getDouble(new String[]{"distance","d"},1,self,target);

        List<LivingEntity> livingEntityList = customLineConfig.getLivingEntityList(self,target);

        if(!(livingEntityList.isEmpty())){
            for(LivingEntity livingEntity : livingEntityList){
                move(self, livingEntity, function, hight, angle, distance);

            }

        }

//        if(target != null){
//            move(self, target, function, hight, angle, distance);
//        }
    }

    public void move(LivingEntity self, LivingEntity livingEntity, String function, double hight, double angle, double distance){

        if(function.toLowerCase().equals("self")){

            double pitch = ((self.getLocation().getPitch() + 90+hight) * Math.PI) / 180;
            double yaw  = ((self.getLocation().getYaw() + 90+angle)  * Math.PI) / 180;
            double x = Math.sin(pitch) * Math.cos(yaw);
            double y = Math.cos(pitch);
            double z = Math.sin(pitch) * Math.sin(yaw);

            Vector vector = new Vector(x, y, z).multiply(distance);

            livingEntity.setVelocity(vector);
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
