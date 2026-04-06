package org.stevefal.megarandomizer.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megamobs.IMegaMob;
import org.stevefal.megarandomizer.megamobs.RandomSpawns;

@Mixin(Mob.class)
public abstract class MobMixin implements IMegaMob {


    /**
     * Override the behavior in IForgeEntity to return the MobCategory of the mapped vanilla EntityType
     * so that the mob cap counts are satisfied as though the randomized mob is the vanilla one.
     */
    public MobCategory getClassification(boolean forSpawnCount) {
        Entity self = (Entity) (Object) this;
        if (self.getServer().getGameRules().getBoolean(MegaGameRules.RULE_DO_RANDOM_SPAWNS)) {
            if (forSpawnCount) {
                // TODO: Return mobCategory of original vanilla mob
                return RandomSpawns.getVanillaEntityType(self.getType()).getCategory();
            }
        }
        return self.getType().getCategory();
    }


    @Shadow
    private boolean persistenceRequired;

    @Unique
    @Override
    public void setMegaMobIsPersistenceRequired(boolean isPersistenceRequired) {
        this.persistenceRequired = isPersistenceRequired;
    }
}
