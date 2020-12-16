package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.entity.LivingEntity;

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
        entity_Map.put("attack_number",ActionManager.getDamage_Number_Map().get(entity.getUniqueId()));

        entity_Map.put("mythic_level",ActionManager.getMythicMobs_Level_Map().get(entity.getUniqueId()));

        entity_Map.put("mmocore_actionbar_spell",ActionManager.getMmocore_ActionBar_Spell_Map().get(entity.getUniqueId()));
        entity_Map.put("mmocore_actionbar_stats",ActionManager.getMmocore_ActionBar_Stats_Map().get(entity.getUniqueId()));

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
