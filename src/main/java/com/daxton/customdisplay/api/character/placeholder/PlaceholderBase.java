package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.LivingEntity;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class PlaceholderBase {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderBase(){

    }

    public String valueOf(LivingEntity entity,LivingEntity target, String inputString){
        String outputString = "";
        String uuidString = entity.getUniqueId().toString();
        String tUUIDString = "";
        if(target != null){
            tUUIDString = target.getUniqueId().toString();
        }
        if(inputString.toLowerCase().contains("<cd_base_name")){
            outputString = entity.getName();
        }
        if(inputString.toLowerCase().contains("<cd_base_uuid")){
            outputString = entity.getUniqueId().toString();
        }
        if(inputString.toLowerCase().contains("<cd_base_height")){
            outputString = String.valueOf(entity.getHeight());
        }
        if(inputString.toLowerCase().contains("<cd_base_nowhealth")){
            outputString = String.valueOf(entity.getHealth());

        }
        if(inputString.toLowerCase().contains("<cd_base_maxhealth")){
            outputString = String.valueOf(entity.getAttribute(GENERIC_MAX_HEALTH).getValue());
        }
        if(inputString.toLowerCase().contains("<cd_base_type")){
            outputString = entity.getType().toString();
        }
        if(inputString.toLowerCase().contains("<cd_base_biome")){
            outputString = entity.getLocation().getBlock().getBiome().toString();
        }
        if(inputString.toLowerCase().contains("<cd_base_world")){
            outputString = entity.getWorld().toString();
        }
        if(inputString.toLowerCase().contains("<cd_base_loc_x")){
            outputString = String.valueOf(entity.getLocation().getX());
        }
        if(inputString.toLowerCase().contains("<cd_base_loc_y")){
            outputString = String.valueOf(entity.getLocation().getY());
        }
        if(inputString.toLowerCase().contains("<cd_base_loc_z")){
            outputString = String.valueOf(entity.getLocation().getZ());
        }
        if(inputString.toLowerCase().contains("<cd_base_vec_x")){
            outputString = String.valueOf(vectorX(entity));
        }
        if(inputString.toLowerCase().contains("<cd_base_vec_y")){
            outputString = String.valueOf(vectorY(entity));
        }
        if(inputString.toLowerCase().contains("<cd_base_vec_z")){
            outputString = String.valueOf(vectorZ(entity));
        }
        if(inputString.toLowerCase().contains("<cd_base_attack_number")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_attack_number>") != null){
                if(target != null){
                    if(PlaceholderManager.cd_Attack_Number.get(uuidString+tUUIDString) != null){
                        outputString = PlaceholderManager.cd_Attack_Number.get(uuidString+tUUIDString);
                    }else {
                        outputString = "null";
                    }
                }else {
                    outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_attack_number>");
                }

            }

        }
        if(inputString.toLowerCase().contains("<cd_base_damaged_number")){
            if(PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_damaged_number>") != null){
                outputString = PlaceholderManager.getCd_Placeholder_Map().get(uuidString+"<cd_damaged_number>");
            }
            cd.getLogger().info(outputString);
        }

        return outputString;
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
