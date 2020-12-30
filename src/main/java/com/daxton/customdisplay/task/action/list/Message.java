package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Message{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private Player player= null;
    private LivingEntity self = null;
    private LivingEntity target = null;


    private String aims = "self";

    public Message(){


    }

    public void setMessage(LivingEntity self,LivingEntity target, String firstString,String taskID){
        this.self = self;
        this.target = target;

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }

        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversion2(self,target,strings[1],"Character").valueConv();
                }
            }

        }
        if(target instanceof Player & aims.toLowerCase().contains("target")){
            player = (Player) target;
        }else {
            if(self instanceof Player){
                player = (Player) self;
            }
        }
        if(player != null){
            sendMessage();
        }
    }

    public void sendMessage(){
        player.sendMessage(message);
    }


}
