package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class Message{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private Player player= null;;

    private LivingEntity self = null;

    private LivingEntity target = null;

    private Particle particle;

    public Message(){


    }



    public void setMessage(Player player, String firstString){
        this.player = player;
        this.self = player;

        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversion2(self,target,strings[1],"Character").valueConv();
                }
            }

        }

    }

    public void sendMessage(){
        message = message.replace("{Particle_ID}",particle.toString());
        player.sendMessage(message);
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }
}
