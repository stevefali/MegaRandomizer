package org.stevefal.megarandomizer.networking.packets.toserver;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadata.MegaSavedData;
import org.stevefal.megarandomizer.megadata.MegaSavedDataAccess;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerDataSyncS2CPacket;

import java.util.function.Supplier;

public class RequestTrackerDataSyncC2SPacket {

    public RequestTrackerDataSyncC2SPacket() {
    }

    public RequestTrackerDataSyncC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            MegaSavedData megaSavedData = MegaSavedDataAccess.getMegaSavedData();
            MegaMessages.sendToPlayer(
                    new TrackerDataSyncS2CPacket(
                            megaSavedData.getDiscoveredDrops(),
                            megaSavedData.getDiscoveredSpawns(),
                            level.getGameRules().getBoolean(
                                    MegaGameRules.RULE_DO_VOLATILE_DROPS)
                    ), player
            );
        });
        return true;
    }


}
