package com.daxton.customdisplay.task.action.list.setplayer;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.PlayerTrigger2;
import com.daxton.customdisplay.api.player.data.set.PlayerLevel;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Experience {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private List<Entity> EntityList = new ArrayList<>();

    private String type = "沒有";
    private int amount = 1;

    private String aims = "";
    private int distance = 1;

    public Experience(){

    }

    public void setExp(LivingEntity self, LivingEntity target, String firstString, String taskID){

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
                    if(type.contains("沒有")){
                        expSet(((Player) entity).getPlayer());
                    }else {
                        expOtherSet(((Player) entity).getPlayer());
                    }

                }
            }
        }

    }

    public void expSet(Player player){
        String uuidString = player.getUniqueId().toString();
        if(amount < 0){
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_exp_type>","default");
            new PlayerTrigger2(player).onExpDown(player);
        }

        player.giveExp(amount);
    }

    public void expOtherSet(Player player){

        new PlayerLevel().addExpMap(player,type,amount);


    }


}
