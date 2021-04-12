package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.*;
import com.daxton.customdisplay.task.action.location.*;
import com.daxton.customdisplay.task.action.orbital.FixedPoint;
import com.daxton.customdisplay.task.action.orbital.LocPng;
import com.daxton.customdisplay.task.action.orbital.OrbitalAction2;
import com.daxton.customdisplay.task.action.noplayer.SetName2;
import com.daxton.customdisplay.task.action.player.*;
import com.daxton.customdisplay.task.action.profession.AttributePoint2;
import com.daxton.customdisplay.task.action.profession.SetSkillLevel;
import com.daxton.customdisplay.task.action.profession.Point2;
import com.daxton.customdisplay.task.action.profession.SetAttribute2;
import com.daxton.customdisplay.task.action.server.LoggerInfo2;
import org.bukkit.entity.LivingEntity;

public class JudgmentAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public JudgmentAction(){

    }

    public void execute(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        String judgMent = customLineConfig.getActionKey();
        //cd.getLogger().info("觸發: "+judgMent + " : "+taskID);


        /**ActionBar的相關判斷**/
        if(judgMent.toLowerCase().contains("actionbar")){
            if(ActionManager.judgment_ActionBar_Map.get(taskID) == null){
                ActionManager.judgment_ActionBar_Map.put(taskID,new ActionBar2());
            }
            if(ActionManager.judgment_ActionBar_Map.get(taskID) != null){
                ActionManager.judgment_ActionBar_Map.get(taskID).setActionBar(self,target, customLineConfig,taskID);
            }
            return;
        }

        /**Action的相關判斷**/
        if(judgMent.toLowerCase().contains("action")){

            new Action().setAction(self, target, customLineConfig, taskID);
            return;
        }

        /**AttributePoint的相關判斷**/
        if(judgMent.toLowerCase().contains("attributepoint")){
            new AttributePoint2().setAttributePoint(self,target,customLineConfig,taskID);
            return;
        }


        /**BossBar的相關判斷**/
        if(judgMent.toLowerCase().contains("bossbar")){

            if(ActionManager.judgment_SendBossBar_Map.get(taskID) == null){
                ActionManager.judgment_SendBossBar_Map.put(taskID,new SendBossBar());
            }
            if(ActionManager.judgment_SendBossBar_Map.get(taskID) != null){
                ActionManager.judgment_SendBossBar_Map.get(taskID).setBossBar(self,target,customLineConfig,taskID);
            }
            return;
        }

        /**Cancell的相關判斷**/
        if(judgMent.toLowerCase().contains("cancell")){
            new Cancell2().setCancell(self,target,customLineConfig,taskID);
            return;
        }

        /**Command的相關判斷**/
        if(judgMent.toLowerCase().contains("command")){
            new Command2().setCommand(self,target,customLineConfig,taskID);
            return;
        }

        /**CustomPoint的相關判斷**/
        if(judgMent.toLowerCase().contains("custompoint")){
            new Point2().setPoint(self,target,customLineConfig,taskID);
            return;
        }

        /**CoreSkill的相關判斷**/
        if(judgMent.toLowerCase().contains("setskilllevel")){
            new SetSkillLevel().setCoreSkill(self,target,customLineConfig,taskID);
            return;
        }

        /**Damage的相關判斷**/
        if(judgMent.toLowerCase().contains("damage")){
            new Damage2().setDamage(self,target,customLineConfig,taskID);
            return;
        }

        /**DCMessage的相關判斷**/
        if(judgMent.toLowerCase().contains("dcmessage")){
            new DCMessage().setDCMessage(self,target,customLineConfig,taskID);
            return;
        }

        /**Experience的相關判斷**/
        if(judgMent.toLowerCase().contains("exp")){
            new Experience2().setExp(self,target,customLineConfig,taskID);
            return;
        }

        /**FixedPoint的相關判斷**/
        if(judgMent.toLowerCase().contains("fixedpoint")){

            new FixedPoint().set(self,target,customLineConfig,taskID+(Math.random()*100000));
            return;
        }

        /**GiveItem的相關判斷**/
        if(judgMent.toLowerCase().contains("item")){
            new GiveItem2().setItem(self,target,customLineConfig,taskID);
            return;
        }

        /**Glow的相關判斷**/
        if(judgMent.toLowerCase().contains("glow")){
            new setGlow2().setGlow(self,target,customLineConfig,taskID);
            return;
        }

        /**Guise的相關判斷**/
        if(judgMent.toLowerCase().contains("guise")){

            if(ActionManager.judgment_Guise_Map.get(taskID) == null){
                ActionManager.judgment_Guise_Map.put(taskID, new Guise());
                ActionManager.judgment_Guise_Map.get(taskID).setItemEntity(self,target,customLineConfig,taskID);
            }else {
                ActionManager.judgment_Guise_Map.get(taskID).setItemEntity(self,target,customLineConfig,taskID);
            }
            return;
        }

        /**Heal的相關判斷**/
        if(judgMent.toLowerCase().contains("heal")){
            new Heal2().setHeal(self,target,customLineConfig,taskID);
            return;
        }


        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){
            if(ActionManager.judgment_Holographic_Map.get(taskID) == null){
                ActionManager.judgment_Holographic_Map.put(taskID,new Holographic2());
            }
            if(ActionManager.judgment_Holographic_Map.get(taskID) != null){
                ActionManager.judgment_Holographic_Map.get(taskID).setHD(self,target,customLineConfig,taskID);
            }
            return;
        }


        /**Inventory的相關判斷**/
        if(judgMent.toLowerCase().contains("inventory")){

            if(ActionManager.judgment_Inventory_Map.get(taskID) == null){
                ActionManager.judgment_Inventory_Map.put(taskID,new OpenInventory2());
            }
            if(ActionManager.judgment_Inventory_Map.get(taskID) != null){
                ActionManager.judgment_Inventory_Map.get(taskID).setInventory(self,target,customLineConfig,taskID);
            }
            return;
        }

        /**LocPng的相關判斷**/
        if(judgMent.toLowerCase().contains("locpng")){

            new LocPng().set(self,target,customLineConfig,taskID);
            return;
        }

        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){

            if(ActionManager.judgment_Loop_Map.get(taskID) == null){
                ActionManager.judgment_Loop_Map.put(taskID,new Loop2());
                ActionManager.judgment_Loop_Map.get(taskID).onLoop(self,target,customLineConfig,taskID);
            }else {
                if(!ActionManager.judgment_Loop_Map.get(taskID).isCancelled()){
                    ActionManager.judgment_Loop_Map.get(taskID).cancel();
                }
                ActionManager.judgment_Loop_Map.remove(taskID);
                ActionManager.judgment_Loop_Map.put(taskID,new Loop2());
                ActionManager.judgment_Loop_Map.get(taskID).onLoop(self,target,customLineConfig,taskID);
            }
            return;
        }

        /**LoggerInfo的相關判斷**/
        if(judgMent.toLowerCase().contains("loggerinfo")){
            new LoggerInfo2().setLoggerInfo(self,target,customLineConfig,taskID);
            return;
        }

        /**Level的相關判斷**/
        if(judgMent.toLowerCase().contains("level")){
            new Level2().setLevel(self,target,customLineConfig,taskID);
            return;
        }


        /**Message的相關判斷**/
        if(judgMent.toLowerCase().contains("message")){
            new Message2().setMessage(self,target,customLineConfig,taskID);
            return;
        }



        /**MythicSkill的相關判斷**/
        if(judgMent.toLowerCase().contains("mythicskill")){
            new MythicAction2().setMythicAction(self,target,customLineConfig,taskID);
            return;
        }

        /**Mana的相關判斷**/
        if(judgMent.toLowerCase().contains("mana")){
            new setMana().setMana(self,target,customLineConfig,taskID);
            return;
        }

        /**Name的相關判斷**/
        if(judgMent.toLowerCase().contains("name")){
            new SetName2().setName(self,target,customLineConfig,taskID);
            return;
        }

        /**OrbitalAttack的相關判斷**/
        if(judgMent.toLowerCase().contains("orbital")){

            new OrbitalAction2().setParabolicAttack(self,target,customLineConfig,taskID); //+(Math.random()*100000)
            return;
        }

        /**Particle的相關判斷**/
        if(judgMent.toLowerCase().contains("particle")){
            new SendParticles2().setParticles(self,target,customLineConfig,taskID);
            return;
        }

        /**SetAttribute的相關判斷**/
        if(judgMent.toLowerCase().contains("setattribute")){
            new SetAttribute2().set(self,target,customLineConfig,taskID);
            return;
        }

        /**Sound的相關判斷**/
        if(judgMent.toLowerCase().contains("sound")){
            new Sound2().setSound(self,target,customLineConfig,taskID);
            return;
        }

        /**Title的相關判斷**/
        if(judgMent.toLowerCase().contains("title")){
            new Title2().setTitle(self,target,customLineConfig,taskID);
            return;
        }

        /**Teleport的相關判斷**/
        if(judgMent.toLowerCase().contains("teleport")){
            new Teleport2().setTp(self,target,customLineConfig,taskID);
            return;
        }

        /**Velocity的相關判斷**/
        if(judgMent.toLowerCase().contains("move")){
            new Move2().setVelocity(self,target,customLineConfig,taskID);
            return;
        }


    }


}
