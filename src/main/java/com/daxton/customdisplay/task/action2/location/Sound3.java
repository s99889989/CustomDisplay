package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Map;

public class Sound3 {

    public Sound3(){

    }

    public static void setSound(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**聲音名稱**/
        String sound = actionMapHandle.getString(new String[]{"sound","s"},"");
        /**音量**/
        float volume = actionMapHandle.getFloat(new String[]{"volume","v"},1);

        /**音調**/
        float pitch = actionMapHandle.getFloat(new String[]{"pitch","p"},1);

        /**聲音的分類**/
        SoundCategory category = actionMapHandle.getSoundCategory(new String[]{"category","c"},"PLAYERS");

        Location location = actionMapHandle.getLocation(null);

        if(location != null){
            location.getWorld().playSound(location, sound, category, volume, pitch);
        }

    }


}
