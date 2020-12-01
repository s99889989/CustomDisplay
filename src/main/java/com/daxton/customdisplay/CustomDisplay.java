package com.daxton.customdisplay;

import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.command.CustomDisplayCommand;
import com.daxton.customdisplay.listener.EntityListener;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.listener.player.AttackListener;
import com.daxton.customdisplay.listener.player.JoinListener;
import com.daxton.customdisplay.listener.player.QuizListener;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
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
        Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new JoinListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new QuizListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new EntityListener(),customDisplay);

    }


    public void load(){
        configManager = new ConfigManager(customDisplay);
        mapReload();
//        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")){
//            Bukkit.getPluginManager().registerEvents(new PackListener(),customDisplay);
//        }
    }
    public void mapReload(){

        /**重新讀取玩家資料 和 OnTimer狀態**/
        for(Player player : Bukkit.getOnlinePlayers()){
            UUID playerUUID = player.getUniqueId();
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
            if(playerData != null){

                /**OnQuiz**/
                for(String string : playerData.getPlayerActionList()){
                    if(string.toLowerCase().contains("~onjoin")){
                        String uuidActionString = playerUUID.toString()+new StringFind().findActionName(string);
                        if(TriggerManager.getPlayerActionTaskMap().get(uuidActionString) != null){
                            TriggerManager.getJudgment_LoopOne_Map().get(uuidActionString).cancel();
                            TriggerManager.getJudgment_LoopOne_Map().remove(uuidActionString);
                            TriggerManager.getPlayerActionTaskMap().remove(uuidActionString);
                        }
                    }
                }

                /**玩家資料**/
                PlayerDataMap.getPlayerDataMap().remove(playerUUID);
                if(TriggerManager.getJudgment_Holographic_Map().get(playerUUID.toString()) != null){
                    TriggerManager.getJudgment_Holographic_Map().get(playerUUID.toString()).deleteHD();
                }
            }


            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) == null){
                /**玩家資料**/
                PlayerDataMap.getPlayerDataMap().put(playerUUID,new PlayerData(player));
            }
            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                /**OnJoin**/
                for(String string : PlayerDataMap.getPlayerDataMap().get(playerUUID).getPlayerActionList()){
                    if(string.toLowerCase().contains("~onjoin")){
                        String uuidActionString = playerUUID.toString()+new StringFind().findActionName(string);
                        if(TriggerManager.getPlayerActionTaskMap().get(uuidActionString) == null){
                            TriggerManager.getPlayerActionTaskMap().put(uuidActionString,new JudgmentAction());
                            TriggerManager.getPlayerActionTaskMap().get(uuidActionString).execute(player,string,uuidActionString);
                        }
                    }
                }
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
        getLogger().info(""+resourcePath);
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
