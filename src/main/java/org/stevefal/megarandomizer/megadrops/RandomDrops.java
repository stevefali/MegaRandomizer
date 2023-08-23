package org.stevefal.megarandomizer.megadrops;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RandomDrops {

    private static ArrayList<Item> masterList;

    private static ArrayList<Item> shuffledList;


    public static ItemStack getRandomizedItem(ItemStack vanillaItem) {

        int index = masterList.indexOf(vanillaItem.getItem());

        if (index == -1) {
            return vanillaItem;
        } else {
            return shuffledList.get(index).getDefaultInstance();
        }
    }


    // This is called when the server is ready to go
    public static void shuffleItems(long gameSeed) {
        ArrayList<Item> copiedList = new ArrayList<>(ForgeRegistries.ITEMS.getValues());

        masterList = new ArrayList<>(copiedList);
        masterList.removeAll(Arrays.asList(excludeItems));
        shuffledList = new ArrayList<>(masterList);
        Collections.shuffle(shuffledList, new Random(gameSeed));
    }

    public static final Item[] excludeItems = {
            Items.AIR,
            Items.BEDROCK,
            Items.SPAWNER,
            Items.END_PORTAL_FRAME,
            Items.COMMAND_BLOCK,
            Items.BARRIER,
            Items.LIGHT,
            Items.REPEATING_COMMAND_BLOCK,
            Items.CHAIN_COMMAND_BLOCK,
            Items.STRUCTURE_VOID,
            Items.STRUCTURE_BLOCK,
            Items.JIGSAW,
            Items.ALLAY_SPAWN_EGG,
            Items.AXOLOTL_SPAWN_EGG,
            Items.BAT_SPAWN_EGG,
            Items.BEE_SPAWN_EGG,
            Items.BLAZE_SPAWN_EGG,
            Items.CAT_SPAWN_EGG,
            Items.CAMEL_SPAWN_EGG,
            Items.CAVE_SPIDER_SPAWN_EGG,
            Items.CHICKEN_SPAWN_EGG,
            Items.COD_SPAWN_EGG,
            Items.COW_SPAWN_EGG,
            Items.CREEPER_SPAWN_EGG,
            Items.DOLPHIN_SPAWN_EGG,
            Items.DONKEY_SPAWN_EGG,
            Items.DROWNED_SPAWN_EGG,
            Items.ELDER_GUARDIAN_SPAWN_EGG,
            Items.ENDER_DRAGON_SPAWN_EGG,
            Items.ENDERMAN_SPAWN_EGG,
            Items.ENDERMITE_SPAWN_EGG,
            Items.EVOKER_SPAWN_EGG,
            Items.FOX_SPAWN_EGG,
            Items.FROG_SPAWN_EGG,
            Items.GHAST_SPAWN_EGG,
            Items.GLOW_SQUID_SPAWN_EGG,
            Items.GOAT_SPAWN_EGG,
            Items.GUARDIAN_SPAWN_EGG,
            Items.HOGLIN_SPAWN_EGG,
            Items.HORSE_SPAWN_EGG,
            Items.HUSK_SPAWN_EGG,
            Items.IRON_GOLEM_SPAWN_EGG,
            Items.LLAMA_SPAWN_EGG,
            Items.MAGMA_CUBE_SPAWN_EGG,
            Items.MOOSHROOM_SPAWN_EGG,
            Items.MULE_SPAWN_EGG,
            Items.OCELOT_SPAWN_EGG,
            Items.PANDA_SPAWN_EGG,
            Items.PARROT_SPAWN_EGG,
            Items.PHANTOM_SPAWN_EGG,
            Items.PIG_SPAWN_EGG,
            Items.PIGLIN_SPAWN_EGG,
            Items.PIGLIN_BRUTE_SPAWN_EGG,
            Items.PILLAGER_SPAWN_EGG,
            Items.POLAR_BEAR_SPAWN_EGG,
            Items.PUFFERFISH_SPAWN_EGG,
            Items.RABBIT_SPAWN_EGG,
            Items.RAVAGER_SPAWN_EGG,
            Items.SALMON_SPAWN_EGG,
            Items.SHEEP_SPAWN_EGG,
            Items.SHULKER_SPAWN_EGG,
            Items.SILVERFISH_SPAWN_EGG,
            Items.SKELETON_SPAWN_EGG,
            Items.SKELETON_HORSE_SPAWN_EGG,
            Items.SLIME_SPAWN_EGG,
            Items.SNIFFER_SPAWN_EGG,
            Items.SNOW_GOLEM_SPAWN_EGG,
            Items.SPIDER_SPAWN_EGG,
            Items.SQUID_SPAWN_EGG,
            Items.STRAY_SPAWN_EGG,
            Items.STRIDER_SPAWN_EGG,
            Items.TADPOLE_SPAWN_EGG,
            Items.TRADER_LLAMA_SPAWN_EGG,
            Items.TROPICAL_FISH_SPAWN_EGG,
            Items.TURTLE_SPAWN_EGG,
            Items.VEX_SPAWN_EGG,
            Items.VILLAGER_SPAWN_EGG,
            Items.VINDICATOR_SPAWN_EGG,
            Items.WANDERING_TRADER_SPAWN_EGG,
            Items.WARDEN_SPAWN_EGG,
            Items.WITCH_SPAWN_EGG,
            Items.WITHER_SPAWN_EGG,
            Items.WITHER_SKELETON_SPAWN_EGG,
            Items.WOLF_SPAWN_EGG,
            Items.ZOGLIN_SPAWN_EGG,
            Items.ZOMBIE_SPAWN_EGG,
            Items.ZOMBIE_HORSE_SPAWN_EGG,
            Items.ZOMBIE_VILLAGER_SPAWN_EGG,
            Items.ZOMBIFIED_PIGLIN_SPAWN_EGG,
            Items.PLAYER_HEAD,
            Items.ZOMBIE_HEAD,
            Items.CREEPER_HEAD,
            Items.PIGLIN_HEAD,
            Items.COMMAND_BLOCK_MINECART,
            Items.DEBUG_STICK
    };

}
