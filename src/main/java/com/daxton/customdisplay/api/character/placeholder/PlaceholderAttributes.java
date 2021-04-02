package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

public class PlaceholderAttributes {

    public PlaceholderAttributes(){

    }

    public static String valueOf(LivingEntity entity, String inputString){
        String outputString = "0";
        //<cd_attribute_max_health
        //CustomDisplay.getCustomDisplay().getLogger().info("屬性: "+inputString);
        if(inputString.toLowerCase().contains("max_health")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_MAX_HEALTH")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("follow_range")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_FOLLOW_RANGE")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("knockback_resistance")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_KNOCKBACK_RESISTANCE")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("movement_speed")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_MOVEMENT_SPEED")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("flying_speed")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_FLYING_SPEED")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("attack_damage")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_ATTACK_DAMAGE")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("attack_knockback")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_ATTACK_KNOCKBACK")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("attack_speed")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_ATTACK_SPEED")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("armor")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_ARMOR")).getValue());
            }catch (NullPointerException exception){

            }
            return outputString;
        }
        if(inputString.toLowerCase().contains("armor_toughness")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_ARMOR_TOUGHNESS")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("luck")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"GENERIC_LUCK")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("horse_jump_strength")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"HORSE_JUMP_STRENGTH")).getValue());
            }catch (NullPointerException exception){

            }
        }
        if(inputString.toLowerCase().contains("zombie_spawn_reinforcements")){
            try {
                outputString = String.valueOf(entity.getAttribute(Enum.valueOf(Attribute.class,"ZOMBIE_SPAWN_REINFORCEMENTS")).getValue());
            }catch (NullPointerException exception){

            }
        }

        return outputString;
    }

}
