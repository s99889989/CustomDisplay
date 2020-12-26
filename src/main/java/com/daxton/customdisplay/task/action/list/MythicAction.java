package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringFind;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.skills.Skill;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.*;

public class MythicAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Skill skill;

    private Player player;

    private List<Entity> EntityList = new ArrayList<>();
    private LivingEntity self = null;
    private Entity target = null;
    private String firstString = "";

    private String aims = "";
    private int distance = 1;
    private String skillName;

    private Optional<Skill> opt;

    public MythicAction( ){

    }

    public void setMythicAction(LivingEntity self, LivingEntity target, String firstString,String taskID){
        //cd.getLogger().info(firstString);
        this.self = self;
        this.target = target;
        setOther(firstString);
    }

    public void setOther(String firstString){
        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("skill=") || allString.toLowerCase().contains("s=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    skillName = strings[1];

                }
            }

            if(allString.toLowerCase().contains("@=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    aims = strings[1].replace(" ","");
                }else if(strings.length == 3){
                    aims = strings[1].replace(" ","");
                    try{
                        distance = Integer.valueOf(strings[2].replace(" ",""));
                    }catch (NumberFormatException exception){
                        cd.getLogger().info(strings[2]+"不是數字");
                    }
                }
            }
        }
        //cd.getLogger().info(skillName +"---"+aims+"---");
        if(target != null & aims.toLowerCase().contains("targetradius")){
            EntityList = getNearbyEntities2(target.getLocation(),distance);
        }else  if(target == null & aims.toLowerCase().contains("targetradius")){
            target = getTarget(self,distance);
            if(getTarget(self,distance) != null){
                EntityList = getNearbyEntities2(target.getLocation(),distance);
            }
        }else if(self !=null & aims.toLowerCase().contains("selfradius")){
            EntityList = getNearbyEntities(self.getLocation(),distance);
        }else if(target == null & aims.toLowerCase().contains("target")){
            if(getTarget(self,distance) != null){
                EntityList.add(getTarget(self,distance));
            }
        }else {
            EntityList.add(self);
        }

        opt = MythicMobs.inst().getSkillManager().getSkill(skillName);
        if(!(opt.isPresent())){
            return;
        }
        this.skill = opt.get();

        if(!(EntityList.isEmpty())){
//            for(Entity e : EntityList){
//                cd.getLogger().info("技能名稱:"+skillName+" 目標: "+aims+" 距離: "+distance +" 目標名稱: "+e.getName());
//            }
            useMythicAction();
        }

    }


    public void useMythicAction(){
//        for(Entity entity : EntityList){
//            MythicMobs.inst().getAPIHelper().castSkill(self, skill.getInternalName(), self, self.getOrigin(), Collections.singletonList(entity), null, 1.0F);
//        }
        MythicMobs.inst().getAPIHelper().castSkill(self, skill.getInternalName(), self, self.getOrigin(), EntityList, null, 1.0F);
        //MythicMobs.inst().getAPIHelper().castSkill(self, skill.getInternalName(), target, target.getEyeLocation(), Collections.singletonList(target), (Collection)null, 1.0F);
    }

    /**目標不包括自己**/
    public Entity getTarget(LivingEntity self,int radius) {
        List<Entity> targetList = getNearbyEntities(self.getLocation(), radius);
        //List<Entity> targetList = self.getNearbyEntities(10, 10, 10);
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

    /**目標不包括自己**/
    public List<Entity>  getNearbyEntities(Location l, int radius){
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

    /**目標包括自己**/
    public Entity getTarget2(LivingEntity self,int radius) {
        List<Entity> targetList = getNearbyEntities2(self.getLocation(), radius);
        //List<Entity> nearbyE = player.getNearbyEntities(20, 20, 20);
        ArrayList<Entity> nearPlayers = new ArrayList<>();
        for (Entity livingEntity : targetList) {
            if(livingEntity instanceof LivingEntity){
                nearPlayers.add(livingEntity);
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
        return target;
    }

    /**目標包括自己**/
    public List<Entity>  getNearbyEntities2(Location l, int radius){
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

}
