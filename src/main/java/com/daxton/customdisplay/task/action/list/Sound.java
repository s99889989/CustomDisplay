package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

public class Sound {

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
    private double x = 0;
    private double y = 0;
    private double z = 0;

    public Sound(){

    }

    public void setSound(LivingEntity self, LivingEntity target, String firstString,String taskID){
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

        for(String string : new StringFind().getStringMessageList(firstString)){
            if(string.toLowerCase().contains("sx=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        x = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("sx不是數字"+strings[1]);
                    }
                }
            }

            if(string.toLowerCase().contains("sy=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        y = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("sy不是數字"+strings[1]);
                    }
                }
            }

            if(string.toLowerCase().contains("sz=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        z = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("sz不是數字"+strings[1]);
                    }
                }
            }
        }

        if(aims.toLowerCase().contains("target")){
            location = target.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("self")){
            location = self.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("world")){
            location = new Location(self.getWorld(),x,y,z);
        }else {
            location = location.add(x,y,z);
        }

        playSound();


    }

    public void playSound(){
        self.getWorld().playSound(location, sound, Enum.valueOf(SoundCategory.class , category), volume, pitch);
    }


}
