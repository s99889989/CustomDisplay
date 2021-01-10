package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.function.Consumer;

public class ParabolicAttack extends BukkitRunnable{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Location targetLocation;
    private Consumer<Location> display;
    private Vector vec;
    private int speed;
    private Location startLocation;
    private int j;

    protected static final Random random = new Random();

    private Hologram hologram;

    private LivingEntity self;

    private LivingEntity target;

    public ParabolicAttack(){

    }

    public void setParabolicAttack(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        if(target == null){
            this.target = (LivingEntity) new EntityFind().getTarget(self,10);

        }
        if(this.target != null){
            startParabolicAttack();
        }

    }

    public void startParabolicAttack(){


        self.getWorld().playSound(self.getLocation(), "start1", 1.0f, 1.0f);

        this.startLocation = self.getLocation().add(0.0, 2.0, 0.0);
        this.targetLocation = target.getLocation().add(0.0, target.getHeight() / 2.0, 0.0);

        hologram= HologramsAPI.createHologram(cd, this.startLocation);
        hologram.appendTextLine("\uF80B䂱\uF80B");

        this.display = (loc) -> { hologram.teleport(loc); };

        this.vec = randomVector(self);
        this.speed = 10;
        this.runTaskTimer(cd, 0L, 1L);
    }

    public void run() {
        j++;
        for(int k = 0; k < this.speed; ++k) {
            double c = Math.min(1.0D, (double) this.j / 40.0D);
            display.accept(this.startLocation.add(this.targetLocation.clone().subtract(this.startLocation).toVector().normalize().multiply(c).add(this.vec.clone().multiply(1.0D - c))));
        }
        if(j > 100 || startLocation.distanceSquared(targetLocation) < 0.8D){
            endRun();
            cancel();
        }
    }

    public void endRun(){
        target.getWorld().playSound(target.getLocation(), "attack2", 1.0f, 1.0f);
        new Damage().giveMagDamage(self,target,10);

        hologram.delete();
    }

    private Vector randomVector(LivingEntity livingEntity) {
        double a = Math.toRadians(livingEntity.getEyeLocation().getYaw() + 0.0f);
        Vector vector = new Vector(Math.cos(a += (double)(random.nextBoolean() ? 1 : -1) * (random.nextDouble() * 2.0 + 1.0) * 3.141592653589793 / 6.0), 0.8, Math.sin(a)).normalize().multiply(0.4);
        Vector vector2 = new Vector(0,0.1,0);
        return vector2;
    }


}
