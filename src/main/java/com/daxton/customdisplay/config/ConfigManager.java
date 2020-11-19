package com.daxton.customdisplay.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.util.ConfigUtil;
import com.daxton.customdisplay.util.FolderConfigUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    CustomDisplay cd;

    public FileConfiguration config;
    public FileConfiguration attack_config;
    public FileConfiguration entity_top_config;
    public FileConfiguration action_bar_config;
    public FileConfiguration boos_bar_config;
    public FileConfiguration title_config;
    public FileConfiguration language;

    public String config_language;
    public boolean config_attack_display;
    public boolean config_entity_top_display;
    public boolean config_boss_bar_display;
    public boolean config_title_display;
    public boolean config_action_bar_display;

    public boolean attack_display_player_enable;
    public String attack_display_player_form;
    public double attack_display_player_hight;
    public List<String> attack_display_player_content;
    public String attack_display_player_decimal;
    public List<String> attack_display_player_conversion;

    public boolean player_top_display_enable;
    public boolean player_top_display_see_self;
    public double player_top_display_hight;
    public int player_top_display_refrsh;
    public List<String> player_top_display_content;

    public boolean monster_top_display_enable;
    public double monster_top_display_hight;
    public int pmonster_top_display_refrsh;
    public String monster_top_display_content;
    public List<String> monster_top_display_health_material;

    public boolean animal_top_display_enable;
    public double animal_top_display_hight;
    public int animal_top_display_refrsh;
    public String animal_top_display_content;
    public List<String> animal_top_display_health_material;

    public String isOP;
    public String reload;
    public String help;
    public List<String> helpinfo;

    public boolean boss_bar_enable;
    public String boss_bar_content;
    public int boss_bar_refrsh;
    public int boss_bar_time;
    public String boss_bar_color;
    public String boss_bar_style;

    public ConfigManager(CustomDisplay plugin){
        cd = plugin;

        readConfig();
        defaultConfig();
        attackDisplayConfig();
        entityTopDisplayConfig();
        languageConfig();
        boosBarConfig();

    }

    public void boosBarConfig(){
        boss_bar_enable = boos_bar_config.getBoolean("damage-bossbar-display.enable");
        boss_bar_content = boos_bar_config.getString("damage-bossbar-display.content");
        boss_bar_refrsh = boos_bar_config.getInt("damage-bossbar-display.refrsh");
        boss_bar_time = boos_bar_config.getInt("damage-bossbar-display.time");
        boss_bar_color = boos_bar_config.getString("damage-bossbar-display.color");
        boss_bar_style = boos_bar_config.getString("damage-bossbar-display.style");
    }

    public void readConfig(){
        new FolderConfigUtil();



        config = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        attack_config = ConfigMapManager.getFileConfigurationMap().get("AttackDisplay.yml");
        entity_top_config = ConfigMapManager.getFileConfigurationMap().get("EntityTopDisplay.yml");
        boos_bar_config = ConfigMapManager.getFileConfigurationMap().get("BoosBarDisplay.yml");
        language = ConfigMapManager.getFileConfigurationMap().get("Language_"+config.getString("Language")+".yml");

//        config = new ConfigUtil("resource\\config.yml").get();
//        attack_config = new ConfigUtil("resource\\AttackDisplay.yml").get();
//        entity_top_config = new ConfigUtil("resource\\EntityTopDisplay.yml").get();
//        action_bar_config = new ConfigUtil("resource\\ActionBarDisplay.yml").get();
//        boos_bar_config = new ConfigUtil("resource\\BoosBarDisplay.yml").get();
//        title_config = new ConfigUtil("resource\\TitleDisplay.yml").get();
//        new ConfigUtil("resource\\Character\\Example.yml");
//        language = new ConfigUtil("resource\\Language\\"+config.getString("Language")+".yml").get();

    }

    public void languageConfig(){
        isOP = language.getString("Language.Command.isOP");
        reload = language.getString("Language.Command.reload");
        help = language.getString("Language.Command.help.Description");
        helpinfo = language.getStringList("Language.Command.help.info");
    }

    public void entityTopDisplayConfig(){
        player_top_display_enable = entity_top_config.getBoolean("player-top.enable");
        player_top_display_see_self = entity_top_config.getBoolean("player-top.seeforself");
        player_top_display_hight = entity_top_config.getDouble("player-top.hight");
        player_top_display_refrsh = entity_top_config.getInt("player-top.refrsh");
        player_top_display_content = entity_top_config.getStringList("player-top.content");

        monster_top_display_enable = entity_top_config.getBoolean("monster-top.enable");
        monster_top_display_hight = entity_top_config.getDouble("monster-top.hight");
        pmonster_top_display_refrsh = entity_top_config.getInt("monster-top.refrsh");
        monster_top_display_content = entity_top_config.getString("monster-top.content");
        monster_top_display_health_material = entity_top_config.getStringList("monster-top.health-material");

        animal_top_display_enable = entity_top_config.getBoolean("animal-top.enable");
        animal_top_display_hight = entity_top_config.getDouble("animal-top.hight");
        animal_top_display_refrsh = entity_top_config.getInt("animal-top.refrsh");
        animal_top_display_content = entity_top_config.getString("animal-top.content");
        animal_top_display_health_material = entity_top_config.getStringList("animal-top.health-material");
    }

    public void attackDisplayConfig(){
        attack_display_player_enable = attack_config.getBoolean("player-damage.enable");
        attack_display_player_form = attack_config.getString("player-damage.form");
        attack_display_player_hight = attack_config.getDouble("player-damage.hight");
        attack_display_player_content = attack_config.getStringList("player-damage.content");
        attack_display_player_decimal = attack_config.getString("player-damage.decimal");
        attack_display_player_conversion = attack_config.getStringList("player-damage.damage-conversion");
    }
    public void defaultConfig(){
        config_language = config.getString("Language");
        config_attack_display = config.getBoolean("AttackDisplay");
        config_entity_top_display = config.getBoolean("EntityTopDisplay");
        config_boss_bar_display = config.getBoolean("BoosBarDisplay");
        config_title_display = config.getBoolean("TitleDisplay");
        config_action_bar_display = config.getBoolean("ActionBarDisplay");

    }
    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getAttack_config() {
        return attack_config;
    }

    public FileConfiguration getEntity_top_config() {
        return entity_top_config;
    }

    public FileConfiguration getAction_bar_config() {
        return action_bar_config;
    }

    public FileConfiguration getBoos_bar_config() {
        return boos_bar_config;
    }

    public FileConfiguration getTitle_config() {
        return title_config;
    }

    public FileConfiguration getLanguage() {
        return language;
    }
}
