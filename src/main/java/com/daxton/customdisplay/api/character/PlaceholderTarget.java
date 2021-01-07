package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class PlaceholderTarget {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String anser = "";

    public PlaceholderTarget(){

    }

    public String getTargetPlaceholder(LivingEntity entity, String firstString){


        String key = firstString.replace("_target","").replace(">","");
        if(entity instanceof Player){
            Player player = ((Player) entity).getPlayer();
            anser = setPlayer(player,key);
        }else {
            anser = setOtherEntity(entity,key);
        }


        return anser;


    }

    public String setPlayer(Player player,String string){
        String playerAnser = "";
        String uuidString = player.getUniqueId().toString();
        File playerFile = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        File attrFile = new File(cd.getDataFolder(),"Players/"+uuidString+"/attributes-stats.yml");
        FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(attrFile);
        File eqmFile = new File(cd.getDataFolder(),"Players/"+uuidString+"/equipment-stats.yml");
        FileConfiguration eqmConfig = YamlConfiguration.loadConfiguration(eqmFile);

        if(string.toLowerCase().contains("<cd_class_")){
            String key = string.replace("<cd_class_","");
            if(string.toLowerCase().contains("<cd_class_attr_stats_")){
                playerAnser = setAttrStats(attrConfig,key,uuidString);
            }else if(string.toLowerCase().contains("<cd_class_eqm_stats_")){
                playerAnser = setEqmStats(eqmConfig,key,uuidString);
            }else {
                playerAnser = setClass(playerConfig,key,uuidString);
            }
        }else {
            playerAnser = setBasic(player,string);

        }

        return playerAnser ;
    }

    public String setBasic(LivingEntity entity,String string){
        String playerAnser = "";
        String uuidString = entity.getUniqueId().toString();
        if(string.toLowerCase().contains("<cd_name")){
            playerAnser = entity.getName();
        }
        if(string.toLowerCase().contains("<cd_uuid")){
            playerAnser = entity.getUniqueId().toString();
        }
        if(string.toLowerCase().contains("<cd_hight")){
            playerAnser = String.valueOf(entity.getHeight());
        }
        if(string.toLowerCase().contains("<cd_nowhealth")){
            playerAnser = String.valueOf(entity.getHealth());
        }
        if(string.toLowerCase().contains("<cd_maxhealth")){
            playerAnser = String.valueOf(entity.getAttribute(GENERIC_MAX_HEALTH).getValue());
        }
        if(string.toLowerCase().contains("<cd_type")){
            playerAnser = entity.getType().toString();
        }
        if(string.toLowerCase().contains("<cd_biome")){
            playerAnser = entity.getLocation().getBlock().getBiome().toString();
        }
        if(string.toLowerCase().contains("<cd_world")){
            playerAnser = entity.getWorld().toString();
        }
        if(string.toLowerCase().contains("<cd_loc_x")){
            playerAnser = String.valueOf(entity.getLocation().getX());
        }
        if(string.toLowerCase().contains("<cd_loc_y")){
            playerAnser = String.valueOf(entity.getLocation().getY());
        }
        if(string.toLowerCase().contains("<cd_loc_z")){
            playerAnser = String.valueOf(entity.getLocation().getZ());
        }
        if(string.toLowerCase().contains("<cd_vec_x")){
            playerAnser = String.valueOf(vectorX(entity));
        }
        if(string.toLowerCase().contains("<cd_vec_y")){
            playerAnser = String.valueOf(vectorY(entity));
        }
        if(string.toLowerCase().contains("<cd_vec_z")){
            playerAnser = String.valueOf(vectorZ(entity));
        }
        if(string.toLowerCase().contains("<cd_last_chat")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_last_chat>");
            }
        }
        if(string.toLowerCase().contains("<cd_cast_command")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_cast_command>");
            }
        }
        if(string.toLowerCase().contains("<cd_attack_number")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_attack_number>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_attack_number>");
            }
        }
        if(string.toLowerCase().contains("<cd_damaged_number")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_damaged_number>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_damaged_number>");
            }
        }
        if(string.toLowerCase().contains("<cd_kill_mob_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_kill_mob_type>");
            }
        }
        if(string.toLowerCase().contains("<cd_up_exp_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_exp_type>");
            }
        }
        if(string.toLowerCase().contains("<cd_down_exp_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_exp_type>");
            }
        }
        if(string.toLowerCase().contains("<cd_up_level_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_up_level_type>");
            }
        }
        if(string.toLowerCase().contains("<cd_down_level_type")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>") != null){
                playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_down_level_type>");
            }
        }
        return playerAnser;
    }

    public String setEqmStats(FileConfiguration eqmConfig,String key,String uuidString){

        String playerAnser = "null";
        String key2 = key.replace("eqm_stats_","");

        if(eqmConfig.getString(uuidString+".Equipment_Stats."+key2) != null){
            playerAnser = eqmConfig.getString(uuidString+".Equipment_Stats."+key2);
        }
        return playerAnser;
    }

    public String setAttrStats(FileConfiguration attrConfig,String key,String uuidString){
        String playerAnser = "null";
        String key2 = key.replace("attr_stats_","");
        if(attrConfig.getString(uuidString+".Attributes_Stats."+key2) != null){
            playerAnser = attrConfig.getString(uuidString+".Attributes_Stats."+key2);
        }
        return playerAnser;
    }

    public String setClass(FileConfiguration playerConfig,String key,String uuidString){
        String playerAnser = "";
        if(key.toLowerCase().contains("name")){
            if(playerConfig.getString(uuidString+".Class_Name") != null){
                playerAnser = playerConfig.getString(uuidString+".Class_Name");
            }
        }
        if(key.toLowerCase().contains("level_now_")){
            String key2 = key.replace("level_now_","");
            int level_now = playerConfig.getInt(uuidString+".Level."+key2+"_now_level");
            playerAnser = String.valueOf(level_now);
        }
        if(key.toLowerCase().contains("level_max_")){
            String key2 = key.replace("level_max_","");
            int level_max = playerConfig.getInt(uuidString+".Level."+key2+"_max_level");
            playerAnser = String.valueOf(level_max);
        }
        if(key.toLowerCase().contains("exp_now_")){
            String key2 = key.replace("exp_now_","");
            int exp_now = playerConfig.getInt(uuidString+".Level."+key2+"_now_exp");
            playerAnser = String.valueOf(exp_now);
        }
        if(key.toLowerCase().contains("exp_max_")){
            String key2 = key.replace("exp_max_","");
            int exp_max = playerConfig.getInt(uuidString+".Level."+key2+"_max_exp");
            playerAnser = String.valueOf(exp_max);
        }
        if(key.toLowerCase().contains("point_last_")){
            String key2 = key.replace("point_last_","");
            int point_last = playerConfig.getInt(uuidString+".Point."+key2+"_last");
            playerAnser = String.valueOf(point_last);
        }
        if(key.toLowerCase().contains("point_max_")){
            String key2 = key.replace("point_max_","");
            int point_max = playerConfig.getInt(uuidString+".Point."+key2+"_max");
            playerAnser = String.valueOf(point_max);
        }
        if(key.toLowerCase().contains("attr_point_")){
            String key2 = key.replace("attr_point_","");
            int attr_point = playerConfig.getInt(uuidString+".Attributes_Point."+key2);
            playerAnser = String.valueOf(attr_point);
        }
        return playerAnser;
    }



    public String setOtherEntity(LivingEntity entity ,String string){
        String playerAnser = "";
        String uuidString = entity.getUniqueId().toString();
        if(string.toLowerCase().contains("<cd_mythic_")){
            if(Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){

                if(string.toLowerCase().contains("<cd_mythic_level")){
                    if(PlaceholderManager.getMythicMobs_Level_Map().get(entity.getUniqueId()) != null){
                        playerAnser = PlaceholderManager.getMythicMobs_Level_Map().get(entity.getUniqueId());
                    }
                }
                if(string.toLowerCase().contains("<cd_mythic_kill_mob_id")){
                    if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>") != null){
                        playerAnser = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_mythic_kill_mob_id>");
                    }
                }
                if(string.toLowerCase().contains("<cd_mythic_class_")){

                    if(MobManager.getMobID_Map().get(uuidString) != null){

                        String mobID = MobManager.getMobID_Map().get(uuidString);

                        File mobFile = new File(cd.getDataFolder(),"Mobs/"+mobID+".yml");
                        FileConfiguration mobConfig = YamlConfiguration.loadConfiguration(mobFile);
                        if(string.toLowerCase().contains("<cd_mythic_class_level")){
                            int mob_level = mobConfig.getInt(mobID+".Mob_Level");
                            playerAnser = String.valueOf(mob_level);
                        }
                        if(string.toLowerCase().contains("<cd_mythic_class_race")){
                            playerAnser = mobConfig.getString(mobID+".Mob_Race");
                        }
                        if(string.toLowerCase().contains("<cd_mythic_class_attribute")){

                            playerAnser = mobConfig.getString(mobID+".Mob_Attribute");

                        }
                        if(string.toLowerCase().contains("<cd_mythic_class_body")){
                            playerAnser = mobConfig.getString(mobID+".Mob_Body");
                        }
                        if(string.toLowerCase().contains("<cd_mythic_class_type")){
                            playerAnser = mobConfig.getString(mobID+".Mob_Type");
                        }
                        if(string.toLowerCase().contains("<cd_mythic_class_stats_")){
                            String key = string.replace("<cd_mythic_class_stats_","");
                            int mob_stats = mobConfig.getInt(mobID+".Attributes_Stats."+key);
                            playerAnser = String.valueOf(mob_stats);
                        }


                    }else {
                        playerAnser = setBasic(entity,string);
                    }
                }



            }
        }else {

        }
        return playerAnser;
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
