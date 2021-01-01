package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.list.*;
import org.bukkit.entity.LivingEntity;

public class JudgmentAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";


    private int delay = 0;

    public JudgmentAction(){

    }

    public void execute(LivingEntity self, LivingEntity target, String firstString,String taskID){

        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        bukkitRun();
    }

    public void bukkitRun(){
        String judgMent = new StringFind().getAction(firstString);
        /**Action的相關判斷**/
        if(judgMent.toLowerCase().contains("action")){

            new Action().setAction(self,target,firstString,taskID);
        }
        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){
            if(ActionManager.getJudgment2_Loop2_Map().get(taskID) == null){
                ActionManager.getJudgment2_Loop2_Map().put(taskID,new Loop());
                ActionManager.getJudgment2_Loop2_Map().get(taskID).onLoop(self,target,firstString,taskID);
            }else{
                ActionManager.getJudgment2_Loop2_Map().get(taskID).cancel();
                ActionManager.getJudgment2_Loop2_Map().remove(taskID);
                ActionManager.getJudgment2_Loop2_Map().put(taskID,new Loop());
                ActionManager.getJudgment2_Loop2_Map().get(taskID).onLoop(self,target,firstString,taskID);
            }
        }

        /**ActionBar的相關判斷**/
        if(judgMent.toLowerCase().contains("actionbar")){
            if(ActionManager.getJudgment2_ActionBar2_Map().get(taskID) == null){
                ActionManager.getJudgment2_ActionBar2_Map().put(taskID,new ActionBar());
            }
            if(ActionManager.getJudgment2_ActionBar2_Map().get(taskID) != null){
                ActionManager.getJudgment2_ActionBar2_Map().get(taskID).setActionBar(self,target,firstString,taskID);
            }
        }

        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){
            if(ActionManager.getJudgment2_Holographic2_Map().get(taskID) == null){
                ActionManager.getJudgment2_Holographic2_Map().put(taskID,new Holographic());
            }
            if(ActionManager.getJudgment2_Holographic2_Map().get(taskID) != null){
                ActionManager.getJudgment2_Holographic2_Map().get(taskID).setHD(self,target,firstString,taskID);
            }
        }


        /**LoggerInfo的相關判斷**/
        if(judgMent.toLowerCase().contains("loggerinfo")){
            new LoggerInfo().setLoggerInfo(self,target,firstString,taskID);
        }

        /**Message的相關判斷**/
        if(judgMent.toLowerCase().contains("message")){
            new Message().setMessage(self,target,firstString,taskID);
        }

        /**MythicSkill的相關判斷**/
        if(judgMent.toLowerCase().contains("mythicskill")){
            new MythicAction().setMythicAction(self,target,firstString,taskID);
        }

        /**BossBar的相關判斷**/
        if(judgMent.toLowerCase().contains("bossbar")){

            if(ActionManager.getJudgment2_SendBossBar2_Map().get(taskID) == null){
                ActionManager.getJudgment2_SendBossBar2_Map().put(taskID,new SendBossBar());
            }
            if(ActionManager.getJudgment2_SendBossBar2_Map().get(taskID) != null){
                ActionManager.getJudgment2_SendBossBar2_Map().get(taskID).setBossBar(self,target,firstString,taskID);
            }
        }

        /**Particle的相關判斷**/
        if(judgMent.toLowerCase().contains("particle")){
            if(ActionManager.getJudgment2_SendParticles_Map().get(taskID) == null){
                ActionManager.getJudgment2_SendParticles_Map().put(taskID,new SendParticles());
            }
            if(ActionManager.getJudgment2_SendParticles_Map().get(taskID) != null){
                ActionManager.getJudgment2_SendParticles_Map().get(taskID).setParticles(self,target,firstString,taskID);
            }
        }

        /**Name的相關判斷**/
        if(judgMent.toLowerCase().contains("name")){

            if(ActionManager.getJudgment2_SetName2_Map().get(taskID) == null){
                ActionManager.getJudgment2_SetName2_Map().put(taskID,new SetName());
            }
            if(ActionManager.getJudgment2_SetName2_Map().get(taskID) != null){
                ActionManager.getJudgment2_SetName2_Map().get(taskID).setName(self,target,firstString,taskID);
            }
        }
        /**Sound的相關判斷**/
        if(judgMent.toLowerCase().contains("sound")){
            new Sound().setSound(self,target,firstString,taskID);
        }
        /**Title的相關判斷**/
        if(judgMent.toLowerCase().contains("title")){
            new Title().setTitle(self,target,firstString,taskID);
        }

        /**Experience的相關判斷**/
        if(judgMent.toLowerCase().contains("exp")){
            new Experience().setExp(self,target,firstString,taskID);
        }

        /**Level的相關判斷**/
        if(judgMent.toLowerCase().contains("level")){
            new Level().setLevel(self,target,firstString,taskID);
        }

        /**CustomPoint的相關判斷**/
        if(judgMent.toLowerCase().contains("custompoint")){
            new Point().setPoint(self,target,firstString,taskID);
        }

        /**Inventory的相關判斷**/
        if(judgMent.toLowerCase().contains("inventory")){

            if(ActionManager.getJudgment2_OpenInventory_Map().get(taskID) == null){
                ActionManager.getJudgment2_OpenInventory_Map().put(taskID,new OpenInventory());
            }
            if(ActionManager.getJudgment2_OpenInventory_Map().get(taskID) != null){
                ActionManager.getJudgment2_OpenInventory_Map().get(taskID).setInventory(self,target,firstString,taskID);
            }
        }

        /**AttributePoint的相關判斷**/
        if(judgMent.toLowerCase().contains("attributepoint")){
            new AttributePoint().setAttributePoint(self,target,firstString,taskID);
        }

        /**Heal的相關判斷**/
        if(judgMent.toLowerCase().contains("heal")){
            new Heal().setHeal(self,target,firstString,taskID);
        }

        /**GiveItem的相關判斷**/
        if(judgMent.toLowerCase().contains("item")){
            new GiveItem().setItem(self,target,firstString,taskID);
        }


    }


}
