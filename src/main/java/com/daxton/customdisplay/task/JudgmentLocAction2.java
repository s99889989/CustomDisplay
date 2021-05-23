package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action2.orbital.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class JudgmentLocAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public JudgmentLocAction2(){

    }

    public void execute(LivingEntity self, LivingEntity target, Map<String, String> action_Map, Location location, String taskID){
        String judgMent = new ActionMapHandle(action_Map, self, target).getString(new String[]{"ActionType"}, "");

        judgMent = judgMent.toLowerCase();

        /**Attribute的相關判斷**/
        if(judgMent.contains("attribute")){
            LocSetAttribute3.set(self,target,action_Map,taskID);
            return;
        }
        //ClassAttr的相關判斷
        if(judgMent.contains("classattr")){
            LocSetClassAttr.set(self,target,action_Map,taskID);
            return;
        }

        /**HolographicDisplays的相關判斷**/
        if(judgMent.contains("hologram")){
            if(ActionManager.judgment_LocHolographic_Map2.get(taskID) == null){
                ActionManager.judgment_LocHolographic_Map2.put(taskID,new LocHolographic3());
                ActionManager.judgment_LocHolographic_Map2.get(taskID).setHD(self, target, action_Map, taskID, location);
            }else {
                ActionManager.judgment_LocHolographic_Map2.get(taskID).setHD(self, target, action_Map, taskID, location);
            }
        }
        //ModelEngine的相關判斷
        if(judgMent.contains("model")){
            if(ActionManager.judgment_LocModelEngine_Map.get(taskID) == null){
                ActionManager.judgment_LocModelEngine_Map.put(taskID, new LocModelEngine());
            }
            if(ActionManager.judgment_LocModelEngine_Map.get(taskID) != null){
                ActionManager.judgment_LocModelEngine_Map.get(taskID).setGuise(self, target, action_Map, taskID, location);
            }
            return;
        }
        /**Damage的相關判斷**/
        if(judgMent.contains("damage")){
            new LocDamage3().setDamage(self, target, action_Map, taskID);
        }
        /**Sound的相關判斷**/
        if(judgMent.contains("sound")){
            new LocSound3().setSound(self,target,action_Map,taskID,location);
        }

        /**Particle的相關判斷**/
        if(judgMent.contains("particle")){
            new LocParticles3().setParticles(self,target,action_Map,taskID,location);
        }

        /**PotionEffect的相關判斷**/
        if(judgMent.contains("potioneffect")){
            new LocPotionEffect3().set(self, target, action_Map, taskID);
        }

        /**Guise的相關判斷**/
        if(judgMent.contains("guise")){

            if(ActionManager.judgment_LocItemEntity_Map2.get(taskID) == null){
                ActionManager.judgment_LocItemEntity_Map2.put(taskID, new LocGuise3());
                ActionManager.judgment_LocItemEntity_Map2.get(taskID).setItemEntity(self,target,action_Map,taskID, location);
            }else {
                ActionManager.judgment_LocItemEntity_Map2.get(taskID).setItemEntity(self,target,action_Map,taskID, location);
            }
        }

        //Light的相關判斷
        if(judgMent.contains("light")){
            if (Bukkit.getServer().getPluginManager().getPlugin("LightAPI") != null) {
                LocLight.setLight(self, target, action_Map, taskID, location);
            }
            return;
        }

        /**Velocity的相關判斷**/
        if(judgMent.contains("move")){
            new LocMove3().setVelocity(self,target,action_Map,taskID);
        }



    }

}
