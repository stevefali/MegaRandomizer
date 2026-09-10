package org.stevefal.megarandomizer.megamobs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ForgeRegistries;
import org.stevefal.megarandomizer.megadata.MegaSavedData;
import org.stevefal.megarandomizer.megadata.MegaSavedDataAccess;

import java.util.*;

public class RandomSpawns {

    private static ArrayList<EntityType<?>> masterEntities;
    private static ArrayList<EntityType<?>> shuffledEntities;
    private static Map<EntityType<?>, EntityType<?>> entityMap;

    private static MegaSavedData megaSavedData;

    public static EntityType<?> getRandomizedEntityType(EntityType<?> vanillaEntityType) {
        if (masterEntities != null) {
            int index = masterEntities.indexOf(vanillaEntityType);

            if (index == -1) {
                return vanillaEntityType;
            } else {
                EntityType<?> randomizedEntityType = shuffledEntities.get(index);
                if (megaSavedData != null && randomizedEntityType != null) {
                    megaSavedData.setDiscoveredSpawnIfNew(
                            vanillaEntityType.getDescription().getString(),
                            randomizedEntityType.getDescription().getString()
                    );
                }
                return randomizedEntityType;
            }
        } else {
            return vanillaEntityType;
        }
    }


    public static void shuffleEntities(long gameSeed, boolean excludeBosses) {
        megaSavedData = MegaSavedDataAccess.getMegaSavedData();

        ArrayList<EntityType<?>> copiedEntities = new ArrayList<>(ForgeRegistries.ENTITY_TYPES.getValues());

        masterEntities = new ArrayList<>(copiedEntities.stream().filter(
                entityType -> !entityType.getCategory().equals(MobCategory.MISC)).toList());


        if (excludeBosses) {
            masterEntities.removeAll(Arrays.asList(bosses));
        }

        masterEntities.sort(Comparator.comparing(EntityType::toShortString));

        shuffledEntities = new ArrayList<>(masterEntities);
        Collections.shuffle(shuffledEntities, new Random(gameSeed));

        entityMap = new HashMap<>();
        for (int i = 0; i < shuffledEntities.size(); i++) {
            entityMap.put(shuffledEntities.get(i), masterEntities.get(i));
        }
    }

    public static EntityType<?> getVanillaEntityType(EntityType<?> randomizedEntityType) {
        if (entityMap != null) {
            return entityMap.getOrDefault(randomizedEntityType, randomizedEntityType);
        }
        return randomizedEntityType;
    }

    public static final EntityType<?>[] bosses = {
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDER_DRAGON,
            EntityType.GIANT,
            EntityType.WARDEN,
            EntityType.WITHER
    };

}
