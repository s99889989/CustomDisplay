package com.daxton.customdisplay.task.locationAction;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.LocationActionManager;
import com.daxton.customdisplay.task.action.list.*;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class LocationAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();


    private int delay = 0;

    public LocationAction(){

    }

    public void execute(LivingEntity self, LivingEntity target, String firstString, Location location, String taskID){
        bukkitRun(self, target, firstString, location, taskID);
    }

    public void bukkitRun(LivingEntity self, LivingEntity target, String firstString, Location location, String taskID){
        String judgMent = new StringFind().getAction(firstString);
        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){
            if(LocationActionManager.location_Holographic_Map.get(taskID) == null){
                LocationActionManager.location_Holographic_Map.put(taskID,new LocationHolographic());
                LocationActionManager.location_Holographic_Map.get(taskID).setHD(self,target,firstString,taskID,location);
            }else {
                LocationActionManager.location_Holographic_Map.get(taskID).setHD(self,target,firstString,taskID,location);
            }
        }
        /**Damage的相關判斷**/
        if(judgMent.toLowerCase().contains("damage")){
            new LocationDamage().setDamage(self,target,firstString,taskID);
        }
        /**Sound的相關判斷**/
        if(judgMent.toLowerCase().contains("sound")){
            new LocationSound().setSound(self,target,firstString,taskID,location);
        }

        /**Particle的相關判斷**/
        if(judgMent.toLowerCase().contains("particle")){
            new LocationParticles().setParticles(self,target,firstString,taskID,location);
        }

    }


}
