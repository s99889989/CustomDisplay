package com.daxton.customdisplay;

import com.comphenix.protocol.ProtocolLibrary;
import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.set.PlayerAttributeCore;
import com.daxton.customdisplay.command.MainCommand;
import com.daxton.customdisplay.command.TabCommand;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.listener.attributeplus.*;
import com.daxton.customdisplay.listener.bukkit.*;
import com.daxton.customdisplay.listener.mmolib.CDMMOLibListener;
import com.daxton.customdisplay.listener.crackshot.CrackShotListener;
import com.daxton.customdisplay.listener.customdisplay.*;
import com.daxton.customdisplay.listener.mmolib.MMOCoreListener;
import com.daxton.customdisplay.listener.mmolib.MMOLibListener;
import com.daxton.customdisplay.listener.mythicmobs.ModelEngineListener;
import com.daxton.customdisplay.listener.mythicLib.CDMythicLibListener;
import com.daxton.customdisplay.listener.mythicLib.MythicLibListener;
import com.daxton.customdisplay.listener.mythicLib.MythicLib_MMOCore_Listener;
import com.daxton.customdisplay.listener.mythicLib.MythicLib_SkillAPI_Listener;
import com.daxton.customdisplay.listener.mythicmobs.MythicMobSpawnListener;
import com.daxton.customdisplay.listener.protocollib.PackListener;
import com.daxton.customdisplay.listener.skillapi.SkillAPIListener;
import com.daxton.customdisplay.listener.mmolib.SkillAPI_MMOLib_Listener;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.DiscordManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import com.daxton.customdisplay.otherfunctions.CreatJson;
import com.daxton.customdisplay.task.ClearAction;
import discord4j.core.DiscordClientBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Level;

public final class CustomDisplay extends JavaPlugin implements Listener {

    public static CustomDisplay customDisplay;

    private ConfigManager configManager;

    public static final int IDX = 233;
    public static final String channel = "msgtutor:test";



    @Override
    public void onEnable() {


        getServer().getMessenger().registerIncomingPluginChannel(this, channel,
                (channel, player, message) ->
                        System.out.println("awsl " + read(message)));
        getServer().getMessenger().registerOutgoingPluginChannel(this, channel);




        customDisplay = this;

        if (Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays") == null) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** CustomDisplay will be disabled. ***");
            getLogger().severe("*** HolographicDisplays未安裝或未啟用。 ***");
            getLogger().severe("*** CustomDisplay將被卸載。 ***");
            this.setEnabled(false);
            return;
        }else {
            getLogger().info(ChatColor.GREEN+"Loaded HolographicDisplays");
        }

        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null){
            getLogger().severe("*** PlaceholderAPI is not installed or not enabled. ***");
            getLogger().severe("*** CustomDisplay will be disabled. ***");
            getLogger().severe("*** PlaceholderAPI未安裝或未啟用。 ***");
            getLogger().severe("*** CustomDisplay將被卸載。 ***");
            this.setEnabled(false);
            return;
        }else {
            getLogger().info(ChatColor.GREEN+"Loaded PlaceholderAPI");
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null){
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

        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
            Bukkit.getPluginManager().registerEvents(new MythicMobSpawnListener(),customDisplay);
            getLogger().info(ChatColor.GREEN+"Loaded MythicMobs");
            if (Bukkit.getServer().getPluginManager().getPlugin("ModelEngine") != null){
                Bukkit.getPluginManager().registerEvents(new ModelEngineListener(),customDisplay);
                getLogger().info(ChatColor.GREEN+"Loaded ModelEngine");
            }
        }

        ActionManager.protocolManager = ProtocolLibrary.getProtocolManager();
        configManager = new ConfigManager(customDisplay);
        Bukkit.getPluginCommand("customdisplay").setExecutor(new MainCommand());
        Bukkit.getPluginCommand("customdisplay").setTabCompleter(new TabCommand());
        /**傷害判斷的核心插件.**/
        AttackCore();
        Bukkit.getPluginManager().registerEvents(new AttackedListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),customDisplay);
        Bukkit.getPluginManager().registerEvents(new MobListener(),customDisplay);



        FileConfiguration fileConfig = getConfigManager().config;

        //設定動作
        new SetActionMap();

        String attackCore = fileConfig.getString("AttackCore");
        if(attackCore.toLowerCase().contains("customcore")){
            /**設置核心公式字串**/
            new PlayerAttributeCore().setFormula();
        }

        boolean discordEnable = fileConfig.getBoolean("Discord.enable");
        if(discordEnable){
            String token = fileConfig.getString("Discord.token");
            DiscordManager.client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();

        }

        //建立Json檔案
        CreatJson.createMain();
        CreatJson.createAll();

    }

    public void AttackCore(){
        String attackCore = configManager.config.getString("AttackCore");
        switch (attackCore.toLowerCase()){
            case "mmolib":
                if(Bukkit.getServer().getPluginManager().getPlugin("MMOLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MMOLibListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MMOLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mythiclib":
                if(Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MythicLibListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MythicLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "skillapi_mmolib":
                if(Bukkit.getServer().getPluginManager().getPlugin("SkillAPI") != null && Bukkit.getServer().getPluginManager().getPlugin("MMOLib") != null){
                    Bukkit.getPluginManager().registerEvents(new SkillAPI_MMOLib_Listener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: SkillAPI_MMOLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mythiclib_mmolib":
                if(Bukkit.getServer().getPluginManager().getPlugin("SkillAPI") != null && Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MythicLib_SkillAPI_Listener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MythicLib_SkillAPI");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mmocore":
                if(Bukkit.getServer().getPluginManager().getPlugin("MMOCore") != null && Bukkit.getServer().getPluginManager().getPlugin("MMOLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MMOCoreListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MMOCore");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mythiclib_mmocore":
                if(Bukkit.getServer().getPluginManager().getPlugin("MMOCore") != null && Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MythicLib_MMOCore_Listener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MythicLib_MMOCore");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "cdmmolib":
                if(Bukkit.getServer().getPluginManager().getPlugin("MMOLib") != null){
                    Bukkit.getPluginManager().registerEvents(new CDMMOLibListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: CDMMOLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "cdmythiclib":
                if(Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null){
                    Bukkit.getPluginManager().registerEvents(new CDMythicLibListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: CDMythicLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "attributeplus":
                if(Bukkit.getServer().getPluginManager().getPlugin("AttributePlus") != null){
                    Bukkit.getPluginManager().registerEvents(new AttributePlusListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: AttributePlus");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "skillapi":
                if(Bukkit.getServer().getPluginManager().getPlugin("SkillAPI") != null){
                    Bukkit.getPluginManager().registerEvents(new SkillAPIListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: SkillAPI");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "crackshot":
                if(Bukkit.getServer().getPluginManager().getPlugin("CrackShot") != null){
                    Bukkit.getPluginManager().registerEvents(new CrackShotListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: CrackShot");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "customcore":
                Bukkit.getPluginManager().registerEvents(new MainListener(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new MeleePhysicalAttack(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new RangePhysicalAttack(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new DamagerNumberListener(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new EquipmentListener(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new MagicAttack(),customDisplay);
                getLogger().info(ChatColor.GREEN+"Loaded AttackCore: CustomCore");
                break;
            case "default":
            default:
                Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
        }

    }

    public void load(){
        //儲存物品資訊
        SaveConfig.saveItemFile();
        configManager = new ConfigManager(customDisplay);

        mapReload();
    }
    public void mapReload(){

        //建立Json檔案
        CreatJson.createMain();
        CreatJson.createAll();

        /**設置核心公式字串**/
        new PlayerAttributeCore().setFormula();

        /**清除所有動作**/
        new ClearAction().all();
        new ClearAction().all2();

        //設定動作
        new SetActionMap();

        /**重新讀取玩家資料**/
        for(Player player : Bukkit.getOnlinePlayers()){

            UUID playerUUID = player.getUniqueId();
            PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
            if(playerData != null){
                /**玩家資料**/
                String attackCore = getConfigManager().config.getString("AttackCore");
                if(attackCore.toLowerCase().contains("customcore")){
                    playerData.getBukkitRunnable().cancel();
                }
                /**儲存人物資料**/
                new SaveConfig().setConfig(player);
                PlayerManager.getPlayerDataMap().remove(playerUUID);
            }

            if(PlayerManager.getPlayerDataMap().get(playerUUID) == null){
                /**玩家資料**/
                PlayerManager.getPlayerDataMap().put(playerUUID,new PlayerData(player));
            }

        }

    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()){
            UUID playerUUID = player.getUniqueId();
            PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
            if(playerData != null){
                /**儲存人物資料**/
                new SaveConfig().setConfig(player);
                //PlayerDataMap.getPlayerDataMap().get(playerUUID).removeAttribute(player);
                PlayerManager.getPlayerDataMap().remove(playerUUID);
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
