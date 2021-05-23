package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class LocSound3 {

    public LocSound3(){

    }

    public void setSound(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location location){
        if(location == null){
            return;
        }

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**聲音名稱**/
        String sound = actionMapHandle.getString(new String[]{"sound","s"},"");
        /**音量**/
        float volume = actionMapHandle.getFloat(new String[]{"volume","v"},1);

        /**音調**/
        float pitch = actionMapHandle.getFloat(new String[]{"pitch","p"},1);

        /**聲音的分類**/
        SoundCategory category = actionMapHandle.getSoundCategory(new String[]{"category","c"},"PLAYERS");

        playSound(location, sound, category, volume, pitch);


    }



    public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch){
        location.getWorld().playSound(location, sound, category, volume, pitch);
    }


}
