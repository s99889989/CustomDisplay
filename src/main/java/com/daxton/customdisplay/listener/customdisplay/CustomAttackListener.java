package com.daxton.customdisplay.listener.customdisplay;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.character.Arithmetic;
import com.daxton.customdisplay.api.character.NumberUtil;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.sucy.skill.api.skills.Skill;
import com.sucy.skill.listener.ListenerUtil;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.util.Random;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class CustomAttackListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private int  r = (int)(Math.random()*10)+1;

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPhysicalDamage(EntityDamageByEntityEvent event){



        PhysicalDamageEvent e = new PhysicalDamageEvent(getDamager(event), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile);
        Bukkit.getPluginManager().callEvent(e);
        String targetUUIDString = e.getTarget().getUniqueId().toString();


        Player player = EntityFind.convertPlayer(e.getDamager());
        if(player != null){
            String playerUUIDString = player.getUniqueId().toString();
            File playerFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/"+playerUUIDString+".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
            String plysical = playerConfig.getString(playerUUIDString+".PhysicalAttackFormula");
            plysical = new StringConversion2(player,e.getTarget(),plysical,"Character").valueConv();
            int attackNumber = 0;
            try {
                double number = Arithmetic.eval(plysical);
                String numberDec = new NumberUtil(number,"#").getDecimalString();
                attackNumber = Integer.valueOf(numberDec);
            }catch (Exception exception){
                attackNumber = 0;
            }

//            int attack = 10;
//            int finalDamage = attack;
//            if(MobManager.getMobID_Map().get(targetUUIDString) != null){
//                String mobID = MobManager.getMobID_Map().get(targetUUIDString);
//                File file = new File(cd.getDataFolder(),"Mobs/Default.yml");
//                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
//                int attr = fileConfiguration.getInt(mobID+".Attributes_Earth");
//
//                finalDamage = attack*attr;
//            }
            event.setDamage(attackNumber);
        }else {
            event.setDamage(e.getDamage());
        }

        event.setCancelled(e.isCancelled());

    }

    public static LivingEntity getDamager(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity) {
            return (LivingEntity)event.getDamager();
        } else {
            if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile)event.getDamager();
                if (projectile.getShooter() instanceof LivingEntity) {
                    return (LivingEntity)projectile.getShooter();
                }
            }

            return null;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPhysicalDamageListener(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }

        double damageNumber = event.getFinalDamage();
        LivingEntity target = (LivingEntity) event.getEntity();
        Player player = EntityFind.convertPlayer(event.getDamager());
        if(player != null){

            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
            new PlayerTrigger(player).onAttack(player,target);
        }

    }


}
