package org.stevefal.megarandomizer.networking.packets.toclient;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.stevefal.megarandomizer.megadata.clientdata.MegaTrackerClientData;


import java.util.function.Supplier;

public class TrackerClearDropsS2CPacket {

    public TrackerClearDropsS2CPacket() {
    }

    public TrackerClearDropsS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        // Client side
        context.enqueueWork(MegaTrackerClientData::clearDrops);
        return true;
    }

}
