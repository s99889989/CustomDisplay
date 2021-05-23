package com.daxton.customdisplay.task;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action2.entity.*;
import com.daxton.customdisplay.task.action2.location.*;
import com.daxton.customdisplay.task.action2.meta.Action3;
import com.daxton.customdisplay.task.action2.meta.Loop3;
import com.daxton.customdisplay.task.action2.meta.SwitchAction;
import com.daxton.customdisplay.task.action2.orbital.LocFixedPoint3;
import com.daxton.customdisplay.task.action2.orbital.LocPng3;
import com.daxton.customdisplay.task.action2.orbital.LocSetClassAttr;
import com.daxton.customdisplay.task.action2.orbital.OrbitalAction3;
import com.daxton.customdisplay.task.action2.player.*;
import com.daxton.customdisplay.task.action2.profession.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class JudgmentAction2 {

    public JudgmentAction2(){

    }

    public void execute(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        //CustomDisplay cd = CustomDisplay.getCustomDisplay();

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
            new AttributePoint3().setAttributePoint(self,target,action_Map,taskID);
            return;
        }

        //Attribute的相關判斷
        if(judgMent.equals("attribute")){
            SetAttribute3.set(self,target,action_Map,taskID);
            return;
        }

        //BossBar的相關判斷
        if(judgMent.equals("bossbar")){

            if(ActionManager.judgment_SendBossBar_Map2.get(taskID) == null){
                ActionManager.judgment_SendBossBar_Map2.put(taskID,new SendBossBar3());
            }
            if(ActionManager.judgment_SendBossBar_Map2.get(taskID) != null){
                ActionManager.judgment_SendBossBar_Map2.get(taskID).setBossBar(self, target, action_Map, taskID);
            }
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
            new Point3().setPoint(self,target,action_Map,taskID);
            return;
        }

        //CoreSkill的相關判斷
        if(judgMent.equals("setskilllevel")){
            new SetSkillLevel3().setCoreSkill(self,target,action_Map,taskID);
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

            new LocFixedPoint3().set(self,target,action_Map,taskID+(Math.random()*100000));
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

            if(ActionManager.judgment_Guise_Map2.get(taskID) == null){
                ActionManager.judgment_Guise_Map2.put(taskID, new Guise3());
            }
            if(ActionManager.judgment_Guise_Map2.get(taskID) != null){
                ActionManager.judgment_Guise_Map2.get(taskID).setGuise(self, target, action_Map, taskID);
            }
            return;
        }

        //Heal的相關判斷
        if(judgMent.equals("heal")){
            Heal3.setHeal(self,target,action_Map,taskID);
            return;
        }


        //HolographicDisplays的相關判斷
        if(judgMent.equals("hologram")){
            if(ActionManager.judgment_Holographic_Map2.get(taskID) == null){
                ActionManager.judgment_Holographic_Map2.put(taskID,new Holographic3());
            }
            if(ActionManager.judgment_Holographic_Map2.get(taskID) != null){
                ActionManager.judgment_Holographic_Map2.get(taskID).setHD(self, target, action_Map, taskID);
            }
            return;
        }


        //Inventory的相關判斷
        if(judgMent.equals("inventory")){
//            String uuidString = self.getUniqueId().toString();
//            if(EditorGUIManager.menu_CustomInventory_Map.get(uuidString) == null){
//                EditorGUIManager.menu_CustomInventory_Map.put(uuidString,new CustomInventory3());
//            }
//            if(EditorGUIManager.menu_CustomInventory_Map.get(uuidString) != null){
//                EditorGUIManager.menu_CustomInventory_Map.get(uuidString).setInventory(self, target, action_Map, taskID);
//            }
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

            new LocPng3().set(self,target,action_Map,taskID);
            return;
        }

        //Loop的相關判斷
        if(judgMent.equals("loop")){

            if(ActionManager.judgment_Loop_Map2.get(taskID) == null){
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
            if (Bukkit.getServer().getPluginManager().getPlugin("LightAPI") != null) {
                SetLight.setLight(self, target, action_Map, taskID);
            }
            //SetLight.setLight(self, target, action_Map, taskID);
            return;
        }

        //ModMessage的相關判斷
        if(judgMent.equals("modmessage")){
            ModMessage3.setMessage(self, target, action_Map, taskID);
            return;
        }

        //Message的相關判斷
        if(judgMent.equals("message")){
            Message3.setMessage(self,target,action_Map,taskID);
            return;
        }



        //MythicSkill的相關判斷
        if(judgMent.equals("mythicskill")){
            new MythicAction3().setMythicAction(self,target,action_Map,taskID);
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
            if(ActionManager.judgment_ModelEngine_Map.get(taskID) == null){
                ActionManager.judgment_ModelEngine_Map.put(taskID, new CDModelEngine());
            }
            if(ActionManager.judgment_ModelEngine_Map.get(taskID) != null){
                ActionManager.judgment_ModelEngine_Map.get(taskID).setGuise(self, target, action_Map, taskID);
            }
            return;
        }

        //Name的相關判斷
        if(judgMent.equals("name")){
            SetName3.setName(self,target,action_Map,taskID);
            return;
        }

        //OrbitalAttack的相關判斷
        if(judgMent.equals("orbital")){

            new OrbitalAction3().setParabolicAttack(self,target,action_Map,taskID); //+(Math.random()*100000)
            return;
        }

        //Particle的相關判斷
        if(judgMent.equals("particle")){
            SendParticles3.setParticles(self,target,action_Map,taskID);
            return;
        }

        //PotionEffect的相關判斷
        if(judgMent.equals("potioneffect")){
            PotionEffect3.set(self,target,action_Map,taskID);
            return;
        }

        //Sound的相關判斷
        if(judgMent.equals("sound")){
            Sound3.setSound(self,target,action_Map,taskID);
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
