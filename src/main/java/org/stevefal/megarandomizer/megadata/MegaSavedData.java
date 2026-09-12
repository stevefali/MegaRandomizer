package org.stevefal.megarandomizer.megadata;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerClearDropsS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerClearSpawnsS2CPacket;

import java.util.HashMap;
import java.util.Map;

public class MegaSavedData extends SavedData {

    private static final String DATA_NAME = "mega_saved_data";
    private static final String DROPS = "discoveredDrops";
    private static final String SPAWNS = "discoveredSpawns";

    public static final Factory<MegaSavedData> MEGA_SAVED_DATA_FACTORY =
            new Factory<>(MegaSavedData::new, MegaSavedData::load, DataFixTypes.LEVEL);

    private static MinecraftServer minecraftServer;

    private final Map<String, String> discoveredDrops = new HashMap<>();
    private final Map<String, String> discoveredSpawns = new HashMap<>();

    private static MegaSavedData load(CompoundTag compoundTag, HolderLookup.Provider registries) {
        MegaSavedData data = new MegaSavedData();

        CompoundTag dropsTag = compoundTag.getCompound(DROPS);
        CompoundTag spawnsTag = compoundTag.getCompound(SPAWNS);

        for (String key : dropsTag.getAllKeys()) {
            data.discoveredDrops.put(key, dropsTag.getString(key));
        }

        for (String key : spawnsTag.getAllKeys()) {
            data.discoveredSpawns.put(key, spawnsTag.getString(key));
        }

        return data;
    }


    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider registries) {

        CompoundTag dropsTag = compoundTag.getCompound(DROPS);
        CompoundTag spawnsTag = compoundTag.getCompound(SPAWNS);

        for (Map.Entry<String, String> entry : discoveredDrops.entrySet()) {
            dropsTag.putString(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : discoveredSpawns.entrySet()) {
            spawnsTag.putString(entry.getKey(), entry.getValue());
        }

        compoundTag.put(DROPS, dropsTag);
        compoundTag.put(SPAWNS, spawnsTag);

        return compoundTag;
    }

    protected static MegaSavedData get(MinecraftServer server) {
        minecraftServer = server;
        ServerLevel serverLevel = minecraftServer.getLevel(Level.OVERWORLD);

        return serverLevel.getDataStorage().computeIfAbsent(
                MEGA_SAVED_DATA_FACTORY,
                DATA_NAME
        );
    }

    public void setDiscoveredDropIfNew(String vanillaDrop, String randomizedDrop) {
        String drop = this.discoveredDrops.putIfAbsent(vanillaDrop, randomizedDrop);
        if (drop == null) {
            setDirty();
        }
    }

    public void setDiscoveredSpawnIfNew(String vanillaMob, String randomizedMob) {
        String spawn = this.discoveredSpawns.putIfAbsent(vanillaMob, randomizedMob);
        if (spawn == null) {
            setDirty();
        }
    }

    public Map<String, String> getDiscoveredDrops() {
        return this.discoveredDrops;
    }

    public Map<String, String> getDiscoveredSpawns() {
        return this.discoveredSpawns;
    }

    public void clearDrops() {
        discoveredDrops.clear();
        setDirty();
        for (ServerPlayer serverPlayer : minecraftServer.getPlayerList().getPlayers()) {
            MegaMessages.sendToPlayer(new TrackerClearDropsS2CPacket(), serverPlayer);
        }
    }

    public void clearSpawns() {
        discoveredSpawns.clear();
        setDirty();
        for (ServerPlayer serverPlayer : minecraftServer.getPlayerList().getPlayers()) {
            MegaMessages.sendToPlayer(new TrackerClearSpawnsS2CPacket(), serverPlayer);
        }
    }

}