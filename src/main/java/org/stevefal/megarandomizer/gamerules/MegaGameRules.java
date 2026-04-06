package org.stevefal.megarandomizer.gamerules;

import net.minecraft.world.level.GameRules;

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



    public static void register() {
        RULE_DO_BLOCK_RANDOMDROPS = GameRules.register("doBlockRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_DO_ENTITY_RANDOMDROPS = GameRules.register("doEntityRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_DO_PLAYER_RANDOMDROPS = GameRules.register("doPlayerRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_EXCLUDE_CREATIVEITEMS = GameRules.register("excludeCreativeItems", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_EXCLUDE_SPAWNEGGS = GameRules.register("excludeSpawnEggs", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_EXCLUDE_HEADS = GameRules.register("excludeHeads", GameRules.Category.DROPS, GameRules.BooleanValue.create(false));

        RULE_DO_VOLATILE_DROPS = GameRules.register("doVolatileDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(false));

        RULE_DO_RANDOM_SPAWNS = GameRules.register("doRandomSpawns", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));

        RULE_EXCLUDE_BOSSES = GameRules.register("excludeBosses", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(false));



    }

}
