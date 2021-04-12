package com.daxton.customdisplay.api.location;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class DirectionLocation {

    public DirectionLocation(){

    }

    public Location getLook(LivingEntity self, int distance){

        Location location = self.getLocation();
        Vector vector = location.getDirection().multiply(distance);
        location.add(vector);

        return location;
    }

    /**給向量移動**/
    public static Location getSetDirection(Location inputLocation, Location dirLocation, double hight, double angle, double distance){

        Location location = inputLocation;

        double pitch = ((dirLocation.getPitch() + 90 + hight) * Math.PI) / 180;
        double yaw  = ((dirLocation.getYaw() + 90 + angle)  * Math.PI) / 180;


        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, y, z).multiply(distance);
        location.add(vector);


        return location;
    }

}
