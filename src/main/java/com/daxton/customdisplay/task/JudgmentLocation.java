package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.list.Holographic;
import com.daxton.customdisplay.task.action2.Action2;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class JudgmentLocation {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public JudgmentLocation(){

    }

    public void execute(LivingEntity self, Location location, CustomLineConfig customLineConfig, String taskID){
        String judgMent = customLineConfig.getActionKey();




        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("hologram")){
            if(ActionManager.getJudgment2_Holographic2_Map().get(taskID) == null){
                ActionManager.getJudgment2_Holographic2_Map().put(taskID,new Holographic());
            }
            if(ActionManager.getJudgment2_Holographic2_Map().get(taskID) != null){
                //ActionManager.getJudgment2_Holographic2_Map().get(taskID).setHD(self,target,firstString,taskID);
            }
        }


//
//        /**Particle的相關判斷**/
//        if(judgMent.toLowerCase().contains("particle")){
//            if(ActionManager.getJudgment2_SendParticles_Map().get(taskID) == null){
//                ActionManager.getJudgment2_SendParticles_Map().put(taskID,new SendParticles());
//            }
//            if(ActionManager.getJudgment2_SendParticles_Map().get(taskID) != null){
//                ActionManager.getJudgment2_SendParticles_Map().get(taskID).setParticles(self,target,firstString,taskID);
//            }
//        }
//

//        /**Sound的相關判斷**/
//        if(judgMent.toLowerCase().contains("sound")){
//            new Sound().setSound(self,target,firstString,taskID);
//        }





//        /**ItemEntity的相關判斷**/
//        if(judgMent.toLowerCase().contains("itementity")){
//            new ItemEntity().setItemEntity(self,target,firstString,taskID);
//        }



    }


}
