package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action2.orbital.*;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class JudgmentLocAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public JudgmentLocAction2(){

    }

    public void execute(LivingEntity self, LivingEntity target, Map<String, String> action_Map, Location location, String taskID){
        String judgMent = new ActionMapHandle(action_Map, self, target).getString(new String[]{"ActionType"}, "");

        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){
            if(ActionManager.judgment_LocHolographic_Map2.get(taskID) == null){
                ActionManager.judgment_LocHolographic_Map2.put(taskID,new LocationHolographic3());
                ActionManager.judgment_LocHolographic_Map2.get(taskID).setHD(self, target, action_Map, taskID, location);
            }else {
                ActionManager.judgment_LocHolographic_Map2.get(taskID).setHD(self, target, action_Map, taskID, location);
            }
        }
        /**Damage的相關判斷**/
        if(judgMent.toLowerCase().contains("damage")){
            new LocationDamage3().setDamage(self, target, action_Map, taskID);
        }
        /**Sound的相關判斷**/
        if(judgMent.toLowerCase().contains("sound")){
            new LocationSound3().setSound(self,target,action_Map,taskID,location);
        }

        /**Particle的相關判斷**/
        if(judgMent.toLowerCase().contains("particle")){
            new LocationParticles3().setParticles(self,target,action_Map,taskID,location);
        }

        /**PotionEffect的相關判斷**/
        if(judgMent.toLowerCase().contains("potioneffect")){
            new LocPotionEffect3().set(self, target, action_Map, taskID);
        }

        /**Guise的相關判斷**/
        if(judgMent.toLowerCase().contains("guise")){
            if(ActionManager.judgment_LocItemEntity_Map2.get(taskID) == null){
                ActionManager.judgment_LocItemEntity_Map2.put(taskID, new LocGuise3());
                ActionManager.judgment_LocItemEntity_Map2.get(taskID).setItemEntity(self,target,action_Map,taskID, location);
            }else {
                ActionManager.judgment_LocItemEntity_Map2.get(taskID).setItemEntity(self,target,action_Map,taskID, location);
            }
        }

        /**Velocity的相關判斷**/
        if(judgMent.toLowerCase().contains("move")){
            new LocMove3().setVelocity(self,target,action_Map,taskID);
        }

        /**SetAttribute的相關判斷**/
        if(judgMent.toLowerCase().contains("setattribute")){
            new LocSetAttribute3().set(self,target,action_Map,taskID);
        }

    }

}
