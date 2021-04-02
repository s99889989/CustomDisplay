package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.task.action.list.*;
import com.daxton.customdisplay.task.action.list.setplayer.*;
import com.daxton.customdisplay.task.action.list.setplayer.CoreSkill;
import com.daxton.customdisplay.task.action2.*;
import com.daxton.customdisplay.task.action2.location.*;
import com.daxton.customdisplay.task.action2.noplayer.SetName2;
import com.daxton.customdisplay.task.action2.player.*;
import com.daxton.customdisplay.task.action2.server.LoggerInfo2;
import com.daxton.customdisplay.task.locationAction.OrbitalAction;
import org.bukkit.entity.LivingEntity;

public class JudgmentAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public JudgmentAction2(){

    }

    public void execute(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        String judgMent = customLineConfig.getActionKey();
        //cd.getLogger().info("觸發: "+judgMent + " : "+taskID);
        /**Action的相關判斷**/
        if(judgMent.toLowerCase().contains("action")){

            new Action2().setAction(self, target, customLineConfig, taskID);

        }
        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){

            if(ActionManager2.judgment_Loop_Map.get(taskID) == null){
                ActionManager2.judgment_Loop_Map.put(taskID,new Loop2());
                ActionManager2.judgment_Loop_Map.get(taskID).onLoop(self,target,customLineConfig,taskID);
            }

        }

        /**ActionBar的相關判斷**/
        if(judgMent.toLowerCase().contains("actionbar")){
            if(ActionManager2.judgment_ActionBar_Map.get(taskID) == null){
                ActionManager2.judgment_ActionBar_Map.put(taskID,new ActionBar2());
            }
            if(ActionManager2.judgment_ActionBar_Map.get(taskID) != null){
                ActionManager2.judgment_ActionBar_Map.get(taskID).setActionBar(self,target, customLineConfig,taskID);
            }
        }

        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){
            if(ActionManager2.judgment_Holographic_Map.get(taskID) == null){
                ActionManager2.judgment_Holographic_Map.put(taskID,new Holographic2());
            }
            if(ActionManager2.judgment_Holographic_Map.get(taskID) != null){
                ActionManager2.judgment_Holographic_Map.get(taskID).setHD(self,target,customLineConfig,taskID);
            }
        }


        /**LoggerInfo的相關判斷**/
        if(judgMent.toLowerCase().contains("loggerinfo")){
            new LoggerInfo2().setLoggerInfo(self,target,customLineConfig,taskID);
        }

        /**Message的相關判斷**/
        if(judgMent.toLowerCase().contains("message")){
            new Message2().setMessage(self,target,customLineConfig,taskID);
        }

        /**MythicSkill的相關判斷**/
        if(judgMent.toLowerCase().contains("mythicskill")){
            new MythicAction2().setMythicAction(self,target,customLineConfig,taskID);
        }

        /**BossBar的相關判斷**/
        if(judgMent.toLowerCase().contains("bossbar")){

            if(ActionManager2.judgment_SendBossBar_Map.get(taskID) == null){
                ActionManager2.judgment_SendBossBar_Map.put(taskID,new SendBossBar2());
            }
            if(ActionManager2.judgment_SendBossBar_Map.get(taskID) != null){
                ActionManager2.judgment_SendBossBar_Map.get(taskID).setBossBar(self,target,customLineConfig,taskID);
            }
        }

        /**Particle的相關判斷**/
        if(judgMent.toLowerCase().contains("particle")){
            new SendParticles2().setParticles(self,target,customLineConfig,taskID);
//            if(ActionManager.getJudgment2_SendParticles_Map().get(taskID) == null){
//                ActionManager.getJudgment2_SendParticles_Map().put(taskID,new SendParticles());
//            }
//            if(ActionManager.getJudgment2_SendParticles_Map().get(taskID) != null){
//                ActionManager.getJudgment2_SendParticles_Map().get(taskID).setParticles(self,target,customLineConfig,taskID);
//            }
        }

        /**Name的相關判斷**/
        if(judgMent.toLowerCase().contains("name")){
            new SetName2().setName(self,target,customLineConfig,taskID);
//            if(ActionManager.getJudgment2_SetName2_Map().get(taskID) == null){
//                ActionManager.getJudgment2_SetName2_Map().put(taskID,new SetName());
//            }
//            if(ActionManager.getJudgment2_SetName2_Map().get(taskID) != null){
//                ActionManager.getJudgment2_SetName2_Map().get(taskID).setName(self,target,customLineConfig,taskID);
//            }
        }
        /**Sound的相關判斷**/
        if(judgMent.toLowerCase().contains("sound")){
            new Sound2().setSound(self,target,customLineConfig,taskID);
        }
        /**Title的相關判斷**/
        if(judgMent.toLowerCase().contains("title")){
            new Title2().setTitle(self,target,customLineConfig,taskID);
        }

        /**Teleport的相關判斷**/
        if(judgMent.toLowerCase().contains("teleport")){
            new Teleport2().setTp(self,target,customLineConfig,taskID);
        }


        /**Inventory的相關判斷**/
        if(judgMent.toLowerCase().contains("inventory")){

            if(ActionManager2.judgment_Inventory_Map.get(taskID) == null){
                ActionManager2.judgment_Inventory_Map.put(taskID,new OpenInventory2());
            }
            if(ActionManager2.judgment_Inventory_Map.get(taskID) != null){
                ActionManager2.judgment_Inventory_Map.get(taskID).setInventory(self,target,customLineConfig,taskID);
            }
        }



        /**Heal的相關判斷**/
        if(judgMent.toLowerCase().contains("heal")){
            new Heal2().setHeal(self,target,customLineConfig,taskID);
        }

        /**GiveItem的相關判斷**/
        if(judgMent.toLowerCase().contains("item")){
            new GiveItem2().setItem(self,target,customLineConfig,taskID);
        }



        /**Cancell的相關判斷**/
        if(judgMent.toLowerCase().contains("cancell")){
            new Cancell2().setCancell(self,target,customLineConfig,taskID);
        }

        /**Damage的相關判斷**/
        if(judgMent.toLowerCase().contains("damage")){
            new Damage2().setDamage(self,target,customLineConfig,taskID);
        }

        /**Command的相關判斷**/
        if(judgMent.toLowerCase().contains("command")){
            new Command2().setCommand(self,target,customLineConfig,taskID);
        }

        if(judgMent.toLowerCase().contains("setattribute")){
            new SetAttribute2().set(self,target,customLineConfig,taskID);
        }

        /**Glow的相關判斷**/
        if(judgMent.toLowerCase().contains("glow")){
            new setGlow2().setGlow(self,target,customLineConfig,taskID);
        }

        /**ItemEntity的相關判斷**/
        if(judgMent.toLowerCase().contains("itementity")){
            new ItemEntity2().setItemEntity(self,target,customLineConfig,taskID);
        }

        /**Velocity的相關判斷**/
        if(judgMent.toLowerCase().contains("move")){
            new Move2().setVelocity(self,target,customLineConfig,taskID);
        }


        /**Experience的相關判斷**/
        if(judgMent.toLowerCase().contains("exp")){
            new Experience2().setExp(self,target,customLineConfig,taskID);
        }

        /**Level的相關判斷**/
        if(judgMent.toLowerCase().contains("level")){
            new Level2().setLevel(self,target,customLineConfig,taskID);
        }

        /**CustomPoint的相關判斷**/
        if(judgMent.toLowerCase().contains("custompoint")){
            new Point2().setPoint(self,target,customLineConfig,taskID);
        }

        /**CoreSkill的相關判斷**/
        if(judgMent.toLowerCase().contains("coreskill")){
            new CoreSkill2().setCoreSkill(self,target,customLineConfig,taskID);
        }
//        /**ParabolicAttack的相關判斷**/
//        if(judgMent.toLowerCase().contains("parabolicattack")){
//
//            new OrbitalAction2().setParabolicAttack(self,target,customLineConfig,taskID+(Math.random()*100000));
//        }



        /**AttributePoint的相關判斷**/
        if(judgMent.toLowerCase().contains("attributepoint")){
            new AttributePoint2().setAttributePoint(self,target,customLineConfig,taskID);
        }

    }


}
