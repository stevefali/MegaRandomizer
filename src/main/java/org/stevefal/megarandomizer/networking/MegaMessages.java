package org.stevefal.megarandomizer.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.stevefal.megarandomizer.MegaRandomizer;
import org.stevefal.megarandomizer.networking.packets.GameRulesSyncS2CPacket;
import org.stevefal.megarandomizer.networking.packets.RequestGameRulesSyncC2SPacket;
import org.stevefal.megarandomizer.networking.packets.SetGameRulesC2SPacket;

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

        /* To Client */
        netReg.messageBuilder(GameRulesSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GameRulesSyncS2CPacket::new)
                .encoder(GameRulesSyncS2CPacket::toBytes)
                .consumerMainThread(GameRulesSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
