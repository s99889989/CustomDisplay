package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.location.AimsLocation;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class Sound {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

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

        String sound = new SetValue(self,target,firstString,"[];","","sound=","s=").getString();

        float volume = new SetValue(self,target,firstString,"[];","","volume","v=").getFloat(1);

        float pitch = new SetValue(self,target,firstString,"[];","","pitch=","p=").getFloat(1);

        SoundCategory category = new SetValue(self,target,firstString,"[];","PLAYERS","category=","c=").getSoundCategory("PLAYERS");

        String locAdd = new StringFind().getKeyValue2(self,target,firstString,"[]; ","","locadd=");
        String[] locAdds = locAdd.split("\\|");
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

        List<Location> locationList = new AimsLocation().valueOf(self ,target, firstString, x, y, z);
        if(!(locationList.isEmpty())){
            double xx = x;
            double yy = y;
            double zz = z;
            locationList.forEach(location1 -> {
                playSound(location1.add(xx,yy,zz), sound, category, volume, pitch);
            });
        }


    }

    public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch){
        self.getWorld().playSound(location, sound, category, volume, pitch);
    }


}
