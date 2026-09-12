package org.stevefal.megarandomizer.networking.packets.toclient;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.stevefal.megarandomizer.megadata.clientdata.MegaTrackerClientData;


public class TrackerClearSpawnsS2CPacket {

    public TrackerClearSpawnsS2CPacket() {
    }

    public TrackerClearSpawnsS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(CustomPayloadEvent.Context context) {
        // Client side
        context.enqueueWork(MegaTrackerClientData::clearSpawns);
        return true;
    }
}