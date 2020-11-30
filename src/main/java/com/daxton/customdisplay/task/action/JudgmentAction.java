package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.task.action.list.*;
import com.daxton.customdisplay.manager.player.TriggerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class JudgmentAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BukkitRunnable bukkitRunnable;

    int delay = 0;

    public JudgmentAction(){

    }


    public void execute(Player player, LivingEntity target, String firstString, double damageNumber,String taskID){
        /**動作第一個關鍵字**/
        String judgMent = new StringFind().getAction(firstString);

        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("createhd") || judgMent.toLowerCase().contains("addlinehd") || judgMent.toLowerCase().contains("removelinehd") || judgMent.toLowerCase().contains("teleporthd") || judgMent.toLowerCase().contains("deletehd")){
            if(TriggerManager.getHolographicTaskMap().get(taskID) == null){
                TriggerManager.getHolographicTaskMap().put(taskID,new HolographicNew());
            }
            if(TriggerManager.getHolographicTaskMap().get(taskID) != null){
                TriggerManager.getHolographicTaskMap().get(taskID).setHD(player,target,firstString,damageNumber,taskID);
            }
        }
        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){
            new Loop().onLoop(player,target,firstString,damageNumber,taskID);
//            if(TriggerManager.getJudgmentActionTaskMap().get(taskID) == null){
//                TriggerManager.getJudgmentActionTaskMap().put(taskID,new Loop());
//                TriggerManager.getJudgmentActionTaskMap().get(taskID).onLoop(player,target,firstString,damageNumber,taskID);
//            }
//            if(TriggerManager.getJudgmentActionTaskMap().get(taskID) != null){
//                TriggerManager.getJudgmentActionTaskMap().get(taskID).onLoop(player,target,firstString,damageNumber,taskID);
//            }

        }

        /**Title的相關判斷**/
        if(judgMent.toLowerCase().contains("title")){
            new Title(player,firstString).sendTitle();
        }
        /**Sound的相關判斷**/
        if (judgMent.toLowerCase().contains("sound")) {
            new Sound(player, firstString).playSound();
        }

        if(judgMent.toLowerCase().contains("boosbar")){
            if(TriggerManager.getJudgment_BossBar_Map().get(taskID) == null){
                TriggerManager.getJudgment_BossBar_Map().put(taskID,new BossBar());
            }
            if(TriggerManager.getJudgment_BossBar_Map().get(taskID) != null){
                TriggerManager.getJudgment_BossBar_Map().get(taskID).set(player,target,firstString,taskID);
            }
        }

    }

    public void executeOne(Player player, String firstString,String taskID){

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
                if(judgMent.toLowerCase().contains("action")){
                    List<String> actionList = new ConfigFind().getActionList(firstString);
                    for(String string : actionList){
                        new JudgmentAction().executeOne(player,string,taskID);
                    }
                }
                /**LoopOne的相關判斷**/
                if(judgMent.toLowerCase().contains("loop")){
                    if(firstString.toLowerCase().contains("unlimited")){
                        if(TriggerManager.getJudgmentActionTaskLoopOneMap().get(taskID) == null){
                            TriggerManager.getJudgmentActionTaskLoopOneMap().put(taskID,new LoopOne());
                            TriggerManager.getJudgmentActionTaskLoopOneMap().get(taskID).onLoop(player,firstString,taskID);
                        }
                    }else {
                        new LoopOne().onLoop(player,firstString,taskID);
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

            }
        };
        bukkitRunnable.runTaskLater(cd, delay);

    }


}
