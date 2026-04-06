package org.stevefal.megarandomizer.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;
import org.stevefal.megarandomizer.megamobs.RandomSpawns;

// This command does not change the drops if nothing has changed at the exclusions
public class ReshuffleCommand {
    public ReshuffleCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("reshuffle").executes(this::execute));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        final long seed = context.getSource().getServer().getWorldData().worldGenOptions().seed();
        final GameRules gameRules = context.getSource().getServer().getWorldData().getGameRules();
        final boolean excludeCreativeItems = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_CREATIVEITEMS);
        final boolean excludeSpawnEggs = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_SPAWNEGGS);
        final boolean excludeHeads = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_HEADS);
        final boolean excludeBosses = gameRules.getBoolean(MegaGameRules.RULE_EXCLUDE_BOSSES);

        RandomDrops.shuffleItems(seed, excludeCreativeItems, excludeSpawnEggs, excludeHeads);
        RandomSpawns.shuffleEntities(seed, excludeBosses);

        final boolean doRandomDrops = gameRules.getBoolean(MegaGameRules.RULE_DO_ENTITY_RANDOMDROPS) || gameRules.getBoolean(MegaGameRules.RULE_DO_BLOCK_RANDOMDROPS);
        final boolean doRandomSpawns = gameRules.getBoolean(MegaGameRules.RULE_DO_RANDOM_SPAWNS);


        context.getSource().sendSuccess(() -> Component.literal(getReshuffleSuccessMessage(doRandomDrops, doRandomSpawns)), true);
        return 1;
    }

    /**
     * Reduce user confusion about what the /reshuffle command does
     * by customizing the success message based on current rule configuration
     */
    public static String getReshuffleSuccessMessage(boolean doRandomDrops, boolean doRandomSpawns) {
        StringBuilder successMessage = new StringBuilder();
        if (doRandomDrops) {
            successMessage.append("Random drops");
            if (doRandomSpawns) {
                successMessage.append(" and spawns");
            }
        } else {
            if (doRandomSpawns) {
                successMessage.append("Random spawns");
            } else { // Neither randomization type is turned on, so just say they've both been shuffled.
                successMessage.append("Random drops and spawns");
            }
        }
        successMessage.append(" updated based on current game rules");

        return successMessage.toString();
    }
}
