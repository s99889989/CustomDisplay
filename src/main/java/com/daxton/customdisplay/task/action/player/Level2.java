package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Level2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;

    public Level2(){

    }

    public void setLevel(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        setOther();
    }

    public void setOther(){

        String type = customLineConfig.getString(new String[]{"type"},"minecraft",self,target);

        String function = customLineConfig.getString(new String[]{"function","fc"},"minecraft",self,target);

        int amount = customLineConfig.getInt(new String[]{"amount","a"},1,self,target);


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
