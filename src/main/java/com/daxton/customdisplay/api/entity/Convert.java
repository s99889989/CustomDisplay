package com.daxton.customdisplay.api.entity;

import org.bukkit.entity.*;

public class Convert {

    /**把一些攻擊轉換成玩家**/
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
        if(entity instanceof Fireball){
            if(((Fireball) entity).getShooter() instanceof Player){
                player = (Player) ((Fireball) entity).getShooter();
                return player;
            }
        }

        return player;
    }

    //把一些攻擊轉換成玩家 去掉一些目標
    public static Player convertPlayer2(Entity entity){
        Player player = null;
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
        if(entity instanceof Fireball){
            if(((Fireball) entity).getShooter() instanceof Player){
                player = (Player) ((Fireball) entity).getShooter();
                return player;
            }
        }

        return player;
    }

    /**把寵物殺死的目標轉換為玩家**/
    public static Player convertKillerPlayer(LivingEntity killer){
        Player player = null;
        if(killer instanceof Player){
            player = ((Player) killer).getPlayer();
            return player;
        }
        if(killer instanceof Parrot){
            Parrot parrot = (Parrot) killer;
            if(parrot.getOwner() != null && parrot.getOwner() instanceof Player){
                player = ((Player) parrot.getOwner()).getPlayer();
                return player;
            }
        }

        if(killer instanceof Cat){
            Cat cat = (Cat) killer;
            if(cat.getOwner() != null && cat.getOwner() instanceof Player){
                player = ((Player) cat.getOwner()).getPlayer();
                return player;
            }
        }
        if(killer instanceof Donkey){
            Donkey donkey = (Donkey) killer;
            if(donkey.getOwner() != null && donkey.getOwner() instanceof Player){
                player = ((Player) donkey.getOwner()).getPlayer();
                return player;
            }
        }
        if(killer instanceof Mule){
            Mule mule = (Mule) killer;
            if(mule.getOwner() != null && mule.getOwner() instanceof Player){
                player = ((Player) mule.getOwner()).getPlayer();
                return player;
            }
        }
        if(killer instanceof Llama){
            Llama llama = (Llama) killer;
            if(llama.getOwner() != null && llama.getOwner() instanceof Player){
                player = ((Player) llama.getOwner()).getPlayer();
                return player;
            }
        }
        if(killer instanceof ZombieHorse){

            ZombieHorse zombieHorse = (ZombieHorse) killer;
            if(zombieHorse.getOwner() != null && zombieHorse.getOwner() instanceof Player){
                player = ((Player) zombieHorse.getOwner()).getPlayer();
                return player;
            }
        }

        return player;
    }
    /**把一些攻擊轉換成生命體**/
    public static LivingEntity convertLivingEntity(Entity entity){
        LivingEntity livingEntity = null;
        if(entity instanceof LivingEntity){
            livingEntity = (LivingEntity) entity;
            return livingEntity;
        }
        if(entity instanceof TippedArrow){
            if(((TippedArrow) entity).getShooter() instanceof LivingEntity){
                livingEntity = (LivingEntity) ((TippedArrow) entity).getShooter();
                return livingEntity;
            }
        }
        if(entity instanceof WitherSkull){
            if(((WitherSkull) entity).getShooter() instanceof LivingEntity){
                livingEntity = (LivingEntity) ((WitherSkull) entity).getShooter();
                return livingEntity;
            }
        }
        if(entity instanceof Arrow){
            if(((Arrow) entity).getShooter() instanceof LivingEntity){
                livingEntity = (LivingEntity) ((Arrow) entity).getShooter();
                return livingEntity;
            }
        }
        if(entity instanceof ThrownPotion){
            if(((ThrownPotion) entity).getShooter() instanceof LivingEntity){
                livingEntity = (LivingEntity) ((ThrownPotion) entity).getShooter();
                return livingEntity;
            }
        }
        if(entity instanceof TNTPrimed){
            if(((TNTPrimed) entity).getSource() instanceof LivingEntity){
                livingEntity = (LivingEntity) ((TNTPrimed) entity).getSource();
                return livingEntity;
            }
        }
        if(entity instanceof Projectile){
            if(((Projectile) entity).getShooter() instanceof LivingEntity){
                livingEntity = (LivingEntity) ((Projectile) entity).getShooter();
                return livingEntity;
            }
        }
        if(entity instanceof Fireball){
            if(((Fireball) entity).getShooter() instanceof LivingEntity){
                livingEntity = (LivingEntity) ((Fireball) entity).getShooter();
                return livingEntity;
            }
        }

        return livingEntity;
    }

    /**CrackShot用轉換**/
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
