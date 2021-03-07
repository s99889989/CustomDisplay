package com.daxton.customdisplay.api;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bukkit.Color.fromRGB;
import static org.bukkit.Particle.REDSTONE;

public class EntityFind {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public EntityFind(){

    }

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

    /**獲得目視目標不包括自己**/
    public Entity getTarget(LivingEntity self,int radius) {
        //List<Entity> targetList = getNearbyEntities(self,self.getLocation(), radius);
        List<Entity> targetList = self.getNearbyEntities(10, 10, 10);
        ArrayList<Entity> nearPlayers = new ArrayList<>();
        if(!(targetList.isEmpty())){
            for (Entity livingEntity : targetList) {
                if(livingEntity instanceof LivingEntity){
                    nearPlayers.add(livingEntity);
                }
            }
        }

        Entity target = null;
        BlockIterator bItr = new BlockIterator(self, radius);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        while (bItr.hasNext()) {
            block = bItr.next();
            bx = block.getX();
            by = block.getY();
            bz = block.getZ();
            if(!(nearPlayers.isEmpty())){
                for (Entity e : nearPlayers) {
                    loc = e.getLocation();
                    ex = loc.getX();
                    ey = loc.getY();
                    ez = loc.getZ();
                    if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                        target = e;
                        break;
                    }
                }
            }
        }
        return target;
    }

    public static Entity getNearestEntityInSight(Player player, int range) {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
        ArrayList<Location> sight = new ArrayList<Location>();
        for (int i = 0;i<sightBlock.size();i++)
            sight.add(sightBlock.get(i).getLocation());
        for (int i = 0;i<sight.size();i++) {
            for (int k = 0;k<entities.size();k++) {
                if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
                    if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
                        if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
                            return entities.get(k);
                        }
                    }
                }
            }
        }
        return null; //Return null/nothing if no entity was found
    }




    /**目標不包括自己**/
    public List<Entity> getNearbyEntities(Entity self,Location l, int radius){
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        List<Entity> livingEntityList = new ArrayList<>();
        for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
            for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
                for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
                }
            }
        }
        Entity[] entities = radiusEntities.toArray(new Entity[radiusEntities.size()]);
        for(Entity entity : entities){
            if(entity instanceof LivingEntity & entity != self & !(entity instanceof ArmorStand) ){
                livingEntityList.add(entity);
            }
        }

        return livingEntityList;
    }

    /**獲得目視生命目標不包括自己**/
    public static LivingEntity getLivingTarget(LivingEntity self, int radius) {
        List<Entity> targetEntityList = self.getNearbyEntities(radius, radius, radius);
        LivingEntity target = null;
        if(targetEntityList.size() > 0){
            double min = radius+1;
            for(Entity targetEntity : targetEntityList){
                if(targetEntity instanceof LivingEntity){
                    Vector targetVector = targetEntity.getLocation().subtract(self.getLocation()).toVector();
                    double rad = targetVector.angle(self.getEyeLocation().getDirection());
                    if(rad < 0.2){
                        Location st = targetEntity.getLocation().subtract(self.getLocation());
                        double dd = Math.sqrt(Math.pow((st.getX()),2) + Math.pow((st.getY()),2) +Math.pow((st.getZ()),2));
                        if(dd <= radius && dd < min){
                            min = dd;
                            target = (LivingEntity) targetEntity;
                        }
                    }
                }
            }
        }
        return target;
    }

    /**獲得圓半徑目標**/
    public static List<LivingEntity> getRadiusLivingEntities(LivingEntity self,int radius) {
        List<Entity> targetEntityList = self.getNearbyEntities(radius, radius, radius);
        double sX = self.getLocation().getX();
        double sY = self.getLocation().getY();
        double sZ = self.getLocation().getZ();
        List<LivingEntity> livingEntityList = new ArrayList<>();
        if(targetEntityList.size() > 0){
            for(Entity targetEntity : targetEntityList){
                if(targetEntity instanceof LivingEntity){
                    double tX = targetEntity.getLocation().getX();
                    double tY = targetEntity.getLocation().getY();
                    double tZ = targetEntity.getLocation().getZ();
                    double dd = Math.sqrt(Math.pow((tX-sX),2) + Math.pow((tY-sY),2) +Math.pow((tZ-sZ),2));
                    if(dd <= radius){
                        livingEntityList.add((LivingEntity) targetEntity);
                    }
                }
            }
        }
        return livingEntityList;
    }

    /**判斷玩家方向**/
    public static String getCardinalDirection(LivingEntity livingEntity) {
        double rotation = (livingEntity.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return "北";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "北東";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "東";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "南東";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "南";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "南西";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "西";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "北西";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "北";
        } else {
            return null;
        }
    }


    public static String rpGetPlayerDirection(LivingEntity livingEntity){
        String dir = "";
        float y = livingEntity.getLocation().getYaw();
        if( y < 0 ){y += 360;}
        y %= 360;
        int i = (int)((y+8) / 22.5);
        if(i == 0){dir = "西";}
        else if(i == 1){dir = "西 北西";}
        else if(i == 2){dir = "北西";}
        else if(i == 3){dir = "北 北西";}
        else if(i == 4){dir = "北";}
        else if(i == 5){dir = "北 北東";}
        else if(i == 6){dir = "北東";}
        else if(i == 7){dir = "東 北東";}
        else if(i == 8){dir = "東";}
        else if(i == 9){dir = "東 南東";}
        else if(i == 10){dir = "南東";}
        else if(i == 11){dir = "南 南東";}
        else if(i == 12){dir = "南";}
        else if(i == 13){dir = "南 南西";}
        else if(i == 14){dir = "南西";}
        else if(i == 15){dir = "西 南西";}
        else {dir = "西";}
        return dir;
    }


}
