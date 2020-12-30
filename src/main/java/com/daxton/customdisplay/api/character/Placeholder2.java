package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.skill.Skill;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class Placeholder2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String,String> entity_Map = new ConcurrentHashMap<>();

    public String change = "";

    public String notChange = "";

    public Placeholder2(String change){

        this.notChange = change;
        this.change = change.replace("cd_other_math_","").replace("<","").replace(">","").replace(" ","");

        if(change.toLowerCase().contains("_math_random_")){

            String randomString = change.replace("<cd_other_math_random_","").replace(">","").replace(" ","");
            int randomNumber = Integer.valueOf(randomString);
            String randomString1 = String.valueOf((int)(Math.random()*randomNumber));
            entity_Map.put("random_"+randomString,randomString1);

        }


    }

    public Placeholder2(LivingEntity entity, String change){
        String uuidString = entity.getUniqueId().toString();
        this.notChange = change;
        this.change = change.replace("cd_","").replace("<","").replace(">","").replace(" ","");
        entity_Map.put("name",entity.getName());
        entity_Map.put("uuid",entity.getUniqueId().toString());
        entity_Map.put("hight",String.valueOf(entity.getHeight()));
        entity_Map.put("nowhealth",String.valueOf(entity.getHealth()));
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
        if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>") !=null){
            entity_Map.put("last_chat",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>"));
        }

        entity_Map.put("cast_command",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>"));
        entity_Map.put("attack_number",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_attack_number>"));
        entity_Map.put("damaged_number",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_damaged_number>"));
        entity_Map.put("kill_mob_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>"));

        entity_Map.put("up_exp_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>"));
        entity_Map.put("down_exp_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>"));
        entity_Map.put("up_level_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>"));
        entity_Map.put("down_level_type",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>"));

        if(entity instanceof Player){

            Player player = (Player) entity;

            File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
            String custom = this.change.replace("level_now_","").replace("level_max_","").replace("exp_now_","").replace("exp_max_","").replace("point_max_","").replace("point_last_","").replace("attr_point_","").replace("attr_stats_","");
            String class_name = playerConfig.getString(uuidString+".ClassName");
            int level_now = playerConfig.getInt(uuidString+".Level."+custom+"_now_level");
            int level_max = playerConfig.getInt(uuidString+".Level."+custom+"_max_level");
            int exp_now = playerConfig.getInt(uuidString+".Level."+custom+"_now_exp");
            int exp_max = playerConfig.getInt(uuidString+".Level."+custom+"_max_exp");
            int point_max = playerConfig.getInt(uuidString+".Point."+custom+"_max");
            int point_now = playerConfig.getInt(uuidString+".Point."+custom+"_last");
            int attr_point = playerConfig.getInt(uuidString+".AttributesPoint."+custom);
            int attr_stats = playerConfig.getInt(uuidString+".AttributesStats."+custom);


            entity_Map.put("class_name",class_name);
            entity_Map.put("level_now_"+custom,String.valueOf(level_now));
            entity_Map.put("level_max_"+custom,String.valueOf(level_max));
            entity_Map.put("exp_now_"+custom,String.valueOf(exp_now));
            entity_Map.put("exp_max_"+custom,String.valueOf(exp_max));
            entity_Map.put("point_last_"+custom,String.valueOf(point_now));
            entity_Map.put("point_max_"+custom,String.valueOf(point_max));
            entity_Map.put("attr_point_"+custom,String.valueOf(attr_point));
            entity_Map.put("attr_stats_"+custom,String.valueOf(attr_stats));


        }

        if(Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
            entity_Map.put("mythic_level", PlaceholderManager.getMythicMobs_Level_Map().get(entity.getUniqueId()));
            entity_Map.put("mythic_kill_mob_id", PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>"));
        }


        if(this.change.toLowerCase().contains("actionbar_mmocore_spell") &entity instanceof Player & Bukkit.getServer().getPluginManager().getPlugin("MMOCore") != null){
            Player player = (Player) entity;
            if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR){
                entity_Map.put("actionbar_mmocore_spell",MMOCoreActionBar(player));
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
