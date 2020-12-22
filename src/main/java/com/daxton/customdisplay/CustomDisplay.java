package com.daxton.customdisplay;

import com.comphenix.protocol.ProtocolLibrary;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.command.CustomDisplayCommand;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.listener.attributeplus.*;
import com.daxton.customdisplay.listener.bukkit.*;
import com.daxton.customdisplay.listener.mmocore.MMOCoreListener;
import com.daxton.customdisplay.listener.mmolib.MMOLibListener;
import com.daxton.customdisplay.listener.mythicmobs.MythicMobSpawnListener;
import com.daxton.customdisplay.listener.protocollib.PackListener;
import com.daxton.customdisplay.listener.skillapi.SkillAPIListener;
import com.daxton.customdisplay.listener.skillapi.SkillAPI_MMOLib_Listener;
import com.daxton.customdisplay.manager.ActionManager;
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
        ActionManager.protocolManager = ProtocolLibrary.getProtocolManager();
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

        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")){
            Bukkit.getPluginManager().registerEvents(new MythicMobSpawnListener(),customDisplay);
            getLogger().info(ChatColor.GREEN+"Loaded MythicMobs");
        }

        configManager = new ConfigManager(customDisplay);
        Bukkit.getPluginCommand("customdisplay").setExecutor(new CustomDisplayCommand());
        /**傷害判斷的核心插件.**/
        AttackCore();
        Bukkit.getPluginManager().registerEvents(new AttackedListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new MobListener(),customDisplay);



    }

    public void AttackCore(){
        String attackCore = configManager.config.getString("AttackCore");
        switch (attackCore.toLowerCase()){
            case "mmolib":
                if(Bukkit.getPluginManager().isPluginEnabled("MMOLib")){
                    Bukkit.getPluginManager().registerEvents(new MMOLibListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MMOLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "skillapi_mmolib":
                if(Bukkit.getPluginManager().isPluginEnabled("SkillAPI") & Bukkit.getPluginManager().isPluginEnabled("MMOLib")){
                    Bukkit.getPluginManager().registerEvents(new SkillAPI_MMOLib_Listener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: SkillAPI_MMOLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mmocore":
                if(Bukkit.getPluginManager().isPluginEnabled("MMOCore")){
                    Bukkit.getPluginManager().registerEvents(new MMOCoreListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MMOCore");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "attributeplus":
                if(Bukkit.getPluginManager().isPluginEnabled("AttributePlus")){
                    Bukkit.getPluginManager().registerEvents(new AttributePlusListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: AttributePlus");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "skillapi":
                if(Bukkit.getPluginManager().isPluginEnabled("SkillAPI")){
                    Bukkit.getPluginManager().registerEvents(new SkillAPIListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: SkillAPI");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "default":
            default:
                Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
        }

    }

    public void load(){
        configManager = new ConfigManager(customDisplay);
        mapReload();
    }
    public void mapReload(){


        new ClearAction();

        /**重新讀取玩家資料**/
        for(Player player : Bukkit.getOnlinePlayers()){
            if(configManager.config.getBoolean("HealthScale.enable")){
                player.setHealthScale(configManager.config.getInt("HealthScale.scale"));
            }
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
