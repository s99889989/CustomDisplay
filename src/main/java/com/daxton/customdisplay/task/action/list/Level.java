package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private List<Entity> EntityList = new ArrayList<>();

    private String type = "沒有";
    private String function = "";
    private int amount = 1;

    private String aims = "";
    private int distance = 1;

    public Level(){

    }

    public void setLevel(LivingEntity self, LivingEntity target, String firstString, String taskID){
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

            if(allString.toLowerCase().contains("@=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    aims = strings[1].replace(" ","");
                }else if(strings.length == 3){
                    aims = strings[1].replace(" ","");
                    try{
                        distance = Integer.valueOf(strings[2].replace(" ",""));
                    }catch (NumberFormatException exception){
                        cd.getLogger().info(strings[2]+"不是數字");
                    }
                }
            }

        }

        EntityList.add(self);


        if(!(EntityList.isEmpty())){
            for(Entity entity : EntityList){
                if(entity instanceof Player){
                    if(type.contains("沒有")){
                        if(function.toLowerCase().contains("set")){
                            levelSet(((Player) entity).getPlayer());
                        }else {
                            levelAdd(((Player) entity).getPlayer());
                        }
                    }else {
                        if(function.toLowerCase().contains("set")){
                            levelOtherSet(((Player) entity).getPlayer());
                        }else {
                            levelOtherAdd(((Player) entity).getPlayer());
                        }
                    }

                }
            }
        }
    }

    public void levelSet(Player player){

        player.setLevel(amount);

    }

    public void levelAdd(Player player){

        player.giveExpLevels(amount);

    }

    public void levelOtherSet(Player player){
        String playerUUIDString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+ playerUUIDString +".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
        int nowLevel = playerConfig.getInt(playerUUIDString +".Level."+type+"_level");
        playerConfig.set(playerUUIDString +".Level."+type+"_level",amount);
        if(amount > nowLevel){
            int count = amount - nowLevel;
            for(int i = 0; i < count;i++){
                PlaceholderManager.getCd_Placeholder_Map().put(playerUUIDString+"<cd_up_level_type>",type);
                new PlayerTrigger(player).onLevelUp(player);
            }
        }else {
            int count = nowLevel - amount;
            for(int i = 0; i < count;i++){
                PlaceholderManager.getCd_Placeholder_Map().put(playerUUIDString+"<cd_down_level_type>",type);
                new PlayerTrigger(player).onLevelDown(player);
            }
        }

        try {
            playerConfig.save(playerFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    public void levelOtherAdd(Player player){
        String playerUUIDString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+ playerUUIDString +".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

        int nowLevel = playerConfig.getInt(playerUUIDString +".Level."+type+"_level");
        int newLevel = nowLevel + amount;
        playerConfig.set(playerUUIDString +".Level."+type+"_level",newLevel);
        if(amount > 0){
            for(int i = 0; i < amount ;i++){
                PlaceholderManager.getCd_Placeholder_Map().put(playerUUIDString+"<cd_up_level_type>",type);
                new PlayerTrigger(player).onLevelUp(player);
            }
        }else {
            for(int i = 0; i < amount*-1 ;i++){
                PlaceholderManager.getCd_Placeholder_Map().put(playerUUIDString+"<cd_down_level_type>",type);
                new PlayerTrigger(player).onLevelDown(player);
            }
        }
        try {
            playerConfig.save(playerFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

}
