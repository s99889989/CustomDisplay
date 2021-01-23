package com.daxton.customdisplay.task.action.list.setplayer;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.data.set.PlayerSkills;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CoreSkill {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private List<Entity> EntityList = new ArrayList<>();

    private String skillName = "沒有";
    private String function = "";
    private int amount = 1;

    public CoreSkill(){

    }

    public void setCoreSkill(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        setOther();

    }

    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){

            if(allString.toLowerCase().contains("skillname=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    skillName = strings[1];

                }
            }

            if(allString.toLowerCase().contains("function=") || allString.toLowerCase().contains("fc=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    function = strings[1];

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

        if(self != null && self instanceof Player && !(skillName.equals("沒有"))){
            Player player = (Player) self;
            addSkill(player);

        }



    }

    public void addSkill(Player player){

        new PlayerSkills().setOneMap(player,skillName,amount);


    }


}