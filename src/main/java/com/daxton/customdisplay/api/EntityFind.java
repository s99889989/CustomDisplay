package com.daxton.customdisplay.api;

import org.bukkit.entity.*;

public class EntityFind {

    public EntityFind(){

    }

    public static Player convertPlayer(Entity entity){
        Player player = null;
        if(entity instanceof Player){
            player = ((Player) entity).getPlayer();
            return player;
        }
        if(entity instanceof Arrow){
            if(((Arrow) entity).getShooter() instanceof Player){
                player = (Player) ((Arrow) entity).getShooter();
                return player;
            }
        }
        if(entity instanceof ThrownPotion){
            if(((ThrownPotion) entity).getShooter() instanceof Player){
                player = (Player) ((ThrownPotion) entity).getShooter();
                return player;
            }
        }
        if(entity instanceof TNTPrimed){
            if(((TNTPrimed) entity).getSource() instanceof Player){
                player = (Player) ((TNTPrimed) entity).getSource();
                return player;
            }
        }
        if(entity instanceof Projectile){
            if(((Projectile) entity).getShooter() instanceof Player){
                player = (Player) ((Projectile) entity).getShooter();
                return player;
            }
        }

        return player;
    }

    public static Player crackShotPlayer(Entity entity){
        Player player = null;
        if(entity instanceof Player){
            player = ((Player) entity).getPlayer();
            return player;
        }
//        if(entity instanceof Arrow){
//            if(((Arrow) entity).getShooter() instanceof Player){
//                player = (Player) ((Arrow) entity).getShooter();
//                return player;
//            }
//        }
//        if(entity instanceof ThrownPotion){
//            if(((ThrownPotion) entity).getShooter() instanceof Player){
//                player = (Player) ((ThrownPotion) entity).getShooter();
//                return player;
//            }
//        }
//        if(entity instanceof Projectile){
//            if(((Projectile) entity).getShooter() instanceof Player){
//                player = (Player) ((Projectile) entity).getShooter();
//                return player;
//            }
//        }

        return player;
    }

}
