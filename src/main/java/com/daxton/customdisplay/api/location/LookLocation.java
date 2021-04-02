package com.daxton.customdisplay.api.location;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class LookLocation {

    public LookLocation(){

    }

    public Location get(LivingEntity self, int distance){
//        double pitch = ((self.getLocation().getPitch() + 90) * Math.PI) / 180;
//        double yaw  = ((self.getLocation().getYaw() + 90)  * Math.PI) / 180;
//        double x = Math.sin(pitch) * Math.cos(yaw);
//        //double y = Math.cos(pitch);
//        double z = Math.sin(pitch) * Math.sin(yaw);
//
//        Vector vector = new Vector(x, 0, z).multiply(distance);
//        Vector vector1 = new Vector(0,hight,0);
        Location location = self.getLocation();
        Vector vector = location.getDirection().multiply(distance);
        location.add(vector);

        return location;
    }

}
