package com.daxton.customdisplay.task.action.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

public class LocationSound2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private Location location;
    private String aims = "self";
    private String sound = "";
    private float volume = 1;
    private float pitch = 1;
    private String category = "MASTER";

    public LocationSound2(){

    }

    public void setSound(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID, Location location){
        if(location == null){
            return;
        }
        /**聲音名稱**/
        String sound = customLineConfig.getString(new String[]{"sound","s"},"",self,target);
        /**音量**/
        float volume = customLineConfig.getFloat(new String[]{"volume","v"},1,self,target);

        /**音調**/
        float pitch = customLineConfig.getFloat(new String[]{"pitch","p"},1,self,target);

        /**聲音的分類**/
        SoundCategory category = customLineConfig.getSoundCategory(new String[]{"category","c"},"PLAYERS",self,target);

        playSound(location, sound, category, volume, pitch);




    }



    public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch){
        location.getWorld().playSound(location, sound, category, volume, pitch);
    }


}
