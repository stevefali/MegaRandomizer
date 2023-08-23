package org.stevefal.megarandomizer.gamerules;

import net.minecraft.world.level.GameRules;

public class MegaGameRules {

    public static GameRules.Key<GameRules.BooleanValue> RULE_DOBLOCKRANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DOENTITYRANDOMDROPS;
    public static GameRules.Key<GameRules.BooleanValue> RULE_DOPLAYERRANDOMDROPS;




    public static void register() {
        RULE_DOBLOCKRANDOMDROPS = GameRules.register("doBlockRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_DOENTITYRANDOMDROPS = GameRules.register("doEntityRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));

        RULE_DOPLAYERRANDOMDROPS = GameRules.register("doPlayerRandomDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));


    }

}
