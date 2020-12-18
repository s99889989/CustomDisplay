package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.task.action.list.*;
import org.bukkit.entity.LivingEntity;

public class JudgmentAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    public JudgmentAction2(){

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
            new Action2().setAction(self,target,firstString,taskID);
//            if(ActionManager2.getJudgment2_Action2_Map().get(taskID) == null){
//                ActionManager2.getJudgment2_Action2_Map().put(taskID,new Action2());
//                ActionManager2.getJudgment2_Action2_Map().get(taskID).setAction(self,target,firstString,taskID);
//            }else {
//                ActionManager2.getJudgment2_Action2_Map().remove(taskID);
//                ActionManager2.getJudgment2_Action2_Map().put(taskID,new Action2());
//                ActionManager2.getJudgment2_Action2_Map().get(taskID).setAction(self,target,firstString,taskID);
//            }
        }

        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){
            if(ActionManager2.getJudgment2_Loop2_Map().get(taskID) == null){
                ActionManager2.getJudgment2_Loop2_Map().put(taskID,new Loop2());
                ActionManager2.getJudgment2_Loop2_Map().get(taskID).onLoop(self,target,firstString,taskID);
            }else{
                ActionManager2.getJudgment2_Loop2_Map().get(taskID).cancel();
                ActionManager2.getJudgment2_Loop2_Map().remove(taskID);
                ActionManager2.getJudgment2_Loop2_Map().put(taskID,new Loop2());
                ActionManager2.getJudgment2_Loop2_Map().get(taskID).onLoop(self,target,firstString,taskID);
            }
        }

        /**ActionBar的相關判斷**/
        if(judgMent.toLowerCase().contains("actionbar")){
            if(ActionManager2.getJudgment2_ActionBar2_Map().get(taskID) == null){
                ActionManager2.getJudgment2_ActionBar2_Map().put(taskID,new ActionBar2());
            }
            if(ActionManager2.getJudgment2_ActionBar2_Map().get(taskID) != null){
                ActionManager2.getJudgment2_ActionBar2_Map().get(taskID).setActionBar(self,target,firstString,taskID);
            }
        }

        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){
            if(ActionManager2.getJudgment2_Holographic2_Map().get(taskID) == null){
                ActionManager2.getJudgment2_Holographic2_Map().put(taskID,new Holographic2());
            }
            if(ActionManager2.getJudgment2_Holographic2_Map().get(taskID) != null){
                ActionManager2.getJudgment2_Holographic2_Map().get(taskID).setHD(self,target,firstString,taskID);
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
            if(ActionManager2.getJudgment2_SendBossBar2_Map().get(taskID) == null){
                ActionManager2.getJudgment2_SendBossBar2_Map().put(taskID,new SendBossBar2());
            }
            if(ActionManager2.getJudgment2_SendBossBar2_Map().get(taskID) != null){
                ActionManager2.getJudgment2_SendBossBar2_Map().get(taskID).setBossBar(self,target,firstString,taskID);
            }
        }

        /**Particle的相關判斷**/
        if(judgMent.toLowerCase().contains("particle")){
            if(ActionManager2.getJudgment2_SendParticles_Map().get(taskID) == null){
                ActionManager2.getJudgment2_SendParticles_Map().put(taskID,new SendParticles());
            }
            if(ActionManager2.getJudgment2_SendParticles_Map().get(taskID) != null){
                ActionManager2.getJudgment2_SendParticles_Map().get(taskID).setParticles(self,target,firstString,taskID);
            }
        }

        /**Name的相關判斷**/
        if(judgMent.toLowerCase().contains("name")){
            if(ActionManager2.getJudgment2_SetName2_Map().get(taskID) == null){
                ActionManager2.getJudgment2_SetName2_Map().put(taskID,new SetName2());
            }
            if(ActionManager2.getJudgment2_SetName2_Map().get(taskID) != null){
                ActionManager2.getJudgment2_SetName2_Map().get(taskID).setName(self,target,firstString,taskID);
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

    }


}
