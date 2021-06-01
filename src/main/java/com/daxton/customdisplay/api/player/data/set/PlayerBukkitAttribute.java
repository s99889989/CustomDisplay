package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlayerBukkitAttribute {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerBukkitAttribute(){

    }

    //增加原有屬性再
    public static void addAttribute(LivingEntity player, String inherit, String operation, double addNumber, String attributeName){
        try {
            AttributeInstance attributeInstance = player.getAttribute(Enum.valueOf(Attribute.class,inherit));

            AttributeModifier healthModifier = new AttributeModifier("customdisplay"+attributeName, addNumber, Enum.valueOf(AttributeModifier.Operation.class,operation));
            attributeInstance.addModifier(healthModifier);
        }catch (IllegalArgumentException exception){

        }
    }

    //移除原有屬性再增加
    public static void removeAddAttribute(LivingEntity player, String inherit, String operation, double addNumber, String label){

        try {
            AttributeInstance attributeInstance = player.getAttribute(Enum.valueOf(Attribute.class,inherit));

            //移除指定標籤屬性
            removeAttribute(player,inherit, label);

            AttributeModifier healthModifier = new AttributeModifier("customdisplay"+label, addNumber, Enum.valueOf(AttributeModifier.Operation.class,operation));
            attributeInstance.addModifier(healthModifier);
        }catch (IllegalArgumentException exception){

        }

    }

    //清除指定標籤屬性
    public static void removeAttribute(LivingEntity player, String inherit, String label){
        try {
            AttributeInstance attributeInstance = player.getAttribute(Enum.valueOf(Attribute.class,inherit));
            if(attributeInstance != null){
                for(AttributeModifier attributeModifier : attributeInstance.getModifiers()){
                    if(attributeModifier.toString().contains("customdisplay"+label)){
                        //player.sendMessage(attributeModifier.getName()+" : "+attributeModifier.getAmount());
                        attributeInstance.removeModifier(attributeModifier);
                    }
                }
            }
        }catch (IllegalArgumentException exception){

        }


    }

    //清除某項屬性
    public static void removeTypeAttribute(LivingEntity livingEntity, String inherit){
        try {
            AttributeInstance attributeInstance = livingEntity.getAttribute(Enum.valueOf(Attribute.class,inherit));
            if(attributeInstance != null){
                for(AttributeModifier attributeModifier : attributeInstance.getModifiers()){
                    String attrName = attributeModifier.getName();
                    //CustomDisplay.getCustomDisplay().getLogger().info("包含: "+attrName);
                    if(attrName.contains("customdisplay")){
                        //player.sendMessage(attributeModifier.getName()+" : "+attributeModifier.getAmount());
                        attributeInstance.removeModifier(attributeModifier);
                    }
                }
            }
        }catch (IllegalArgumentException exception){

        }

    }

    public static void removeAllAttribute(Player player){
        removeTypeAttribute(player,"GENERIC_MAX_HEALTH");
        //removeAttribute(player,"GENERIC_FOLLOW_RANGE");
        removeTypeAttribute(player,"GENERIC_KNOCKBACK_RESISTANCE");
        removeTypeAttribute(player,"GENERIC_MOVEMENT_SPEED");
        //removeAttribute(player,"GENERIC_FLYING_SPEED");
        removeTypeAttribute(player,"GENERIC_ATTACK_DAMAGE");
        //removeAttribute(player,"GENERIC_ATTACK_KNOCKBACK");
        removeTypeAttribute(player,"GENERIC_ATTACK_SPEED");
        removeTypeAttribute(player,"GENERIC_ARMOR");
        removeTypeAttribute(player,"GENERIC_ARMOR_TOUGHNESS");
        removeTypeAttribute(player,"GENERIC_LUCK");
        //removeAttribute(player,"HORSE_JUMP_STRENGTH");
        //removeAttribute(player,"ZOMBIE_SPAWN_REINFORCEMENTS");
    }


}
