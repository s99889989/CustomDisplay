package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.events.PacketContainer;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.task.action.ClearAction;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Color.*;
import static org.bukkit.Particle.REDSTONE;

public class SendParticles {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID;
    private Player player;
    private LivingEntity target;

    private String function = "";

    private boolean remove = false;

    private Particle putParticle;

    public SendParticles(){



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
                    try{
                        putParticle = Enum.valueOf(Particle.class,strings[1].toUpperCase());
                    }catch (Exception exception){

                    }

                }
            }

        }



        if(function.toLowerCase().contains("remove")){

        }else {
            sendParticle();
        }
    }
    public void sendParticle(){
        try{
            if(putParticle == REDSTONE){
                Particle.DustOptions dustOptions = new Particle.DustOptions(fromRGB(0x808080), 1);
                target.getWorld().spawnParticle(putParticle, target.getLocation().add(0.0, target.getHeight() / 2.0, 0.0), 8, 0.0, 0.0, 0.0, 0.15,dustOptions);
            }else {
                target.getWorld().spawnParticle(putParticle, target.getLocation().add(0.0, target.getHeight() / 2.0, 0.0), 8, 0.0, 0.0, 0.0, 0.15);
            }
        }catch (Exception e){
            //cd.getLogger().info(e.toString());
        }


    }

    /**是否取消遇到的粒子類型**/
    public boolean getResult(Particle inParticle){
        if(function.toLowerCase().contains("remove")){
            if(inParticle == putParticle){
                remove = true;
            }
        }
        return remove;
    }

    public Particle getPutParticle() {
        return putParticle;
    }
}
