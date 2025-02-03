package org.stevefal.megarandomizer.gamerules;

import net.minecraft.world.level.GameRules;

public class MegaGameRules {

    public static GameRules.Key<GameRules.BooleanValue> RULE_DOBLOCKRANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DOENTITYRANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DOPLAYERRANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDECREATIVEITEMS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDESPAWNEGGS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_EXCLUDEHEADS;



    public static void register() {
        RULE_DOBLOCKRANDOMDROPS = GameRules.register("doBlockRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_DOENTITYRANDOMDROPS = GameRules.register("doEntityRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_DOPLAYERRANDOMDROPS = GameRules.register("doPlayerRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_EXCLUDECREATIVEITEMS = GameRules.register("excludeCreativeItems", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_EXCLUDESPAWNEGGS = GameRules.register("excludeSpawnEggs", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_EXCLUDEHEADS = GameRules.register("excludeHeads", GameRules.Category.DROPS, GameRules.BooleanValue.create(false));

    }

}
