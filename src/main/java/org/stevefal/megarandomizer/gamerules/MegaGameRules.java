package org.stevefal.megarandomizer.gamerules;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.stevefal.megarandomizer.megadata.MegaSavedDataAccess;
import org.stevefal.megarandomizer.megadrops.RandomDrops;
import org.stevefal.megarandomizer.megamobs.RandomSpawns;

import java.util.HashMap;
import java.util.Map;

public class MegaGameRules {

    public static GameRules.Key<GameRules.BooleanValue> RULE_DO_BLOCK_RANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DO_ENTITY_RANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DO_PLAYER_RANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDE_CREATIVEITEMS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDE_SPAWNEGGS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDE_HEADS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DO_VOLATILE_DROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DO_RANDOM_SPAWNS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDE_BOSSES;

    private static final Map<String, GameRules.Key<GameRules.BooleanValue>> megaRules = new HashMap<>();

    public static void register() {
//        RULE_DO_BLOCK_RANDOMDROPS = GameRules.register("doBlockRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
//
//        RULE_DO_ENTITY_RANDOMDROPS = GameRules.register("doEntityRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
//
//        RULE_DO_PLAYER_RANDOMDROPS = GameRules.register("doPlayerRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
//
//        RULE_EXCLUDE_CREATIVEITEMS = GameRules.register("excludeCreativeItems", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
//
//        RULE_EXCLUDE_SPAWNEGGS = GameRules.register("excludeSpawnEggs", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
//
//        RULE_EXCLUDE_HEADS = GameRules.register("excludeHeads", GameRules.Category.DROPS, GameRules.BooleanValue.create(false));
//
//        RULE_DO_VOLATILE_DROPS = GameRules.register("doVolatileDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(false));
//
//        RULE_DO_RANDOM_SPAWNS = GameRules.register("doRandomSpawns", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
//
//        RULE_EXCLUDE_BOSSES = GameRules.register("excludeBosses", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));

        RULE_DO_BLOCK_RANDOMDROPS = registerMegaRule(
                "doBlockRandomDrops",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.NORMAL
        );

        RULE_DO_ENTITY_RANDOMDROPS = registerMegaRule(
                "doEntityRandomDrops",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.NORMAL
        );

        RULE_DO_PLAYER_RANDOMDROPS = registerMegaRule(
                "doPlayerRandomDrops",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.NORMAL
        );

        RULE_EXCLUDE_CREATIVEITEMS = registerMegaRule(
                "excludeCreativeItems",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.DROP_EXCLUSION
        );

        RULE_EXCLUDE_SPAWNEGGS = registerMegaRule(
                "excludeSpawnEggs",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.DROP_EXCLUSION
        );

        RULE_EXCLUDE_HEADS = registerMegaRule(
                "excludeHeads",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.DROP_EXCLUSION
        );

        RULE_DO_VOLATILE_DROPS = registerMegaRule(
                "doVolatileDrops",
                GameRules.Category.DROPS,
                false,
                MegaGameRuleType.NORMAL
        );

        RULE_DO_RANDOM_SPAWNS = registerMegaRule(
                "doRandomSpawns",
                GameRules.Category.SPAWNING,
                true,
                MegaGameRuleType.NORMAL
        );

        RULE_EXCLUDE_BOSSES = registerMegaRule(
                "excludeBosses",
                GameRules.Category.SPAWNING,
                true,
                MegaGameRuleType.MOB_EXCLUSION
        );

    }

    public static GameRules.Key<GameRules.BooleanValue> getMegaGameRuleById(String ruleId) {
        return megaRules.get(ruleId);
    }

    private static GameRules.Key<GameRules.BooleanValue> registerMegaRule(
            String name,
            GameRules.Category category,
            boolean defaultValue,
            MegaGameRuleType megaGameRuleType) {

        GameRules.Key<GameRules.BooleanValue> megaRule = GameRules.register(
                name,
                category,
                GameRules.BooleanValue.create(
                        defaultValue, ((minecraftServer, booleanValue) -> {
                            onMegaRuleChanged(minecraftServer, booleanValue, name, megaGameRuleType);
                        })
                )
        );

        megaRules.put(megaRule.getId(), megaRule);
        return megaRule;
    }

    private static void onMegaRuleChanged(
            MinecraftServer minecraftServer,
            GameRules.BooleanValue value,
            String name,
            MegaGameRuleType megaGameRuleType) {

        minecraftServer.sendSystemMessage(Component.literal("MegaGameRule " + name + " set to " + value.get()));
        if (megaGameRuleType == MegaGameRuleType.DROP_EXCLUSION) {
            RandomDrops.shuffleItems(
                    minecraftServer.getWorldData().worldGenOptions().seed(),
                    minecraftServer.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDE_CREATIVEITEMS),
                    minecraftServer.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDE_SPAWNEGGS),
                    minecraftServer.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDE_HEADS)
            );
            MegaSavedDataAccess.getMegaSavedData().clearDrops();

        } else if (megaGameRuleType == MegaGameRuleType.MOB_EXCLUSION) {
            RandomSpawns.shuffleEntities(
                    minecraftServer.getWorldData().worldGenOptions().seed(),
                    minecraftServer.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDE_BOSSES)
            );
            MegaSavedDataAccess.getMegaSavedData().clearSpawns();
        }

    }


    enum MegaGameRuleType {
        NORMAL,
        DROP_EXCLUSION,
        MOB_EXCLUSION
    }

}
