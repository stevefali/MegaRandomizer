package org.stevefal.megarandomizer.networking.packets.toclient;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.stevefal.megarandomizer.megadata.clientdata.MegaTrackerClientData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TrackerDataSyncS2CPacket {

    private final Map<String, String> discoveredDrops;
    private final Map<String, String> discoveredSpawns;
    private final boolean isDoVolatile;

    public TrackerDataSyncS2CPacket(
            Map<String, String> discoveredDrops,
            Map<String, String> discoveredSpawns,
            boolean isDoVolatile) {
        this.discoveredDrops = discoveredDrops;
        this.discoveredSpawns = discoveredSpawns;
        this.isDoVolatile = isDoVolatile;
    }

    public TrackerDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.discoveredDrops = buf.readMap(HashMap::new, FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf);
        this.discoveredSpawns = buf.readMap(HashMap::new, FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf);
        this.isDoVolatile = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(discoveredDrops, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeUtf);
        buf.writeMap(discoveredSpawns, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeUtf);
        buf.writeBoolean(isDoVolatile);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client side
            MegaTrackerClientData.updateDrops(discoveredDrops);
            MegaTrackerClientData.updateSpawns(discoveredSpawns);
            MegaTrackerClientData.setIsDoVolatile(isDoVolatile);
        });
        return true;
    }

}
