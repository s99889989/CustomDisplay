package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MobDeath {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String playerUUIDString = "";
    private List<String> levelNameList = new ArrayList<>();
    private File playerFilePatch;
    private FileConfiguration playerConfig;
    private File levelFilePatch;
    private FileConfiguration levelConfig;

    private String setModType = "";
    private String killType = "";
    private int amonut = 0;


    public MobDeath(){

    }

    public void mythicID(Player player ,String mobID){
        setExpConfig(player);
        for(String levelName : levelNameList){
            int nowLevel = playerConfig.getInt(playerUUIDString +".Level."+levelName+"_level");
            int nowExp = playerConfig.getInt(playerUUIDString +".Level."+levelName+"_exp");
            levelFilePatch = new File(cd.getDataFolder(),"Class/Level/"+levelName+".yml");
            levelConfig = YamlConfiguration.loadConfiguration(levelFilePatch);
            int needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
            String setModType = "";
            String killType = "";
            int amonut = 0;
            for(String get : levelConfig.getStringList("Exp-Get")){
                String first = new StringFind().getAction2(get);

                if(first.toLowerCase().contains("killmob")){
                    for(String string : new StringFind().getStringMessageList(get)){
                        if(string.toLowerCase().contains("mmid=")){
                            String[] strings = string.split("=");
                            if(strings.length == 2){
                                killType = strings[0];
                                setModType = strings[1];
                            }
                        }
                        if(string.toLowerCase().contains("amount=")){
                            String[] strings = string.split("=");
                            if(strings.length == 2){
                                try {
                                    amonut = Integer.valueOf(strings[1]);
                                }catch (NumberFormatException exception){

                                }

                            }

                        }
                    }
                }

                if(killType.toLowerCase().contains("mmid") & mobID.toLowerCase().contains(setModType)){
                    int nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));

                    int newExp = 0;
                    if(nextExp != 0){
                        newExp = nowExp + amonut;
                    }

                    if(nextExp != 0 && newExp >= needExp){
                        newExp = newExp - needExp;
                        nowLevel = nowLevel + 1;
                    }

                    playerConfig.set(playerUUIDString +".Level."+levelName+"_exp",newExp);
                    playerConfig.set(playerUUIDString +".Level."+levelName+"_level",nowLevel);

                    try {
                        playerConfig.save(playerFilePatch);
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                    needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                    int testExp = levelConfig.getInt("Exp-Amount.31");
                    player.sendMessage("目前等級: "+nowLevel+" 目前經驗:"+ newExp+"/"+needExp);
                    setModType = "";
                    killType = "";
                    amonut = 0;
                }

            }

        }
    }

    public void mobType(Player player ,String mobType){
        setExpConfig(player);
        for(String levelName : levelNameList){
            int nowLevel = playerConfig.getInt(playerUUIDString +".Level."+levelName+"_level");
            int nowExp = playerConfig.getInt(playerUUIDString +".Level."+levelName+"_exp");
            levelFilePatch = new File(cd.getDataFolder(),"Class/Level/"+levelName+".yml");
            levelConfig = YamlConfiguration.loadConfiguration(levelFilePatch);
            int needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
            String setModType = "";
            String killType = "";
            int amonut = 0;
            for(String get : levelConfig.getStringList("Exp-Get")){
                String first = new StringFind().getAction2(get);

                if(first.toLowerCase().contains("killmob")){
                    for(String string : new StringFind().getStringMessageList(get)){
                        if(string.toLowerCase().contains("type=")){
                            String[] strings = string.split("=");
                            if(strings.length == 2){
                                killType = strings[0];
                                setModType = strings[1];
                            }
                        }
                        if(string.toLowerCase().contains("amount=")){
                            String[] strings = string.split("=");
                            if(strings.length == 2){
                                try {
                                    amonut = Integer.valueOf(strings[1]);
                                }catch (NumberFormatException exception){

                                }

                            }

                        }
                    }
                }

                if(killType.toLowerCase().contains("type") & mobType.toLowerCase().contains(setModType)){
                    int nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));

                    int newExp = 0;
                    if(nextExp != 0){
                        newExp = nowExp + amonut;
                    }

                    while(nextExp != 0 && newExp >= needExp){
                        newExp = newExp - needExp;
                        nowLevel = nowLevel + 1;
                        playerConfig.set(playerUUIDString +".Level."+levelName+"_exp",newExp);
                        playerConfig.set(playerUUIDString +".Level."+levelName+"_level",nowLevel);
                        needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                        nextExp = levelConfig.getInt("Exp-Amount."+(nowLevel+1));
                        player.sendMessage("目前等級: "+nowLevel+" 目前經驗:"+ newExp+"/"+needExp);
                        try {
                            playerConfig.save(playerFilePatch);
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }

                    }

//                    if(nextExp != 0 && newExp >= needExp){
//                        newExp = newExp - needExp;
//                        nowLevel = nowLevel + 1;
//                    }



                    //new PlayerConfig(player).saveFile();

                    //needExp = levelConfig.getInt("Exp-Amount."+nowLevel);
                    //player.sendMessage("目前等級: "+nowLevel+" 目前經驗:"+ newExp+"/"+needExp);
                    setModType = "";
                    killType = "";
                    amonut = 0;
                }

            }

        }
    }

    public void setExpConfig(Player player){
        playerUUIDString = player.getUniqueId().toString();
        playerFilePatch = new File(cd.getDataFolder(),"Players/"+ playerUUIDString +".yml");
        playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

        ConfigurationSection cs = playerConfig.getConfigurationSection(playerUUIDString +".Level");
        for(String key : cs.getKeys(false)){
            if(key.toLowerCase().contains("_level")){
                String levelName = key.toLowerCase().replace("_level","");
                levelNameList.add(levelName);
            }
        }



    }

}
