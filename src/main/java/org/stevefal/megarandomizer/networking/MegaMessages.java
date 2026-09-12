package org.stevefal.megarandomizer.networking;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;
import org.stevefal.megarandomizer.MegaRandomizer;
import org.stevefal.megarandomizer.networking.packets.toclient.GameRulesSyncS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerClearDropsS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerClearSpawnsS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toclient.TrackerDataSyncS2CPacket;
import org.stevefal.megarandomizer.networking.packets.toserver.RequestGameRulesSyncC2SPacket;
import org.stevefal.megarandomizer.networking.packets.toserver.RequestTrackerDataSyncC2SPacket;
import org.stevefal.megarandomizer.networking.packets.toserver.SetGameRulesC2SPacket;

public class MegaMessages {

    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel netReg = ChannelBuilder
                .named(MegaRandomizer.MODID + "_messages")
                .networkProtocolVersion(1)
                .clientAcceptedVersions((s, a) -> true)
                .serverAcceptedVersions((s, a) -> true)
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
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(message, PacketDistributor.PLAYER.with(player));
    }
}
