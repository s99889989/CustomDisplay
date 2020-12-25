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
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class Placeholder {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String,String> entity_Map = new HashMap<>();

    public String change = "";

    public String notChange = "";

    public Placeholder(String change){

        this.notChange = change;
        this.change = change.replace("cd_other_math_","").replace("<","").replace(">","").replace(" ","");

        if(change.toLowerCase().contains("_math_random_")){

            String randomString = change.replace("<cd_other_math_random_","").replace(">","").replace(" ","");
            int randomNumber = Integer.valueOf(randomString);
            String randomString1 = String.valueOf((int)(Math.random()*randomNumber));
            entity_Map.put("random_"+randomString,randomString1);

        }


    }

    public Placeholder(LivingEntity entity,String change){
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
        entity_Map.put("last_chat",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>"));
        entity_Map.put("cast_command",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>"));
        entity_Map.put("attack_number",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_attack_number>"));
        entity_Map.put("damaged_number",PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_damaged_number>"));

        entity_Map.put("mythic_level", PlaceholderManager.getMythicMobs_Level_Map().get(entity.getUniqueId()));


        if(this.change.toLowerCase().contains("actionbar_mmocore_spell") &entity instanceof Player & Bukkit.getPluginManager().isPluginEnabled("MMOCore")){
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
