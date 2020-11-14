package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.holographicdisplays.AnimalHD;
import com.daxton.customdisplay.task.holographicdisplays.MonsterHD;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HDMapManager {
    private static Map<UUID, PlayerHD> playerHDMap = new HashMap<>();

    private static Map<UUID, MonsterHD> monsterHDMap = new HashMap<>();

    private static Map<UUID, AnimalHD> animalHDMap = new HashMap<>();

    public static Map<UUID, PlayerHD> getPlayerHDMap() {
        return playerHDMap;
    }

    public static Map<UUID, MonsterHD> getMonsterHDMap() {
        return monsterHDMap;
    }

    public static Map<UUID, AnimalHD> getAnimalHDMap() {
        return animalHDMap;
    }

}
