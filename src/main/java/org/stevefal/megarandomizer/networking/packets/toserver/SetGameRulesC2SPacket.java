package org.stevefal.megarandomizer.networking.packets.toserver;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.toclient.GameRulesSyncS2CPacket;


public class SetGameRulesC2SPacket {

    private final boolean ruleValue;
    private final String ruleId;

    public SetGameRulesC2SPacket(
            boolean ruleValue,
            String ruleId
    ) {
        this.ruleValue = ruleValue;
        this.ruleId = ruleId;
    }

    public SetGameRulesC2SPacket(FriendlyByteBuf buf) {
        this.ruleValue = buf.readBoolean();
        this.ruleId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(ruleValue);
        buf.writeUtf(ruleId);
    }


    public boolean handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            // Server side
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            level.getGameRules().getRule(MegaGameRules.getMegaGameRuleById(ruleId)).set(ruleValue, level.getServer());

            // Sync the data back to the client
            MegaMessages.sendToPlayer(
                    new GameRulesSyncS2CPacket(
                            level.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                            level.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                            level.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS),
                            level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDECREATIVEITEMS),
                            level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDESPAWNEGGS),
                            level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDEHEADS),
                            level.getGameRules().getBoolean(MegaGameRules.RULE_DO_VOLATILE_DROPS),
                            level.getGameRules().getBoolean(MegaGameRules.RULE_DO_RANDOM_SPAWNS),
                            level.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDE_BOSSES)
                    ), player
            );
        });
        return true;
    }

}
