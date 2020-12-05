package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.events.PacketContainer;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.task.action.ClearAction;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SendParticles {

    private String taskID;
    private Player player;
    private LivingEntity target;

    private String function = "";

    private String putParticle = "";

    public SendParticles(){



    }

    public void sendParticle(){
        target.getWorld().spawnParticle(Particle.LAVA, target.getLocation().add(0.0, target.getHeight() / 2.0, 0.0), 8, 0.0, 0.0, 0.0, 0.15);
    }

    public void setParticles(Player player, String firstString, String taskID){
        this.player = player;
        this.taskID = taskID;
        stringSetting(firstString);
    }

    public void setParticles(Player player, LivingEntity target, String firstString, String taskID){
        this.player = player;
        this.target = target;
        this.taskID = taskID;
        stringSetting(firstString);
    }

    public void stringSetting(String firstString){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String allString : stringList) {
            if (allString.toLowerCase().contains("function=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    function = strings[1];
                }
            }
            if (allString.toLowerCase().contains("particle=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    putParticle = strings[1];
                }
            }

        }



        if(function.toLowerCase().contains("remove")){

        }
    }



    public boolean getResult(Particle inParticle){
        boolean b = false;


        if(Enum.valueOf(Particle.class,putParticle) == inParticle){
            b = true;
        }
        return b;
    }

}
