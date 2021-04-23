package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Map;

public class Sound3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private Map<String, String> action_Map;
    private String taskID = "";

    public Sound3(){

    }

    public void setSound(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        this.self = self;
        this.target = target;
        this.action_Map = action_Map;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        /**聲音名稱**/
        String sound = actionMapHandle.getString(new String[]{"sound","s"},"");
        /**音量**/
        float volume = actionMapHandle.getFloat(new String[]{"volume","v"},1);

        /**音調**/
        float pitch = actionMapHandle.getFloat(new String[]{"pitch","p"},1);

        /**聲音的分類**/
        SoundCategory category = actionMapHandle.getSoundCategory(new String[]{"category","c"},"PLAYERS");

        /**增加座標**/
        String[] locAdds = actionMapHandle.getStringList(new String[]{"locadd"},new String[]{"0","0","0"},"\\|",3);
        double x = 0;
        double y = 0;
        double z = 0;
        if(locAdds.length == 3){
            try {
                x = Double.valueOf(locAdds[0]);
                y = Double.valueOf(locAdds[1]);
                z = Double.valueOf(locAdds[2]);
            }catch (NumberFormatException exception){
                x = 0;
                y = 0;
                z = 0;
            }
        }
        List<LivingEntity> targetList = actionMapHandle.getLivingEntityList();

        for(LivingEntity livingEntity : targetList){
            Location location = livingEntity.getLocation();
            playSound(location.add(x,y,z), sound, category, volume, pitch);
        }

    }

    public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch){
        location.getWorld().playSound(location, sound, category, volume, pitch);
    }


}
