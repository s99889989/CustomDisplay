package com.daxton.customdisplay.task.locationAction;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.StringConversion;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

public class LocationSound {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private Location location = new Location(Bukkit.getWorld("world"),0,0,0);
    private String aims = "self";
    private String sound = "";
    private float volume = 1;
    private float pitch = 1;
    private String category = "MASTER";

    public LocationSound(){

    }

    public void setSound(LivingEntity self, LivingEntity target, String firstString,String taskID,Location location){
        this.location = location;
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){

        for(String string : new StringFind().getStringList(firstString)){

            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
            if(string.toLowerCase().contains("sound=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    sound = strings[1];
                }
            }

            if(string.toLowerCase().contains("volume=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    volume = Float.valueOf(strings[1]);
                }
            }

            if(string.toLowerCase().contains("pitch=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    pitch = Float.valueOf(strings[1]);
                }
            }

            if(string.toLowerCase().contains("category=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    category = strings[1];
                }
            }
        }



        playSound();


    }

    public void playSound(){
        self.getWorld().playSound(location, sound, Enum.valueOf(SoundCategory.class , category), volume, pitch);
    }


}
