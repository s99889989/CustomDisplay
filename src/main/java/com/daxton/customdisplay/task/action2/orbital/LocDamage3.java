package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import org.bukkit.entity.LivingEntity;

import java.util.Map;


public class LocDamage3 {

    public LocDamage3(){

    }

    public void setDamage(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        if(target == null){
            return;
        }

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

        String type = actionMapHandle.getString(new String[]{"type","t"},"");

        type = type.toLowerCase();
        //CustomDisplay.getCustomDisplay().getLogger().info(type+" : "+amount);
        //遠距離
        if(type.equals("range_multiply")){
            setDamage(self, target, amount+0.3444440000000001);
            return;
        }
        if(type.equals("range_add")){
            setDamage(self, target, amount+0.3333330000000001);
            return;
        }
        if(type.equals("range_attack")){
            setDamage(self, target, amount+0.3222220000000001);
            return;
        }
        //魔法
        if(type.equals("magic_multiply")){
            setDamage(self, target, amount+0.2444440000000001);
            return;
        }
        if(type.equals("magic_add")){
            setDamage(self, target, amount+0.2333330000000001);
            return;
        }
        if(type.equals("magic_attack")){
            setDamage(self, target, amount+0.2222220000000001);
            return;
        }
//        if(type.equals("magic_speed")){
//            setDamage(self, target, amount+0.2111110000000001);
//            return;
//        }
        //近距離
        if(type.equals("melee_multiply")){
            setDamage(self, target, amount+0.1444440000000001);
            return;
        }
        if(type.equals("melee_add")){
            setDamage(self, target, amount+0.1333330000000001);
            return;
        }
        if(type.equals("melee_attack")){
            setDamage(self, target, amount);
            return;
        }
        setDamage(self, target, amount);



    }

    public void setDamage(LivingEntity self, LivingEntity target,double amount){

        target.damage(amount, self);
    }




}
