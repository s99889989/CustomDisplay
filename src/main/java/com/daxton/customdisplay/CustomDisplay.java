package com.daxton.customdisplay;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.command.CustomDisplayCommand;
import com.daxton.customdisplay.listener.DamageListener;
import com.daxton.customdisplay.listener.EntityListener;
import com.daxton.customdisplay.listener.PlayerListener;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.manager.HDMapManager;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.holographicdisplays.AnimalHD;
import com.daxton.customdisplay.task.holographicdisplays.MonsterHD;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;
import com.daxton.customdisplay.task.player.OnTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.UUID;
import java.util.logging.Level;

public final class CustomDisplay extends JavaPlugin {

    public static CustomDisplay customDisplay;

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** CustomDisplay will be disabled. ***");
            getLogger().severe("*** HolographicDisplays未安裝或未啟用。 ***");
            getLogger().severe("*** CustomDisplay將被卸載。 ***");
            this.setEnabled(false);
            return;
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            getLogger().severe("*** PlaceholderAPI is not installed or not enabled. ***");
            getLogger().severe("*** CustomDisplay will be disabled. ***");
            getLogger().severe("*** PlaceholderAPI未安裝或未啟用。 ***");
            getLogger().severe("*** CustomDisplay將被卸載。 ***");
            this.setEnabled(false);
            return;
        }
        customDisplay = this;
        load();
        Bukkit.getPluginCommand("customdisplay").setExecutor(new CustomDisplayCommand());

    }


    public void load(){
        configManager = new ConfigManager(customDisplay);
        Bukkit.getPluginManager().registerEvents(new DamageListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new EntityListener(),customDisplay);
        mapReload();
//        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")){
//            Bukkit.getPluginManager().registerEvents(new PackListener(),customDisplay);
//        }
    }
    public void mapReload(){

        /**移除OnTimer**/
        for(Player player : Bukkit.getOnlinePlayers()){
            String playerName = player.getName();
            UUID uuid = player.getUniqueId();
            PlayerData playerDataUse = PlayerDataMap.getPlayerDataMap().get(uuid);
            int i = 0;
            if(playerDataUse != null){
                for(String string : playerDataUse.getPlayerActionList()) {
                    i++;
                    playerName = playerName + i;
                    OnTimer onTimer = TriggerManager.getOnTimerMap().get(playerName);
                    if (onTimer != null) {
                        onTimer.getBukkitRunnable().cancel();
                        TriggerManager.getOnTimerMap().remove(playerName);
                    }
                }
            }
        }

        /**移除玩家資料**/

        for(Player player : Bukkit.getOnlinePlayers()){
            UUID uuid = player.getUniqueId();
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
            if(playerData != null){
                PlayerDataMap.getPlayerDataMap().remove(uuid);
            }
        }




        for(PlayerHD playerHD : HDMapManager.getPlayerHDMap().values()){
            playerHD.getHologram().delete();
            playerHD.getBukkitRunnable().cancel();
            HDMapManager.getPlayerHDMap().clear();
        }
        for(MonsterHD monsterHD : HDMapManager.getMonsterHDMap().values()){
            monsterHD.getHologram().delete();
            monsterHD.getHealthMap().clear();
            monsterHD.getLocationMap().clear();
            monsterHD.getBukkitRunnable().cancel();
            HDMapManager.getMonsterHDMap().clear();
        }
        for(AnimalHD animalHD : HDMapManager.getAnimalHDMap().values()){
            animalHD.getHologram().delete();
            animalHD.getHealthMap().clear();
            animalHD.getLocationMap().clear();
            animalHD.getBukkitRunnable().cancel();
            HDMapManager.getAnimalHDMap().clear();
        }

        /**讀取玩家自訂設定檔**/
        for(Player player : Bukkit.getOnlinePlayers()){
            UUID uuid = player.getUniqueId();
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
            if(playerData == null){
                PlayerDataMap.getPlayerDataMap().put(uuid,new PlayerData(player));
            }
        }


        /**增加OnTimer**/
        for(Player player : Bukkit.getOnlinePlayers()){
            UUID uuid = player.getUniqueId();
            PlayerData playerDataUse = PlayerDataMap.getPlayerDataMap().get(uuid);
            int i = 0;
            String playerName = player.getName();
            if(playerDataUse != null){
                for(String string : playerDataUse.getPlayerActionList()){
                    i++;
                    if(string.contains("onTimer:")){
                        OnTimer onTimer = TriggerManager.getOnTimerMap().get(uuid);
                        if(onTimer == null){
                            playerName = playerName + i;
                            TriggerManager.getOnTimerMap().put(playerName,new OnTimer(player,string));
                        }
                    }
                }
            }

        }


    }


    @Override
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');

        String res = resourcePath.replace("resource/","");

        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + super.getFile());
        }

        File outFile = new File(super.getDataFolder(), res);

        int lastIndex = res.lastIndexOf('/');
        File outDir = new File(super.getDataFolder(), res.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                File outFileF = new File(super.getDataFolder(), res);
                OutputStream out = new FileOutputStream(outFileF);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                super.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            super.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }


    public static CustomDisplay getCustomDisplay() {
        return customDisplay;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public void onDisable() {
        for(PlayerHD playerHD : HDMapManager.getPlayerHDMap().values()){
            playerHD.getHologram().delete();
            playerHD.getBukkitRunnable().cancel();
            HDMapManager.getPlayerHDMap().clear();
        }
        for(MonsterHD monsterHD : HDMapManager.getMonsterHDMap().values()){
            monsterHD.getHologram().delete();
            monsterHD.getHealthMap().clear();
            monsterHD.getLocationMap().clear();
            monsterHD.getBukkitRunnable().cancel();
            HDMapManager.getMonsterHDMap().clear();
        }
        for(AnimalHD animalHD : HDMapManager.getAnimalHDMap().values()){
            animalHD.getHologram().delete();
            animalHD.getHealthMap().clear();
            animalHD.getLocationMap().clear();
            animalHD.getBukkitRunnable().cancel();
            HDMapManager.getAnimalHDMap().clear();
        }

        getLogger().info("Plugin disable");
        getLogger().info("插件卸載");
    }
}
