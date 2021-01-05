package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.skill.Skill;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class Placeholder {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String,String> entity_Map = new HashMap<>();

    public String change = "";

    public String notChange = "";

    public Placeholder(LivingEntity entity,String change){
        String uuidString = entity.getUniqueId().toString();
        this.notChange = change;
        this.change = change.replace("cd_","").replace("<","").replace(">","").replace(" ","");
        entity_Map.put("name",entity.getName());
        entity_Map.put("uuid",entity.getUniqueId().toString());
        entity_Map.put("hight",String.valueOf(entity.getHeight()));
        double nowH = entity.getHealth();
        entity_Map.put("nowhealth",String.valueOf(nowH));
        entity_Map.put("maxhealth",String.valueOf(entity.getAttribute(GENERIC_MAX_HEALTH).getValue()));
        entity_Map.put("type",entity.getType().toString());
        entity_Map.put("biome",entity.getLocation().getBlock().getBiome().toString());
        entity_Map.put("world",entity.getWorld().toString());
        entity_Map.put("loc_x",String.valueOf(entity.getLocation().getX()));
        entity_Map.put("loc_y",String.valueOf(entity.getLocation().getY()));
        entity_Map.put("loc_z",String.valueOf(entity.getLocation().getZ()));
        entity_Map.put("vec_x",String.valueOf(vectorX(entity)));
        entity_Map.put("vec_y",String.valueOf(vectorY(entity)));
        entity_Map.put("vec_z",String.valueOf(vectorZ(entity)));
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>") != null){
            entity_Map.put("last_chat",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>"));
        }
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>") != null){
            entity_Map.put("cast_command",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>"));
        }
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_attack_number>") != null){
            entity_Map.put("attack_number",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_attack_number>"));
        }
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_damaged_number>") != null){
            entity_Map.put("damaged_number",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_damaged_number>"));
        }
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>") != null){
            entity_Map.put("kill_mob_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>"));
        }
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>") != null){
            entity_Map.put("up_exp_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>"));
        }
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>") != null){
            entity_Map.put("down_exp_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>"));
        }
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>") != null){
            entity_Map.put("up_level_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>"));
        }
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>") != null){
            entity_Map.put("down_level_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>"));
        }


        if(entity instanceof Player){

            Player player = (Player) entity;

            File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

            File attrFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/attributes-stats.yml");
            FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(attrFilePatch);

            File eqmFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/equipment-stats.yml");
            FileConfiguration eqmConfig = YamlConfiguration.loadConfiguration(eqmFilePatch);

            String custom = this.change.replace("level_now_","").replace("level_max_","").replace("exp_now_","").replace("exp_max_","").replace("point_max_","").replace("point_last_","").replace("attr_point_","").replace("attr_stats_","").replace("eqm_stats_","");
            String class_name = playerConfig.getString(uuidString+".Class_Name");
            String player_race = playerConfig.getString(uuidString+".Player_Race");
            String player_body = playerConfig.getString(uuidString+".Player_Body");
            String player_attr_atk = playerConfig.getString(uuidString+".Player_Attribute_Attack");
            String player_attr_def = playerConfig.getString(uuidString+".Player_Attribute_Defense");
            int level_now = playerConfig.getInt(uuidString+".Level."+custom+"_now_level");
            int level_max = playerConfig.getInt(uuidString+".Level."+custom+"_max_level");
            int exp_now = playerConfig.getInt(uuidString+".Level."+custom+"_now_exp");
            int exp_max = playerConfig.getInt(uuidString+".Level."+custom+"_max_exp");
            int point_max = playerConfig.getInt(uuidString+".Point."+custom+"_max");
            int point_now = playerConfig.getInt(uuidString+".Point."+custom+"_last");
            int attr_point = playerConfig.getInt(uuidString+".Attributes_Point."+custom);
            String attr_stats = attrConfig.getString(uuidString+".Attributes_Stats."+custom);
            String attr_eqm = eqmConfig.getString(uuidString+".Equipment_Stats."+custom);
//            if(custom.contains("Attributes")){
//                cd.getLogger().info(custom+" : "+attr_eqm);
//            }
            if(class_name != null){
                entity_Map.put("class_name",class_name);
            }
            if(player_race != null){
                entity_Map.put("class_race",player_race);
            }
            if(player_body != null){
                entity_Map.put("class_body",player_body);
            }
            if(player_attr_atk != null){
                entity_Map.put("class_attr_attack",player_attr_atk);
            }
            if(player_attr_def != null){
                entity_Map.put("class_attr_defense",player_attr_def);
            }

            entity_Map.put("level_now_"+custom,String.valueOf(level_now));
            entity_Map.put("level_max_"+custom,String.valueOf(level_max));
            entity_Map.put("exp_now_"+custom,String.valueOf(exp_now));
            entity_Map.put("exp_max_"+custom,String.valueOf(exp_max));
            entity_Map.put("point_last_"+custom,String.valueOf(point_now));
            entity_Map.put("point_max_"+custom,String.valueOf(point_max));
            entity_Map.put("attr_point_"+custom,String.valueOf(attr_point));
            entity_Map.put("attr_stats_"+custom,attr_stats);
            entity_Map.put("eqm_stats_"+custom,attr_eqm);

        }

        if(Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
            if(PlaceholderManager.getMythicMobs_Level_Map().get(entity.getUniqueId()) != null){
                entity_Map.put("mythic_level", PlaceholderManager.getMythicMobs_Level_Map().get(entity.getUniqueId()));
            }
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>") != null){
                entity_Map.put("mythic_kill_mob_id", PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>"));
            }

            if(MobManager.getMobID_Map().get(uuidString) != null && this.change.contains("mythic_class_")){
                String mobID = MobManager.getMobID_Map().get(uuidString);
                File mobFile = new File(cd.getDataFolder(),"Mobs/"+mobID+".yml");
                FileConfiguration mobConfig = YamlConfiguration.loadConfiguration(mobFile);
                String custom = this.change.replace("mythic_class_","").replace("stats_","");
                int mob_level = mobConfig.getInt(mobID+".Mob_Level");
                String mob_race = mobConfig.getString(mobID+".Mob_Race");
                String mob_attr = mobConfig.getString(mobID+".Mob_Attribute");
                String mob_body = mobConfig.getString(mobID+".Mob_Body");
                String mob_type = mobConfig.getString(mobID+".Mob_Type");
                int mob_stats = mobConfig.getInt(mobID+".Attributes_Stats."+custom);
                entity_Map.put("mythic_class_level",String.valueOf(mob_level));
                entity_Map.put("mythic_class_race",mob_race);
                entity_Map.put("mythic_class_attribute",mob_attr);
                entity_Map.put("mythic_class_body",mob_body);
                entity_Map.put("mythic_class_type",mob_type);
                entity_Map.put("mythic_class_stats_"+custom,String.valueOf(mob_stats));
            }else {
                String custom = this.change.replace("mythic_class_","").replace("stats_","");
                entity_Map.put("mythic_class_level",String.valueOf(0));
                entity_Map.put("mythic_class_race","");
                entity_Map.put("mythic_class_attribute","");
                entity_Map.put("mythic_class_body","");
                entity_Map.put("mythic_class_type","");
                entity_Map.put("mythic_class_stats_"+custom,String.valueOf(0));
            }

        }


        if(this.change.toLowerCase().contains("actionbar_mmocore_spell") &entity instanceof Player & Bukkit.getServer().getPluginManager().getPlugin("MMOCore") != null){
            Player player = (Player) entity;
            if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR){
                if(MMOCoreActionBar(player) != null){
                    entity_Map.put("actionbar_mmocore_spell",MMOCoreActionBar(player));
                }
            }
        }


    }

    public String MMOCoreActionBar(Player player){
        StringBuilder str = new StringBuilder();

            PlayerData data = PlayerData.get(player);
            String ready = MMOCore.plugin.configManager.getSimpleMessage("casting.action-bar.ready", new String[0]).message();
            String onCooldown = MMOCore.plugin.configManager.getSimpleMessage("casting.action-bar.on-cooldown", new String[0]).message();
            String noMana = MMOCore.plugin.configManager.getSimpleMessage("casting.action-bar.no-mana", new String[0]).message();
            String split = MMOCore.plugin.configManager.getSimpleMessage("casting.split", new String[0]).message();
            for(int j = 0; j < data.getBoundSkills().size(); ++j) {
                Skill.SkillInfo skill = data.getBoundSkill(j);
                str.append(str.length() == 0 ? "" : split).append((onCooldown(data, skill) ? onCooldown.replace("{cooldown}", "" + data.getSkillData().getCooldown(skill) / 1000L) : (noMana(data, skill) ? noMana : ready)).replace("{index}", "" + (j + 1 + (data.getPlayer().getInventory().getHeldItemSlot() <= j ? 1 : 0))).replace("{skill}", data.getBoundSkill(j).getSkill().getName()));
            }


        return str.toString();
    }
    private boolean onCooldown(PlayerData data, Skill.SkillInfo skill) {
        return skill.getSkill().hasModifier("cooldown") && data.getSkillData().getCooldown(skill) > 0L;
    }
    private boolean noMana(PlayerData data, Skill.SkillInfo skill) {
        return skill.getSkill().hasModifier("mana") && skill.getModifier("mana", data.getSkillLevel(skill.getSkill())) > data.getMana();
    }

    public String getString(){
        String re = notChange;
        if(entity_Map.get(change) != null){
            re = entity_Map.get(change);
        }
        return re;
    }

    public double vectorX(LivingEntity livingEntity){
        double xVector = livingEntity.getLocation().getDirection().getX();
        double rxVector = 0;
        if(xVector > 0){
            rxVector = 1;
        }else{
            rxVector = -1;
        }
        return rxVector;
    }
    public double vectorY(LivingEntity livingEntity){
        double yVector = livingEntity.getLocation().getDirection().getY();
        double ryVector = 0;
        if(yVector > 0){
            ryVector = 1;
        }else{
            ryVector = -1;
        }
        return ryVector;
    }
    public double vectorZ(LivingEntity livingEntity){
        double zVector = livingEntity.getLocation().getDirection().getZ();
        double rzVector = 0;
        if(zVector > 0){
            rzVector = 1;
        }else{
            rzVector = -1;
        }
        return rzVector;
    }

}
