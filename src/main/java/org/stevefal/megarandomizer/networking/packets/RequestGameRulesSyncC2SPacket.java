package org.stevefal.megarandomizer.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;


public class RequestGameRulesSyncC2SPacket {

    public RequestGameRulesSyncC2SPacket() {

    }

    public RequestGameRulesSyncC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            // Server side
            ServerPlayer player = context.getSender();

            ServerLevel level = player.serverLevel();

            MegaMessages.sendToPlayer(new GameRulesSyncS2CPacket(level.getGameRules().getBoolean(MegaGameRules.RULE_DO_BLOCK_RANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DO_ENTITY_RANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DO_PLAYER_RANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDE_CREATIVEITEMS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDE_SPAWNEGGS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDE_HEADS)), player);

        });
        return true;
    }


}
