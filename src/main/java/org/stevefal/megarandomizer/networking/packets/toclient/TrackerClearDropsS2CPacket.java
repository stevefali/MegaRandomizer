package org.stevefal.megarandomizer.networking.packets.toclient;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.stevefal.megarandomizer.megadata.clientdata.MegaTrackerClientData;


public class TrackerClearDropsS2CPacket {

    public TrackerClearDropsS2CPacket() {
    }

    public TrackerClearDropsS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(CustomPayloadEvent.Context context) {
        // Client side
        context.enqueueWork(MegaTrackerClientData::clearDrops);
        return true;
    }

}