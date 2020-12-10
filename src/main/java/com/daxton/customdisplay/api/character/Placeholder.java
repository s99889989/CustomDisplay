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

    public Placeholder(LivingEntity entity,String change){
        this.notChange = change;
        this.change = change.replace("cd_target_","").replace("cd_self_","").replace("<","").replace(">","").replace(" ","");
        entity_Map.put("name",entity.getName());
        entity_Map.put("uuid",entity.getUniqueId().toString());
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

        entity_Map.put("mythic_level",ActionManager.getMythicMobs_Level_Map().get(entity.getUniqueId()));

        entity_Map.put("mmocore_actionbar",ActionManager.getMmocore_ActionBar_Map().get(entity.getUniqueId()));

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