package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Experience3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private Map<String, String> action_Map;

    private List<Entity> EntityList = new ArrayList<>();

    private String type = "沒有";
    private int amount = 1;


    public Experience3(){

    }

    public void setExp(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        this.self = self;
        this.target = target;
        this.action_Map = action_Map;

        setOther();
    }

    public void setOther(){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        amount = actionMapHandle.getInt(new String[]{"amount","a"},10);


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
            new PlayerTrigger(player).onTwo(player, target, "~onexpdown");
        }

        player.giveExp(amount);
    }

    public void expOtherSet(Player player){

        new PlayerLevel().addExpMap(player,type,amount);


    }


}
