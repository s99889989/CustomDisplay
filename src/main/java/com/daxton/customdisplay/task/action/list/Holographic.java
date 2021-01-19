package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Random;

public class Holographic {

    private static final Random random = new Random();

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private String firstString = "";
    private LivingEntity self = null;
    private LivingEntity target = null;

    private Hologram hologram;

    private Location createLocation = new Location(Bukkit.getWorld("world"),0,0,0);

    private String function = "";
    private String message = "";
    private String aims = "";
    private double x = 0;
    private double y = 0;
    private double z = 0;


    public Holographic(){

    }

    public void setHD(LivingEntity self, LivingEntity target, String firstString,String taskID) {
        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.firstString = firstString;
        if(target == null){
            this.target = (LivingEntity) new EntityFind().getTarget(self,10);

        }
        if(setOther()){
            return;
        }

    }

    public boolean setOther(){
        boolean ret = false;
        aims = "";
        function = "";
        message = "";
        x = 0;
        y = 0;
        z = 0;
        for(String string : new StringFind().getStringMessageList(firstString)){
            if(string.toLowerCase().contains("message=") || string.toLowerCase().contains("m=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    message = new ConversionMain().valueOf(self,target,strings[1]);
                }
            }
        }

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("function=") || string.toLowerCase().contains("fc=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    function = strings[1];
                }
            }

            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("x=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        if(strings[1].contains("&")){
                            x = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                        }else {
                            x = Double.valueOf(strings[1]);
                        }
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("x不是數字");
                    }
                }
            }

            if(string.toLowerCase().contains("y=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        if(strings[1].contains("&")){
                            y = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                        }else {
                            y = Double.valueOf(strings[1]);
                        }
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("y不是數字"+y);
                    }
                }
            }

            if(string.toLowerCase().contains("z=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        if(strings[1].contains("&")){
                            z = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                        }else {
                            z = Double.valueOf(strings[1]);
                        }
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("z不是數字");
                    }
                }
            }
        }

        if(aims.toLowerCase().contains("target")){
            if(target == null){
                ret = true;
            }else {
                createLocation = target.getLocation().add(x,y,z);
            }
        }else if(aims.toLowerCase().contains("self")){
            createLocation = self.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("world")){
            createLocation = new Location(self.getWorld(),x,y,z);
        }else {
            createLocation = createLocation.add(x,y,z);
        }
        if(!(ret)){
            if(function.toLowerCase().contains("create") && hologram == null){
                createHD();
            }
            if(function.toLowerCase().contains("addtextline") && hologram != null){
                addLineHD();
            }
            if(function.toLowerCase().contains("removetextline") && hologram != null){
                removeLineHD();
            }
            if(function.toLowerCase().contains("teleport") && hologram != null){
                teleportHD();
            }
            if(function.toLowerCase().contains("delete") && hologram != null){
                deleteHD();
            }
        }

        return ret;
    }


    public void createHD(){
        hologram = HologramsAPI.createHologram(cd, createLocation);
        hologram.appendTextLine(message);
    }

    public void addLineHD(){
        hologram.appendTextLine(message);
    }

    public void removeLineHD(){
        try{
            hologram.removeLine(Integer.valueOf(message));
        }catch (NumberFormatException exception){
            cd.getLogger().info("移除內容錯誤");
        }
    }

    public void teleportHD(){
        hologram.teleport(createLocation);
    }

    public void deleteHD(){
        new ClearAction(taskID);
        if(ActionManager.getJudgment2_Holographic2_Map().get(taskID) != null){
            ActionManager.getJudgment2_Holographic2_Map().remove(taskID);
        }
        hologram.delete();
    }

    public Hologram getHologram() {
        return hologram;
    }
}
