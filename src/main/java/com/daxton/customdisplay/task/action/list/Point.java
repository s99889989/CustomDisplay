package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Point {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private List<Entity> EntityList = new ArrayList<>();

    private String type = "沒有";
    private String function = "";
    private int amount = 1;

    public Point(){

    }

    public void setPoint(LivingEntity self, LivingEntity target, String firstString, String taskID){

        this.self = self;
        this.target = target;
        this.firstString = firstString;
        setOther();
    }

    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("type=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    type = strings[1];

                }
            }

            if(allString.toLowerCase().contains("function=") || allString.toLowerCase().contains("fc=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    function = strings[1];

                }
            }

            if(allString.toLowerCase().contains("amount=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    try{
                        amount = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("amount=內只能放整數數字");
                    }
                }
            }

        }

        EntityList.add(self);

        if(!(EntityList.isEmpty())){
            for(Entity entity : EntityList){
                if(entity instanceof Player){
                    if(type.contains("沒有")){

                    }else {
                        addPoint(((Player) entity).getPlayer());
//                        if(function.toLowerCase().contains("set")){
//                            setPoint(((Player) entity).getPlayer());
//                        }else {
//
//                        }
                    }
                }
            }
        }

    }

    public void addPoint(Player player){
        String playerUUIDString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+ playerUUIDString +".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);


        int nowLastPoint = playerConfig.getInt(playerUUIDString +".Point."+type+"_max");
        int addLastPoint = nowLastPoint+amount;
        if(addLastPoint < 0){
            //playerConfig.set(playerUUIDString +".Point."+type+"_last",0);
        }else {
            playerConfig.set(playerUUIDString +".Point."+type+"_last",addLastPoint);
        }

        int nowMaxPoint = playerConfig.getInt(playerUUIDString +".Point."+type+"_max");
        int addMaxPoint = nowMaxPoint+amount;
        if(addLastPoint < 0){
            //playerConfig.set(playerUUIDString +".Point."+type+"_max",0);
        }else {
            playerConfig.set(playerUUIDString +".Point."+type+"_max",addMaxPoint);
        }


        try {
            playerConfig.save(playerFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        nowLastPoint = playerConfig.getInt(playerUUIDString +".Point."+type+"_max");
        nowMaxPoint = playerConfig.getInt(playerUUIDString +".Point."+type+"_max");
        //player.sendMessage(type+"目前點數: "+nowMaxPoint+" "+type+"剩餘點數: "+nowLastPoint);
    }

    public void setPoint(Player player){
        String playerUUIDString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+ playerUUIDString +".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

        playerConfig.set(playerUUIDString +".Point."+type+"_max",amount);
        playerConfig.set(playerUUIDString +".Point."+type+"_last",amount);

    }

}
