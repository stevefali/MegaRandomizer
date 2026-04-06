package org.stevefal.megarandomizer.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.stevefal.megarandomizer.gamerules.ClientSideRulesHolder;


public class GameRulesSyncS2CPacket {

    private final boolean isDoBlockRandomDrops;
    private final boolean isDoEntityRandomDrops;
    private final boolean isDoPlayerRandomDrops;
    private final boolean isExcludeCreativeItems;
    private final boolean isExcludeSpawnEggs;
    private final boolean isExcludeHeads;
    private final boolean isDoVolatileDrops;
    private final boolean isDoRandomSpawns;
    private final boolean isExcludeBosses;

    public GameRulesSyncS2CPacket(
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

    public GameRulesSyncS2CPacket(FriendlyByteBuf buf) {
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
            // Client side!
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_DO_BLOCK_RANDOMDROPS, isDoBlockRandomDrops);
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_DO_ENTITY_RANDOMDROPS, isDoEntityRandomDrops);
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_DO_PLAYER_RANDOMDROPS, isDoPlayerRandomDrops);
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_EXCLUDE_CREATIVEITEMS, isExcludeCreativeItems);
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_EXCLUDE_SPAWNEGGS, isExcludeSpawnEggs);
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_EXCLUDE_HEADS, isExcludeHeads);
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_DO_VOLATILE_DROPS, isDoVolatileDrops);
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_DO_RANDOM_SPAWNS, isDoRandomSpawns);
            ClientSideRulesHolder.setClientMegaRule(ClientSideRulesHolder.RULE_EXCLUDE_BOSSES, isExcludeBosses);
        });
        return true;
    }

}
