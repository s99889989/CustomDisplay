package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.data.set.PlayerPoint;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Point2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;

    private List<Entity> EntityList = new ArrayList<>();

    private String type = "沒有";
    private String function = "";
    private int amount = 1;

    public Point2(){

    }

    public void setPoint(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        setOther();
    }

    public void setOther(){

        String type = customLineConfig.getString(new String[]{"type"},"minecraft",self,target);

        int amount = customLineConfig.getInt(new String[]{"amount","a"},1,self,target);




        if(self instanceof Player){
            Player player = (Player) self;
            if(type.contains("minecraft")){

            }else {
                addPoint(player, type, amount);
            }
        }



    }

    public void addPoint(Player player, String type, int amount){

        new PlayerPoint().setOneMap(player,type,amount);


    }


}
