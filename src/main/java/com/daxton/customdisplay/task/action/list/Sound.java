package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Sound {

    private Player player;

    private Location location;
    private String sound = "";
    private float volume = 1;
    private float pitch = 1;
    private String category = "MASTER";

    public Sound(Player player,String firstString){
        this.player = player;
        location = player.getLocation();
        setSound(firstString);

    }

    public void playSound(){
        player.getWorld().playSound(location, sound, Enum.valueOf(SoundCategory.class , category), volume, pitch);
        //Enum.valueOf(SoundCategory.class , category);
    }

    public void setSound(String firstString){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String string1 : stringList){
            if(string1.toLowerCase().contains("sound=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    sound = strings[1];
                }
            }

            if(string1.toLowerCase().contains("volume=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    volume = Float.valueOf(strings[1]);
                }
            }

            if(string1.toLowerCase().contains("pitch=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    pitch = Float.valueOf(strings[1]);
                }
            }

            if(string1.toLowerCase().contains("category=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    category = strings[1];
                }
            }

        }
    }

}
