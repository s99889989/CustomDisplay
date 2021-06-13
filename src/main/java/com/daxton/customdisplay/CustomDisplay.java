package com.daxton.customdisplay;

import com.comphenix.protocol.ProtocolLibrary;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.gui.SetGui;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.command.MainCommand;
import com.daxton.customdisplay.command.TabCommand;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.listener.bukkit.*;
import com.daxton.customdisplay.listener.mythicmobs.ModelEngineListener;
import com.daxton.customdisplay.listener.mythicmobs.MythicMobSpawnListener;
import com.daxton.customdisplay.listener.protocollib.PackListener;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.DiscordManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import com.daxton.customdisplay.otherfunctions.CreatJson;
import com.daxton.customdisplay.otherfunctions.CreateFontJson;
import com.daxton.customdisplay.task.ClearAction;
import com.daxton.customdisplay.task.RunTask;
import discord4j.core.DiscordClientBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public final class CustomDisplay extends JavaPlugin implements Listener {

    public static CustomDisplay customDisplay;

    private ConfigManager configManager;

    public static final int IDX = 233;
    public static final String channel = "msgtutor:test";


    @Override
    public void onEnable() {

        customDisplay = this;

        if(!LoadOtherPlugin.load(customDisplay)){
            this.setEnabled(false);
            return;
        }

        //////////////////////////////////////////////
        //模組控制通道
        getServer().getMessenger().registerIncomingPluginChannel(this, channel,
                (channel, player, message) ->
                        System.out.println("awsl " + read(message)));
        getServer().getMessenger().registerOutgoingPluginChannel(this, channel);
        //ProtocolLib
        ActionManager.protocolManager = ProtocolLibrary.getProtocolManager();
        //設定檔
        configManager = new ConfigManager(customDisplay);
        //指令
        Bukkit.getPluginCommand("customdisplay").setExecutor(new MainCommand());
        Bukkit.getPluginCommand("customdisplay").setTabCompleter(new TabCommand());
        //傷害判斷的核心插件
        AttackCore.setCore(configManager);
        //基礎監聽
        Bukkit.getPluginManager().registerEvents(new AttackedListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new MobListener(),customDisplay);

        //設定動作
        new SetActionMap();
        //設定GUI名稱
        SetGui.setGuiName();
        SetGui.setButtomMap();
        //設定DC連接
        FileConfiguration fileConfig = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean discordEnable = fileConfig.getBoolean("Discord.enable");
        if(discordEnable){

            String token = fileConfig.getString("Discord.token");
            if(token != null){

                DiscordManager.client = DiscordClientBuilder.create(token)
                        .build()
                        .login()
                        .block();
            }


        }

        //建立Json檔案
        CreatJson.createMain();
        CreatJson.createAll();
        //持續執行的動作  例如回魔
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        String skill = fileConfiguration.getString("AttackCore");
        if(skill != null && skill.toLowerCase().contains("customcore")){
            RunTask.run20(customDisplay);
        }


    }

    public void setCore(){

    }


    public void load(){
        //儲存物品資訊
        SaveConfig.saveItemFile();
        configManager = new ConfigManager(customDisplay);
        //CreateFontJson.createMain();
        mapReload();
    }
    public void mapReload(){

        //建立Json檔案
        CreatJson.createMain();
        CreatJson.createAll();

        //清除所有動作
        ClearAction.all();

        //設定動作
        new SetActionMap();
        //設定GUI名稱
        SetGui.setGuiName();
        SetGui.setButtomMap();

        //重新讀取玩家資料
        for(Player player : Bukkit.getOnlinePlayers()){
            String uuidString = player.getUniqueId().toString();
            if(PlayerManager.player_Data_Map.get(uuidString) != null){
                PlayerManager.player_Data_Map.get(uuidString).savePlayerFile();
            }

            PlayerManager.player_Data_Map.put(uuidString, new PlayerData2(player));
        }


    }

    @Override
    public void onDisable() {

        //儲存人物資料
        for(Player player : Bukkit.getOnlinePlayers()){
            String uuidString = player.getUniqueId().toString();
            if(PlayerManager.player_Data_Map.get(uuidString) != null){
                PlayerManager.player_Data_Map.get(uuidString).savePlayerFile();
                PlayerManager.player_Data_Map.remove(uuidString);
            }
        }

        //儲存物品資訊
        SaveConfig.saveItemFile();

        getLogger().info("Plugin disable");
        getLogger().info("插件卸載");
    }


    /**複寫saveResource的存檔位置，因為位置是讀取jar內的，所以要去除resource/**/
    public void saveResource(String resourcePath,String savePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');

        String res = savePath;

        InputStream in = getResource(resourcePath);

        //****
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

    public String read(byte[] array) {
        ByteBuf buf = Unpooled.wrappedBuffer(array);
        if (buf.readUnsignedByte() == IDX) {
            return buf.toString(StandardCharsets.UTF_8);
        } else throw new RuntimeException();
    }

    public void send(Player player, String msg) {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = Unpooled.buffer(bytes.length + 1);
        buf.writeByte(IDX);
        buf.writeBytes(bytes);
        player.sendPluginMessage(this, channel, buf.array());
    }

    public void sendMessage(Player player, String message){
        try {
            Class<? extends CommandSender> senderClass = player.getClass();
            Method addChannel = senderClass.getDeclaredMethod("addChannel", String.class);
            addChannel.setAccessible(true);
            addChannel.invoke(player, channel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bukkit.getScheduler().runTaskLater(this, () -> send(player, message), 100);
    }

    public static CustomDisplay getCustomDisplay() {
        return customDisplay;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static int getIDX() {
        return IDX;
    }

    public String getChannel() {
        return channel;
    }
}
