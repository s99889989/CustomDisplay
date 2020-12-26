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

public class Experience {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private List<Entity> EntityList = new ArrayList<>();

    private String type = "沒有";
    private int amount = 1;

    private String aims = "";
    private int distance = 1;

    public Experience(){

    }

    public void setExp(LivingEntity self, LivingEntity target, String firstString, String taskID){
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
                        expSet(((Player) entity).getPlayer());
                    }else {
                        expOtherSet(((Player) entity).getPlayer());
                    }

                }
            }
        }

    }

    public void expSet(Player player){
        String uuidString = player.getUniqueId().toString();
        if(amount < 0){
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_down_exp_type>","default");
            new PlayerTrigger(player).onExpDown(player);
        }

        player.giveExp(amount);
    }

    public void expOtherSet(Player player){
        String playerUUIDString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+ playerUUIDString +".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

        File levelFilePatch = new File(cd.getDataFolder(),"Class/Level/"+type+".yml");
        FileConfiguration levelConfig = YamlConfiguration.loadConfiguration(levelFilePatch);


        int nowLevel = playerConfig.getInt(playerUUIDString +".Level."+type+"_level");
        int nowExp = playerConfig.getInt(playerUUIDString +".Level."+type+"_exp");
        int needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
        int nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));
        int beforeExp = levelConfig.getInt("Exp-Amount."+(nowLevel-1));

        int newExp = 0;
        if(nextExp != 0){
            newExp = nowExp + amount;
            playerConfig.set(playerUUIDString +".Level."+type+"_exp",newExp);
            try {
                playerConfig.save(playerFilePatch);
            }catch (Exception exception){
                exception.printStackTrace();
            }
            if(amount < 0){
                PlaceholderManager.getCd_Placeholder_Map().put(playerUUIDString+"<cd_down_exp_type>",type);
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(playerUUIDString+"<cd_up_exp_type>",type);
            }
            nowExp = playerConfig.getInt(playerUUIDString +".Level."+type+"_exp");
            //player.sendMessage("目前等級: "+nowLevel+" 目前經驗: "+nowExp+"/"+needExp+"增加: "+amount);
        }

        while (beforeExp != 0 && newExp < 0){
            nowLevel = nowLevel - 1;
            needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
            newExp = newExp + needExp;
            playerConfig.set(playerUUIDString +".Level."+type+"_exp",newExp);
            playerConfig.set(playerUUIDString +".Level."+type+"_level",nowLevel);
            try {
                playerConfig.save(playerFilePatch);
            }catch (Exception exception){
                exception.printStackTrace();
            }
            PlaceholderManager.getCd_Placeholder_Map().put(playerUUIDString+"<cd_down_level_type>",type);
        }


        while(nextExp != 0 && newExp >= needExp){
            newExp = newExp - needExp;
            nowLevel = nowLevel + 1;
            playerConfig.set(playerUUIDString +".Level."+type+"_exp",newExp);
            playerConfig.set(playerUUIDString +".Level."+type+"_level",nowLevel);
            needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
            nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));

            //player.sendMessage("升級目前等級: "+nowLevel+" 目前經驗:"+ newExp+"/"+needExp);
            try {
                playerConfig.save(playerFilePatch);
            }catch (Exception exception){
                exception.printStackTrace();
            }
            PlaceholderManager.getCd_Placeholder_Map().put(playerUUIDString+"<cd_up_level_type>",type);
            new PlayerTrigger(player).onLevelUp(player);
        }


    }


}
