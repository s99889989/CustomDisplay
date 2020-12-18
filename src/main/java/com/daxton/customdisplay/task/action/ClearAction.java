package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.task.action.list.Holographic2;
import com.daxton.customdisplay.task.action.list.Loop2;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

public class ClearAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ClearAction(){

        /**judgment2->Loop2**/
        for(Loop2 loop2 : ActionManager2.getJudgment2_Loop2_Map().values()){
            loop2.cancel();
        }
        ActionManager2.getJudgment2_Loop2_Map().clear();

        /**PlayerTrigger->judgment2**/
        ActionManager2.getPlayerTrigger_Judgment2_Map().clear();

        /**judgment2->Action2**/
        ActionManager2.getJudgment2_Action2_Map().clear();

        /**judgment2->Holographic2**/
        for (Holographic2 holographic2 : ActionManager2.getJudgment2_Holographic2_Map().values()){
            holographic2.getHologram().delete();
        }
        ActionManager2.getJudgment2_Holographic2_Map().clear();


        /**judgment2->ActionBar2**/
        ActionManager2.getJudgment2_ActionBar2_Map().clear();

        /**judgment2->SendBossBar2**/
        ActionManager2.getJudgment2_SendBossBar2_Map().clear();

        /**judgment2->SetName2**/

        ActionManager2.getJudgment2_SendParticles_Map().clear();


        /**action2->judgment2**/
        ActionManager2.getAction2_Judgment2_Map().clear();

        /**loop2->judgment2**/
        ActionManager2.getLoop2_Judgment2_Map().clear();


        /**SendParticles->ProtocolManager**/
        ActionManager2.getSendParticles_ProtocolManager_Map().clear();

    }

    public ClearAction(String taskID){

        /**judgment2->Loop2**/
        if(ActionManager2.getJudgment2_Loop2_Map().get(taskID) != null){
            ActionManager2.getJudgment2_Loop2_Map().get(taskID).cancel();
            ActionManager2.getJudgment2_Loop2_Map().remove(taskID);
        }

        /**PlayerTrigger->judgment2**/
        if(ActionManager2.getPlayerTrigger_Judgment2_Map().get(taskID) != null){
            ActionManager2.getPlayerTrigger_Judgment2_Map().remove(taskID);
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
            ActionManager2.getJudgment2_SendBossBar2_Map().remove(taskID);
        }
        /**judgment2->SetName2**/
        if(ActionManager2.getJudgment2_SendParticles_Map().get(taskID) != null){
            ActionManager2.getJudgment2_SendParticles_Map().remove(taskID);
        }

        /**action2->judgment2**/
        if(ActionManager2.getAction2_Judgment2_Map().get(taskID) != null){
            ActionManager2.getAction2_Judgment2_Map().remove(taskID);
        }
        /**loop2->judgment2**/
        if(ActionManager2.getLoop2_Judgment2_Map().get(taskID) != null){
            ActionManager2.getLoop2_Judgment2_Map().remove(taskID);
        }

        /**SendParticles->ProtocolManager**/
        if(ActionManager2.getSendParticles_ProtocolManager_Map().get(taskID) != null){
            ActionManager2.getSendParticles_ProtocolManager_Map().remove(taskID);
        }

    }

}
