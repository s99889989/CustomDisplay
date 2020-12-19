package com.daxton.customdisplay;

import com.comphenix.protocol.ProtocolLibrary;
import com.daxton.customdisplay.api.mobs.MobData;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.command.CustomDisplayCommand;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.listener.attributeplus.AttrAttackListener;
import com.daxton.customdisplay.listener.mmocore.SpellCastListener;
import com.daxton.customdisplay.listener.customdisplay.*;
import com.daxton.customdisplay.listener.mmolib.MMOAttackListener2;
import com.daxton.customdisplay.listener.mythicmobs.MythicMobSpawnListener;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.ClearAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        customDisplay = this;
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** CustomDisplay will be disabled. ***");
            getLogger().severe("*** HolographicDisplays未安裝或未啟用。 ***");
            getLogger().severe("*** CustomDisplay將被卸載。 ***");
            this.setEnabled(false);
            return;
        }else {
            getLogger().info(ChatColor.GREEN+"Loaded HolographicDisplays");
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            getLogger().severe("*** PlaceholderAPI is not installed or not enabled. ***");
            getLogger().severe("*** CustomDisplay will be disabled. ***");
            getLogger().severe("*** PlaceholderAPI未安裝或未啟用。 ***");
            getLogger().severe("*** CustomDisplay將被卸載。 ***");
            this.setEnabled(false);
            return;
        }else {
            getLogger().info(ChatColor.GREEN+"Loaded PlaceholderAPI");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")){
            getLogger().info(ChatColor.GREEN+"Loaded ProtocolLib");
            Bukkit.getPluginManager().registerEvents(new PackListener(),customDisplay);
        }else {
            getLogger().severe("*** ProtocolLib is not installed or not enabled. ***");
            getLogger().severe("*** CustomDisplay will be disabled. ***");
            getLogger().severe("*** ProtocolLib未安裝或未啟用。 ***");
            getLogger().severe("*** ProtocolLib將被卸載。 ***");
            this.setEnabled(false);
            return;
        }

        configManager = new ConfigManager(customDisplay);
        Bukkit.getPluginCommand("customdisplay").setExecutor(new CustomDisplayCommand());

        if(Bukkit.getPluginManager().isPluginEnabled("MMOLib")){
            Bukkit.getPluginManager().registerEvents(new MMOAttackListener2(),customDisplay);
            getLogger().info(ChatColor.GREEN+"Loaded MMOLib");
        }else if(Bukkit.getPluginManager().isPluginEnabled("AttributePlus")){
            Bukkit.getPluginManager().registerEvents(new AttrAttackListener(),customDisplay);
            getLogger().info(ChatColor.GREEN+"Loaded AttributePlus");
        }else {
            Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
        }
        Bukkit.getPluginManager().registerEvents(new AttackedListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new JoinListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new QuizListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new MobListener(),customDisplay);

        if (Bukkit.getPluginManager().isPluginEnabled("MMOCore")){
            getLogger().info(ChatColor.GREEN+"Loaded MMOCore");
            Bukkit.getPluginManager().registerEvents(new SpellCastListener(),customDisplay);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")){
            getLogger().info(ChatColor.GREEN+"Loaded MythicMobs");
            Bukkit.getPluginManager().registerEvents(new MythicMobSpawnListener(),customDisplay);
        }
        ActionManager2.protocolManager = ProtocolLibrary.getProtocolManager();

    }


    public void load(){
        configManager = new ConfigManager(customDisplay);
        mapReload();
    }
    public void mapReload(){

        new ClearAction();

        if(!(MobManager.getMob_Data_Map().isEmpty())){
            MobManager.getMob_Data_Map().clear();
        }


        /**重新讀取玩家資料**/
        for(Player player : Bukkit.getOnlinePlayers()){
            UUID playerUUID = player.getUniqueId();
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
            if(playerData != null){
                /**玩家資料**/
                PlayerDataMap.getPlayerDataMap().remove(playerUUID);
            }

            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) == null){
                /**玩家資料**/
                PlayerDataMap.getPlayerDataMap().put(playerUUID,new PlayerData(player));
            }

        }

    }

    /**複寫saveResource的存檔位置，因為位置是讀取jar內的，所以要去除resource/**/
    public void saveResource(String resourcePath,String savePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');

        String res = savePath;

        InputStream in = getResource(resourcePath);

        /****/
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
        getLogger().info("Plugin disable");
        getLogger().info("插件卸載");
    }
}
