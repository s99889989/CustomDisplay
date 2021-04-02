package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.location.AimsLocation;
import com.daxton.customdisplay.api.other.SetValue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class Sound2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;
    private String taskID = "";

    public Sound2(){

    }

    public void setSound(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){

        /**聲音名稱**/
        String sound = customLineConfig.getString(new String[]{"sound","s"},"",self,target);
        /**音量**/
        float volume = customLineConfig.getFloat(new String[]{"volume","v"},1,self,target);

        /**音調**/
        float pitch = customLineConfig.getFloat(new String[]{"pitch","p"},1,self,target);

        /**聲音的分類**/
        SoundCategory category = customLineConfig.getSoundCategory(new String[]{"category","c"},"PLAYERS",self,target);

        /**增加座標**/
        String[] locAdds = customLineConfig.getStringList(new String[]{"locadd"},new String[]{"0","0","0"},"\\|",3,self,target);
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
        String locTarget = customLineConfig.getString(new String[]{"loctarget","lt"},"target",self,target);
        Location location = new Location(Bukkit.getWorld("world"),0,0,0);
        if(locTarget.toLowerCase().contains("self")){
            location = self.getLocation();
        }else if(locTarget.toLowerCase().contains("target")){
            if(target != null){
                location = target.getLocation();
            }
        }else if(locTarget.toLowerCase().contains("world")){
            location = new Location(self.getWorld(),0,0,0);
        }else {
            location = self.getLocation();
        }
        playSound(location.add(x,y,z), sound, category, volume, pitch);


    }

    public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch){
        location.getWorld().playSound(location, sound, category, volume, pitch);
    }


}
