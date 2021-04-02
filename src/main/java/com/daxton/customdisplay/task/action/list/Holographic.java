package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.entity.EntityFind;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.LookTarget;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.api.location.AimsLocation;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class Holographic {

    private static final Random random = new Random();

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private String firstString = "";
    private LivingEntity self = null;
    private LivingEntity target = null;

    private Hologram hologram;

    private Location createLocation = new Location(Bukkit.getWorld("world"),0,0,0);

    public Map<String, Hologram> hologramMap = new HashMap<>();
    public Map<String, Location> locationMap = new HashMap<>();
    public Map<String, LivingEntity> livingEntityMap = new HashMap<>();


    public Holographic(){

    }

    public void setHD(LivingEntity self, LivingEntity target, String firstString,String taskID) {

        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.firstString = firstString;
        setOther();
    }

    public void setOther(){

        //String message = new StringFind().getKeyValue2(self,target,firstString,"[];","","message=","m=");

        String function = new SetValue(self,target,firstString,"[]; ","","function=","fc=").getString();

        double x = 0;
        double y = 0;
        double z = 0;
        String[] locAdds = new SetValue(self,target,firstString,"[];","","locadd=").getStringList("\\|");// locAdd.split("\\|");
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

        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);

        if(function.toLowerCase().contains("create")){
            if(!(targetList.isEmpty())){
                for (LivingEntity livingEntity : targetList){

                    String uuidString = livingEntity.getUniqueId().toString();
                    if(hologramMap.get(taskID+uuidString) == null){
                        String message2 = new SetValue(self,livingEntity,firstString,"[];","","message=","m=").getString();
                        locationMap.put(taskID+uuidString, livingEntity.getLocation().add(x,y,z));
                        livingEntityMap.put(taskID+uuidString, livingEntity);
                        hologramMap.put(taskID+uuidString, createHD(livingEntity.getLocation().add(x,y,z), message2));
                    }
                }
            }
        }
        if(function.toLowerCase().contains("addtextline")){
            hologramMap.forEach((s, hologram1) -> {
                LivingEntity livingEntity = livingEntityMap.get(s);
                String message2 = new SetValue(self,livingEntity,firstString,"[];","","message=","m=").getString();
                addLineHD(hologram1,message2);
            });
        }
        if(function.toLowerCase().contains("additemline")){
            hologramMap.forEach((s, hologram1) -> {
                LivingEntity livingEntity = livingEntityMap.get(s);
                String message2 = new SetValue(self,livingEntity,firstString,"[];","","message=","m=").getString();
                addItemHD(self, livingEntity, hologram1, message2);
            });
        }
        if(function.toLowerCase().contains("removetextline")){
            hologramMap.forEach((s, hologram1) -> {
                LivingEntity livingEntity = livingEntityMap.get(s);
                String message2 = new SetValue(self,livingEntity,firstString,"[];","","message=","m=").getString();
                removeLineHD(hologram1,message2);
            });
        }
        if(function.toLowerCase().contains("teleport")){
            double xx = x;
            double yy = y;
            double zz = z;

            hologramMap.forEach((s, hologram1) -> {

                Location location = hologram1.getLocation().add(xx,yy,zz);

                if(!(targetList.isEmpty())){

                    for(LivingEntity livingEntity : targetList){
                        String uuidString = livingEntity.getUniqueId().toString();
                        if(livingEntityMap.get(taskID+uuidString) == livingEntityMap.get(s)){
                            location = livingEntity.getLocation().add(xx,yy,zz);
                        }
                    }
                }


                teleportHD(hologram1,location);

            });
        }
        if(function.toLowerCase().contains("delete")){
            hologramMap.forEach((s, hologram1) -> {
                deleteHD(hologram1);
            });
            new ClearAction(taskID);
            if(ActionManager.getJudgment2_Holographic2_Map().get(taskID) != null){
                ActionManager.getJudgment2_Holographic2_Map().remove(taskID);
            }
        }


    }


    public Hologram createHD(Location location, String message){

        hologram = HologramsAPI.createHologram(cd, location);
        hologram.appendTextLine(message);
        return hologram;
    }

    public void addLineHD(Hologram hologram, String message){
        hologram.appendTextLine(message);
    }

    public void addItemHD(LivingEntity self,LivingEntity target, Hologram hologram, String itemID){
        //ItemStack itemStack = giveItem(self,target, itemID);
        ItemStack itemStack = CustomItem.valueOf(self, target, itemID,1);
        hologram.appendItemLine(itemStack);
    }

    public void removeLineHD(Hologram hologram, String message){
        try{
            hologram.removeLine(Integer.valueOf(message));
        }catch (NumberFormatException exception){
            hologram.removeLine(0);
        }
    }

    public void teleportHD(Hologram hologram, Location location){
        hologram.teleport(location);
    }

    public void deleteHD(Hologram hologram){
        hologram.delete();
    }

    public Hologram getHologram() {
        return hologram;
    }




}
