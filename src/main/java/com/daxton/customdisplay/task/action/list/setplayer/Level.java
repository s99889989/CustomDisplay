package com.daxton.customdisplay.task.action.list.setplayer;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private List<Entity> EntityList = new ArrayList<>();

    private String type = "沒有";
    private String function = "";
    private int amount = 1;

    private String aims = "";
    private int distance = 1;

    public Level(){

    }

    public void setLevel(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        setOther();
    }

    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){

            if(allString.toLowerCase().contains("type=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    type = strings[1];

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

            if(allString.toLowerCase().contains("@=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    aims = strings[1].replace(" ","");
                }else if(strings.length == 3){
                    aims = strings[1].replace(" ","");
                    try{
                        distance = Integer.valueOf(strings[2].replace(" ",""));
                    }catch (NumberFormatException exception){
                        cd.getLogger().info(strings[2]+"不是數字");
                    }
                }
            }

        }

        EntityList.add(self);


        if(!(EntityList.isEmpty())){
            for(Entity entity : EntityList){
                if(entity instanceof Player){
                    Player player = (Player) entity;
                    if(type.contains("沒有")){
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
    }




}
