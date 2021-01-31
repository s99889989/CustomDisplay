package com.daxton.customdisplay.task.action.list.setplayer;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.PlayerConfig;
import com.daxton.customdisplay.api.player.data.set.PlayerAttributesPoint;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;

public class AttributePoint {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private String type = "沒有";
    private int amount = 1;

    public AttributePoint(){

    }

    public void setAttributePoint(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        setOther();

        if(self instanceof Player){
            Player player = (Player) self;
            addPoint(player);
        }
    }

    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("type=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    type = strings[1];

                }
            }

            if(allString.toLowerCase().contains("amount=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    try{
                        amount = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("amount=內只能放整數數字");
                    }
                }
            }

        }

    }

    public void addPoint(Player player){

        new PlayerAttributesPoint().setOneMap(player,type,amount);


    }


}
