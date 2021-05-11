package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Experience2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;

    private List<Entity> EntityList = new ArrayList<>();

    private String type = "沒有";
    private int amount = 1;


    public Experience2(){

    }

    public void setExp(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;

        setOther();
    }

    public void setOther(){

        type = customLineConfig.getString(new String[]{"type"},"minecraft",self,target);

        amount = customLineConfig.getInt(new String[]{"amount","a"},10,self,target);


        if(self instanceof Player){
            Player player = (Player) self;
            if(type.contains("minecraft")){
                expSet(player);
            }else {
                expOtherSet(player);
            }

        }

    }

    public void expSet(Player player){
        String uuidString = player.getUniqueId().toString();
        if(amount < 0){
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_exp_type>","default");
            PlayerTrigger.onPlayer(player, target, "~onexpdown");
        }

        player.giveExp(amount);
    }

    public void expOtherSet(Player player){

        new PlayerLevel().addExpMap(player,type,amount);


    }


}
