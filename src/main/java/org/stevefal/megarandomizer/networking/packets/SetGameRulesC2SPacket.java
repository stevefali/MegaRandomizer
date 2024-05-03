package org.stevefal.megarandomizer.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;

import java.util.function.Supplier;

public class SetGameRulesC2SPacket {

    private final boolean isDoBlockRandomDrops;
    private final boolean isDoEntityRandomDrops;
    private final boolean isDoPlayerRandomDrops;
    private final boolean isExcludeCreativeItems;
    private final boolean isExcludeSpawnEggs;
    private final boolean isExcludeHeads;

    public SetGameRulesC2SPacket(boolean isDoBlocks, boolean isDoEntities, boolean isDoPlayer, boolean isExcludeCreativeItems, boolean isExcludeSpawnEggs, boolean isExcludeHeads) {
        this.isDoBlockRandomDrops = isDoBlocks;
        this.isDoEntityRandomDrops = isDoEntities;
        this.isDoPlayerRandomDrops = isDoPlayer;
        this.isExcludeCreativeItems = isExcludeCreativeItems;
        this.isExcludeSpawnEggs = isExcludeSpawnEggs;
        this.isExcludeHeads = isExcludeHeads;
    }

    public SetGameRulesC2SPacket(FriendlyByteBuf buf) {
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
            // Server side
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            // Set the rules
            level.getGameRules().getRule(MegaGameRules.RULE_DOBLOCKRANDOMDROPS).set(isDoBlockRandomDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_DOENTITYRANDOMDROPS).set(isDoEntityRandomDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_DOPLAYERRANDOMDROPS).set(isDoPlayerRandomDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDECREATIVEITEMS).set(isExcludeCreativeItems, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDESPAWNEGGS).set(isExcludeSpawnEggs, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_EXCLUDEHEADS).set(isExcludeHeads, level.getServer());

            // Sync the data back to the client
            MegaMessages.sendToPlayer(new GameRulesSyncS2CPacket(isDoBlockRandomDrops, isDoEntityRandomDrops, isDoPlayerRandomDrops, isExcludeCreativeItems, isExcludeSpawnEggs, isExcludeHeads), player);
        });
        return true;
    }

}
