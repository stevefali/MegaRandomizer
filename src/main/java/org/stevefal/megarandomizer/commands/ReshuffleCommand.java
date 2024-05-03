package org.stevefal.megarandomizer.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;

// This command does not change the drops if nothing has changed at the exclusions
public class ReshuffleCommand
{
	public ReshuffleCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("reshuffle").executes(this::execute));
	}

	private int execute(CommandContext<CommandSourceStack> context) {
		final long seed = context.getSource().getServer().getWorldData().worldGenOptions().seed();
		final GameRules gameRules = context.getSource().getServer().getWorldData().getGameRules();
		final boolean excludeCreativeItems = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDECREATIVEITEMS);
		final boolean excludeSpawnEggs = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDESPAWNEGGS);
		final boolean excludeHeads = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDEHEADS);
		RandomDrops.shuffleItems(seed, excludeCreativeItems, excludeSpawnEggs, excludeHeads);

		context.getSource().sendSuccess(() -> Component.literal("Reshuffled the random drops."), true);
		return 1;
	}
}
