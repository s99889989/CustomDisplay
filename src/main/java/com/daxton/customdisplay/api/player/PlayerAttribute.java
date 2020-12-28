package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerAttribute {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerAttribute(){

    }

    public void addAttribute(Player player,String inherit,String operation,double addNumber,String attributeName){


        AttributeInstance attributeInstance = player.getAttribute(Enum.valueOf(Attribute.class,inherit));

        for(AttributeModifier attributeModifier : attributeInstance.getModifiers()){
            if(attributeModifier.toString().contains(attributeName)){
                attributeInstance.removeModifier(attributeModifier);
            }
        }

        AttributeModifier healthModifier = new AttributeModifier(attributeName, addNumber, Enum.valueOf(AttributeModifier.Operation.class,operation));
        attributeInstance.addModifier(healthModifier);




//        for(AttributeModifier attributeModifier : attributeInstance.getModifiers()){
//            player.sendMessage(attributeModifier.getName()+" : "+attributeModifier.getAmount());
//        }

        player.saveData();

    }

    public void removeAttribute(Player player){
        Attribute maxHealth = Attribute.GENERIC_MAX_HEALTH;
        AttributeInstance playerMaxHealth = player.getAttribute(maxHealth);
        for(AttributeModifier attributeModifier : playerMaxHealth.getModifiers()){
            if(attributeModifier.getName().toLowerCase().contains("customdisplay")){
                playerMaxHealth.removeModifier(attributeModifier);
            }
            player.sendMessage(attributeModifier.getName());
        }
        cd.getLogger().info("血量: "+playerMaxHealth.getValue());
    }

}
