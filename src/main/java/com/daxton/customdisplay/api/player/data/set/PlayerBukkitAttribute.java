package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import io.lumine.xikage.mythicmobs.utils.config.file.FileConfiguration;
import io.lumine.xikage.mythicmobs.utils.config.file.YamlConfiguration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;
import static org.bukkit.attribute.Attribute.GENERIC_MOVEMENT_SPEED;

public class PlayerBukkitAttribute {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerBukkitAttribute(){

    }

    public void addAttribute(Player player,String inherit,String operation,double addNumber,String attributeName){


        AttributeInstance attributeInstance = player.getAttribute(Enum.valueOf(Attribute.class,inherit));


        removeAttribute(player,inherit);

        AttributeModifier healthModifier = new AttributeModifier("customdisplay"+attributeName, addNumber, Enum.valueOf(AttributeModifier.Operation.class,operation));
        attributeInstance.addModifier(healthModifier);


        //player.sendMessage("值: "+attributeInstance.getValue());
//        if(inherit.contains("GENERIC_ATTACK_SPEED")){
//            player.sendMessage("攻擊速度"+attributeInstance.getValue());
//        }

//        player.saveData();
//        if(inherit.contains("GENERIC_MAX_HEALTH")){
//            for(AttributeModifier attributeModifier : attributeInstance.getModifiers()){
//
//                player.sendMessage(attributeModifier.getName()+" : "+attributeModifier.getAmount());
//            }
//            //double maxHealth = player.getAttribute(GENERIC_MAX_HEALTH).getValue();
//            //player.setHealth(maxHealth);
//        }
    }

    public void removeAttribute(Player player,String inherit){
        AttributeInstance attributeInstance = player.getAttribute(Enum.valueOf(Attribute.class,inherit));

        for(AttributeModifier attributeModifier : attributeInstance.getModifiers()){
            if(attributeModifier.toString().contains("customdisplay")){
                //player.sendMessage(attributeModifier.getName()+" : "+attributeModifier.getAmount());
                attributeInstance.removeModifier(attributeModifier);
            }
        }
    }


}
