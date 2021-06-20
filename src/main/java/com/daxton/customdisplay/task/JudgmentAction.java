package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.entity.*;
import com.daxton.customdisplay.task.action.location.*;
import com.daxton.customdisplay.task.action.meta.Action3;
import com.daxton.customdisplay.task.action.meta.run.Loop3;
import com.daxton.customdisplay.task.action.meta.SwitchAction;
import com.daxton.customdisplay.task.action.meta.run.FixedPoint3;
import com.daxton.customdisplay.task.action.meta.run.LocPng3;
import com.daxton.customdisplay.task.action.meta.run.OrbitalAction3;
import com.daxton.customdisplay.task.action.player.*;
import com.daxton.customdisplay.task.action.profession.*;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class JudgmentAction {

    public JudgmentAction(){

    }

    public static void execute(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location inputLocation){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        String judgMent = new ActionMapHandle(action_Map, self, target).getString(new String[]{"ActionType"}, "");

        //cd.getLogger().info("觸發: "+judgMent + " : "+taskID);


        //ActionBar的相關判斷
        if(judgMent.equals("actionbar")){
            ActionBar3.setActionBar(self, target, action_Map, taskID);
            return;
        }

        //Action的相關判斷
        if(judgMent.equals("action")){
            Action3.setAction(self, target, action_Map, taskID);
            return;
        }

        //AttributePoint的相關判斷
        if(judgMent.equals("attributepoint")){
            AttributePoint3.setAttributePoint(self,target,action_Map,taskID);
            return;
        }

        //Attribute的相關判斷
        if(judgMent.equals("attribute")){
            SetAttribute3.set(self,target,action_Map,taskID);
            return;
        }

        //BossBar的相關判斷
        if(judgMent.equals("block")){
            setBlock.go(self, target, action_Map, taskID, inputLocation);
            return;
        }

        //BossBar的相關判斷
        if(judgMent.equals("bossbar")){
            SendBossBar4.setBossBar(self, target, action_Map, taskID);
            return;
        }

//        //Cancell的相關判斷
//        if(judgMent.toLowerCase().contains("cancell")){
//            new Cancell3().setCancell(self,target,action_Map,taskID);
//            return;
//        }

        //Command的相關判斷
        if(judgMent.equals("command")){
            Command3.setCommand(self,target,action_Map,taskID);
            return;
        }

        //CustomPoint的相關判斷
        if(judgMent.equals("custompoint")){
            Point3.setPoint(self,target,action_Map,taskID);
            return;
        }

        //CoreSkill的相關判斷
        if(judgMent.equals("setskilllevel")){
            SetSkillLevel3.setCoreSkill(self,target,action_Map,taskID);
            return;
        }

        //ClassAttr的相關判斷
        if(judgMent.contains("classattr")){
            SetClassAttr.set(self,target,action_Map,taskID);
            return;
        }

        //Damage的相關判斷
        if(judgMent.equals("damage")){
            Damage3.setOther(self,target,action_Map,taskID);
            return;
        }

        //DCMessage的相關判斷
        if(judgMent.equals("dcmessage")){
            DCMessage3.setDCMessage(self,target,action_Map,taskID);
            return;
        }

        //Experience的相關判斷
        if(judgMent.equals("exp")){
            Experience3.setExp(self,target,action_Map,taskID);
            return;
        }

//        //Entity的相關判斷
//        if(judgMent.toLowerCase().contains("entity")){
//            setEnitty.setEnitty(self,target,action_Map,taskID);
//            return;
//        }

        //FixedPoint的相關判斷
        if(judgMent.equals("fixedpoint")){
            if(ActionManager.judgment_FixedPoint_Map2.get(taskID) == null){
                ActionManager.judgment_FixedPoint_Map2.put(taskID, new FixedPoint3());
                ActionManager.judgment_FixedPoint_Map2.get(taskID).set(self, target, action_Map, taskID, inputLocation);
            }else {
                ActionManager.judgment_FixedPoint_Map2.get(taskID).cancel();
                ActionManager.judgment_FixedPoint_Map2.remove(taskID);

                ActionManager.judgment_FixedPoint_Map2.put(taskID, new FixedPoint3());
                ActionManager.judgment_FixedPoint_Map2.get(taskID).set(self,target,action_Map,taskID, inputLocation);
            }
            //new FixedPoint3().set(self,target,action_Map,taskID);  //+(Math.random()*100000)
            return;
        }

        //GiveItem的相關判斷
        if(judgMent.equals("item")){
            GiveItem3.setItem(self,target,action_Map,taskID);
            return;
        }

        //Glow的相關判斷
        if(judgMent.equals("glow")){
            setGlow3.setGlow(self,target,action_Map,taskID);
            return;
        }

        //Guise的相關判斷
        if(judgMent.equals("guise")){
            Guise4.setGuise(self, target, action_Map, taskID, inputLocation);
            return;
        }

        //Heal的相關判斷
        if(judgMent.equals("heal")){
            Heal3.setHeal(self,target,action_Map,taskID);
            return;
        }

        //HolographicDisplays的相關判斷
        if(judgMent.equals("hologram")){
            Holographic4.setHD(self, target, action_Map, taskID, inputLocation);
            return;
        }

        //Inventory的相關判斷
        if(judgMent.equals("inventory")){
            CustomInventory3.setInventory(self, target, action_Map, taskID);
            return;
        }

        //Invisible的相關判斷
        if(judgMent.equals("invisible")){
            setInvisible.setInvisible(self,target,action_Map,taskID);
            return;
        }

        //LocPng的相關判斷
        if(judgMent.equals("locpng")){
            if(ActionManager.judgment_LocPng_Map2.get(taskID) == null){
                ActionManager.judgment_LocPng_Map2.put(taskID, new LocPng3());
                ActionManager.judgment_LocPng_Map2.get(taskID).set(self,target,action_Map,taskID, inputLocation);
            }else {
                ActionManager.judgment_LocPng_Map2.get(taskID).cancel();
                ActionManager.judgment_LocPng_Map2.remove(taskID);

                ActionManager.judgment_LocPng_Map2.put(taskID, new LocPng3());
                ActionManager.judgment_LocPng_Map2.get(taskID).set(self,target,action_Map,taskID, inputLocation);
            }

            //new LocPng3().set(self,target,action_Map,taskID, inputLocation);
            return;
        }

        //Loop的相關判斷
        if(judgMent.equals("loop")){
            if(ActionManager.judgment_Loop_Map2.get(taskID) == null){
                ActionManager.judgment_Loop_Map2.put(taskID,new Loop3());
                ActionManager.judgment_Loop_Map2.get(taskID).onLoop(self, target, action_Map, taskID);
            }else {
                ActionManager.judgment_Loop_Map2.get(taskID).cancel();
                ActionManager.judgment_Loop_Map2.remove(taskID);
                ActionManager.judgment_Loop_Map2.put(taskID,new Loop3());
                ActionManager.judgment_Loop_Map2.get(taskID).onLoop(self, target, action_Map, taskID);
            }
            return;
        }

        //LoggerInfo的相關判斷
        if(judgMent.equals("loggerinfo")){
            LoggerInfo3.setLoggerInfo(self, target, action_Map, taskID);
            return;
        }

        //Level的相關判斷
        if(judgMent.equals("level")){
            Level3.setLevel(self, target, action_Map, taskID);
            return;
        }

        //Light的相關判斷
        if(judgMent.equals("light")){
            SetLight.setLight(self, target, action_Map, taskID, inputLocation);
            return;
        }

        //ModMessage的相關判斷
        if(judgMent.equals("modmessage")){
            ModMessage3.setMessage(self, target, action_Map, taskID);
            return;
        }

        //Money的相關判斷
        if(judgMent.equals("money")){
            setMoney.setM(self, target, action_Map, taskID);
            return;
        }

        //Message的相關判斷
        if(judgMent.equals("message")){
            Message3.setMessage(self,target,action_Map,taskID);
            return;
        }

        //MythicSkill的相關判斷
        if(judgMent.equals("mythicskill")){
            MythicAction3.setMythicAction(self,target,action_Map,taskID);
            return;
        }

        //Mana的相關判斷
        if(judgMent.equals("mana")){
            setMana3.setMana(self,target,action_Map,taskID);
            return;
        }

        //Move的相關判斷
        if(judgMent.equals("move")){
            Move3.setVelocity(self,target,action_Map,taskID);
            return;
        }

        //ModelEngine的相關判斷
        if(judgMent.equals("model")){
            CDModelEngine2.setGuise(self, target, action_Map, taskID, inputLocation);
            return;
        }

        //Name的相關判斷
        if(judgMent.equals("name")){
            SetName3.setName(self,target,action_Map,taskID);
            return;
        }

        //OrbitalAttack的相關判斷
        if(judgMent.equals("orbital")){

            if(ActionManager.judgment_OrbitalAction_Map2.get(taskID) == null){
                ActionManager.judgment_OrbitalAction_Map2.put(taskID, new OrbitalAction3());
                ActionManager.judgment_OrbitalAction_Map2.get(taskID).setParabolicAttack(self,target,action_Map,taskID);
            }else {
                try {
                    ActionManager.judgment_OrbitalAction_Map2.get(taskID).cancel();
                }catch (IllegalStateException exception){
                    //
                }
                ActionManager.judgment_OrbitalAction_Map2.remove(taskID);

                ActionManager.judgment_OrbitalAction_Map2.put(taskID, new OrbitalAction3());
                ActionManager.judgment_OrbitalAction_Map2.get(taskID).setParabolicAttack(self,target,action_Map,taskID);
            }
           // new OrbitalAction3().setParabolicAttack(self,target,action_Map,taskID); //+(Math.random()*100000)
            return;
        }

        //Particle的相關判斷
        if(judgMent.equals("particle")){
            SendParticles3.setParticles(self, target, action_Map, taskID, inputLocation);
            return;
        }

        //PotionEffect的相關判斷
        if(judgMent.equals("potioneffect")){
            PotionEffect3.set(self,target,action_Map,taskID);
            return;
        }

        //Sound的相關判斷
        if(judgMent.equals("sound")){
            Sound3.setSound(self, target, action_Map, taskID, inputLocation);
            return;
        }

        //SwitchAction的相關判斷
        if(judgMent.equals("switchaction")){
            SwitchAction.setAction(self, target, action_Map, taskID);
            return;
        }

        //Title的相關判斷
        if(judgMent.equals("title")){
            Title3.setTitle(self,target,action_Map,taskID);
            return;
        }

        //Teleport的相關判斷
        if(judgMent.equals("teleport")){
            Teleport3.setTp(self,target,action_Map,taskID);
        }

    }


}
