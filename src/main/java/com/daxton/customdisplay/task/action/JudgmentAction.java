package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.task.action.list.*;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class JudgmentAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BukkitRunnable bukkitRunnable;

    private String taskID = "";
    private String firstString = "";

    int delay = 0;
    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private double damageNumber = 0;

    public JudgmentAction(){

    }

    public void execute(Player player, LivingEntity target, String firstString, double damageNumber,String taskID){
        this.self = player;
        this.player = player;
        this.target = target;
        this.damageNumber = damageNumber;
        bukkitRun(player,firstString,taskID);
    }

    /**只有玩家**/
    public void execute(Player player, String firstString,String taskID){
        this.self = player;
        bukkitRun(player,firstString,taskID);
    }

    public void executeOneTwo(Player player,LivingEntity target, String firstString,String taskID){
        this.self = player;
        this.target = target;
        bukkitRun(player,firstString,taskID);
    }


    public void bukkitRun(Player player,String firstString,String taskID){
        if (firstString.toLowerCase().contains("delay ")) {
            String[] slt = firstString.split(" ");
            if (slt.length == 2) {
                delay = delay + Integer.valueOf(slt[1]);
            }
        }
        bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                /**動作第一個關鍵字**/
                String judgMent = new StringFind().getAction(firstString);

                /**Action的相關判斷**/
                if (judgMent.toLowerCase().contains("action")) {
                    if(ActionManager.getJudgment_Action_Map().get(taskID) == null){
                        ActionManager.getJudgment_Action_Map().put(taskID,new Action());
                    }
                    if(ActionManager.getJudgment_Action_Map().get(taskID) != null){
                        if(target == null){
                            ActionManager.getJudgment_Action_Map().get(taskID).setAction(player,firstString,taskID);
                        }else if(damageNumber == 0){
                            ActionManager.getJudgment_Action_Map().get(taskID).setAction(player,target,firstString,taskID);
                        }else {
                            ActionManager.getJudgment_Action_Map().get(taskID).setAction(player,target,firstString,damageNumber,taskID);
                        }
                    }

                }

                /**HolographicDisplays的相關判斷**/
                if(judgMent.toLowerCase().contains("hologram")){

                    if(ActionManager.getJudgment_Holographic_Map().get(taskID) == null){
                        ActionManager.getJudgment_Holographic_Map().put(taskID,new Holographic());
                    }
                    if(ActionManager.getJudgment_Holographic_Map().get(taskID) != null){
                        ActionManager.getJudgment_Holographic_Map().get(taskID).setHD(player,target,firstString,damageNumber,taskID);
                    }
                }
                /**Loop的相關判斷**/
                if(judgMent.toLowerCase().contains("loop")){
                        if(ActionManager.getJudgment_Loop_Map().get(taskID) == null){
                            ActionManager.getJudgment_Loop_Map().put(taskID,new Loop());
                        }
                        if(ActionManager.getJudgment_Loop_Map().get(taskID) != null){
                            if(target == null){
                                ActionManager.getJudgment_Loop_Map().get(taskID).onLoop(player,firstString,taskID);
                            }else {
                                ActionManager.getJudgment_Loop_Map().get(taskID).onLoop(player,target,firstString,damageNumber,taskID);
                            }
                        }
                }

                /**Name的相關判斷**/
                if(judgMent.toLowerCase().contains("name")){
                    if(ActionManager.getJudgment_SetName_Map().get(taskID) == null){
                        ActionManager.getJudgment_SetName_Map().put(taskID,new SetName());
                    }
                    if(ActionManager.getJudgment_SetName_Map().get(taskID) != null){
                        if(target == null){
                            ActionManager.getJudgment_SetName_Map().get(taskID).setName(player,firstString,taskID);
                        }else {
                            ActionManager.getJudgment_SetName_Map().get(taskID).setName(player,target,firstString,taskID);
                        }
                    }
                }

                /**Title的相關判斷**/
                if(judgMent.toLowerCase().contains("title")){
                    new Title(player,firstString).sendTitle();
                }
                /**Sound的相關判斷**/
                if (judgMent.toLowerCase().contains("sound")) {
                    new Sound(player, firstString).playSound();
                }
                /**ActionBar的相關判斷**/
                if(judgMent.toLowerCase().contains("actionbar")){
                    new ActionBar(player,firstString).sendActionBar();
                }

                /**BossBar的相關判斷**/
                if(judgMent.toLowerCase().contains("boosbar")){
                    if(ActionManager.getJudgment_BossBar_Map().get(taskID) == null){
                        ActionManager.getJudgment_BossBar_Map().put(taskID,new SendBossBar());
                    }
                    if(ActionManager.getJudgment_BossBar_Map().get(taskID) != null){
                        if(target == null){
                            ActionManager.getJudgment_BossBar_Map().get(taskID).set(player,firstString,taskID);
                        }else {
                            ActionManager.getJudgment_BossBar_Map().get(taskID).set(player,target,firstString,taskID);
                        }
                    }
                }

                /**Particle的相關判斷**/
                if(judgMent.toLowerCase().contains("particle")){

                    if(ActionManager.getJudgment_SendParticles_Map().get(taskID) == null){
                        ActionManager.getJudgment_SendParticles_Map().put(taskID,new SendParticles());
                    }
                    if(ActionManager.getJudgment_SendParticles_Map().get(taskID) != null){
                        ActionManager.getJudgment_SendParticles_Map().get(taskID).setParticles(self,target,firstString,taskID);
                    }
                }

                /**Message的相關判斷**/
                if(judgMent.toLowerCase().contains("message")){
                    if(ActionManager.getJudgment_Message_Map().get(taskID) == null){
                        ActionManager.getJudgment_Message_Map().put(taskID,new Message());
                    }
                    if(ActionManager.getJudgment_Message_Map().get(taskID) != null){
                        ActionManager.getJudgment_Message_Map().get(taskID).setMessage(player,firstString);
                        ActionManager.getJudgment_Message_Map().get(taskID).sendMessage();
                    }


                }

                /**Mythic的相關判斷**/
                if(judgMent.toLowerCase().contains("mythic")){

                    new MythicAction(player,target,firstString).useMythicAction();

                }


            }
        };
        bukkitRunnable.runTaskLater(cd, delay);
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }
}
