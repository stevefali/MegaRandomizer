package org.stevefal.megarandomizer.networking.packets.toserver;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.stevefal.megarandomizer.megadata.MegaSavedData;
import org.stevefal.megarandomizer.megadata.MegaSavedDataAccess;

import java.util.function.Supplier;

public class TrackerClearSpawnsC2SPacket {

    public TrackerClearSpawnsC2SPacket() {
    }

    public TrackerClearSpawnsC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MegaSavedData megaSavedData = MegaSavedDataAccess.getMegaSavedData();
            megaSavedData.clearSpawns();
        });
        return true;
    }

}
