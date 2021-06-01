package com.daxton.customdisplay;

import com.daxton.customdisplay.listener.mythicmobs.ModelEngineListener;
import com.daxton.customdisplay.listener.mythicmobs.MythicMobSpawnListener;
import com.daxton.customdisplay.listener.protocollib.PackListener;
import com.daxton.customdisplay.manager.player.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LoadOtherPlugin {

    public static boolean load(CustomDisplay customDisplay){

        if (Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays") == null) {
            customDisplay.getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            customDisplay.getLogger().severe("*** CustomDisplay will be disabled. ***");
            customDisplay.getLogger().severe("*** HolographicDisplays未安裝或未啟用。 ***");
            customDisplay.getLogger().severe("*** CustomDisplay將被卸載。 ***");
            //this.setEnabled(false);
            return false;
        }else {
            customDisplay.getLogger().info(ChatColor.GREEN+"Loaded HolographicDisplays");
        }

        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null){
            customDisplay.getLogger().severe("*** PlaceholderAPI is not installed or not enabled. ***");
            customDisplay.getLogger().severe("*** CustomDisplay will be disabled. ***");
            customDisplay.getLogger().severe("*** PlaceholderAPI未安裝或未啟用。 ***");
            customDisplay.getLogger().severe("*** CustomDisplay將被卸載。 ***");

            return false;
        }else {
            customDisplay.getLogger().info(ChatColor.GREEN+"Loaded PlaceholderAPI");
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null){
            customDisplay.getLogger().info(ChatColor.GREEN+"Loaded ProtocolLib");
            Bukkit.getPluginManager().registerEvents(new PackListener(),customDisplay);
        }else {
            customDisplay.getLogger().severe("*** ProtocolLib is not installed or not enabled. ***");
            customDisplay.getLogger().severe("*** CustomDisplay will be disabled. ***");
            customDisplay.getLogger().severe("*** ProtocolLib未安裝或未啟用。 ***");
            customDisplay.getLogger().severe("*** ProtocolLib將被卸載。 ***");

            return false;
        }

        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
            Bukkit.getPluginManager().registerEvents(new MythicMobSpawnListener(),customDisplay);
            customDisplay.getLogger().info(ChatColor.GREEN+"Loaded MythicMobs");
            if (Bukkit.getServer().getPluginManager().getPlugin("ModelEngine") != null){
                Bukkit.getPluginManager().registerEvents(new ModelEngineListener(),customDisplay);
                customDisplay.getLogger().info(ChatColor.GREEN+"Loaded ModelEngine");
            }
        }

        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp != null) {
                PlayerManager.econ = rsp.getProvider();
            }
        }
        return true;
    }

}
