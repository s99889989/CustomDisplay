package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

public class Level3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private Map<String, String> action_Map;

    public Level3(){

    }

    public void setLevel(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        this.self = self;
        this.target = target;
        this.action_Map = action_Map;
        setOther();
    }

    public void setOther(){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        String function = actionMapHandle.getString(new String[]{"function","fc"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);


        if(self instanceof Player){
            Player player = (Player) self;
            if(type.contains("minecraft")){
                if(function.toLowerCase().contains("set")){
                    /**設定原版等級**/
                    player.setLevel(amount);
                }else {
                    /**增加原版等級**/
                    player.giveExpLevels(amount);
                }
            }else {
                if(function.toLowerCase().contains("set")){
                    /**設定自訂等級**/
                    new PlayerLevel().setLevelMap(player,type,amount);
                }else {
                    /**增加自訂等級**/
                    new PlayerLevel().addLevelMap(player,type,amount);
                }
            }

        }


    }




}
