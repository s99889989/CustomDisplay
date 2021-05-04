package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.location.DirectionLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.Map;

public class Teleport3 {

    public Teleport3(){

    }

    public void setTp(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        double x = 0;
        double y = 0;
        double z = 0;
        String[] locAdds = actionMapHandle.getStringList(new String[]{"locadd","la"},new String[]{"0","0","0"},"\\|",3);
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

        /**向量增加座標**/
        String[] directionAdd = actionMapHandle.getStringList(new String[]{"directionadd","da"},new String[]{"0","0","0"},"\\|",3);
        double daX = 0;
        double daY = 0;
        double daZ = 0;
        if(directionAdd.length == 3){
            try {
                daX = Double.parseDouble(directionAdd[0]);
                daY = Double.parseDouble(directionAdd[1]);
                daZ = Double.parseDouble(directionAdd[2]);
            }catch (NumberFormatException exception){
                daX = 0;
                daY = 0;
                daZ = 0;
            }
        }

        boolean onblock = actionMapHandle.getBoolean(new String[]{"onblock","ob"},true);

        Location location = new Location(Bukkit.getWorld("world"),0,0,0);

        String locTarget = actionMapHandle.getString(new String[]{"loctarget","lt"},"");

        if(locTarget.toLowerCase().contains("self")){
            location = DirectionLocation.getSetDirection(self.getLocation(), self.getLocation(), daX, daY, daZ).add(x, y, z);

        }else if(locTarget.toLowerCase().contains("target")){
            if(target != null){
                location = DirectionLocation.getSetDirection(target.getLocation(), self.getLocation(), daX, daY, daZ).add(x, y, z);
            }else {
                location = self.getLocation();
            }
        }else if(locTarget.toLowerCase().contains("world")){
            location = new Location(self.getWorld(),x,y,z);
        }else {
            location = location.add(x,y,z);
        }

        if(onblock){
            while(!location.getBlock().getTranslationKey().equals("block.minecraft.air")){
                location.setY(location.getY()+1);
            }
            while(location.getBlock().getTranslationKey().equals("block.minecraft.air")){
                location.setY(location.getY()-0.05);
            }
            location.setY(location.getY()+0.2);
        }


//        Block block = location.getBlock();
//        Block block2 = location.getBlock().getRelative(BlockFace.DOWN);
//        if(!block2.getTranslationKey().equals("block.minecraft.air")){
            goTp(self, location);
//        }

    }


    public void goTp(LivingEntity livingEntity, Location location){

        livingEntity.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

}
