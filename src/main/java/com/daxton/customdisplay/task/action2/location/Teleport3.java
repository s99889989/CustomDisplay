package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.Map;

public class Teleport3 {

    public Teleport3(){

    }

    public void setTp(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        double angle = actionMapHandle.getDouble(new String[]{"angle","a"},0);
        double distance = actionMapHandle.getDouble(new String[]{"distance","d"},1);

        double x = 0;
        double y = 0;
        double z = 0;
        String[] locAdds = actionMapHandle.getStringList(new String[]{"locadd","la"},new String[]{"0","0","0"},"\\|",3);
        if(locAdds.length == 3){

            try {
                x = Double.valueOf(locAdds[0]);
                y = Double.valueOf(locAdds[1]);
                z = Double.valueOf(locAdds[2]);
            }catch (NumberFormatException exception){
                x = 0;
                y = 0;
                z = 0;
            }
        }


        Location location = new Location(Bukkit.getWorld("world"),0,0,0);
        String locTarget = actionMapHandle.getString(new String[]{"loctarget","lt"},"");

        if(locTarget.toLowerCase().contains("self")){
            Vector vector = getVector(self, angle, distance);
            location = self.getLocation().add(x,y,z).add(vector);
        }else if(locTarget.toLowerCase().contains("target")){
            if(target != null){
                Vector vector = getVector(target, angle, distance);
                location = target.getLocation().add(x,y,z).add(vector);
            }
        }else if(locTarget.toLowerCase().contains("world")){
            location = new Location(self.getWorld(),x,y,z);
        }else {
            location = location.add(x,y,z);
        }

        goTp(self, location);

    }

    public Vector getVector(LivingEntity livingEntity, double angle, double distance){
        double pitch = ((livingEntity.getLocation().getPitch() + 90) * Math.PI) / 180;
        double yaw  = ((livingEntity.getLocation().getYaw() + 90+angle)  * Math.PI) / 180;
        double x = Math.sin(pitch) * Math.cos(yaw);
        //double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);
        Vector vector = new Vector(x, 0, z).multiply(distance);
        return vector;
    }

    public void goTp(LivingEntity livingEntity, Location location){

        livingEntity.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

}
