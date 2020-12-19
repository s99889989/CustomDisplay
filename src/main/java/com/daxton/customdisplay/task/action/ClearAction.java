package com.daxton.customdisplay.task.action;

import com.comphenix.protocol.ProtocolManager;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.task.action.list.Holographic2;
import com.daxton.customdisplay.task.action.list.Loop2;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class ClearAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ClearAction(){

        /**judgment2->Loop2**/
        for(Loop2 loop2 : ActionManager2.getJudgment2_Loop2_Map().values()){
            loop2.cancel();
        }
        if(!(ActionManager2.getJudgment2_Loop2_Map().isEmpty())){
            ActionManager2.getJudgment2_Loop2_Map().clear();
        }
        /**Other->judgment2**/
        if(!(ActionManager2.getOther_Judgment2_Map().isEmpty())){
            ActionManager2.getOther_Judgment2_Map().clear();
        }


        /**judgment2->Action2**/
        if(!(ActionManager2.getJudgment2_Action2_Map().isEmpty())){
            ActionManager2.getJudgment2_Action2_Map().clear();
        }

        /**judgment2->Holographic2**/
        for (Holographic2 holographic2 : ActionManager2.getJudgment2_Holographic2_Map().values()){
            holographic2.getHologram().delete();
        }
        if(!(ActionManager2.getJudgment2_Holographic2_Map().isEmpty())){
            ActionManager2.getJudgment2_Holographic2_Map().clear();
        }


        /**judgment2->ActionBar2**/
        if(!(ActionManager2.getJudgment2_ActionBar2_Map().isEmpty())){
            ActionManager2.getJudgment2_ActionBar2_Map().clear();
        }


        /**judgment2->SendBossBar2**/
        if(!(ActionManager2.getJudgment2_SendBossBar2_Map().isEmpty())){
            ActionManager2.getJudgment2_SendBossBar2_Map().clear();
        }


        /**judgment2->SetName2**/
        if(!(ActionManager2.getJudgment2_SendParticles_Map().isEmpty())){
            ActionManager2.getJudgment2_SendParticles_Map().clear();
        }




    }

    public ClearAction(LivingEntity self,LivingEntity target){
        if(self != null){
            UUID selfUUID = self.getUniqueId();
            String uuidString = selfUUID.toString();
            if(PlaceholderManager.getActionBar_function().get(uuidString+"function") != null){
                PlaceholderManager.getActionBar_function().remove(uuidString);
            }
        }
        if(target != null){
            UUID targetUUID = target.getUniqueId();
            String uuidString = targetUUID .toString();
            if(PlaceholderManager.getActionBar_function().get(uuidString+"function") != null){
                PlaceholderManager.getActionBar_function().remove(uuidString);
            }
        }





    }


    public ClearAction(String taskID){

        /**Other->judgment2**/
        if(ActionManager2.getOther_Judgment2_Map().get(taskID) != null){
            ActionManager2.getOther_Judgment2_Map().remove(taskID);
        }

        /**judgment2->Loop2**/
        if(ActionManager2.getJudgment2_Loop2_Map().get(taskID) != null){
            ActionManager2.getJudgment2_Loop2_Map().get(taskID).cancel();
            ActionManager2.getJudgment2_Loop2_Map().remove(taskID);
        }

        /**judgment2->Action2**/
        if(ActionManager2.getJudgment2_Action2_Map().get(taskID) != null){
            ActionManager2.getJudgment2_Action2_Map().remove(taskID);
        }

        /**judgment2->Holographic2**/
        if(ActionManager2.getJudgment2_Holographic2_Map().get(taskID) != null){
            ActionManager2.getJudgment2_Holographic2_Map().get(taskID).getHologram().delete();
            ActionManager2.getJudgment2_Holographic2_Map().remove(taskID);
        }

        /**judgment2->ActionBar2**/
        if(ActionManager2.getJudgment2_ActionBar2_Map().get(taskID) != null){
            ActionManager2.getJudgment2_ActionBar2_Map().remove(taskID);
        }
        /**judgment2->SendBossBar2**/
        if(ActionManager2.getJudgment2_SendBossBar2_Map().get(taskID) != null){
            ActionManager2.getJudgment2_SendBossBar2_Map().get(taskID).getBossBar().removeAll();
            ActionManager2.getJudgment2_SendBossBar2_Map().remove(taskID);
        }
        /**judgment2->SetName2**/
        if(ActionManager2.getJudgment2_SendParticles_Map().get(taskID) != null){
            ActionManager2.getJudgment2_SendParticles_Map().remove(taskID);
        }


    }

}
