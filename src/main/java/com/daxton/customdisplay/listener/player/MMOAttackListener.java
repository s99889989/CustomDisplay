package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import net.mmogroup.mmolib.MMOLib;
import net.mmogroup.mmolib.api.DamageType;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import net.mmogroup.mmolib.api.stat.StatMap;
import net.mmogroup.mmolib.comp.MythicMobsHook;
import net.mmogroup.mmolib.listener.AttackEffects;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import static net.mmogroup.mmolib.api.stat.SharedStat.*;
import static org.bukkit.attribute.Attribute.GENERIC_ATTACK_DAMAGE;
import static org.bukkit.entity.EntityType.ARMOR_STAND;
import static org.bukkit.entity.EntityType.MAGMA_CUBE;

public class MMOAttackListener extends AttackEffects implements Listener {

    private static final Random random = new Random();
    private double critCoefficient;
    private double spellCritCoefficient;

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;


    @Override
    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.HIGHEST
    )
    public void c(PlayerAttackEvent event) {

        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        target = event.getEntity();

        if(event.getData().getPlayer() instanceof Player){
            player = event.getData().getPlayer();
            playerUUID = player.getUniqueId();
            targetUUID = target.getUniqueId();
        }else {
            return;
        }

//        player.sendMessage("自身WEAPON_DAMAGE:"+event.getData().getStatMap().getStat(WEAPON_DAMAGE));
//        player.sendMessage("自身UNDEAD_DAMAGE:"+event.getData().getStatMap().getStat(UNDEAD_DAMAGE));
//        player.sendMessage("自身PVP:"+event.getData().getStatMap().getStat(PVP_DAMAGE));
//        player.sendMessage("自身PVE:"+event.getData().getStatMap().getStat(PVE_DAMAGE));
        /**基礎傷害**/
        double attack_damage = event.getData().getStatMap().getStat(ATTACK_DAMAGE);
        /**而外物理攻擊**/
        double physical_damage = event.getData().getStatMap().getStat(PHYSICAL_DAMAGE);
        /**最終傷害**/
        double final_damage = attack_damage+physical_damage+5;
        //player.sendMessage("自身攻擊:"+(final_damage));
        //player.sendMessage("傷害數字:"+event.getAttack().getDamage());
        if(event.getAttack().getDamage() > final_damage){
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
            playerData.runAction("~oncrit",event);
        }else {
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
            playerData.runAction("~onattack",event);
        }
        //MMOLib.plugin.getDamage().damage(this.player.getPlayer(), target, new AttackResult(sweep - event.getAttack().getDamage(), new DamageType[]{DamageType.SKILL, DamageType.PHYSICAL}));

//        StatMap stats = event.getData().getStatMap();
//
//        if (event.getAttack().hasType(DamageType.WEAPON) && random.nextDouble() <= stats.getStat("CRITICAL_STRIKE_CHANCE") / 100.0D) {
//            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
//            playerData.runAction("~oncrit",event);
//        }else if (event.getAttack().hasType(DamageType.SKILL) && random.nextDouble() <= stats.getStat("SPELL_CRITICAL_STRIKE_CHANCE") / 100.0D) {
//            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
//            playerData.runAction("~oncrit",event);
//        }else {
//            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
//            playerData.runAction("~onattack",event);
//        }

    }

}
