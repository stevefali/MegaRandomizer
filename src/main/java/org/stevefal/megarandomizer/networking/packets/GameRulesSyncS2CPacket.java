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

    public GameRulesSyncS2CPacket(boolean isDoBlocks, boolean isDoEntities, boolean isDoPlayer, boolean isExcludeCreativeItems, boolean isExcludeSpawnEggs, boolean isExcludeHeads) {
        this.isDoBlockRandomDrops = isDoBlocks;
        this.isDoEntityRandomDrops = isDoEntities;
        this.isDoPlayerRandomDrops = isDoPlayer;
        this.isExcludeCreativeItems = isExcludeCreativeItems;
        this.isExcludeSpawnEggs = isExcludeSpawnEggs;
        this.isExcludeHeads = isExcludeHeads;
    }

    public GameRulesSyncS2CPacket(FriendlyByteBuf buf) {
        this.isDoBlockRandomDrops = buf.readBoolean();
        this.isDoEntityRandomDrops = buf.readBoolean();
        this.isDoPlayerRandomDrops = buf.readBoolean();
        this.isExcludeCreativeItems = buf.readBoolean();
        this.isExcludeSpawnEggs = buf.readBoolean();
        this.isExcludeHeads = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isDoBlockRandomDrops);
        buf.writeBoolean(isDoEntityRandomDrops);
        buf.writeBoolean(isDoPlayerRandomDrops);
        buf.writeBoolean(isExcludeCreativeItems);
        buf.writeBoolean(isExcludeSpawnEggs);
        buf.writeBoolean(isExcludeHeads);
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
        });
        return true;
    }

}
