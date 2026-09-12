package org.stevefal.megarandomizer.networking.packets.toserver;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.toclient.GameRulesSyncS2CPacket;


public class SetGameRulesC2SPacket {

    private final boolean isDoBlockRandomDrops;
    private final boolean isDoEntityRandomDrops;
    private final boolean isDoPlayerRandomDrops;
    private final boolean isExcludeCreativeItems;
    private final boolean isExcludeSpawnEggs;
    private final boolean isExcludeHeads;
    private final boolean isDoVolatileDrops;
    private final boolean isDoRandomSpawns;
    private final boolean isExcludeBosses;

    public SetGameRulesC2SPacket(
            boolean isDoBlocks,
            boolean isDoEntities,
            boolean isDoPlayer,
            boolean isExcludeCreativeItems,
            boolean isExcludeSpawnEggs,
            boolean isExcludeHeads,
            boolean isDoVolatileDrops,
            boolean isDoRandomSpawns,
            boolean isExcludeBosses
    ) {
        this.isDoBlockRandomDrops = isDoBlocks;
        this.isDoEntityRandomDrops = isDoEntities;
        this.isDoPlayerRandomDrops = isDoPlayer;
        this.isExcludeCreativeItems = isExcludeCreativeItems;
        this.isExcludeSpawnEggs = isExcludeSpawnEggs;
        this.isExcludeHeads = isExcludeHeads;
        this.isDoVolatileDrops = isDoVolatileDrops;
        this.isDoRandomSpawns = isDoRandomSpawns;
        this.isExcludeBosses = isExcludeBosses;
    }

    public SetGameRulesC2SPacket(FriendlyByteBuf buf) {
        this.isDoBlockRandomDrops = buf.readBoolean();
        this.isDoEntityRandomDrops = buf.readBoolean();
        this.isDoPlayerRandomDrops = buf.readBoolean();
        this.isExcludeCreativeItems = buf.readBoolean();
        this.isExcludeSpawnEggs = buf.readBoolean();
        this.isExcludeHeads = buf.readBoolean();
        this.isDoVolatileDrops = buf.readBoolean();
        this.isDoRandomSpawns = buf.readBoolean();
        this.isExcludeBosses = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isDoBlockRandomDrops);
        buf.writeBoolean(isDoEntityRandomDrops);
        buf.writeBoolean(isDoPlayerRandomDrops);
        buf.writeBoolean(isExcludeCreativeItems);
        buf.writeBoolean(isExcludeSpawnEggs);
        buf.writeBoolean(isExcludeHeads);
        buf.writeBoolean(isDoVolatileDrops);
        buf.writeBoolean(isDoRandomSpawns);
        buf.writeBoolean(isExcludeBosses);
    }


    public boolean handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            // Server side
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            // Set the rules
            level.getGameRules().getRule(MegaGameRules.RULE_DO_BLOCK_RANDOMDROPS).set(isDoBlockRandomDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_DO_ENTITY_RANDOMDROPS).set(isDoEntityRandomDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_DO_PLAYER_RANDOMDROPS).set(isDoPlayerRandomDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDE_CREATIVEITEMS).set(isExcludeCreativeItems, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDE_SPAWNEGGS).set(isExcludeSpawnEggs, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDE_HEADS).set(isExcludeHeads, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_DO_VOLATILE_DROPS).set(isDoVolatileDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_DO_RANDOM_SPAWNS).set(isDoRandomSpawns, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDE_BOSSES).set(isExcludeBosses, level.getServer());

            // Sync the data back to the client
            MegaMessages.sendToPlayer(new GameRulesSyncS2CPacket(
                    isDoBlockRandomDrops,
                    isDoEntityRandomDrops,
                    isDoPlayerRandomDrops,
                    isExcludeCreativeItems,
                    isExcludeSpawnEggs,
                    isExcludeHeads,
                    isDoVolatileDrops,
                    isDoRandomSpawns,
                    isExcludeBosses), player);
        });
        return true;
    }
}
