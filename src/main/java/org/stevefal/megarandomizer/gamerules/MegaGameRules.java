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

    public static GameRules.Key<GameRules.BooleanValue> RULE_DOBLOCKRANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DOENTITYRANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DOPLAYERRANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDECREATIVEITEMS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDESPAWNEGGS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDEHEADS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DO_VOLATILE_DROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DO_RANDOM_SPAWNS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDE_BOSSES;

    private static final Map<String, GameRules.Key<GameRules.BooleanValue>> megaRules = new HashMap<>();

    public static void register() {

        RULE_DOBLOCKRANDOMDROPS = registerMegaRule(
                "doBlockRandomDrops",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.NORMAL
        );

        RULE_DOENTITYRANDOMDROPS = registerMegaRule(
                "doEntityRandomDrops",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.NORMAL
        );

        RULE_DOPLAYERRANDOMDROPS = registerMegaRule(
                "doPlayerRandomDrops",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.NORMAL
        );

        RULE_EXCLUDECREATIVEITEMS = registerMegaRule(
                "excludeCreativeItems",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.DROP_EXCLUSION
        );

        RULE_EXCLUDESPAWNEGGS = registerMegaRule(
                "excludeSpawnEggs",
                GameRules.Category.DROPS,
                true,
                MegaGameRuleType.DROP_EXCLUSION
        );

        RULE_EXCLUDEHEADS = registerMegaRule(
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
                    minecraftServer.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDECREATIVEITEMS),
                    minecraftServer.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDESPAWNEGGS),
                    minecraftServer.getGameRules().getBoolean(MegaGameRules.RULE_EXCLUDEHEADS)
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
