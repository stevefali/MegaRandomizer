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

            MegaMessages.sendToPlayer(new GameRulesSyncS2CPacket(level.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDECREATIVEITEMS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDESPAWNEGGS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDEHEADS)), player);

        });
        return true;
    }


}
