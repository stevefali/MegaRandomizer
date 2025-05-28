package org.stevefal.megarandomizer.gamerules;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientSideRulesHolder {

    public static final String RULE_DO_BLOCK_RANDOMDROPS = "doBlockRandomDrops";
    public static final String RULE_DO_ENTITY_RANDOMDROPS = "doEntityRandomDrops";
    public static final String RULE_DO_PLAYER_RANDOMDROPS = "doPlayerRandomDrops";
    public static final String RULE_EXCLUDE_CREATIVEITEMS = "excludeCreativeItems";
    public static final String RULE_EXCLUDE_SPAWNEGGS = "excludeSpawnEggs";
    public static final String RULE_EXCLUDE_HEADS = "excludeHeads";


    private static Map<String, Boolean> clientMegaGameRules = new HashMap<>();


    public static boolean getClientMegaRule(String clientSideRule) {
        return clientMegaGameRules.getOrDefault(clientSideRule, true);
    }

    public static void setClientMegaRule(String rule, boolean value) {
        clientMegaGameRules.put(rule, value);
    }



}
