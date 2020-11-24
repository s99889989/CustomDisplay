package com.daxton.customdisplay.api.action.list;

import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

import static org.bukkit.SoundCategory.PLAYERS;

public class Sound {

    private Player player;

    private String string;

    public Sound(Player player,String string){
        this.player = player;
        this.string = string;

    }

    public void playSound(){
        player.getWorld().playSound(player.getLocation(), getSound(),Enum.valueOf(SoundCategory.class , getCategory()), getVolume(), getPitch()); //,Enum.valueOf(SoundCategory.class , getCategory())

    }

    public String getSound(){
        String string4 = "";
        String string1 = string.toLowerCase().replace("sound[","").replace("]","");
        String[] strings1 = string1.split(",");
        for(String string2 : strings1){
            if(string2.contains("sound=")){
                String[] string3 = string2.split("=");
                string4 = string3[1];
            }
        }
        return string4;
    }

    public String getCategory(){
        String string4 = "";
        String string1 = string.toUpperCase().replace("SOUND[","").replace("]","");
        String[] strings1 = string1.split(",");
        for(String string2 : strings1){
            if(string2.contains("SOUNDCATEGORY")){
                String[] string3 = string2.split("=");
                string4 = string3[1];
            }
        }
        return string4;
    }

    public int getVolume(){
        int int1 = 0;
        String string1 = string.toLowerCase().replace("sound[","").replace("]","");
        String[] strings1 = string1.split(",");
        for(String string2 : strings1){
            if(string2.contains("volume=")){
                String[] string3 = string2.split("=");
                int1 = Integer.valueOf(string3[1]);
            }
        }
        return int1;
    }

    public int getPitch(){
        int int1 = 0;
        String string1 = string.toLowerCase().replace("sound[","").replace("]","");
        String[] strings1 = string1.split(",");
        for(String string2 : strings1){
            if(string2.contains("pitch=")){
                String[] string3 = string2.split("=");
                int1 = Integer.valueOf(string3[1]);
            }
        }
        return int1;
    }

}
