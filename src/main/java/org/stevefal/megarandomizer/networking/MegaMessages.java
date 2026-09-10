package org.stevefal.megarandomizer.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.stevefal.megarandomizer.MegaRandomizer;
import org.stevefal.megarandomizer.networking.packets.toclient.GameRulesSyncS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerClearDropsS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerClearSpawnsS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerDataSyncS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toserver.*;

public class MegaMessages {

    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel netReg = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MegaRandomizer.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = netReg;

        /* To Server */
        netReg.messageBuilder(RequestGameRulesSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestGameRulesSyncC2SPacket::new)
                .encoder(RequestGameRulesSyncC2SPacket::toBytes)
                .consumerMainThread(RequestGameRulesSyncC2SPacket::handle)
                .add();

        netReg.messageBuilder(SetGameRulesC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetGameRulesC2SPacket::new)
                .encoder(SetGameRulesC2SPacket::toBytes)
                .consumerMainThread(SetGameRulesC2SPacket::handle)
                .add();

        netReg.messageBuilder(RequestTrackerDataSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestTrackerDataSyncC2SPacket::new)
                .encoder(RequestTrackerDataSyncC2SPacket::toBytes)
                .consumerMainThread(RequestTrackerDataSyncC2SPacket::handle)
                .add();

        netReg.messageBuilder(TrackerClearDropsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TrackerClearDropsC2SPacket::new)
                .encoder(TrackerClearDropsC2SPacket::toBytes)
                .consumerMainThread(TrackerClearDropsC2SPacket::handle)
                .add();

        netReg.messageBuilder(TrackerClearSpawnsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TrackerClearSpawnsC2SPacket::new)
                .encoder(TrackerClearSpawnsC2SPacket::toBytes)
                .consumerMainThread(TrackerClearSpawnsC2SPacket::handle)
                .add();


        /* To Client */
        netReg.messageBuilder(GameRulesSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GameRulesSyncS2CPacket::new)
                .encoder(GameRulesSyncS2CPacket::toBytes)
                .consumerMainThread(GameRulesSyncS2CPacket::handle)
                .add();

        netReg.messageBuilder(TrackerDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TrackerDataSyncS2CPacket::new)
                .encoder(TrackerDataSyncS2CPacket::toBytes)
                .consumerMainThread(TrackerDataSyncS2CPacket::handle)
                .add();

        netReg.messageBuilder(TrackerClearDropsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TrackerClearDropsS2CPacket::new)
                .encoder(TrackerClearDropsS2CPacket::toBytes)
                .consumerMainThread(TrackerClearDropsS2CPacket::handle)
                .add();

        netReg.messageBuilder(TrackerClearSpawnsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TrackerClearSpawnsS2CPacket::new)
                .encoder(TrackerClearSpawnsS2CPacket::toBytes)
                .consumerMainThread(TrackerClearSpawnsS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
