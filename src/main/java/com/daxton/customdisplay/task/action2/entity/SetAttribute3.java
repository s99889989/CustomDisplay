package com.daxton.customdisplay.task.action2.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.set.PlayerBukkitAttribute;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;

public class SetAttribute3 {



    public SetAttribute3(){

    }

    public static void set(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //屬性名稱
        String attributes = actionMapHandle.getString(new String[]{"attributes","attr"},"GENERIC_MAX_HEALTH");

        //屬性類型
        String label = actionMapHandle.getString(new String[]{"label"},"action");

        //持續時間
        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},200);

        //量
        double amount = actionMapHandle.getDouble(new String[]{"amount","a"},1);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();
        livingEntityList.forEach(livingEntity -> giveAttr(livingEntity,attributes, label,amount,duration) );

    }

    public static void giveAttr(LivingEntity livingEntity, String attributes, String label , Double amount, int duration){
        String uuidString = livingEntity.getUniqueId().toString();

        //如果原本技能效果不存在
        if(ActionManager.setAttribute_Run_Map.get(uuidString+attributes) == null){

            setAttr(livingEntity, attributes, amount, label, duration);

        //如果原本技能效果還存在
        }else {

            ActionManager.setAttribute_Run_Map.get(uuidString+attributes).cancel();

            setAttr(livingEntity, attributes, amount, label, duration);

        }


    }

    public static void setAttr(LivingEntity livingEntity, String attributes, Double amount, String label, int duration){

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        String uuidString = livingEntity.getUniqueId().toString();

        PlayerBukkitAttribute.removeAddAttribute(livingEntity,attributes.toUpperCase(),"ADD_NUMBER", amount, label);

        if(duration <= 0){
            duration = 20;
        }

        ActionManager.setAttribute_Run_Map.put(uuidString + attributes, new BukkitRunnable() {
            @Override
            public void run() {


                PlayerBukkitAttribute.removeAttribute(livingEntity, attributes.toUpperCase(), label);

                ActionManager.setAttribute_Run_Map.remove(uuidString+attributes);

            }
        });

        ActionManager.setAttribute_Run_Map.get(uuidString+attributes).runTaskLater(cd,duration);

    }


}
