package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import com.daxton.customdisplay.task.action2.*;
import com.daxton.customdisplay.task.action2.location.*;
import com.daxton.customdisplay.task.action2.meta.Action3;
import com.daxton.customdisplay.task.action2.meta.Loop3;
import com.daxton.customdisplay.task.action2.orbital.FixedPoint3;
import com.daxton.customdisplay.task.action2.orbital.LocPng3;
import com.daxton.customdisplay.task.action2.orbital.OrbitalAction3;
import com.daxton.customdisplay.task.action2.player.*;
import com.daxton.customdisplay.task.action2.profession.AttributePoint3;
import com.daxton.customdisplay.task.action2.profession.Point3;
import com.daxton.customdisplay.task.action2.profession.SetAttribute3;
import com.daxton.customdisplay.task.action2.profession.SetSkillLevel3;
import com.daxton.customdisplay.task.action2.server.LoggerInfo3;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class JudgmentAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public JudgmentAction2(){

    }

    public void execute(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        String judgMent = new ActionMapHandle(action_Map, self, target).getString(new String[]{"ActionType"}, "");
        //cd.getLogger().info("觸發: "+judgMent + " : "+taskID);


        /**ActionBar的相關判斷**/
        if(judgMent.toLowerCase().contains("actionbar")){
            new ActionBar3().setActionBar(self, target, action_Map, taskID);
            return;
        }

        /**Action的相關判斷**/
        if(judgMent.toLowerCase().contains("action")){

            new Action3().setAction(self, target, action_Map, taskID);
            return;
        }

        /**AttributePoint的相關判斷**/
        if(judgMent.toLowerCase().contains("attributepoint")){
            new AttributePoint3().setAttributePoint(self,target,action_Map,taskID);
            return;
        }


        /**BossBar的相關判斷**/
        if(judgMent.toLowerCase().contains("bossbar")){

            if(ActionManager.judgment_SendBossBar_Map2.get(taskID) == null){
                ActionManager.judgment_SendBossBar_Map2.put(taskID,new SendBossBar3());
            }
            if(ActionManager.judgment_SendBossBar_Map2.get(taskID) != null){
                ActionManager.judgment_SendBossBar_Map2.get(taskID).setBossBar(self, target, action_Map, taskID);
            }
            return;
        }

        /**Cancell的相關判斷**/
        if(judgMent.toLowerCase().contains("cancell")){
            new Cancell3().setCancell(self,target,action_Map,taskID);
            return;
        }

        /**Command的相關判斷**/
        if(judgMent.toLowerCase().contains("command")){
            new Command3().setCommand(self,target,action_Map,taskID);
            return;
        }

        /**CustomPoint的相關判斷**/
        if(judgMent.toLowerCase().contains("custompoint")){
            new Point3().setPoint(self,target,action_Map,taskID);
            return;
        }

        /**CoreSkill的相關判斷**/
        if(judgMent.toLowerCase().contains("setskilllevel")){
            new SetSkillLevel3().setCoreSkill(self,target,action_Map,taskID);
            return;
        }

        /**Damage的相關判斷**/
        if(judgMent.toLowerCase().contains("damage")){
            new Damage3().setDamage(self,target,action_Map,taskID);
            return;
        }

        /**DCMessage的相關判斷**/
        if(judgMent.toLowerCase().contains("dcmessage")){
            new DCMessage3().setDCMessage(self,target,action_Map,taskID);
            return;
        }

        /**Experience的相關判斷**/
        if(judgMent.toLowerCase().contains("exp")){
            new Experience3().setExp(self,target,action_Map,taskID);
            return;
        }

        /**FixedPoint的相關判斷**/
        if(judgMent.toLowerCase().contains("fixedpoint")){

            new FixedPoint3().set(self,target,action_Map,taskID+(Math.random()*100000));
            return;
        }

        /**GiveItem的相關判斷**/
        if(judgMent.toLowerCase().contains("item")){
            new GiveItem3().setItem(self,target,action_Map,taskID);
            return;
        }

        /**Glow的相關判斷**/
        if(judgMent.toLowerCase().contains("glow")){
            new setGlow3().setGlow(self,target,action_Map,taskID);
            return;
        }

        /**Guise的相關判斷**/
        if(judgMent.toLowerCase().contains("guise")){

            if(ActionManager.judgment_Guise_Map2.get(taskID) == null){
                ActionManager.judgment_Guise_Map2.put(taskID, new Guise3());
            }
            if(ActionManager.judgment_Guise_Map2.get(taskID) != null){
                ActionManager.judgment_Guise_Map2.get(taskID).setItemEntity(self,target,action_Map,taskID);
            }
            return;
        }

        /**Heal的相關判斷**/
        if(judgMent.toLowerCase().contains("heal")){
            new Heal3().setHeal(self,target,action_Map,taskID);
            return;
        }


        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){

            if(ActionManager.judgment_Holographic_Map2.get(taskID) == null){
                ActionManager.judgment_Holographic_Map2.put(taskID,new Holographic3());
            }
            if(ActionManager.judgment_Holographic_Map2.get(taskID) != null){
                ActionManager.judgment_Holographic_Map2.get(taskID).setHD(self, target, action_Map, taskID);
            }
            return;
        }


        /**Inventory的相關判斷**/
        if(judgMent.toLowerCase().contains("inventory")){
            String uuidString = self.getUniqueId().toString();
            if(EditorGUIManager.menu_OpenInventory_Map.get(uuidString) == null){
                EditorGUIManager.menu_OpenInventory_Map.put(uuidString,new OpenInventory3());
            }
            if(EditorGUIManager.menu_OpenInventory_Map.get(uuidString) != null){
                EditorGUIManager.menu_OpenInventory_Map.get(uuidString).setInventory(self, target, action_Map, taskID);
            }
            return;
        }

        /**LocPng的相關判斷**/
        if(judgMent.toLowerCase().contains("locpng")){

            new LocPng3().set(self,target,action_Map,taskID);
            return;
        }

        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){

            if(ActionManager.judgment_Loop_Map2.get(taskID) == null){
                ActionManager.judgment_Loop_Map2.put(taskID,new Loop3());
                ActionManager.judgment_Loop_Map2.get(taskID).onLoop(self, target, action_Map, taskID);
            }

            return;
        }

        /**LoggerInfo的相關判斷**/
        if(judgMent.toLowerCase().contains("loggerinfo")){
            new LoggerInfo3().setLoggerInfo(self,target,action_Map,taskID);
            return;
        }

        /**Level的相關判斷**/
        if(judgMent.toLowerCase().contains("level")){
            new Level3().setLevel(self,target,action_Map,taskID);
            return;
        }


        /**ModMessage的相關判斷**/
        if(judgMent.toLowerCase().contains("modmessage")){
            new ModMessage3().setMessage(self,target,action_Map,taskID);
            return;
        }

        /**Message的相關判斷**/
        if(judgMent.toLowerCase().contains("message")){
            new Message3().setMessage(self,target,action_Map,taskID);
            return;
        }


        /**MythicSkill的相關判斷**/
        if(judgMent.toLowerCase().contains("mythicskill")){
            new MythicAction3().setMythicAction(self,target,action_Map,taskID);
            return;
        }

        /**Mana的相關判斷**/
        if(judgMent.toLowerCase().contains("mana")){
            new setMana3().setMana(self,target,action_Map,taskID);
            return;
        }

        /**Name的相關判斷**/
        if(judgMent.toLowerCase().contains("name")){
            new SetName3().setName(self,target,action_Map,taskID);
            return;
        }

        /**OrbitalAttack的相關判斷**/
        if(judgMent.toLowerCase().contains("orbital")){

            new OrbitalAction3().setParabolicAttack(self,target,action_Map,taskID); //+(Math.random()*100000)
            return;
        }

        /**Particle的相關判斷**/
        if(judgMent.toLowerCase().contains("particle")){
            new SendParticles3().setParticles(self,target,action_Map,taskID);
            return;
        }

        /**SetAttribute的相關判斷**/
        if(judgMent.toLowerCase().contains("setattribute")){
            new SetAttribute3().set(self,target,action_Map,taskID);
            return;
        }

        /**Sound的相關判斷**/
        if(judgMent.toLowerCase().contains("sound")){
            new Sound3().setSound(self,target,action_Map,taskID);
            return;
        }

        /**Title的相關判斷**/
        if(judgMent.toLowerCase().contains("title")){
            new Title3().setTitle(self,target,action_Map,taskID);
            return;
        }

        /**Teleport的相關判斷**/
        if(judgMent.toLowerCase().contains("teleport")){
            new Teleport3().setTp(self,target,action_Map,taskID);
            return;
        }

        /**Velocity的相關判斷**/
        if(judgMent.toLowerCase().contains("move")){
            new Move3().setVelocity(self,target,action_Map,taskID);
            return;
        }


    }


}
