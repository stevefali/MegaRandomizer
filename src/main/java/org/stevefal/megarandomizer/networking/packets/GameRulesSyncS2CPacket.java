package org.stevefal.megarandomizer.networking.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;

import java.util.function.Supplier;

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

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client side!
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_DOBLOCKRANDOMDROPS).set(isDoBlockRandomDrops, null);
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_DOENTITYRANDOMDROPS).set(isDoEntityRandomDrops, null);
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_DOPLAYERRANDOMDROPS).set(isDoPlayerRandomDrops, null);
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDECREATIVEITEMS).set(isExcludeCreativeItems, null);
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDESPAWNEGGS).set(isExcludeSpawnEggs, null);
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDEHEADS).set(isExcludeHeads, null);
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_DO_VOLATILE_DROPS).set(isDoVolatileDrops, null);
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_DO_RANDOM_SPAWNS).set(isDoRandomSpawns, null);
            Minecraft.getInstance().level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDE_BOSSES).set(isExcludeBosses, null);
        });
        return true;
    }

}
