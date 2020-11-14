package com.daxton.customdisplay.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.util.ConfigUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    CustomDisplay customDisplay;

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

    public String action_bar_content;
    public int action_bar_refrsh;

    public String isOP;
    public String reload;
    public String help;
    public List<String> helpinfo;

    public int title_scale;
    public int title_delay;
    public int title_period;
    public String title_display_top0;
    public String title_display_down0;
    public int title_time0;

    public String title_display_top1;
    public String title_display_down1;
    public String title_display_sound1;
    public double title_sound_volume1;
    public double title_sound_pitch1;
    public long title_delay1;
    public int title_time1;

    public String title_display_top2;
    public String title_display_down2;
    public String title_display_sound2;
    public double title_sound_volume2;
    public double title_sound_pitch2;
    public long title_delay2;
    public int title_time2;

    public boolean boss_bar_enable;
    public String boss_bar_content;
    public int boss_bar_refrsh;
    public int boss_bar_time;
    public String boss_bar_color;
    public String boss_bar_style;

    public ConfigurationSection title_section;

    public ConfigManager(CustomDisplay plugin){
        customDisplay = plugin;

        readConfig();
        defaultConfig();
        attackDisplayConfig();
        entityTopDisplayConfig();
        actionBarConfig();
        languageConfig();
        titleDisplayConfig();
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

    public void titleDisplayConfig(){

        title_scale = title_config.getInt("Judge-Title.scale");
        title_delay = title_config.getInt("Judge-Title.delay");
        title_period = title_config.getInt("Judge-Title.period");
        title_display_top0 = title_config.getString("Judge-Title.title-display-top0");
        title_display_down0 = title_config.getString("Judge-Title.title-display-down0");
        title_time0 = title_config.getInt("Judge-Title.time0");

        title_display_top1 = title_config.getString("After-Judge-1.title-display-top1");
        title_display_down1 = title_config.getString("After-Judge-1.title-display-down1");
        title_display_sound1 = title_config.getString("After-Judge-1.sound1");
        title_sound_volume1 = title_config.getDouble("After-Judge-1.volume1");
        title_sound_pitch1 = title_config.getDouble("After-Judge-1.pitch1");
        title_delay1 = title_config.getLong("After-Judge-1.delay1");
        title_time1 = title_config.getInt("After-Judge-1.time1");

        title_display_top2 = title_config.getString("After-Judge-2.title-display-top2");
        title_display_down2 = title_config.getString("After-Judge-2.title-display-down2");
        title_display_sound2 = title_config.getString("After-Judge-2.sound2");
        title_sound_volume2 = title_config.getDouble("After-Judge-2.volume2");
        title_sound_pitch2 = title_config.getDouble("After-Judge-2.pitch2");
        title_delay2 = title_config.getLong("After-Judge-2.delay2");
        title_time2 = title_config.getInt("After-Judge-2.time2");

        title_section = title_config.getConfigurationSection("");
    }

    public void readConfig(){

        config = new ConfigUtil("config.yml").get();
        attack_config = new ConfigUtil("AttackDisplay.yml").get();
        entity_top_config = new ConfigUtil("EntityTopDisplay.yml").get();
        action_bar_config = new ConfigUtil("ActionBarDisplay.yml").get();
        boos_bar_config = new ConfigUtil("BoosBarDisplay.yml").get();
        title_config = new ConfigUtil("TitleDisplay.yml").get();
        language = new ConfigUtil("Language\\"+config.getString("Language")+".yml").get();

    }

    public void languageConfig(){
        isOP = language.getString("Language.Command.isOP");
        reload = language.getString("Language.Command.reload");
        help = language.getString("Language.Command.help.Description");
        helpinfo = language.getStringList("Language.Command.help.info");
    }

    public void actionBarConfig(){
        action_bar_content = action_bar_config.getString("action-bar-disply.content");
        action_bar_refrsh = action_bar_config.getInt("action-bar-disply.refrsh");
    }

    public void entityTopDisplayConfig(){
        player_top_display_enable = entity_top_config.getBoolean("player-top.enable");
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
