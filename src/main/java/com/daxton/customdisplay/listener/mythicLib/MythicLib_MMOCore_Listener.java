package com.daxton.customdisplay.listener.mythicLib;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;
import net.citizensnpcs.api.CitizensAPI;
import io.lumine.mythic.lib.api.event.PlayerAttackEvent;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

import static io.lumine.mythic.lib.api.stat.SharedStat.*;
import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class MythicLib_MMOCore_Listener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private UUID playerUUID;

    private UUID targetUUID;

    private double damageNumber = 0;

    private double damageNumberPAE = 0.0;

    private String damageType = "";


    @EventHandler(priority = EventPriority.MONITOR)
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }

        LivingEntity target = (LivingEntity) event.getEntity();
        damageNumber = event.getFinalDamage();
        player = Convert.convertPlayer(event.getDamager());
        if(player != null){

            String uuidString = player.getUniqueId().toString();
            String tUUIDString = target.getUniqueId().toString();

            playerUUID = player.getUniqueId();
            targetUUID = target.getUniqueId();


            /**基礎傷害**/
            double attack_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(ATTACK_DAMAGE);
            /**額外物理攻擊**/
            double physical_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(PHYSICAL_DAMAGE);
            physical_damage = (attack_damage/100)*physical_damage;
            /**爆擊增幅**/
            double physical_STRIKE_POWER = MMOPlayerData.get(playerUUID).getStatMap().getStat(CRITICAL_STRIKE_POWER);
            physical_STRIKE_POWER = (attack_damage+physical_damage)*((physical_STRIKE_POWER+180)/100);

            /**額外魔法攻擊**/
            PlayerData data = PlayerData.get(player);
            double magical_damage = data.getStats().getStat(StatType.MAGIC_DAMAGE);
            magical_damage = (magical_damage/100);

            /**魔法爆擊增幅**/
            double spell_CRITICAL_STRIKE_POWER = MMOPlayerData.get(playerUUID).getStatMap().getStat(SPELL_CRITICAL_STRIKE_POWER);
            spell_CRITICAL_STRIKE_POWER = (spell_CRITICAL_STRIKE_POWER/100) + 1.5;


            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,"Miss");
                PlayerTrigger.onPlayer(player, target, "~onatkmiss");
                return;
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,String.valueOf(damageNumber));
            }

            if(damageType.contains("PHYSICAL")){
                if(damageNumber > physical_STRIKE_POWER ){
                    PlayerTrigger.onPlayer(player, target, "~oncrit");
                }else {
                    PlayerTrigger.onPlayer(player, target, "~onattack");
                }
            }
            if(damageType.contains("MAGIC")){
                if(damageNumber > ((damageNumberPAE*magical_damage)*spell_CRITICAL_STRIKE_POWER)){
                    PlayerTrigger.onPlayer(player, target, "~onmcrit");
                }else {
                    PlayerTrigger.onPlayer(player, target, "~onmagic");
                }
            }

        }

    }

    @EventHandler
    public void c(PlayerAttackEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }

        damageNumberPAE = event.getAttack().getDamage();
        damageType = event.getAttack().getTypes().toString();
    }

}
