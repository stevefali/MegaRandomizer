package org.stevefal.megarandomizer.megamobs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class RandomSpawns {

    private static ArrayList<EntityType<?>> masterEntities;
    private static ArrayList<EntityType<?>> shuffledEntities;


    public static EntityType<?> getRandomizedEntityType(EntityType<?> vanillaEntityType) {
        if (masterEntities != null) {
            int index = masterEntities.indexOf(vanillaEntityType);

            if (index == -1) {
                return vanillaEntityType;
            } else {
                return shuffledEntities.get(index);
            }
        } else {
            return vanillaEntityType;
        }
    }

    public static void shuffleEntities(long gameSeed, boolean excludeBosses) {
        ArrayList<EntityType<?>> copiedEntities = new ArrayList<>(ForgeRegistries.ENTITY_TYPES.getValues());

        masterEntities = new ArrayList<>(copiedEntities.stream().filter(
                entityType -> !entityType.getCategory().equals(MobCategory.MISC)).toList());

        // Remove problematic mobs
        masterEntities.removeAll(Arrays.asList(excludeEntityTypes));

        if (excludeBosses) {
            masterEntities.removeAll(Arrays.asList(bosses));
        }

        masterEntities.sort(Comparator.comparing(EntityType::toShortString));

        shuffledEntities = new ArrayList<>(masterEntities);
        Collections.shuffle(shuffledEntities, new Random(gameSeed));

    }

    public static EntityType<?> getVanillaEntityType(EntityType<?> randomizedEntityType) {
        if (shuffledEntities != null) {
            int index = shuffledEntities.indexOf(randomizedEntityType);
            if (index == -1) {
                return randomizedEntityType;
            } else {
                return masterEntities.get(index);
            }
        } else {
            return randomizedEntityType;
        }
    }


    public static final EntityType<?>[] excludeEntityTypes = {
            EntityType.CREAKING,
            EntityType.CREAKING_TRANSIENT
    };

    public static final EntityType<?>[] bosses = {
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDER_DRAGON,
            EntityType.GIANT,
            EntityType.WARDEN,
            EntityType.WITHER
    };

}
