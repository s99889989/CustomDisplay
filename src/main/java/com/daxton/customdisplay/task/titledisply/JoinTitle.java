package com.daxton.customdisplay.task.titledisply;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinTitle {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BukkitRunnable bukkitRunnable;

    private BukkitRunnable bukkitRunnable1;

    private BukkitRunnable bukkitRunnable2;


    public JoinTitle(Player p) {

        double maxHealth =  p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        bukkitRunnable = new BukkitRunnable() {
            //int tickRun0;
            @Override
            public void run() {
                //tickRun0++;

                double nowHealth = p.getHealth();
                double scale = nowHealth*10/maxHealth;
                if (scale < cd.getConfigManager().title_scale) {
                    p.sendTitle(cd.getConfigManager().title_display_top0, cd.getConfigManager().title_display_down0, 1, cd.getConfigManager().title_time0, 1);
                    //p.sendMessage("測試"+tickRun0);
                    task1(p);

                }

            }
        };
        bukkitRunnable.runTaskTimer(cd,1,cd.getConfigManager().title_period);
    }

    public void task1(Player p){
        bukkitRunnable1 = new BukkitRunnable() {
            @Override
            public void run() {
                final float volume1 = (float) cd.getConfigManager().title_sound_volume1;
                final float pitch1 =  (float) cd.getConfigManager().title_sound_pitch1;
                //p.sendMessage("任務1");
                p.sendTitle(cd.getConfigManager().title_display_top1, cd.getConfigManager().title_display_down1, 1, 1, 1);
                p.getWorld().playSound(p.getLocation(), cd.getConfigManager().title_display_sound1, volume1, pitch1);
                task2(p);
                cancel();
            }
        };
        bukkitRunnable1.runTaskLater(cd, cd.getConfigManager().title_delay1);
    }

    public void task2(Player p){
        bukkitRunnable2 = new BukkitRunnable() {
            @Override
            public void run() {
                float volume2 = (float) cd.getConfigManager().title_sound_volume2;
                float pitch2 =  (float) cd.getConfigManager().title_sound_pitch2;
                //p.sendMessage("任務2");
                p.sendTitle(cd.getConfigManager().title_display_top2, cd.getConfigManager().title_display_down2, 1, 1, 1);
                p.getWorld().playSound(p.getLocation(), cd.getConfigManager().title_display_sound2, volume2, pitch2);
                cancel();
            }
        };
        bukkitRunnable2.runTaskLater(cd, cd.getConfigManager().title_delay2);
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }

    public BukkitRunnable getBukkitRunnable1() {
        return bukkitRunnable1;
    }

    public BukkitRunnable getBukkitRunnable2() {
        return bukkitRunnable2;
    }
}
