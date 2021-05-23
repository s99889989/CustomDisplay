package com.daxton.customdisplay.listener.customcore;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class MainAttack implements Listener {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    //攻擊分類
    @EventHandler(priority = EventPriority.LOW)
    public void onPhysicalDamage(EntityDamageByEntityEvent event){

        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }

        Entity damager = event.getDamager();
        double damagerNumber = event.getDamage();

        if(damager instanceof Player){
            Player player = (Player) event.getDamager();
            if(deBug()){
                //player.sendMessage("傷害判斷: "+damagerNumber);
                CustomDisplay.getCustomDisplay().getLogger().info("傷害條件判斷: "+damagerNumber);
            }


            //遠距離攻擊(倍率)
            if(String.valueOf(damagerNumber).contains(".3444")){
                SetAttack(event, "RANGE_MULTIPLY", "", player);
                return;
            }
            //遠距離攻擊(增加)
            if(String.valueOf(damagerNumber).contains(".3333")){
                SetAttack(event, "RANGE_ADD", "", player);
                return;
            }
            //遠距離攻擊(攻擊)
            if(String.valueOf(damagerNumber).contains(".3222")){
                SetAttack(event, "RANGE_ATTACK", "", player);
                return;
            }

            //魔法攻擊(倍率)
            if(String.valueOf(damagerNumber).contains(".2444")){
                SetAttack(event, "MAGIC_MULTIPLY", "", player);
                return;
            }
            //魔法攻擊(增加)
            if(String.valueOf(damagerNumber).contains(".2333")){
                SetAttack(event, "MAGIC_ADD", "", player);
                return;
            }
            //魔法攻擊(攻擊)
            if(String.valueOf(damagerNumber).contains(".2222")){
                SetAttack(event, "MAGIC_ATTACK", "", player);
                return;
            }
//            //魔法攻擊(限速)
//            if(String.valueOf(damagerNumber).contains(".2111")){
//                SetAttack(event, "MAGIC_SPEED", "", player);
//                return;
//            }

            //近距離攻擊(倍率)
            if(String.valueOf(damagerNumber).contains(".1333")){
                SetAttack(event, "MELEE_MULTIPLY", "", player);
                return;
            }
            //近距離攻擊(增加)
            if(String.valueOf(damagerNumber).contains(".1222")){
                SetAttack(event, "MELEE_ADD", "", player);
                return;
            }

            //判斷物品是否有取消攻擊
            if(attackCan(player)){
                event.setCancelled(true);
                return;
            }

            //近距離攻擊(攻擊)
            SetAttack(event, "MELEE_ATTACK", "", player);
        }

    }

    //攻擊重發
    public void SetAttack(EntityDamageByEntityEvent event, String damageType, String operate, Entity damager){
        PhysicalDamageEvent e = new PhysicalDamageEvent(damager, (LivingEntity) event.getEntity(), event.getFinalDamage(), event.getDamager() instanceof Projectile, damageType, operate);
        Bukkit.getPluginManager().callEvent(e);
        event.setDamage(e.getDamage());
        event.setCancelled(e.isCancelled());
    }
    //判斷物品是否有取消攻擊
    public boolean attackCan(Player player){
        boolean outB = false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(itemStack.getType() != Material.AIR){
            String disableAttack = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "DisableAttack"), PersistentDataType.STRING);
            if(disableAttack != null){
                outB = Boolean.parseBoolean(disableAttack);
            }
        }
        return outB;
    }

    public static boolean deBug(){
        return false;
    }

    //CustomDisplay.getCustomDisplay().getLogger().info("攻擊傷害: "+damagerNumber);
    //遠距離物理攻擊
//        if(damager instanceof Arrow){
//            if(((Arrow) damager).getShooter() instanceof Player){
//                SetAttack(event, "RANGE_PHYSICAL_ATTACK", "", event.getDamager());
//            }
//            if(((Arrow) damager).getShooter() instanceof Skeleton){
//                CustomDisplay.getCustomDisplay().getLogger().info("骷髏攻擊");
//            }
//            return;
//        }

}
