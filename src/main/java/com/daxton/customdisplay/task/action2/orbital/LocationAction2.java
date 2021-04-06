package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.task.action2.Move2;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class LocationAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public LocationAction2(){

    }

    public void execute(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, Location location, String taskID){

        String judgMent = customLineConfig.getActionKey();
        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){
            if(ActionManager2.judgment_LocHolographic_Map.get(taskID) == null){
                ActionManager2.judgment_LocHolographic_Map.put(taskID,new LocationHolographic2());
                ActionManager2.judgment_LocHolographic_Map.get(taskID).setHD(self,target,customLineConfig,taskID,location);
            }else {
                ActionManager2.judgment_LocHolographic_Map.get(taskID).setHD(self,target,customLineConfig,taskID,location);
            }
        }
        /**Damage的相關判斷**/
        if(judgMent.toLowerCase().contains("damage")){
            new LocationDamage2().setDamage(self, target, customLineConfig, taskID);
        }
        /**Sound的相關判斷**/
        if(judgMent.toLowerCase().contains("sound")){
            new LocationSound2().setSound(self,target,customLineConfig,taskID,location);
        }

        /**Particle的相關判斷**/
        if(judgMent.toLowerCase().contains("particle")){
            new LocationParticles2().setParticles(self,target,customLineConfig,taskID,location);
        }

        /**PotionEffect的相關判斷**/
        if(judgMent.toLowerCase().contains("potioneffect")){
            new LocParticle().set(self, target, customLineConfig, taskID);
        }

        /**Guise的相關判斷**/
        if(judgMent.toLowerCase().contains("guise")){
            if(ActionManager2.judgment_LocItemEntity_Map.get(taskID) == null){
                ActionManager2.judgment_LocItemEntity_Map.put(taskID, new LocGuise());
                ActionManager2.judgment_LocItemEntity_Map.get(taskID).setItemEntity(self,target,customLineConfig,taskID, location);
            }else {
                ActionManager2.judgment_LocItemEntity_Map.get(taskID).setItemEntity(self,target,customLineConfig,taskID, location);
            }
        }

        /**Velocity的相關判斷**/
        if(judgMent.toLowerCase().contains("move")){
            new LocMove2().setVelocity(self,target,customLineConfig,taskID);
        }

    }

}
