package com.daxton.customdisplay;

import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.listener.attributeplus.AttributePlusListener;
import com.daxton.customdisplay.listener.bukkit.AttackListener;
import com.daxton.customdisplay.listener.crackshot.CrackShotListener;
import com.daxton.customdisplay.listener.customcore.*;
import com.daxton.customdisplay.listener.mmolib.CDMMOLibListener;
import com.daxton.customdisplay.listener.mmolib.MMOCoreListener;
import com.daxton.customdisplay.listener.mmolib.MMOLibListener;
import com.daxton.customdisplay.listener.mmolib.SkillAPI_MMOLib_Listener;
import com.daxton.customdisplay.listener.mythicLib.CDMythicLibListener;
import com.daxton.customdisplay.listener.mythicLib.MythicLibListener;
import com.daxton.customdisplay.listener.mythicLib.MythicLib_MMOCore_Listener;
import com.daxton.customdisplay.listener.mythicLib.MythicLib_SkillAPI_Listener;
import com.daxton.customdisplay.listener.skillapi.SkillAPIListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class AttackCore {

    public static void setCore(ConfigManager configManager){
        CustomDisplay customDisplay = CustomDisplay.getCustomDisplay();
        String attackCore = configManager.config.getString("AttackCore");
        switch (attackCore.toLowerCase()){
            case "mmolib":
                if(Bukkit.getServer().getPluginManager().getPlugin("MMOLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MMOLibListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MMOLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mythiclib":
                if(Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MythicLibListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MythicLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "skillapi_mmolib":
                if(Bukkit.getServer().getPluginManager().getPlugin("SkillAPI") != null && Bukkit.getServer().getPluginManager().getPlugin("MMOLib") != null){
                    Bukkit.getPluginManager().registerEvents(new SkillAPI_MMOLib_Listener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: SkillAPI_MMOLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mythiclib_mmolib":
                if(Bukkit.getServer().getPluginManager().getPlugin("SkillAPI") != null && Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MythicLib_SkillAPI_Listener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MythicLib_SkillAPI");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mmocore":
                if(Bukkit.getServer().getPluginManager().getPlugin("MMOCore") != null && Bukkit.getServer().getPluginManager().getPlugin("MMOLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MMOCoreListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MMOCore");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "mythiclib_mmocore":
                if(Bukkit.getServer().getPluginManager().getPlugin("MMOCore") != null && Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null){
                    Bukkit.getPluginManager().registerEvents(new MythicLib_MMOCore_Listener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MythicLib_MMOCore");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "cdmmolib":
                if(Bukkit.getServer().getPluginManager().getPlugin("MMOLib") != null){
                    Bukkit.getPluginManager().registerEvents(new CDMMOLibListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: CDMMOLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "cdmythiclib":
                if(Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null){
                    Bukkit.getPluginManager().registerEvents(new CDMythicLibListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: CDMythicLib");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "attributeplus":
                if(Bukkit.getServer().getPluginManager().getPlugin("AttributePlus") != null){
                    Bukkit.getPluginManager().registerEvents(new AttributePlusListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: AttributePlus");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "skillapi":
                if(Bukkit.getServer().getPluginManager().getPlugin("SkillAPI") != null){
                    Bukkit.getPluginManager().registerEvents(new SkillAPIListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: SkillAPI");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "crackshot":
                if(Bukkit.getServer().getPluginManager().getPlugin("CrackShot") != null){
                    Bukkit.getPluginManager().registerEvents(new CrackShotListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: CrackShot");
                }else {
                    Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                    customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
                }
                break;
            case "customcore":
                Bukkit.getPluginManager().registerEvents(new MainAttack(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new NumberAttack(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new MeleeAttack(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new MagicAttack(),customDisplay);
                Bukkit.getPluginManager().registerEvents(new RangeAttack(),customDisplay);
                customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: CustomCore");
                break;
            case "default":
            default:
                Bukkit.getPluginManager().registerEvents(new AttackListener(),customDisplay);
                customDisplay.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
        }

    }

}
