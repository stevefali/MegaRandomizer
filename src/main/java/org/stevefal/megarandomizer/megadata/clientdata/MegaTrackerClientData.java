package org.stevefal.megarandomizer.megadata.clientdata;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class MegaTrackerClientData {

    private static final Map<String, String> discoveredDrops = new HashMap<>();
    private static final Map<String, String> discoveredSpawns = new HashMap<>();

    private static boolean isDoVolatile;

    public static void updateDrops(Map<String, String> updatedDrops) {
        discoveredDrops.putAll(updatedDrops);
    }

    public static void updateSpawns(Map<String, String> updatedSpawns) {
        discoveredSpawns.putAll(updatedSpawns);
    }

    public static Map<String, String> getDiscoveredDrops() {
        return discoveredDrops;
    }

    public static Map<String, String> getDiscoveredSpawns() {
        return discoveredSpawns;
    }

    public static void clearDrops() {
        discoveredDrops.clear();
    }

    public static void clearSpawns() {
        discoveredSpawns.clear();
    }

    public static boolean getIsDoVolatile() {
        return isDoVolatile;
    }

    public static void setIsDoVolatile(boolean doVolatile) {
        isDoVolatile = doVolatile;
    }


}
